package com.dingyi.myluaapp.common.kts

import android.animation.LayoutTransition
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
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
import com.dingyi.myluaapp.MainApplication
import com.google.android.material.snackbar.Snackbar

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

fun ViewGroup.addLayoutTransition() {
    layoutTransition = LayoutTransition().apply {
        enableTransitionType(LayoutTransition.CHANGING)
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
    get() = LayoutInflater.from(context)

inline fun DrawerLayout.addDrawerListener(
    crossinline onDrawerSlide: (View, Float) -> Unit = { _, _ -> },
    crossinline onDrawerOpened: (View) -> Unit = {},
    crossinline onDrawerClosed: (View) -> Unit = {},
    crossinline onDrawerStateChanged: (Int) -> Unit = {}
) {

    addDrawerListener(object : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            onDrawerSlide(drawerView, slideOffset)
        }

        override fun onDrawerOpened(drawerView: View) {
            onDrawerOpened(drawerView)
        }

        override fun onDrawerClosed(drawerView: View) {
            onDrawerClosed(drawerView)
        }

        override fun onDrawerStateChanged(newState: Int) {
            onDrawerStateChanged(newState)
        }

    })
}

fun TextView.setTextIfDiff(data: String) {
    if (this.text.toString() != data) {
        text = data
    }

}

fun String.showToast() =
    Toast.makeText(MainApplication.instance, this, Toast.LENGTH_LONG).show()

fun String.showSnackBar(view: View) =
    Snackbar.make(view, this, Snackbar.LENGTH_LONG)
        .apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
        }.show()




inline val Int.dp: Int
    get() = (MainApplication.instance.resources.displayMetrics.density * this + 0.5f).toInt()