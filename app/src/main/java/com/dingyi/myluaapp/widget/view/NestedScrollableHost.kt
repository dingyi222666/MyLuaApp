package com.dingyi.myluaapp.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * @author: dingyi
 * @date: 2021/9/3 21:15
 * @description:
 **/
class NestedScrollableHost : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f

    //循环遍历找到viewPager2
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    //找到子RecyclerView
    private val child: View? get() = if (childCount > 0) getChildAt(0) else null

    init {
        //最小滑动距离
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            //判断子RecyclerView在水平方向是否可以滑动deltaX
            0 -> child?.canScrollHorizontally(direction) ?: false
            //判断子RecyclerView在竖直方向是否可以滑动deltaY
            1 -> child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val orientation = parentViewPager?.orientation ?: return

        //如果子RecyclerView在viewPager2的滑动方向上不能滑动直接返回
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            //down事件直接强制禁止父view拦截事件，后续事件先交给子RecyclerView先判断是否能够消费
            //如果这一块不强制禁止父view会导致后续事件可能直接没到子RecyclerView就被父view拦截了
            //默认RecyclerView onTouchEvent返回true但是viewPager2会在onInterceptTouchEvent拦截住
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            //计算手指滑动距离
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ORIENTATION_HORIZONTAL

            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            //滑动距离超过最小滑动值
            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                    //如果viewPager2是横向滑动但手势是竖直方向滑动，则允许所有父类拦截
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    //手势滑动方向和viewPage2是同方向的，需要询问子RecyclerView是否在同方向能滑动
                    if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        //子RecyclerView能滑动直接禁止父view拦截事件
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        //子RecyclerView不能滑动(划到第一个Item还往右滑或者划到最后面一个Item还往左划的时候)允许父view拦截
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}