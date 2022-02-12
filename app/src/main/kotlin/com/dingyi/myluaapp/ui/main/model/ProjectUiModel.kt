package com.dingyi.myluaapp.ui.main.model

import android.view.View
import com.bumptech.glide.Glide
import com.dingyi.myluaapp.databinding.LayoutItemMainProjectBinding
import com.dingyi.myluaapp.plugin.api.project.Project
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind

/**
 * @author: dingyi
 * @date: 2021/10/25 9:07
 * @description:
 **/
class ProjectUiModel(val project: Project) : ItemBind {


    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding = LayoutItemMainProjectBinding.bind(holder.itemView)
        binding.apply {
            appName.text = project.name
            appPackageName.text = project.packageName
            if (project.iconPath!=null) {
                Glide.with(image)
                    .load(project.iconPath)
                    .into(image)
            }

        }


    }


}