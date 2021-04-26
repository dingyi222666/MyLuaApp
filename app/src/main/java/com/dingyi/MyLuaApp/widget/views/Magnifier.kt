package com.dingyi.MyLuaApp.widget.views

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.dingyi.MyLuaApp.databinding.ViewMaginfierLayoutBinding
import com.dingyi.MyLuaApp.utils.SharedPreferencesUtil
import com.dingyi.MyLuaApp.utils.dp2px
import com.dingyi.MyLuaApp.utils.getDecorView
import com.dingyi.MyLuaApp.utils.printError
import kotlin.properties.Delegates

//部分参数需要自己调整呢
class Magnifier(private val activity: Activity, view: View?) {




    private val binding = ViewMaginfierLayoutBinding.inflate(activity.layoutInflater, activity.getDecorView(), false)

    private val point = Point(0, 0)

    var nowView by Delegates.notNull<View>()

    var scale = 1.5f

    init {
        if (view != null) {
            nowView=view
        }
        activity.getDecorView<ViewGroup>().addView(binding.root, -2, -2)
    }

    private fun scaleAndCropViewBitmap(x: Int, y: Int, w: Int, h: Int, sx: Float, sy: Float): Bitmap? {
        runCatching {
            nowView.isDrawingCacheEnabled = true
            nowView.buildDrawingCache(true)
            val bitmap = nowView.getDrawingCache(true)

            val oldBitmap = Bitmap.createBitmap(bitmap,
                    x, y, (w /sx).toInt(), (h / sy).toInt(), Matrix().apply { setScale(sx, sy) }, false)

            nowView.isDrawingCacheEnabled = false
            nowView.destroyDrawingCache()
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
            return 0f
        }
        return i
    }

    fun show(x: Int, y: Int,scaleX:Int,scaleY:Int) {
        if (binding.root.visibility == View.GONE || !PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("editor_magnifier",true)) {
            return
        }
        point.set(x, y)
        recycle()
        binding.root.x= abs(x.toFloat()-activity.dp2px(86/2))
        binding.root.y= abs(y.toFloat()+activity.dp2px(56/2))

        binding.image.setImageBitmap(scaleAndCropViewBitmap(
                scaleX, scaleY,
                activity.dp2px(86), activity.dp2px(56),
                scale, scale)
        )
    }

    fun unbinding() {
        activity.getDecorView<ViewGroup>().removeView(binding.getRoot())
    }

    fun dismiss() {
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