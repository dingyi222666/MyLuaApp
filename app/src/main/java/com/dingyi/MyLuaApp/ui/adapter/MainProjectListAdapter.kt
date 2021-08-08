package com.dingyi.MyLuaApp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.common.kts.applyRoundedCorners
import com.dingyi.MyLuaApp.common.kts.layoutInflater
import com.dingyi.MyLuaApp.common.kts.toFile
import com.dingyi.MyLuaApp.databinding.LayoutItemMainProjectBinding

/**
 * @author: dingyi
 * @date: 2021/8/7 17:00
 * @description:
 **/
class MainProjectListAdapter :
    ListAdapter<ProjectInfo, MainProjectListAdapter.ViewHolder>(DiffItemCallback) {

    private var onClickListener: (ProjectInfo) -> Unit = {}

    fun setOnClickListener(block: (ProjectInfo) -> Unit) {
        this.onClickListener = block;
    }

    companion object DiffItemCallback : DiffUtil.ItemCallback<ProjectInfo>() {
        override fun areItemsTheSame(oldItem: ProjectInfo, newItem: ProjectInfo): Boolean {
            return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: ProjectInfo, newItem: ProjectInfo): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(val binding: LayoutItemMainProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutItemMainProjectBinding.inflate(
                parent.layoutInflater,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = currentList[position]

        holder.binding.apply {

            root.setOnClickListener {
                onClickListener(currentList[position])
            }

            appName.text = info.appName
            appPackageName.text = info.appPackageName

            Glide.with(image)
                .run {
                    if (info.iconPath.toFile().exists())
                        load(info.iconPath)
                    else
                        load(R.mipmap.ic_launcher_round)
                }
                .applyRoundedCorners()
                .into(image)

        }
    }


}