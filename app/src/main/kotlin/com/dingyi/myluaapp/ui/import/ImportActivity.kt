package com.dingyi.myluaapp.ui.import

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.core.helper.ImportHelper
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = intent

        val uri = intent
            .data

        if (uri == null) {
            showFinishFailedToast()
            return
        }

        contentResolver
            .openInputStream(uri)
            .apply {
                if (this == null) {
                    showFinishFailedToast()
                    return
                } else this.close()
            }


        kotlin.runCatching {

            when (MimeTypeMap.getFileExtensionFromUrl(uri.toString())) {

                "mpk" -> {
                    showPluginInfoDialog(
                        uri
                    )
                }

                else -> {
                    showFinishFailedToast()
                    return
                }
            }
        }.onFailure {
            showFinishFailedToast()
            throw it
        }


    }

    private fun showFinishFailedToast() {
        Toast
            .makeText(this, "非法启动", Toast.LENGTH_LONG)
            .show()
        finish()
    }

    private fun showPluginInfoDialog(uri: Uri) {

        val (info, result) = ImportHelper
            .getPluginInfo(
                contentResolver
                    .openInputStream(
                        uri
                    ).checkNotNull(),
                listOf(
                    "pluginName" to R.string.import_plugin_name,
                    "pluginAuthor" to R.string.import_plugin_author,
                    "pluginId" to R.string.import_plugin_id,
                    "pluginDescription" to R.string.import_plugin_description,
                    "pluginVersion" to R.string.import_plugin_version,
                    "pluginVersionCode" to R.string.import_plugin_version_code,
                    "pluginMainClass" to R.string.import_plugin_class_name
                ).map { it.first to it.second.getString() }
            )

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.import_plugin_title.getString() + result["pluginName"])
            .setCancelable(true)
            .setOnCancelListener {
                finish()
            }
            .setMessage(info)
            .setNeutralButton(android.R.string.cancel) { _, _ -> finish() }
            .setPositiveButton(R.string.import_dialog_plugin, null)
            .show()

        dialog.apply {
            window?.setWindowAnimations(R.style.BaseDialogAnim)
            dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                importPlugin(uri) {
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.dismiss()
                }
            }
        }

    }

    private fun importPlugin(uri: Uri, callback: () -> Unit) {
        ImportHelper
            .importPlugin(
                contentResolver
                    .openInputStream(
                        uri
                    ).checkNotNull()
            ) {

                lifecycleScope.launch {

                    PluginModule
                        .installPlugin(it.path)

                    withContext(Dispatchers.IO) {
                        it.delete()
                    }

                    callback.invoke()

                    showImportSuccessToast()
                }
            }
    }

    private fun showImportSuccessToast() {
        Toast
            .makeText(this, R.string.import_success_toast, Toast.LENGTH_LONG)
            .show()
        finish()
    }
}