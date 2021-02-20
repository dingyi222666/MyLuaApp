package com.dingyi.MyLuaApp.ui.activitys;

import com.androlua.LuaBaseActivity;
import com.dingyi.MyLuaApp.base.BaseActivity;
import com.dingyi.MyLuaApp.bean.ProjectInfo;
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding;
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding;

public class EditorActivity extends LuaBaseActivity<ActivityEditorBinding> {



    @Override
    protected void initView(ActivityMainBinding binding) {

    }

    @Override
    protected String getToolBarTitle() {
        ProjectInfo info=getIntent().getParcelableExtra("projectInfo");
        return info.getName();
    }

    @Override
    protected Class<ActivityEditorBinding> getViewBindingClass() {
        return ActivityEditorBinding.class;
    }
}
