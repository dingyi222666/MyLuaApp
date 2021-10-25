package com.dingyi.myluaapp.ui.newproject

import com.dingyi.myluaapp.base.BaseActivity
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.databinding.ActivityNewProjectBinding

/**
 * @author: dingyi
 * @date: 2021/10/25 19:43
 * @description:
 **/
class NewProjectActivity: BaseActivity<
        ActivityNewProjectBinding,MainViewModel>() {
    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun getViewBindingInstance(): ActivityNewProjectBinding {
        return ActivityNewProjectBinding.inflate(layoutInflater)
    }
}