 package com.dingyi.myluaapp.configurationStore

 import com.dingyi.myluaapp.diagnostic.logger
 import com.dingyi.myluaapp.openapi.components.StateStorage
 import com.intellij.openapi.util.JDOMUtil
 import com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream
 import com.intellij.util.LineSeparator
 import com.intellij.util.containers.CollectionFactory
 import org.json.JSONObject
 import java.io.FileNotFoundException
 import java.io.OutputStream
 import java.io.Writer
 import java.nio.file.Path
 import kotlin.io.path.deleteIfExists
 import kotlin.io.path.outputStream


 abstract class JSONElementStorage protected constructor(val fileSpec: String,
                                                        protected val rootElementName: String?,
                                                        /*private val pathMacroSubstitutor: PathMacroSubstitutor? = null,
                                                        roamingType: RoamingType? = RoamingType.DEFAULT,*/
                                                        private val provider: StreamProvider? = null) : StorageBaseEx<StateMap>() {


     protected abstract fun loadLocalData(): JSONObject?

     final override fun getSerializedState(storageData: StateMap, component: Any?, componentName: String, archive: Boolean) = storageData.getState(componentName, archive)

     final override fun archiveState(storageData: StateMap, componentName: String, serializedState: JSONObject?) {
         storageData.archive(componentName, serializedState)
     }

     final override fun hasState(storageData: StateMap, componentName: String) = storageData.hasState(componentName)

     final override fun loadData() = loadElement()?.let { loadState(it) } ?: StateMap.EMPTY

     private fun loadElement(useStreamProvider: Boolean = true): JSONObject? {
         var element: JSONObject? = null
         try {
             val isLoadLocalData: Boolean
             if (useStreamProvider && provider != null) {
                 isLoadLocalData = !provider.read(fileSpec) { inputStream ->
                     inputStream?.let {
                         element = deserializeElementFromBinary(it)
                         providerDataStateChanged(createDataWriterForElement(element!!, toString()), DataStateChanged.LOADED)
                     }
                 }
             }
             else {
                 isLoadLocalData = true
             }

             if (isLoadLocalData) {
                 element = loadLocalData()
             }
         }
         catch (e: FileNotFoundException) {
             throw e
         }
         catch (e: Throwable) {
             LOG.error("Cannot load data for $fileSpec", e)
         }
         return element
     }

     protected open fun providerDataStateChanged(writer: DataWriter?, type: DataStateChanged) {
     }

     private fun loadState(element: Element): StateMap {
         beforeElementLoaded(element)
         return StateMap.fromMap(FileStorageCoreUtil.load(element, pathMacroSubstitutor, true))
     }

     final override fun createSaveSessionProducer(): SaveSessionProducer? {
         return if (checkIsSavingDisabled()) null else createSaveSession(getStorageData())
     }

     protected abstract fun createSaveSession(states: StateMap): SaveSessionProducer

     override fun analyzeExternalChangesAndUpdateIfNeeded(componentNames: MutableSet<in String>) {
         val oldData = storageDataRef.get()
         val newData = getStorageData(true)
         if (oldData == null) {
             LOG.debug { "analyzeExternalChangesAndUpdateIfNeeded: old data null, load new for ${toString()}" }
             componentNames.addAll(newData.keys())
         }
         else {
             val changedComponentNames = oldData.getChangedComponentNames(newData)
             if (changedComponentNames.isNotEmpty()) {
                 LOG.debug { "analyzeExternalChangesAndUpdateIfNeeded: changedComponentNames $changedComponentNames for ${toString()}" }
                 componentNames.addAll(changedComponentNames)
             }
         }
     }

     private fun setStates(oldStorageData: StateMap, newStorageData: StateMap?) {
         if (oldStorageData !== newStorageData && storageDataRef.getAndSet(newStorageData) !== oldStorageData) {
             LOG.warn("Old storage data is not equal to current, new storage data was set anyway")
         }
     }

         abstract class XmlElementStorageSaveSession<T : JSONElementStorage>(private val originalStates: StateMap, protected val storage: T) : SaveSessionBase() {
         private var copiedStates: MutableMap<String, Any>? = null

         private var newLiveStates: MutableMap<String, JSONObject>? = HashMap()

         protected open fun isSaveAllowed() = !storage.checkIsSavingDisabled()

         final override fun createSaveSession(): SaveSession? {
             if (copiedStates == null || !isSaveAllowed()) {
                 return null
             }

             val stateMap = StateMap.fromMap(copiedStates!!)
             val element = save(stateMap, newLiveStates ?: throw IllegalStateException("createSaveSession was already called"))
             newLiveStates = null

             val writer: DataWriter?
             if (element == null) {
                 writer = null
             }
             else {
                 val rootAttributes = LinkedHashMap<String, String>()
                 storage.beforeElementSaved(elements, rootAttributes)
               //  val macroManager = if (storage.pathMacroSubstitutor == null) null else (storage.pathMacroSubstitutor as TrackingPathMacroSubstitutorImpl).macroManager
                 writer = JsonDataWriter(
                     element //
                 )// storage.rootElementName, elements, rootAttributes, macroManager, storage.toString())
             }

             // during beforeElementSaved() elements can be modified and so, even if our save() never returns empty list, at this point, elements can be an empty list
             return SaveExecutor(element, writer, stateMap)
         }

         private inner class SaveExecutor(private val element:  JSONObject?,
                                          private val writer: DataWriter?,
                                          private val stateMap: StateMap) : SaveSession {
             override fun save() {
                 var isSavedLocally = false
                 val provider = storage.provider
                 if (element == null) {
                     if (provider == null || !provider.delete(storage.fileSpec)) {
                         isSavedLocally = true
                         saveLocally(writer)
                     }
                 }
                 else if (provider != null && provider.isApplicable(storage.fileSpec)) {
                     // we should use standard line-separator (\n) - stream provider can share file content on any OS
                     provider.write(storage.fileSpec, writer!!.toBufferExposingByteArray())
                 }
                 else {
                     isSavedLocally = true
                     saveLocally(writer)
                 }

                 if (!isSavedLocally) {
                     storage.providerDataStateChanged(writer, DataStateChanged.SAVED)
                 }

                 storage.setStates(originalStates, stateMap)
             }
         }

         override fun setSerializedState(componentName: String, element: JSONObject?) {
             val newLiveStates = newLiveStates ?: throw IllegalStateException("createSaveSession was already called")

             val normalized = element?.normalizeRootName()
             if (copiedStates == null) {
                 copiedStates = setStateAndCloneIfNeeded(componentName, element, originalStates, newLiveStates)
             }
             else {
                 updateState(copiedStates!!, componentName, normalized, newLiveStates)
             }
         }

         protected abstract fun saveLocally(dataWriter: DataWriter?)
     }

     protected open fun beforeElementLoaded(element: JSONObject) {
     }

     protected open fun beforeElementSaved(elements: MutableList<JSONObject>, rootAttributes: MutableMap<String, String>) {
     }

     fun updatedFromStreamProvider(changedComponentNames: MutableSet<String>, deleted: Boolean) {
         updatedFrom(changedComponentNames, deleted, true)
     }

     fun updatedFrom(changedComponentNames: MutableSet<String>, deleted: Boolean, useStreamProvider: Boolean) {
         if (roamingType == RoamingType.DISABLED) {
             // storage roaming was changed to DISABLED, but settings repository has old state
             return
         }

         LOG.runAndLogException {
             val newElement = if (deleted) null else loadElement(useStreamProvider)
             val states = storageDataRef.get()
             if (newElement == null) {
                 // if data was loaded, mark as changed all loaded components
                 if (states != null) {
                     changedComponentNames.addAll(states.keys())
                     setStates(states, null)
                 }
             }
             else if (states != null) {
                 val newStates = loadState(newElement)
                 changedComponentNames.addAll(states.getChangedComponentNames(newStates))
                 setStates(states, newStates)
             }
         }
     }
 }


 fun interface DataWriterFilter {
     enum class ElementLevel {
         ZERO, FIRST
     }


     fun hasData(element: JSONObject): Boolean
 }

 internal class JSONDataWriterFilter : DataWriterFilter {
     override fun hasData(element: JSONObject): Boolean {
         for (key in element.keys()) {
             val value = element.get(key)
             if (value !== JSONObject.NULL) {
                 return true
             }
         }
         return false
     }
 }

 interface DataWriter {
     // LineSeparator cannot be used because custom (with an indent) line separator can be used
     fun write(output: OutputStream, lineSeparator: String = LineSeparator.LF.separatorString, filter: DataWriterFilter? = null)

     fun hasData(filter: DataWriterFilter): Boolean
 }

 internal fun DataWriter?.writeTo(file: Path, requestor: Any?, lineSeparator: String = LineSeparator.LF.separatorString) {
     if (this == null) {
         file.deleteIfExists()
     }
     else {
         (/*if (safe) file.safeOutputStream() else */file.outputStream()).use {
             write(it, lineSeparator)
         }
     }
 }

 internal class JsonDataWriter(val data: JSONObject) : StringDataWriter() {
     companion object {
         private val LOG = logger<JsonDataWriter>()
     }

     override fun hasData(filter: DataWriterFilter): Boolean {
         return filter.hasData(data)
     }

     override fun write(writer: Writer, lineSeparator: String, filter: DataWriterFilter?) {
         if (filter == null || filter.hasData(data)) {
             val formattedJson = data.toString(1)
             writer.write(formattedJson)
             writer.write(lineSeparator)
         }
     }
 }


 // newStorageData - myStates contains only live (unarchived) states
 private fun StateMap.getChangedComponentNames(newStates: StateMap): Set<String> {
     val newKeys = newStates.keys()
     val existingKeys = keys()

     val bothStates = ArrayList<String>(Math.min(newKeys.size, existingKeys.size))
     val existingKeysSet = if (existingKeys.size < 3) existingKeys.asList() else CollectionFactory.createSmallMemoryFootprintSet(existingKeys.asList())
     for (newKey in newKeys) {
         if (existingKeysSet.contains(newKey)) {
             bothStates.add(newKey)
         }
     }

     val diffs = CollectionFactory.createSmallMemoryFootprintSet<String>(newKeys.size + existingKeys.size)
     diffs.addAll(newKeys)
     diffs.addAll(existingKeys)
     diffs.removeAll(bothStates)

     for (componentName in bothStates) {
         compare(componentName, newStates, diffs)
     }
     return diffs
 }

 enum class DataStateChanged {
     LOADED, SAVED
 }


 internal abstract class StringDataWriter : DataWriter {
     final override fun write(output: OutputStream, lineSeparator: String, filter: DataWriterFilter?) {
         output.bufferedWriter().use {
             write(it, lineSeparator, filter)
         }
     }

     internal abstract fun write(writer: Writer, lineSeparator: String, filter: DataWriterFilter?)
 }

 internal fun DataWriter.toBufferExposingByteArray(lineSeparator: LineSeparator = com.intellij.util.LineSeparator.LF): BufferExposingByteArrayOutputStream {
     val out = BufferExposingByteArrayOutputStream(1024)
     out.use { write(out, lineSeparator.separatorString) }
     return out
 }

 // use ONLY for non-ordinal usages (default project, deprecated directoryBased storage)
 internal fun createDataWriterForElement(element: JSONObject, storageFilePathForDebugPurposes: String): DataWriter {
     return object: DataWriter {
         override fun hasData(filter: DataWriterFilter) = filter.hasData(element)

         override fun write(output: OutputStream, lineSeparator: String, filter: DataWriterFilter?) {
             output.bufferedWriter().use {
                 it.write(element.toString(1))
             }
         }
     }
 }


 interface ExternalStorageWithInternalPart {
     val internalStorage: StateStorage
 }