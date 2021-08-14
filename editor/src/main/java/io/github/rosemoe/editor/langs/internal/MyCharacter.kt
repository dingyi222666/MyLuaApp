package io.github.rosemoe.editor.langs.internal

import java.util.*

/**
 * @author: dingyi
 * @date: 2021/8/14 22:15
 * @description:
 **/
object MyCharacter {

    init {
        initMapInternal()
    }

    /**
     * Compressed bit set for isJavaIdentifierStart()
     */
    private lateinit var bitsIsStart: IntArray

    /**
     * Compressed bit set for isJavaIdentifierPart()
     */
    private lateinit var bitsIsPart: IntArray

    /**
     * Get bit in compressed bit set
     *
     * @param values   Compressed bit set
     * @param bitIndex Target index
     * @return Boolean value at the index
     */
    private operator fun get(values: IntArray?, bitIndex: Int): Boolean {
        return values!![bitIndex / 32] and (1 shl bitIndex % 32) != 0
    }

    /**
     * Make the given position's bit true
     *
     * @param values   Compressed bit set
     * @param bitIndex Index of bit
     */
    private operator fun set(values: IntArray, bitIndex: Int) {
        values[bitIndex / 32] = values[bitIndex / 32] or (1 shl bitIndex % 32)
    }

    /**
     * Init maps
     *
     */
    @Deprecated("The class will be initialized automatically")
    fun initMap() {
        // Empty
    }

    /**
     * Init maps
     */
    private fun initMapInternal() {
        if (::bitsIsStart.isInitialized) {
            return
        }
        bitsIsPart = IntArray(2048)
        bitsIsStart = IntArray(2048)
        Arrays.fill(bitsIsPart, 0)
        Arrays.fill(bitsIsStart, 0)
        for (i in 0..65535) {
            if (Character.isJavaIdentifierPart(i.toChar())) {
                set(bitsIsPart, i)
            }
            if (Character.isJavaIdentifierStart(i.toChar())) {
                set(bitsIsStart, i)
            }
        }
    }

    /**
     * @param key Character
     * @return Whether a identifier part
     * @see Character.isJavaIdentifierPart
     */
    fun isJavaIdentifierPart(key: Int): Boolean {
        return get(bitsIsPart, key)
    }

    /**
     * @param key Character
     * @return Whether a identifier start
     * @see Character.isJavaIdentifierStart
     */
    fun isJavaIdentifierStart(key: Int): Boolean {
        return get(bitsIsStart, key)
    }

}