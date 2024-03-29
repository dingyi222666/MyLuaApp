package com.dingyi.myluaapp.build.modules.android.tasks.build


import com.android.apksigner.ApkSignerTool
import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.store.KeyStoreProvider
import com.dingyi.myluaapp.common.ktx.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.conscrypt.OpenSSLProvider
import org.luaj.vm2.LuaBoolean
import org.luaj.vm2.LuaNil
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import java.io.File
import java.security.Security
import java.util.*

class SignApk(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Sign${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Apk"
        }
        return javaClass.simpleName
    }

    private lateinit var inputPath: File

    private val outputPath: String
        get() = "build/outputs/apk/$buildVariants/app-$buildVariants.apk"


    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants

        arrayOf(
            "build/outputs/apk/$buildVariants/app-$buildVariants-unsigned.apk",
            "build/outputs/apk/$buildVariants/app-$buildVariants-zipalign.apk"
        ).forEach {
            val file = module
                .getFileManager()
                .resolveFile(it, module)

            if (file.exists()) {
                inputPath = file
                return@forEach
            }
        }

        val openSSLProvider = Security.getProvider("ssl")

        if (openSSLProvider == null) {
            Security.addProvider(OpenSSLProvider("ssl"))
        }


        val jksProvider = Security.getProvider("JKSProvider")

        if (jksProvider == null) {
            Security.addProvider(KeyStoreProvider())
        }


        return Task.State.DEFAULT
    }


    override suspend fun run() = withContext(Dispatchers.IO) {

        val args = mutableListOf<String>()

        val signerConfig = module
            .getMainBuilderScript()
            .get("android.buildTypes.${buildVariants}.signingConfig")

        args.add("sign")

        val ksTypeMap = mapOf(
            "jks" to "jks"
        )

        var configSign = false

        if (signerConfig is LuaTable) {
            args.add("--v1-signing-enabled")
            args.add((signerConfig["v1SigningEnabled"]
                ?.let {
                    if (it is LuaBoolean) it.booleanValue() else true
                }
                ?: true).toString())

            args.add("--v2-signing-enabled")
            args.add((signerConfig["v2SigningEnabled"]
                ?.let {
                    if (it is LuaBoolean) it.booleanValue() else true
                }
                ?: true).toString())

            args.add("--v3-signing-enabled")
            args.add((signerConfig["v3SigningEnabled"]
                ?.let {
                    if (it is LuaBoolean) it.booleanValue() else true
                }
                ?: true).toString())

            args.add("--v4-signing-enabled")
            args.add((signerConfig["v4SigningEnabled"]
                ?.let {
                    if (it is LuaBoolean) it.booleanValue() else false
                }
                ?: true).toString())

            val storeFile = signerConfig["storeFile"]


            if (storeFile == null || storeFile is LuaNil) {
                configSign = false
            } else {

                val file = getFile(storeFile.tojstring()).checkNotNull().toFile()

                if (!file.suffix.endsWith("bks", "jks")) {

                    module.getLogger()
                        .error("\n")


                    module
                        .getLogger()
                        .error("Not support this key store, only support the type of bks or jks(with rsa)")

                    module.getLogger()
                        .error("\n")

                    throw CompileError("Not support this key store, only support the type of bks or jks(with rsa)")

                }

                args.add("--ks")
                args.add(file.path)

                args.add("--ks-type")
                args.add(ksTypeMap.getOrDefault(file.suffix, "bks"))

                args.add("--ks-key-alias")
                args.add(check(signerConfig["keyAlias"], "keyAlias"))

                args.add("--ks-pass")
                args.add(checkFile(check(signerConfig["storePassword"], "storePassword"), "pass:"))

                args.add("--key-pass")
                args.add(checkFile(check(signerConfig["keyPassword"], "keyPassword"), "pass:"))

                configSign = true

            }

        }


        if (!configSign) {
            args.add("--key")
            args.add(File(Paths.assetsDir.toFile(), "keys/testkey.pk8").path)

            args.add("--cert")
            args.add(File(Paths.assetsDir.toFile(), "keys/testkey.x509.pem").path)
        }



        args.add("--out")
        args.add(module.getFileManager().resolveFile(outputPath, module).path)
        args.add(inputPath.path)




        try {
            ApkSignerTool
                .main(args.toTypedArray())
        } catch (it: Exception) {
            if (it.message == "Wrong version of key store.") {
                module
                    .getLogger()
                    .error("Not support this key store, only support the type of bks or jks(with rsa)")

                module.getLogger()
                    .error("\n")
                module
                    .getLogger()
                    .error(it.stackTraceToString())

                throw CompileError("Not support this key store, only support the type of bks or jks(with rsa)")
            }
        }



        inputPath.deleteRecursively()

        module
            .getLogger()
            .info("\n")

        module
            .getLogger()
            .info(
                string = "APK generated successfully for project ${module.getProject().name},[install] or [open] APK",
                extra = module.getFileManager().resolveFile(outputPath, module).path
            )

    }

    private fun checkFile(check: String, s: String): String {
        val file = getFile(check)
        return file ?: s + check
    }

    private fun check(value: LuaValue, errorName: String): String {
        if (value is LuaNil) {
            throw CompileError("missing signConfig $errorName")
        }
        return value.tojstring()
    }

    private fun getFile(string: String): String? {

        val file = File(string)

        if (file.isFile) {
            return string
        }

        return module
            .getFileManager()
            .resolveFile(string, module)
            .let {
                if (it.isFile) {
                    it.path
                } else null
            }
    }
}