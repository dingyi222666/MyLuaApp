package com.dingyi.MyLuaApp.widget.dialogs

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.databinding.DialogLayoutInput1Binding
import com.dingyi.MyLuaApp.databinding.DialogLayoutInput2Binding
import com.google.android.material.internal.TextWatcherAdapter
import com.google.android.material.textfield.TextInputEditText

class InputDialog(private val activity: BaseActivity<*>):BaseDialog(activity) {

    private var mAlertDialog:AlertDialog?=null;
    private val mode=Mode.INPUT_1

    private var mBinding:ViewBinding?=null;

    fun setMode(mode:Mode) :InputDialog{
        mBinding = when (mode) {
            Mode.INPUT_1 -> {
                DialogLayoutInput1Binding.inflate(activity.layoutInflater);
            }
            Mode.INPUT_2 -> {
                DialogLayoutInput2Binding.inflate(activity.layoutInflater);
            }
        }
        return this;
    }

    fun with(block: (self:InputDialog)->Unit):InputDialog {
        block(this)
        return this
    }


    fun setTitle(title:String):InputDialog {
        super.setTitle(title)
        return this;
    }

    fun <T:ViewBinding> getBinding():T {
        return when(mode) {
            Mode.INPUT_1 -> mBinding!! as T
            Mode.INPUT_2 -> mBinding!! as T
        }
    }

    @SuppressLint("RestrictedApi")
    fun addListener(button:Int, listener:Listener, edit:TextInputEditText):InputDialog {
        edit.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                val isEnabled=listener.isEnabled(s.toString())
                mAlertDialog?.getButton(button)?.isEnabled=isEnabled
            }
        })
        return this;
    }


    override fun show(): AlertDialog {
        setView(mBinding?.root)
        mAlertDialog=super.show()
        return mAlertDialog as AlertDialog
    }

    enum class Mode {
        INPUT_1,INPUT_2
    }

    interface Listener {
        fun isEnabled(text:String):Boolean
    }

}