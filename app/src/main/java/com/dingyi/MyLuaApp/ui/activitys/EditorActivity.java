package com.dingyi.MyLuaApp.ui.activitys;

import android.animation.LayoutTransition;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.androlua.LuaBaseActivity;
import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.bean.ProjectInfo;
import com.dingyi.MyLuaApp.core.editor.EditorManager;
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding;
import com.dingyi.MyLuaApp.ui.adapters.BaseViewPager2Adapter;
import com.dingyi.MyLuaApp.ui.fragments.FileListFragment;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;

public class EditorActivity extends LuaBaseActivity<ActivityEditorBinding> {

    EditorManager manager;

    ProjectInfo info;

    BaseViewPager2Adapter slideAdapter;


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

        slideAdapter= new BaseViewPager2Adapter(this);
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

        TextView subTitleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbar, "mSubtitleTextView");

        manager=new EditorManager(this,info,binding);

        manager.setOpenCallBack((str)->{
            getSupportActionBar().setSubtitle(str);
            if (subTitleView.getVisibility()==View.GONE) {
                getHandler().postDelayed(()-> subTitleView.setVisibility(View.VISIBLE),400);
            }
            return null;
        });

        manager.open(info.getPath()+"/main.lua");
        manager.open(info.getPath()+"/layout.aly");

        initFragment();


    }


    private void initFragment() {
        getViewBinding().slide.page.setAdapter(slideAdapter);
        slideAdapter.add(FileListFragment.INSTANCE);
        FileListFragment.INSTANCE.setOnCreateViewFunction(()->{
            FileListFragment.INSTANCE.initView(manager,info);
            return null;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int code, KeyEvent event) {
        if (code == KeyEvent.KEYCODE_BACK&&getViewBinding().drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getViewBinding().drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyDown(code, event);
    }
}
