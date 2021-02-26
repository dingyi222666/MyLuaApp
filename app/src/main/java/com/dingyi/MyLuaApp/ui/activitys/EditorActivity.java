package com.dingyi.MyLuaApp.ui.activitys;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.androlua.LuaBaseActivity;
import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.base.BaseActivity;
import com.dingyi.MyLuaApp.bean.ProjectInfo;
import com.dingyi.MyLuaApp.core.edtior.EditorManager;
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding;
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;

public class EditorActivity extends LuaBaseActivity<ActivityEditorBinding> {

    EditorManager manager;

    ProjectInfo info;
    @Override
    protected void initToolBar() {
        super.initToolBar();
        try {
            TextView titleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbar, "mTitleTextView");
            TextView subTitleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbar, "mSubtitleTextView");
            subTitleView.setVisibility(View.GONE);
            LayoutTransition transition = new LayoutTransition();
            transition.enableTransitionType(LayoutTransition.CHANGING);
            ((ViewGroup) titleView.getParent()).setLayoutTransition(transition);
        } catch (Exception e) {
            TextUtils.printError(e.toString());
        }



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

    @Override
    protected void initView(ActivityEditorBinding binding) {
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,
                0,0);
        toggle.syncState();
        binding.drawerLayout.setScrimColor(0);
        binding.drawerLayout.addDrawerListener(toggle);

        initToolBar();
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener(){
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    binding.drawerLayout.getChildAt(0).setTranslationX(binding.drawerLayout.getChildAt(1).getWidth() * slideOffset);
                }
        });

        info=getIntent().getParcelableExtra("projectInfo");

        manager=new EditorManager(this,info,binding);
        manager.open(info.getPath()+"/main.lua");
        manager.open(info.getPath()+"/layout.aly");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
