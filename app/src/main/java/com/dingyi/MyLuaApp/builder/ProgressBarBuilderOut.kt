package com.dingyi.MyLuaApp.builder

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.utils.createProgressBarDialog

//a simple builderOut show
class ProgressBarBuilderOut: IBuilderOut {

    var progressDialog:ProgressDialog?=null
    var title="";
    var builder:IBuilder?=null;

    override fun hasMessage(string: String) {
       progressDialog?.setMessage(string)
    }

    override fun hasError(string: String) {
        builder?.stop()
    }

    override fun init(activity: AppCompatActivity) {
       progressDialog= createProgressBarDialog(activity as BaseActivity,title,"");
       progressDialog?.show();
    }

    override fun bindBuilder(builder: IBuilder) {
        this.builder=builder
    }
}