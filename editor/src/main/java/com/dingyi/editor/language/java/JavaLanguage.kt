package com.dingyi.editor.language.java

import com.dingyi.editor.language.textmate.BaseTextMateTheme
import com.dingyi.editor.language.textmate.TextMateBridgeLanguage
import io.github.rosemoe.sora.widget.CodeEditor
import java.io.FileInputStream

/**
 * @author: dingyi
 * @date: 2021/10/10 18:25
 * @description:
 **/
class JavaLanguage(codeEditor: CodeEditor) :TextMateBridgeLanguage(codeEditor){
    override fun getLanguageConfig(): LanguageConfig {
        return LanguageConfig(
            language = "java",
            languagePath = "java.tmLanguage.json",
            languageInputStream = FileInputStream(codeEditor.context.filesDir.path+"/res/api/textmate/java.tmLanguage.json")
        )
    }

    init {
        codeEditor.colorScheme = BaseTextMateTheme {
            FileInputStream(codeEditor.context.filesDir.path+"/res/api/textmate/theme/light.css")
        }
    }

}