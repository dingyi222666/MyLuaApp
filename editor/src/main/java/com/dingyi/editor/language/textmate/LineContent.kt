package com.dingyi.editor.language.textmate

/**
 * @author: dingyi
 * @date: 2021/10/10 16:43
 * @description:
 **/
class LineContent {

    private val bufferContent = mutableListOf<CharArray>()

    private val diffContent = mutableMapOf<Int, CharArray>()

    private val emptyCharArray = charArrayOf()

    private val defaultChar = { _: Int -> Char(1) }

    fun commitText(text: String): Boolean {
        var allDiffFlag = false
        diffContent.clear()
        text.reader().readLines().forEachIndexed { indexed, line ->
            val lineCharArray = line.toCharArray()
            val bufferCharArray = bufferContent.getOrNull(indexed) ?: emptyCharArray
            var diffFlag = false

            for (i in lineCharArray.indices) {
                if (bufferCharArray.size >= i) {
                    diffFlag = true
                    break
                }
                if (bufferCharArray.getOrElse(i, defaultChar) != lineCharArray.getOrElse(
                        i,
                        defaultChar
                    )
                ) {
                    diffFlag = true
                    break
                }

            }
            if (diffFlag) {
                bufferContent[indexed] = lineCharArray
                diffContent[indexed] = lineCharArray
                allDiffFlag = true
            }

        }
        return allDiffFlag
    }

    fun getDiffLines(): Map<Int, CharArray> {
        return diffContent
    }

    fun getLines():List<CharArray> {
        return bufferContent
    }

}