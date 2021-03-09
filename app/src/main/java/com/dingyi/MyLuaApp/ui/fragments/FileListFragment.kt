package com.dingyi.MyLuaApp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.editor.EditorManager
import com.dingyi.MyLuaApp.databinding.FragmentFileListBinding
import com.dingyi.MyLuaApp.ui.adapters.FileListAdapter
import com.dingyi.MyLuaApp.utils.toFile
import kotlin.properties.Delegates

object FileListFragment: Fragment() {

    @SuppressLint("StaticFieldLeak")
    var binding:FragmentFileListBinding?=null;

    private var info: ProjectInfo?=null;
    private var fileListAdapter by Delegates.notNull<FileListAdapter>()
    private var lastFilePath=""
    var onCreateViewFunction:()->Unit={

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding=FragmentFileListBinding.inflate(inflater,container,false);
        onCreateViewFunction();
        return binding?.root
    }


    fun initView(editorManager: EditorManager, info: ProjectInfo) {
        binding?.let { it ->
            fileListAdapter= FileListAdapter(info)
            it.list.adapter = fileListAdapter
            it.list.layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
            fileListAdapter.onClickListener={ path->
                val file=("$lastFilePath/$path").toFile()
                if (path=="...") {
                    loadFileListData(info,lastFilePath.toFile().parentFile.path)
                }else if(file.isDirectory){
                    loadFileListData(info,file.path)
                }else {
                    editorManager.open(file.path)
                }
            }
            loadFileListData(info,editorManager.projectManager.getLastOpenPath()!!.toFile().parentFile.path)
        }

    }


    private fun loadFileListData(info:ProjectInfo,path:String) {
        lastFilePath=path
        val fileList=mutableListOf<String>()
        binding?.let { binding ->
            binding.title.text=path
            path.toFile().listFiles().sortedWith{ a,b->
                if (a.isDirectory) {
                    return@sortedWith -1
                }else if (b.isDirectory) {
                    return@sortedWith 1
                }else {
                    return@sortedWith a.name.compareTo(b.name)
                }
            }.forEach {
                fileList.add(it.path)
            }
        }

        if (info.path!=path) {
            fileList.add(0,"...")
        }

        fileListAdapter.addAll(fileList)

    }

}