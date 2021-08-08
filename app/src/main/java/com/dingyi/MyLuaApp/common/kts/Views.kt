package com.dingyi.MyLuaApp.common.kts

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dingyi.MyLuaApp.MainApplication

/**
 * @author: dingyi
 * @date: 2021/8/4 17:51
 * @description:
 **/


fun Menu.iconColor(color: Int) {
    this.children.forEach { item ->
        val drawable = item.icon
        drawable?.setTint(color)
    }
}

fun RequestBuilder<Drawable>.applyRoundedCorners(): RequestBuilder<Drawable> {
    return apply(RequestOptions.bitmapTransform(RoundedCorners(12.dp)))
        .transition(DrawableTransitionOptions.withCrossFade(160))
}

fun TextView.bindTextChangedToLiveData(data: MutableLiveData<String>) {
    addTextChangedListener(
        onTextChanged = { it, _, _, _ ->
            data.postValue(it?.toString())
        }
    )
}

inline val View.layoutInflater: LayoutInflater
    get() = android.view.LayoutInflater.from(context)

class AdapterDrawerListener(
    private val _onDrawerSlide: (View, Float) -> Unit = { _, _ -> },
    private val _onDrawerOpened: (View) -> Unit = {},
    private val _onDrawerClosed: (View) -> Unit = {},
    private val _onDrawerStateChanged: (Int) -> Unit = {}
) : DrawerLayout.DrawerListener {

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        _onDrawerSlide(drawerView, slideOffset)
    }

    override fun onDrawerOpened(drawerView: View) {
        _onDrawerOpened(drawerView)
    }

    override fun onDrawerClosed(drawerView: View) {
        _onDrawerClosed(drawerView)
    }

    override fun onDrawerStateChanged(newState: Int) {
        _onDrawerStateChanged(newState)
    }

}

fun TextView.setTextIfDiff(data: String) {
    if (this.text.toString() != data) {
        text = data
    }

}

fun String.showToast() =
    Toast.makeText(MainApplication.instance, this, Toast.LENGTH_LONG).show()

fun Int.getString(): String = MainApplication.instance.getString(this)


inline val Int.dp: Int
    get() = (MainApplication.instance.resources.displayMetrics.density * this + 0.5f).toInt()