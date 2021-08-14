package com.dingyi.myluaapp.ui.editor.presenter

import androidx.fragment.app.Fragment
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.common.kts.isDirectory
import com.dingyi.myluaapp.common.kts.sortBySelf
import com.dingyi.myluaapp.common.kts.suffix
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.ui.editor.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/8/11 4:22
 * @description:
 **/
class FileListPresenter(
    fragment: Fragment,
    private val viewModel: MainViewModel
) : BasePresenter<MainViewModel>(
    viewModel,
    fragment.viewLifecycleOwner
) {

    private var imageData = mapOf(
        "lua,java,aly,gradle,xml,py" to R.drawable.ic_twotone_code_24,
        "default" to R.drawable.ic_twotone_insert_drive_file_24,
        "png,jpg,bmp" to R.drawable.ic_twotone_image_24,
        "dir" to R.drawable.ic_twotone_folder_24
    )

    private fun getImageResId(path: String): Int {
        val file = path.toFile()
        if (file.isDirectory)
            return imageData.getValue("dir")

        if (path == "...")
            return R.drawable.ic_twotone_undo_24


        imageData.forEach {
            if (it.key.lastIndexOf(file.suffix) != -1) {
                return it.value
            }
        }

        return imageData.getValue("default")
    }

    fun mapToPairList(it: List<String>): List<Pair<Int, String>> {
        return it.map {
            getImageResId(it) to it
        }
    }


    suspend fun refreshFileList(path: String) = withContext(Dispatchers.IO) {

        viewModel.projectConfig.value?.run {
            val list =
                path
                    .toFile()
                    .walk()
                    .maxDepth(1)
                    .toList()
                    .drop(1)
                    .sortBySelf()
                    .map { it.absolutePath }
                    .toMutableList()
                    .apply {
                        val openDir = path.toFile().absolutePath
                        val projectDir = projectPath.toFile().absolutePath
                        if (openDir != projectDir) {
                            add(0, "...")
                        }
                    }

            viewModel.fileList.postValue(list)
        }
    }

    fun loadFileList(it: String, block: (String) -> Unit) {
        val path = when {
            it == "..." -> {
                viewModel.fileListTitle.value?.run {
                    toFile().parentFile?.absolutePath
                }
            }
            it.toFile().isDirectory -> it
            it.toFile().isFile -> it
            else -> it
        }

        
        path?.let {
            block(path)
            it.toFile().isDirectory {
                updateProjectConfig(it)
                viewModel.fileListTitle.value = it
            }

        }


    }

    private fun updateProjectConfig(it: String) {
        viewModel.projectConfig.value?.run {
            lastOpenDir = it
            update(id.toLong())
        }

    }

}