package com.dingyi.myluaapp.ui.editor.presenter

import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.database.service.ProjectService
import com.dingyi.myluaapp.ui.editor.MainViewModel
import com.dingyi.myluaapp.ui.editor.fragment.EditPagerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2021/8/11 4:28
 * @description:
 **/
class EditPagerPresenter(
    private val fragment: EditPagerFragment,
    private val viewModel: MainViewModel
) : BasePresenter<MainViewModel>(viewModel, fragment.viewLifecycleOwner) {


    fun readCodeFile() {
        if (!fragment.fileIsInitialized) {
            fragment.lifecycleScope.launch {
                ProjectService.queryCodeFile(fragment.openPath)?.let {
                    fragment.codeFile = it
                    fragment.editorView.apply {
                        text = it.filePath.toFile().readText()
                        setSelection(it.openSelection)
                    }
                }
            }
        }

    }

    fun saveCodeFile() {
        fragment.apply {
            val saveText = editorView.text.toString()
            lifecycleScope.launch {
                codeFile.apply {
                    openSelection = editorView.selectionEnd
                }
                withContext(Dispatchers.IO) {
                    codeFile.filePath.toFile().writeText(saveText)
                    codeFile.update(codeFile.id.toLong())
                }

            }
        }

    }

    fun checkFile() {
        fragment.apply {
            lifecycleScope.launch {
                val file = openPath.toFile()
                val name = file.name
                val text = withContext(Dispatchers.Default) { file.readText() }

                val tmp = withContext(Dispatchers.Default) {
                    if (text == editorView.text.toString())
                        name
                    else
                        "*$name"
                }


                viewModel.projectConfig.value?.run {
                    findCodeFileByPath(openPath)?.let {
                        viewModel.editPagerTabText[it]?.value = tmp
                    }
                }

            }
        }
    }


}