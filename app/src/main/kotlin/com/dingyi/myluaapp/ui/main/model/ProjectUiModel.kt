package com.dingyi.myluaapp.ui.main.model

import android.view.View
import com.bumptech.glide.Glide
import com.dingyi.myluaapp.core.project.Project
import com.dingyi.myluaapp.databinding.LayoutItemMainProjectBinding
import com.drake.brv.BindingAdapter
import com.drake.brv.animation.ItemAnimation
import com.drake.brv.item.ItemBind

/**
 * @author: dingyi
 * @date: 2021/10/25 9:07
 * @description:
 **/
class ProjectUiModel(val project: Project.AppProject):ItemBind {


    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding = LayoutItemMainProjectBinding.bind(holder.itemView)
        binding.apply {
            appName.text = project.appName
            appPackageName.text = project.appPackageName
            Glide.with(image)
                .load(project.iconPath)
                .into(image)
        }

    }
}