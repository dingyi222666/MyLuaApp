package com.dingyi.myluaapp.editor.language.java

import com.dingyi.editor.language.textmate.TextMateBridgeLanguage
import com.dingyi.editor.language.textmate.TextMateGlobal
import com.dingyi.editor.language.textmate.theme.VSCodeTheme
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
            languageGrammarInputStream = FileInputStream(codeEditor.context.filesDir.path + "/res/textmate/java/java.tmLanguage.json")
        )
    }

    init {

        codeEditor.colorScheme = TextMateGlobal.loadTheme("light") {
            VSCodeTheme("light.json") {
                FileInputStream(codeEditor.context.filesDir.path + "/res/textmate/theme/light.json")
            }
        }
    }

}