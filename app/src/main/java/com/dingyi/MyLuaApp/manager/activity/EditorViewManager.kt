package com.dingyi.MyLuaApp.manager.activity

import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.activitys.EditorActivity
import com.dingyi.MyLuaApp.base.BaseViewManager
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding
import com.dingyi.MyLuaApp.utils.getDefaultPath
import com.dingyi.MyLuaApp.utils.showSnackBar
import com.google.android.material.tabs.TabLayout

class EditorViewManager(val activity: EditorActivity,val binding: ActivityEditorBinding) : BaseViewManager() {

     fun deleteTab(tab: TabLayout.Tab) {
        val text: String = binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)!!.text.toString()
        var pos = binding.tabLayout.selectedTabPosition //这里需要对pos计算下
        if (tab.position < pos) { //不需要等于判断 等于就不会走select的if了
            pos --;
        }
        binding.tabLayout.removeTab(tab)

         if (binding.tabLayout.tabCount == 0) {
             activity.util.openFile(getDefaultPath(activity.info.path!!))
         }

        if (binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)!!.text.toString() == text) {
            binding.tabLayout.getTabAt(pos)!!.select() //如果删除的不是选择的标签 就重新选择已经选择的标签
        }

        showSnackBar(binding.getRoot(), activity.getString(R.string.delete_tag))
    }

}