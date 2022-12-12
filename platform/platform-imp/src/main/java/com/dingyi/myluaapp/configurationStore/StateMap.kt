package com.dingyi.myluaapp.configurationStore



import com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream
import com.intellij.util.ArrayUtil

import net.jpountz.lz4.LZ4FrameInputStream
import net.jpountz.lz4.LZ4FrameOutputStream

import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import java.util.concurrent.atomic.AtomicReferenceArray

private fun archiveState(state: JSONObject): BufferExposingByteArrayOutputStream {
    val byteOut = BufferExposingByteArrayOutputStream()
    LZ4FrameOutputStream(byteOut, LZ4FrameOutputStream.BLOCKSIZE.SIZE_256KB).use {
        serializeElementToBinary(state, it)
    }
    return byteOut
}

fun serializeElementToBinary(state: JSONObject, it: OutputStream) {
    it.write(state.toString(2).encodeToByteArray())
}

private fun unarchiveState(state: ByteArray): JSONObject {
    return LZ4FrameInputStream(ByteArrayInputStream(state)).use {
        deserializeElementFromBinary(it)
    }
}

fun deserializeElementFromBinary(it: InputStream):JSONObject {
    return JSONObject(it.readBytes().decodeToString())
}



private fun getNewByteIfDiffers(key: String, newState: Any, oldState: ByteArray): ByteArray? {
    val newBytes: ByteArray
    if (newState is JSONObject) {
        val byteOut = archiveState(newState)
        if (arrayEquals(byteOut.internalBuffer, oldState, byteOut.size())) {
            return null
        }

        newBytes = byteOut.toByteArray()
    } else {
        newBytes = newState as ByteArray
        if (newBytes.contentEquals(oldState)) {
            return null
        }
    }

    return newBytes
}

fun stateToElement(
    key: String,
    state: Any?,
    newLiveStates: Map<String, JSONObject>? = null
): JSONObject? {
    if (state is JSONObject) {
        val array = state.names()
        val targetArray = arrayListOf<String>()
        if (array == null) {
            return JSONObject()
        }
        for (i in 0 until array.length()) {
            targetArray.set(i, array.optString(i))
        }
        return JSONObject(state, targetArray.toTypedArray())
    } else {
        return newLiveStates?.get(key) ?: (state as? ByteArray)?.let(::unarchiveState)
    }
}

class StateMap private constructor(
    private val names: Array<String>,
    private val states: AtomicReferenceArray<Any>
) {
    override fun toString() = if (this == EMPTY) "EMPTY" else states.toString()

    companion object {
        internal val EMPTY = StateMap(ArrayUtil.EMPTY_STRING_ARRAY, AtomicReferenceArray(0))

        fun fromMap(map: Map<String, Any>): StateMap {
            if (map.isEmpty()) {
                return EMPTY
            }

            val names = map.keys.toTypedArray()
            if (map !is TreeMap) {
                Arrays.sort(names)
            }

            val states = AtomicReferenceArray<Any>(names.size)
            for (i in names.indices) {
                states.set(i, map[names[i]])
            }
            return StateMap(names, states)
        }
    }

    fun toMutableMap(): MutableMap<String, Any> {
        val map = HashMap<String, Any>(names.size)
        for (i in names.indices) {
            map.put(names[i], states.get(i))
        }
        return map
    }

    /**
     * Sorted by name.
     */
    fun keys(): Array<String> = names

    fun get(key: String): Any? {
        val index = Arrays.binarySearch(names, key)
        return if (index < 0) null else states.get(index)
    }

    fun getElement(key: String, newLiveStates: Map<String, JSONObject>? = null): JSONObject? =
        stateToElement(key, get(key), newLiveStates)

    fun isEmpty(): Boolean = names.isEmpty()

    fun hasState(key: String): Boolean = get(key) is JSONObject

    fun hasStates(): Boolean {
        return !isEmpty() && names.indices.any { states.get(it) is JSONObject }
    }

    fun compare(key: String, newStates: StateMap, diffs: MutableSet<String>) {
        val oldState = get(key)
        val newState = newStates.get(key)
        if (oldState is JSONObject) {
            //TODO: compare all json object
            /*  if (!((oldState as JSONObject?)?. as JSONObject?)) {
                  diffs.add(key)
              }*/
        } else if (oldState == null) {
            if (newState != null) {
                diffs.add(key)
            }
        } else if (newState == null || getNewByteIfDiffers(
                key,
                newState,
                oldState as ByteArray
            ) != null
        ) {
            diffs.add(key)
        }
    }

    fun getState(key: String, archive: Boolean = false): JSONObject? {
        val index = names.binarySearch(key)
        if (index < 0) {
            return null
        }

        val prev = states.getAndUpdate(index) { state ->
            when {
                archive && state is JSONObject -> archiveState(state).toByteArray()
                !archive && state is ByteArray -> unarchiveState(state)
                else -> state
            }
        }
        return prev as? JSONObject
    }

    fun archive(key: String, state: JSONObject?) {
        val index = Arrays.binarySearch(names, key)
        if (index < 0) {
            return
        }

        states.set(index, state?.let { archiveState(state).toByteArray() })
    }
}

fun setStateAndCloneIfNeeded(
    key: String,
    newState: JSONObject?,
    oldStates: StateMap,
    newLiveStates: MutableMap<String, JSONObject>? = null
): MutableMap<String, Any>? {
    val oldState = oldStates.get(key)
    if (newState == null || newState.length() == 0) {
        if (oldState == null) {
            return null
        }

        val newStates = oldStates.toMutableMap()
        newStates.remove(key)
        return newStates
    }

    newLiveStates?.put(key, newState)

    var newBytes: ByteArray? = null
    if (oldState is JSONObject) {
        //TODO: compare all json object
        /* if (JDOMUtil.areElementsEqual(oldState as JSONObject?, newState)) {
             return null
         }*/
    } else if (oldState != null) {
        newBytes = getNewByteIfDiffers(key, newState, oldState as ByteArray) ?: return null
    }

    val newStates = oldStates.toMutableMap()
    // ?? See StateMap#194L
    newStates.put(key, newBytes ?: newState /*JDOMUtil.internElement(newState)*/)
    return newStates
}

// true if updated (not equals to previous state)
internal fun updateState(
    states: MutableMap<String, Any>,
    key: String,
    newState: JSONObject?,
    newLiveStates: MutableMap<String, JSONObject>? = null
): Boolean {
    if (newState == null || newState.length() == 0) {
        states.remove(key)
        return true
    }

    newLiveStates?.put(key, newState)

    val oldState = states.get(key)

    var newBytes: ByteArray? = null
    if (oldState is JSONObject) {
        //TODO: compare all json object
        /* if (JDOMUtil.areElementsEqual(oldState as JSONObject?, newState)) {
             return null
         }*/
    } else if (oldState != null) {
        newBytes = getNewByteIfDiffers(key, newState, oldState as ByteArray) ?: return false
    }

    // ?? See StateMap#194L
    states.put(key, newBytes ?: newState /*JDOMUtil.internElement(newState)*/)
    return true
}

private fun arrayEquals(a: ByteArray, a2: ByteArray, size: Int = a.size): Boolean {
    if (a === a2) {
        return true
    }
    return a2.size == size && (0 until size).none { a[it] != a2[it] }
}