package com.dingyi.myluaapp.build.modules.android.tasks.build


import com.android.zipflinger.FullFileSource
import com.android.zipflinger.ZipArchive
import com.android.zipflinger.ZipSource
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

import java.util.*
import java.util.zip.Deflater

class PackageApk(private val module: Module) : DefaultTask(module) {



    //add to assets
    private val assetsFiles = mutableListOf<Pair<File, File>>()

    private val nativeLibraryFiles = mutableListOf<Pair<File, File>>()

    private val dexFiles = mutableListOf<File>()

    private val resourcesFiles = mutableListOf<Pair<File, File>>()

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Package${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Apk"
        }
        return javaClass.simpleName
    }

    private lateinit var aaptResourceApk: File

    private val outputPath: String
        get() = "build/outputs/apk/$buildVariants/app-$buildVariants-unsigned.apk"

    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants

        module
            .getFileManager()
            .resolveFile("build/intermediates/merge_project_dex/$buildVariants", module)
            .let { dexFile ->
                dexFile.walkBottomUp()
                    .filter { it.isFile && it.name.endsWith("dex") }
                    .forEach {
                        dexFiles.add(it)
                    }
            }

        module
            .getFileManager()
            .resolveFile("build/intermediates/merged_java_res/$buildVariants", module)
            .let { resources ->
                resources
                    .walkBottomUp()
                    .filter {
                        it.isFile
                    }
                    .forEach {
                        resourcesFiles.add(it to resources)
                    }
            }


        module
            .getFileManager()
            .resolveFile("build/intermediates/merged_jni/$buildVariants", module)
            .let { resources ->
                resources
                    .walkBottomUp()
                    .filter {
                        it.isFile
                    }
                    .forEach {
                        nativeLibraryFiles.add(it to resources)
                    }
            }

        module
            .getFileManager()
            .resolveFile("build/intermediates/merged_assets/$buildVariants", module)
            .let { resources ->
                resources
                    .walkBottomUp()
                    .filter {
                        it.isFile
                    }
                    .forEach {
                        assetsFiles.add(it to resources)
                    }
            }


        aaptResourceApk =
            module.getFileManager()
                .resolveFile(
                    "build/intermediates/processed_res/${buildVariants}/out/resources-${buildVariants}.ap_",
                    module
                )

        return Task.State.DEFAULT
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        runCatching {

            val file = module
                .getFileManager()
                .resolveFile(outputPath, module)

            file.parentFile?.mkdirs()

            if (file.exists()) {
                file.delete()
            }

            val archive = ZipArchive(
                file.toPath()
            )

            //add aapt resource
            archive.add(
                ZipSource.selectAll(
                    aaptResourceApk.toPath()
                )
            )


            assetsFiles.forEach {
                archive.add(
                    FullFileSource(
                        it.first.toPath(),
                        "assets/"+it.first.path.substring(it.second.path.length + 1),
                        Deflater.BEST_COMPRESSION
                    )
                )
            }

            nativeLibraryFiles.forEach {
                archive.add(
                    FullFileSource(
                        it.first.toPath(),
                        "lib/"+it.first.path.substring(it.second.path.length + 1),
                        Deflater.DEFAULT_COMPRESSION
                    )
                )
            }

            resourcesFiles.forEach {
                archive.add(
                    FullFileSource(
                        it.first.toPath(),
                        it.first.path.substring(it.second.path.length + 1),
                        Deflater.DEFAULT_COMPRESSION
                    )
                )
            }

            dexFiles.forEach {
                archive.add(
                    FullFileSource(
                        it.toPath(),
                        it.name,
                        Deflater.DEFAULT_COMPRESSION
                    )
                )
            }

            archive
                .close()

        }.getOrThrow()

    }


}