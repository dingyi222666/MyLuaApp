package com.dingyi.MyLuaApp.widget.views

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import com.dingyi.MyLuaApp.databinding.ViewMaginfierLayoutBinding
import com.dingyi.MyLuaApp.utils.dp2px
import com.dingyi.MyLuaApp.utils.getDecorView
import com.dingyi.MyLuaApp.utils.printError

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

    }

    private fun recycle() {
        if (binding.image.drawable is BitmapDrawable) {
            (binding.image.drawable as BitmapDrawable).bitmap?.let {
               binding.image.setImageBitmap(null)
            }
        }
    }

    private fun abs(i:Float):Float{
        if (i<0) {
            return 0f;
        }
        return i
    }

    fun show(x: Int, y: Int) {
        if (binding.root.visibility == View.GONE) {
            return
        }
        point.set(x, y)
        recycle()
        binding.root.x= abs(x.toFloat()-activity.dp2px(86/2))
        binding.root.y= abs(y.toFloat()+activity.dp2px(56/2))

        binding.image.setImageBitmap(scaleAndCropViewBitmap(
                x-activity.dp2px(86/2), y-activity.dp2px((56-12)),
                activity.dp2px(86), activity.dp2px(56),
                scale, scale)
        )
    }

    fun unbinding() {
        activity.getDecorView<ViewGroup>().removeView(binding.getRoot())
    }

    fun close() {
        closeWindow()
        recycle()
    }

    private fun closeWindow() {
        binding.root.visibility = View.GONE

    }

    fun preShow() {
        showWindow()
    }

}