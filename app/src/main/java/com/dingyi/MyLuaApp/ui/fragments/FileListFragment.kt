package com.dingyi.MyLuaApp.ui.fragments


import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.base.BaseFragment
import com.dingyi.MyLuaApp.beans.ProjectInfo
import com.dingyi.MyLuaApp.core.editor.EditorManager
import com.dingyi.MyLuaApp.core.project.helper.ProjectFileHelper
import com.dingyi.MyLuaApp.databinding.FragmentFileListBinding
import com.dingyi.MyLuaApp.ui.adapters.FileListAdapter
import com.dingyi.MyLuaApp.utils.listSortFiles
import com.dingyi.MyLuaApp.utils.toFile
import kotlin.properties.Delegates

class FileListFragment(val mActivity: BaseActivity<*>) :
    BaseFragment<FragmentFileListBinding>(mActivity), ProjectFileHelper.RefreshCallBack {


    private var mFileListAdapter by Delegates.notNull<FileListAdapter>()
    private var mLastDirPath = ""
    private var mLastSelectPath = ""
    private val mProjectFileHelper = ProjectFileHelper(mActivity, this)
    private var mEditorManager by Delegates.notNull<EditorManager>()
    private var mProjectInfo by Delegates.notNull<ProjectInfo>()
    fun initView(editorManager: EditorManager, info: ProjectInfo) {
        viewBinding.let {
            mEditorManager = editorManager
            mProjectInfo = info
            mFileListAdapter = FileListAdapter(info)
            it.list.adapter = mFileListAdapter
            it.list.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

            mFileListAdapter.onLongClickListener = { path: String, view: View ->
                mLastSelectPath = "$mLastDirPath/$path"
                mProjectFileHelper.showFileSelectPopupMenu("$mLastDirPath/$path", view, true)
            }

            mFileListAdapter.onClickListener = { path ->
                val file = ("$mLastDirPath/$path").toFile()
                if (path == "...") {
                    loadFileListData(info, mLastDirPath.toFile().parentFile.path)
                } else if (file.isDirectory) {
                    loadFileListData(info, file.path)
                } else {
                    editorManager.open(file.path)
                }
            }
            loadFileListData(
                info,
                editorManager.mProjectManager.getLastOpenPath()!!.toFile().parentFile.path
            )
        }

    }


    private fun loadFileListData(info: ProjectInfo, path: String) {
        mLastDirPath = path
        val fileList = mutableListOf<String>()
        viewBinding?.let { binding ->
            binding.title.text = path
            path.toFile().listSortFiles().forEach {
                fileList.add(it.path)
            }
        }

        if (info.projectPath != path) {
            fileList.add(0, "...")
        }

        mFileListAdapter.addAll(fileList)

    }


    override fun getViewBindingClass(): Class<FragmentFileListBinding> {
        return FragmentFileListBinding::class.java
    }

    override fun initView(binding: FragmentFileListBinding?) {
        binding?.let {
            it.more.setOnClickListener { clickView ->
                mProjectFileHelper.showFileMorePopupMenu(clickView, it.title.text.toString())
            }
        }
    }


    override fun onRefresh(path: String) {
        if (path.toFile().isDirectory) {
            loadFileListData(mProjectInfo, path)
        } else {
            loadFileListData(mProjectInfo, mLastDirPath)

            //判断是否还存在

            if (mLastSelectPath.toFile().isFile && mLastSelectPath != path) {
                mEditorManager.replace(mLastSelectPath, path)
            } else if (!path.toFile().exists()) {
                mEditorManager.remove(mEditorManager.mProjectManager.getShortPath(path))
            }
        }
    }

}