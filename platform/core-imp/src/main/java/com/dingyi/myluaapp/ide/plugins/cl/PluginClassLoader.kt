package com.dingyi.myluaapp.ide.plugins.cl


import com.dingyi.myluaapp.diagnostic.Logger
import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.ide.android.cl.AndroidClassLoader
import com.dingyi.myluaapp.ide.android.cl.AndroidParentClassLoader
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.intellij.openapi.util.ShutDownTracker
import com.intellij.util.SmartList
import com.intellij.util.ui.EDT
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.TestOnly
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.Writer
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.CodeSource
import java.security.ProtectionDomain
import java.security.cert.Certificate
import java.util.ArrayDeque
import java.util.Arrays
import java.util.Collections
import java.util.Deque
import java.util.Enumeration
import java.util.StringTokenizer
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.function.BiFunction
import java.util.function.Function
import kotlin.io.path.pathString


@ApiStatus.Internal
@ApiStatus.NonExtendable
class PluginClassLoader(
    parent: AndroidParentClassLoader,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    pluginDescriptor: PluginDescriptor,
    pluginRoot: Path,
    coreLoader: ClassLoader,
    resolveScopeManager: ResolveScopeManager?,
    packagePrefix: String?,
    /*  resourceFileFactory: ClassPath.ResourceFileFactory?*/
) : AndroidClassLoader(
    dexPath = pluginRoot.pathString,
    optimizedDirectory, librarySearchPath, parent
), PluginAwareClassLoader {
    /*   private var parents: Array<ClassLoader?>

       // cache of computed list of all parents (not only direct)
       @Volatile
       private var allParents: Array<ClassLoader>

       @Volatile
       private var allParentsLastCacheId = 0*/
    override val pluginDescriptor: PluginDescriptor

    // to simplify analyzing of heap dump (dynamic plugin reloading)
    override val pluginId: PluginId
    override val packagePrefix: String?

    /* private val libDirectories: MutableList<String>*/
    /* private val edtTime = AtomicLong()
     private override val backgroundTime = AtomicLong()*/
    private val loadedClassCounter = AtomicInteger()
    private val coreLoader: ClassLoader
    override val instanceId: Int = instanceIdProducer.incrementAndGet()

    @get:ApiStatus.Internal
    @set:ApiStatus.Internal
    @Volatile
    override var state = PluginAwareClassLoader.ACTIVE
    private val resolveScopeManager: ResolveScopeManager

    fun interface ResolveScopeManager {
        fun isDefinitelyAlienClass(name: String, packagePrefix: String?, force: Boolean): Boolean
    }

    init {
        this.resolveScopeManager = resolveScopeManager
            ?: ResolveScopeManager { _, _, _ -> false }
        /* this.parents = parents*/
        this.pluginDescriptor = pluginDescriptor
        pluginId = pluginDescriptor.getPluginId()
        this.packagePrefix =
            if (packagePrefix == null || packagePrefix.endsWith(".")) packagePrefix else "$packagePrefix."
        this.coreLoader = coreLoader
        /*  if (PluginClassLoader::class.java.desiredAssertionStatus()) {
              for (parent: ClassLoader? in this.parents) {
                  if (parent === coreLoader) {
                      Logger.getInstance(PluginClassLoader::class.java).error(
                          "Core loader must be not specified in parents " +
                                  "(parents=" + Arrays.toString(parents) + ", coreLoader=" + coreLoader + ")"
                      )
                  }
              }
          }*/
        /* libDirectories = SmartList()
         if (pluginRoot != null) {
             val libDir = pluginRoot.resolve("lib")
             if (Files.exists(libDir)) {
                 libDirectories.add(libDir.toAbsolutePath().toString())
             }
         }*/
    }

    /*  fun getEdtTime(): Long {
          return edtTime.get()
      }

      override fun getBackgroundTime(): Long {
          return backgroundTime.get()
      }*/

    override val loadedClassCount: Long
        get() = loadedClassCounter.get().toLong()

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String, resolve: Boolean): Class<*> {
        val c = tryLoadingClass(name, false)
        if (c == null) {
            flushDebugLog()
            throw ClassNotFoundException("$name $this")
        }
        return c
    }

    /**
     * See https://stackoverflow.com/a/5428795 about resolve flag.
     */
    @Throws(ClassNotFoundException::class)
    override fun tryLoadingClass(
        name: String,
        forceLoadFromSubPluginClassloader: Boolean
    ): Class<*>? {
        if (mustBeLoadedByPlatform(name)) {
            return coreLoader.loadClass(name)
        }
        /* val startTime =
             if (StartUpMeasurer.measuringPluginStartupCosts) StartUpMeasurer.getCurrentTime() else -1
      */
        val c: Class<*>?
        try {
            c = loadClassInsideSelf(name, forceLoadFromSubPluginClassloader)
        } catch (e: IOException) {
            throw ClassNotFoundException(name, e)
        }
        /* if (c == null) {
             for (classloader: ClassLoader in getAllParents()) {
                 if (classloader is UrlClassLoader) {
                     try {
                         c = (classloader as UrlClassLoader).loadClassInsideSelf(name, false)
                     } catch (e: IOException) {
                         throw ClassNotFoundException(name, e)
                     }
                     if (c != null) {
                         break
                     }
                 } else {
                     try {
                         c = classloader.loadClass(name)
                         if (c != null) {
                             break
                         }
                     } catch (ignoreAndContinue: ClassNotFoundException) {
                         // ignore and continue
                     }
                 }
             }
         }
         if (startTime != -1L) {
             // EventQueue.isDispatchThread() is expensive
             (if (EDT.isCurrentThreadEdt()) edtTime else backgroundTime).addAndGet(StartUpMeasurer.getCurrentTime() - startTime)
         }*/
        return c
    }
    /*
        private fun getAllParents(): Array<ClassLoader> {
            var result = allParents
            if (result != null && allParentsLastCacheId == parentListCacheIdCounter.get()) {
                return result
            }
            if (parents.size == 0) {
                result = arrayOf(coreLoader)
                allParents = result
                return result
            }
            val parentSet: MutableSet<ClassLoader> = LinkedHashSet()
            val queue: Deque<ClassLoader> = ArrayDeque()
            Collections.addAll(queue, *parents)
            var classLoader: ClassLoader
            while ((queue.pollFirst().also { classLoader = it }) != null) {
                if (classLoader === coreLoader || !parentSet.add(classLoader)) {
                    continue
                }
                if (classLoader is PluginClassLoader) {
                    Collections.addAll(queue, *(classLoader as PluginClassLoader).parents)
                }
            }
            parentSet.add(coreLoader)
            result = parentSet.toArray(EMPTY_CLASS_LOADER_ARRAY)
            allParents = result
            allParentsLastCacheId = parentListCacheIdCounter.get()
            return result
        }

        fun clearParentListCache() {
            allParents = null
        }*/

    @Throws(IOException::class)
    fun loadClassInsideSelf(name: String, forceLoadFromSubPluginClassloader: Boolean): Class<*>? {
        if (resolveScopeManager.isDefinitelyAlienClass(
                name,
                packagePrefix,
                forceLoadFromSubPluginClassloader
            )
        ) {
            return null
        }
        synchronized(this/*getClassLoadingLock(name)*/) {
            var c: Class<*>? = findLoadedClass(name)
            if (c != null && c.classLoader === this) {
                return c
            }
            val logStream: Writer? = logStream
            try {
                c = super.loadClass(name, false)
            } catch (e: LinkageError) {
                if (logStream != null) {
                    logClass(name, logStream, e)
                }
                flushDebugLog()
                throw PluginException(
                    ("Cannot load class " + name + " (" +
                            "\n  error: " + e.message +
                            ",\n  classLoader=" + this + "\n)"), e, pluginId
                )
            }
            if (c == null) {
                return null
            }
            loadedClassCounter.incrementAndGet()
            if (logStream != null) {
                logClass(name, logStream, null)
            }
            return c
        }
    }

    private fun logClass(name: String, logStream: Writer, exception: LinkageError?) {
        try {
            // must be as one write call since write is performed from multiple threads
            val specifier = "m"
            logStream.write(
                ((name + " [" + specifier + "] " + pluginId.getIdString()).toString() + (if (packagePrefix == null) "" else (":$packagePrefix"))
                        + '\n' + (if (exception == null) "" else exception.message))
            )
        } catch (ignored: IOException) {
        }
    }
    /*
        fun findResource(name: String): URL? {
            return findResource(name, Resource::getURL,
                BiFunction { obj: ClassLoader, name: String? ->
                    obj.getResource(
                        name
                    )
                })
        }

        fun getResourceAsStream(name: String): InputStream? {
            val f1: Function<Resource, InputStream?> =
                Function<Resource, InputStream?> { resource: Resource ->
                    try {
                        return@Function resource.getInputStream()
                    } catch (e: IOException) {
                        Logger.getInstance(PluginClassLoader::class.java)
                            .error(e)
                        return@Function null
                    }
                }
            val f2 =
                BiFunction { cl: ClassLoader, path: String? ->
                    try {
                        return@BiFunction cl.getResourceAsStream(path)
                    } catch (e: Exception) {
                        Logger.getInstance(PluginClassLoader::class.java)
                            .error(e)
                        return@BiFunction null
                    }
                }
            return findResource(name, f1, f2)
        }

        private fun <T> findResource(
            name: String,
            f1: Function<Resource, T>,
            f2: BiFunction<ClassLoader, String, T>
        ): T? {
            var canonicalPath: String = toCanonicalPath(name)
            if (canonicalPath.startsWith("/")) {
                if (!canonicalPath.startsWith("/org/bridj/")) {
                    val message =
                        "Do not request resource from classloader using path with leading slash"
                    Logger.getInstance(PluginClassLoader::class.java)
                        .error(message, PluginException(name, pluginId))
                }
                canonicalPath = canonicalPath.substring(1)
            }
            var resource: Resource = classPath.findResource(canonicalPath)
            if (resource != null) {
                return f1.apply(resource)
            }
            for (classloader: ClassLoader in getAllParents()) {
                if (classloader is PluginClassLoader) {
                    resource = (classloader as PluginClassLoader).classPath.findResource(canonicalPath)
                    if (resource != null) {
                        return f1.apply(resource)
                    }
                } else {
                    val t: T? = f2.apply(classloader, canonicalPath)
                    if (t != null) {
                        return t
                    }
                }
            }
            return null
        }

        @Throws(IOException::class)
        override fun findResources(name: String): Enumeration<URL> {
            val resources: MutableList<Enumeration<URL>> = ArrayList()
            resources.add(classPath.getResources(name))
            for (classloader: ClassLoader in getAllParents()) {
                if (classloader is PluginClassLoader) {
                    resources.add((classloader as PluginClassLoader).classPath.getResources(name))
                } else {
                    try {
                        resources.add(classloader.getResources(name))
                    } catch (ignore: IOException) {
                    }
                }
            }
            return DeepEnumeration(resources)
        }*/

    /* fun addLibDirectories(libDirectories: Collection<String>) {
         this.libDirectories.addAll(libDirectories)
     }

     protected fun findLibrary(libName: String?): String? {
         if (!libDirectories.isEmpty()) {
             val libFileName = System.mapLibraryName(libName)
             val i: ListIterator<String> = libDirectories.listIterator(libDirectories.size)
             while (i.hasPrevious()) {
                 val libFile = File(i.previous(), libFileName)
                 if (libFile.exists()) {
                     return libFile.absolutePath
                 }
             }
         }
         return null
     }*/


    /*override fun getPluginId(): PluginId {
        return pluginId
    }*/

    /*override fun getPluginDescriptor(): PluginDescriptor {
        return pluginDescriptor
    } */

    override fun toString(): String {
        return (javaClass.simpleName + "(plugin=" + pluginDescriptor +
                ", packagePrefix=" + packagePrefix +
                ", instanceId=" + instanceId +
                ", state=" + (if (state == PluginAwareClassLoader.ACTIVE) "active" else "unload in progress") +
                ")")
    }

    /*  private class DeepEnumeration internal constructor(private val list: List<Enumeration<URL>>) :
          Enumeration<URL?> {
          private var myIndex = 0
          override fun hasMoreElements(): Boolean {
              while (myIndex < list.size) {
                  val e: Enumeration<URL> = list[myIndex]
                  if (e != null && e.hasMoreElements()) {
                      return true
                  }
                  myIndex++
              }
              return false
          }

          override fun nextElement(): URL {
              if (!hasMoreElements()) {
                  throw NoSuchElementException()
              }
              return list[myIndex].nextElement()
          }
      }

      @TestOnly
      @ApiStatus.Internal
      fun _getParents(): List<ClassLoader> {
          return Collections.unmodifiableList(Arrays.asList(*parents))
      }

      @ApiStatus.Internal
      fun attachParent(classLoader: ClassLoader) {
          val length = parents.size
          val result = arrayOfNulls<ClassLoader>(length + 1)
          System.arraycopy(parents, 0, result, 0, length)
          result[length] = classLoader
          parents = result
          parentListCacheIdCounter.incrementAndGet()
      }*/

    /**
     * You must clear allParents cache for all loaded plugins.
     *//*
    @ApiStatus.Internal
    fun detachParent(classLoader: ClassLoader): Boolean {
        for (i in parents.indices) {
            if (classLoader !== parents[i]) {
                continue
            }
            val length = parents.size
            val result = arrayOfNulls<ClassLoader>(length - 1)
            System.arraycopy(parents, 0, result, 0, i)
            System.arraycopy(parents, i + 1, result, i, length - i - 1)
            parents = result
            parentListCacheIdCounter.incrementAndGet()
            return true
        }
        return false
    }*/

    companion object {
        val EMPTY_CLASS_LOADER_ARRAY = arrayOfNulls<ClassLoader>(0)

        /*    private val isParallelCapable: Boolean = registerAsParallelCapable()*/
        private var logStream: Writer? = null
        val instanceIdProducer = AtomicInteger()
        private val parentListCacheIdCounter = AtomicInteger()
        private var KOTLIN_STDLIB_CLASSES_USED_IN_SIGNATURES: Set<String>? = null


        init {
            val kotlinStdlibClassesUsedInSignatures: MutableSet<String> = HashSet(
                Arrays.asList(
                    "kotlin.Function",
                    "kotlin.sequences.Sequence",
                    "kotlin.ranges.IntRange",
                    "kotlin.ranges.IntRange\$Companion",
                    "kotlin.ranges.IntProgression",
                    "kotlin.ranges.ClosedRange",
                    "kotlin.ranges.IntProgressionIterator",
                    "kotlin.ranges.IntProgression\$Companion",
                    "kotlin.ranges.IntProgression",
                    "kotlin.collections.IntIterator",
                    "kotlin.Lazy", "kotlin.Unit",
                    "kotlin.Pair", "kotlin.Triple",
                    "kotlin.jvm.internal.DefaultConstructorMarker",
                    "kotlin.jvm.internal.ClassBasedDeclarationContainer",
                    "kotlin.properties.ReadWriteProperty",
                    "kotlin.properties.ReadOnlyProperty",
                    "kotlin.coroutines.ContinuationInterceptor",
                    "kotlinx.coroutines.CoroutineDispatcher",
                    "kotlin.coroutines.Continuation",
                    "kotlin.coroutines.CoroutineContext",
                    "kotlin.coroutines.CoroutineContext\$Element",
                    "kotlin.coroutines.CoroutineContext\$Key"
                )
            )
            val classes = System.getProperty("idea.kotlin.classes.used.in.signatures")
            if (classes != null) {
                val t = StringTokenizer(classes, ",")
                while (t.hasMoreTokens()) {
                    kotlinStdlibClassesUsedInSignatures.add(t.nextToken())
                }
            }
            KOTLIN_STDLIB_CLASSES_USED_IN_SIGNATURES = kotlinStdlibClassesUsedInSignatures
            var logStreamCandidate: Writer? = null
            val debugFilePath = System.getProperty("plugin.classloader.debug", "")
            if (debugFilePath.isNotEmpty()) {
                try {
                    logStreamCandidate = Files.newBufferedWriter(Paths.get(debugFilePath))
                    ShutDownTracker.getInstance().registerShutdownTask {
                        try {

                            logStream?.close()

                        } catch (e: IOException) {
                            Logger.getInstance(PluginClassLoader::class.java)
                                .error(e)
                        }
                    }
                } catch (e: IOException) {
                    Logger.getInstance(PluginClassLoader::class.java).error(e)
                }
            }
            logStream = logStreamCandidate
        }

        private fun mustBeLoadedByPlatform(@NonNls className: String): Boolean {
            return if (className.startsWith("java.")) {
                true
            } else className.startsWith("kotlin.") && ((className.startsWith("kotlin.jvm.functions.") ||
                    (className.startsWith("kotlin.reflect.") &&
                            className.indexOf('.', 15 /* "kotlin.reflect".length */) < 0) ||
                    KOTLIN_STDLIB_CLASSES_USED_IN_SIGNATURES!!.contains(className)))

            // some commonly used classes from kotlin-runtime must be loaded by the platform classloader. Otherwise if a plugin bundles its own version
            // of kotlin-runtime.jar it won't be possible to call platform's methods with these types in signatures from such a plugin.
            // We assume that these classes don't change between Kotlin versions so it's safe to always load them from platform's kotlin-runtime.
        }

        private fun flushDebugLog() {

            try {
                logStream?.flush()
            } catch (ignore: IOException) {
            }

        }
    }
}
