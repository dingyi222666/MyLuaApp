package com.dingyi.myluaapp.openapi.util

import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.containers.JBIterable
import java.io.File
import java.util.Arrays
import java.util.Collections


class PathsList {
    private val myPath: MutableList<String> = ArrayList()
    private val myPathTail: MutableList<String> = ArrayList()
    private val myPathSet: MutableSet<String> = HashSet()
    val isEmpty: Boolean
        get() = myPathSet.isEmpty()

    fun add(path: String) {
        addAllLast(chooseFirstTimeItems(path), myPath)
    }

    fun remove(path: String) {
        myPath.remove(path)
        myPathTail.remove(path)
        myPathSet.remove(path)
    }

    fun clear() {
        myPath.clear()
        myPathTail.clear()
        myPathSet.clear()
    }

    fun add(file: VirtualFile) {
        val path: String = LOCAL_PATH(file)
        val trimmed = path.trim { it <= ' ' } ?: ""
        if (trimmed.isNotEmpty() && myPathSet.add(trimmed)) {
            myPath.add(trimmed)
        }
    }

    fun addFirst(path: String?) {
        var index = 0
        for (element in chooseFirstTimeItems(path)) {
            myPath.add(index, element)
            myPathSet.add(element)
            index++
        }
    }

    fun addTail(path: String) {
        addAllLast(chooseFirstTimeItems(path), myPathTail)
    }


    private fun chooseFirstTimeItems(path: String?): Iterable<String> {
        return if (path == null) {
            Collections.emptyList()
        } else {
            JBIterable.from(StringUtil.tokenize(path, File.pathSeparator)).filter { element ->
                val element = element.trim()
                !element.isEmpty() && !myPathSet.contains(element)
            }
        }
    }

    private fun addAllLast(elements: Iterable<String>, toArray: MutableList<in String>) {
        for (element in elements) {
            toArray.add(element)
            myPathSet.add(element)
        }
    }


    val pathsString: String
        get() = StringUtil.join(pathList, File.pathSeparator)


    val pathList: List<String>
        get() {
            val result = ArrayList<String>()
            result.addAll(myPath)
            result.addAll(myPathTail)
            return result
        }

    /**
     * @return [VirtualFile]s on local file system (returns jars as files).
     */
    val virtualFiles: List<Any>
        get() = JBIterable.from(pathList).filterMap(PATH_TO_LOCAL_VFILE).toList()

    /**
     * @return The same as [.getVirtualFiles] but returns jars as `JarFileSystem` roots.
     */
    val rootDirs: List<Any>
        get() = JBIterable.from(pathList).filterMap(PATH_TO_DIR).toList()

    fun addAll(allClasspath: List<String>) {
        for (path in allClasspath) {
            add(path)
        }
    }

    fun addAllFiles(files: Array<File>) {
        addAllFiles(files.toList())
    }

    fun addAllFiles(files: List<File>) {
        for (file in files) {
            add(file)
        }
    }

    fun add(file: File) {
        add(FileUtil.toCanonicalPath(file.getAbsolutePath()).replace('/', File.separatorChar))
    }

    fun addFirst(file: File) {
        addFirst(FileUtil.toCanonicalPath(file.getAbsolutePath()).replace('/', File.separatorChar))
    }

    fun addVirtualFiles(files: Collection<VirtualFile>) {
        for (file in files) {
            add(file)
        }
    }

    fun addVirtualFiles(files: Array<VirtualFile>) {
        addVirtualFiles(files.toList())
    }



    companion object {
        private val PATH_TO_LOCAL_VFILE: (String) -> VirtualFile =
             { path ->
                 TODO("")
                //StandardFileSystems.local().findFileByPath(path.replace(File.separatorChar, '/'))
            }
        private val LOCAL_PATH: (VirtualFile) -> String = { file -> TODO("") }
        private val PATH_TO_DIR: (String) -> VirtualFile? = { s ->
            /* val file: VirtualFile = PATH_TO_LOCAL_VFILE.`fun`(s) ?: return@Function null
             if (!file.isDirectory() && FileTypeRegistry.getInstance()
                     .getFileTypeByFileName(file.getNameSequence()) === ArchiveFileType.INSTANCE
             ) {
                 return@Function StandardFileSystems.jar()
                     .findFileByPath(file.getPath() + URLUtil.JAR_SEPARATOR)
             }
             file*/
            null
        }
    }
}
