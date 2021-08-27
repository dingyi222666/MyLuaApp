package com.dingyi.editor.scheme

import io.github.rosemoe.editor.widget.EditorColorScheme

/**
 * @author: dingyi
 * @date: 2021/8/15 0:41
 * @description:
 **/
class SchemeLua: EditorColorScheme() {


    override fun applyDefault() {
        super.applyDefault()

        setColor(STRING,0xFFDD4488.toInt())
        setColor(COMMENT,0xFF3F7F5F.toInt())
        setColor(KEYWORD, 0xFFD040DD.toInt())
        setColor(NAME,0xFF2A40FF.toInt())
        setColor(LITERAL, 0xFF6080FF.toInt())
        setColor(OPERATOR,0xFF808080.toInt())
        setColor(GLOBAL,0xFF88AA88.toInt())
        setColor(LOCAL,0xFFAAAA88.toInt())
        setColor(LINE_NUMBER_BACKGROUND,getColor(WHOLE_BACKGROUND))
        setColor(ANNOTATION,0xff0f9f2f.toInt())
        setColor(AUTO_COMP_PANEL_CORNER,0xff000000.toInt())
    }

    companion object {
        const val STRING= 0xff53131
        const val NAME= 0xff64622
        const val GLOBAL=0xff54455
        const val LOCAL=0x25165255
    }
}