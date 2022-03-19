package com.dingyi.myluaapp.ui.import

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ImportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        val uri = intent
            .data

        if (uri == null) {
            Toast
                .makeText(this,"非法启动",Toast.LENGTH_LONG)
                .show()
            finish()
            return
        }

        val inputStream = uri.let {
            contentResolver
                .openInputStream(
                    uri
                )
        }


        val suffix = MimeTypeMap.getFileExtensionFromUrl(uri.toString())

        when (suffix) {

            "mpk" -> {

            }

            else -> {
                inputStream?.close()
                Toast
                    .makeText(this,"非法启动",Toast.LENGTH_LONG)
                    .show()
                finish()
                return
            }
        }





    }
}