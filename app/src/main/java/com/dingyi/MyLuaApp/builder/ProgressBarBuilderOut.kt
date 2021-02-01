package com.dingyi.MyLuaApp.builder

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.dialogs.MyDialog
import com.dingyi.MyLuaApp.utils.createProgressBarDialog

//a simple builderOut show
class ProgressBarBuilderOut : IBuilderOut {

    var progressDialog: ProgressDialog? = null
    var title = "l"
    set(value) {
        field=value
        progressDialog?.setTitle(value)
    }


    var activity: AppCompatActivity? = null

    override fun hasMessage(string: String) {
        activity?.runOnUiThread {
            progressDialog?.setMessage(string)
        }
    }

    override fun hasError(string: String) {
        activity?.runOnUiThread {
            progressDialog?.dismiss()
            MyDialog(activity!!,(activity!! as BaseActivity<*>).themeUtil)
                    .setTitle(R.string.build_error_title)
                    .setMessage(string)
                    .setPositiveButton(android.R.string.ok,null)
                    .show()
        }
    }

    override fun init(activity: AppCompatActivity):ProgressBarBuilderOut {
        this.activity = activity
        activity.runOnUiThread {
            progressDialog = createProgressBarDialog(activity as BaseActivity<*>, title, "");
            progressDialog?.show();
        }
        return this
    }

    override fun end() {
        activity?.runOnUiThread {
            progressDialog?.dismiss()
        }
    }

}