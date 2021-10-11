package com.dingyi.editor.language.java

import com.dingyi.editor.language.textmate.TextMateBridgeLanguage
import com.dingyi.editor.language.textmate.TextMateTheme
import io.github.rosemoe.sora.widget.CodeEditor
import java.io.FileInputStream

/**
 * @author: dingyi
 * @date: 2021/10/10 18:25
 * @description:
 **/
class JavaLanguage(codeEditor: CodeEditor) : TextMateBridgeLanguage(codeEditor) {
    override fun getLanguageConfig(): LanguageConfig {

        return LanguageConfig(
            language = "java",
            languagePath = "java.tmLanguage.json",
            languageInputStream = FileInputStream(codeEditor.context.filesDir.path + "/res/textmate/java.tmLanguage.json")
        )
    }

    init {

        codeEditor.colorScheme = TextMateTheme(codeEditor) {
            TextMateTheme.Builder.VSCodeTheme ("light.json") {
                FileInputStream(codeEditor.context.filesDir.path + "/res/textmate/theme/light.json")
            }
        }
    }

}