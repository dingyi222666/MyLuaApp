package com.dingyi.MyLuaApp.widget.views

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.databinding.ViewMaginfierLayoutBinding
import com.dingyi.MyLuaApp.utils.dp2px
import com.dingyi.MyLuaApp.utils.getDecorView
import com.dingyi.MyLuaApp.utils.printError
import kotlin.properties.Delegates

class Magnifier(private val activity: Activity, private val view: View) {


    private val binding = ViewMaginfierLayoutBinding.inflate(activity.layoutInflater, activity.getDecorView(), false)

    val point = Point(0, 0)


    var scale = 1.5f

    init {

        activity.getDecorView<ViewGroup>().addView(binding.root, -2, -2)

    }

    private fun scaleAndCropViewBitmap(x: Int, y: Int, w: Int, h: Int, sx: Float, sy: Float): Bitmap? {
        runCatching {

            view.isDrawingCacheEnabled = true
            view.buildDrawingCache(true)
            val bitmap = view.getDrawingCache(true)

            val oldBitmap = Bitmap.createBitmap(bitmap,
                    x, y, (w /sx).toInt(), (h / sy).toInt(), Matrix().apply { setScale(sx, sy) }, false)

            view.isDrawingCacheEnabled = false
            view.destroyDrawingCache()
            return oldBitmap
        }.onFailure {
            //close()
            printError(it.toString())
        }
        return null
    }

    private fun showWindow() {
        binding.root.visibility = View.VISIBLE
        //TODO 显示动画
    }

    private fun recycle() {
        if (binding.image.drawable is BitmapDrawable) {
            (binding.image.drawable as BitmapDrawable).bitmap?.let {
               binding.image.setImageBitmap(null)
            }
        }
    }

    fun show(x: Int, y: Int) {
        if (binding.root.visibility == View.GONE) {
            return
        }
        point.set(x, y)
        recycle()
        binding.root.x= x.toFloat()-activity.dp2px(86/2)
        binding.root.y= y.toFloat()
        binding.image.setImageBitmap(scaleAndCropViewBitmap(
                x-activity.dp2px(86/2), y-activity.dp2px((38-4)),
                activity.dp2px(86), activity.dp2px(38),
                scale, scale)
        )
    }

    fun close() {
        closeWindow()
        recycle()
    }

    private fun closeWindow() {
        binding.root.visibility = View.GONE
        //TODO 关闭动画
    }

    fun preShow() {
        showWindow()
    }

}