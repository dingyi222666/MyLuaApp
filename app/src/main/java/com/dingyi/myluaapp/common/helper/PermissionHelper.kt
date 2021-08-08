package com.dingyi.myluaapp.common.helper

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.R
import com.permissionx.guolindev.PermissionX

/**
 * @author: dingyi
 * @date: 2021/7/14 23:03
 * @description:
 **/
class PermissionHelper(private val context: AppCompatActivity) {


    fun requestExternalStoragePermission(block: () -> Unit) {

        PermissionX.init(context)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    context.getString(R.string.permission_request_sdcard),
                    context.getString(android.R.string.ok),
                    context.getString(android.R.string.cancel)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    context.getString(R.string.permission_request_sdcard_denied),
                    context.getString(android.R.string.ok),
                    context.getString(android.R.string.cancel)
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    block()

                }
            }

    }
}