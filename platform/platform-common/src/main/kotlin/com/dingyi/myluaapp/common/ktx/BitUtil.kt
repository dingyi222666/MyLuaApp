package com.dingyi.myluaapp.common.ktx

import org.jetbrains.annotations.Contract
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

object BitUtil {
    @Contract(pure = true)
    fun isSet(value: Byte, mask: Byte): Boolean {
        assertOneBitMask(mask)

        return value and mask == mask
    }

    @Contract(pure = true)
    fun isSet(value: Int, mask: Int): Boolean {
        assertOneBitMask(mask)
        return value and mask == mask
    }

    @Contract(pure = true)
    operator fun set(value: Byte, mask: Byte, setBit: Boolean): Byte {
        assertOneBitMask(mask)
        return (if (setBit) value or mask else value and mask.inv()).toByte()
    }

    @Contract(pure = true)
    operator fun set(value: Int, mask: Int, setBit: Boolean): Int {
        assertOneBitMask(mask)
        return if (setBit) value or mask else value and mask.inv()
    }

    @Contract(pure = true)
    fun clear(value: Int, mask: Int): Int {
        return set(value, mask, false)
    }

    private fun assertOneBitMask(mask: Byte) {
        assertOneBitMask(mask.toLong() and 255L)
    }

    fun assertOneBitMask(mask: Int) {
        assertOneBitMask(mask.toLong() and 4294967295L)
    }

    private fun assertOneBitMask(mask: Long) {
        assert(mask and mask - 1L == 0L) {
            "Mask must have only one bit set, but got: " + java.lang.Long.toBinaryString(
                mask
            )
        }
    }
}
