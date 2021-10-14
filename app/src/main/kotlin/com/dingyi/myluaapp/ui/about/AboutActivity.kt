package com.dingyi.myluaapp.ui.about

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dingyi.myluaapp.BuildConfig
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.applyRoundedCorners
import com.drakeet.about.AbsAboutActivity

/**
 * @author: dingyi
 * @date: 2021/9/4 20:42
 * @description:
 **/
class AboutActivity : AbsAboutActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        Glide.with(this)
            .load(R.mipmap.ic_launcher_round)
            .applyRoundedCorners()
            .into(icon)

        slogan.visibility = View.INVISIBLE
        version.text = "V ${BuildConfig.VERSION_NAME}"
    }

    override fun onItemsCreated(items: MutableList<Any>) {

    }
}