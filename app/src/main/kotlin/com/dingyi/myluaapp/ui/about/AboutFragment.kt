package com.dingyi.myluaapp.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.versionName
import com.dingyi.myluaapp.databinding.FragmentAboutBinding
import com.dingyi.myluaapp.ui.GeneralActivityViewModel

class AboutFragment : BaseFragment<FragmentAboutBinding, GeneralActivityViewModel>() {
    override fun getViewModelClass(): Class<GeneralActivityViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutBinding =
        FragmentAboutBinding
            .inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val requireActivity = requireActivity()

        if (requireActivity is AppCompatActivity) {
            requireActivity
                .supportActionBar?.apply {
                    title = R.string.about_slogan_title.getString()

                    setDisplayHomeAsUpEnabled(true)
                }
        }

        viewBinding.apply {
            versionCode.text = requireActivity.versionName
            contact.setOnClickListener {
                //发送邮件到dingyi222666@foxmail.com
                Intent(Intent.ACTION_SENDTO)
                    .apply {
                        data = Uri.parse("mailto:dingyi222666@foxmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "")
                        putExtra(Intent.EXTRA_TEXT, "")
                    }.also { startActivity(it) }
            }

            openSource.setOnClickListener {
                //打开浏览器，网址为github.com/dingyi222666/MyLuaApp

                Intent(Intent.ACTION_VIEW)
                    .apply {
                        data = Uri.parse("https://github.com/dingyi222666/MyLuaApp")
                    }.also { startActivity(it) }

            }

        }

    }
}