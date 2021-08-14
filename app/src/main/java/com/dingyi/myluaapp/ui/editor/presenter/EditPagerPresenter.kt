package com.dingyi.myluaapp.ui.editor.presenter

import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.database.service.ProjectService
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.fragment.EditPagerFragment
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2021/8/11 4:28
 * @description:
 **/
class EditPagerPresenter(
    private val fragment: EditPagerFragment,
    private val viewModel: MainViewModel
) : BasePresenter<MainViewModel>(viewModel, fragment.viewLifecycleOwner) {


    fun readCodeFile(useDataBase: Boolean = false) {

        fragment.lifecycleScope.launch {
            ProjectService.queryCodeFile(fragment.openPath)?.let {
                fragment.codeFile = it
                viewModel.projectConfig.value?.let { config ->
                    config.findCodeFileByPath(it.filePath)?.let { position ->
                        config.openFiles[position] = it
                    }
                }
                fragment.editorView.apply {
                    text = if (useDataBase) {
                        it.code
                    } else {
                        it.filePath.toFile().readText()
                    }
                    it.code= text.toString()
                    setSelection(it.openSelection)
                }
            }

            saveCodeFile(true)
        }


    }

    fun saveCodeFile(useDataBase: Boolean = false, block: () -> Unit = {}) {
        fragment.apply {
            val saveText = editorView.text.toString()

            codeFile.apply {
                openSelection = editorView.selectionEnd
                code = saveText
                update(codeFile.id.toLong())
            }

            if (!useDataBase) {
                codeFile.filePath.toFile().writeText(saveText)
            }

            block()

        }

    }


    fun checkFile() {
        fragment.apply {
            lifecycleScope.launch {
                val file = openPath.toFile()
                val name = file.name
                val text = file.readText()

                val tmp = if (text == editorView.text.toString())
                    name
                else
                    "*$name"

                viewModel.projectConfig.value?.run {
                    findCodeFileByPath(openPath)?.let {
                        viewModel.editPagerTabText[it]?.value = tmp
                    }
                }

            }
        }
    }


}