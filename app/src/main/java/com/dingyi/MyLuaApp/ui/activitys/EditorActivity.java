package com.dingyi.MyLuaApp.ui.activitys;

import android.animation.LayoutTransition;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.androlua.LuaBaseActivity;
import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.bean.ProjectInfo;
import com.dingyi.MyLuaApp.core.editor.EditorManager;
import com.dingyi.MyLuaApp.core.project.ProjectUtil;
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding;
import com.dingyi.MyLuaApp.ui.adapters.BaseViewPager2Adapter;
import com.dingyi.MyLuaApp.ui.fragments.FileListFragment;
import com.dingyi.MyLuaApp.ui.fragments.FileTreeFragment;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;

import java.util.Objects;

public class EditorActivity extends LuaBaseActivity<ActivityEditorBinding> {

    EditorManager editorManager;

    ProjectInfo mProjectInfo;

    BaseViewPager2Adapter mSlideAdapter;


    @Override
    protected void initToolBar() {
        super.initToolBar();
        try {
            TextView titleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbarParent.toolbar, "mTitleTextView");
            TextView subTitleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbarParent.toolbar, "mSubtitleTextView");
            Objects.requireNonNull(subTitleView).setVisibility(View.GONE);
            LayoutTransition transition = new LayoutTransition();
            transition.enableTransitionType(LayoutTransition.CHANGING);
            ((ViewGroup) titleView.getParent()).setLayoutTransition(transition);
        } catch (Exception e) {
            TextUtils.printError(e.toString());
        }


    }

    @Override
    protected String getToolBarTitle() {
        ProjectInfo info = getIntent().getParcelableExtra("projectInfo");
        return info.getProjectName();
    }

    @Override
    protected Class<ActivityEditorBinding> getViewBindingClass() {
        return ActivityEditorBinding.class;
    }

    @Override
    protected void initView(ActivityEditorBinding binding) {
        setSupportActionBar(binding.toolbarParent.toolbar);

        mSlideAdapter = new BaseViewPager2Adapter(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbarParent.toolbar,
                0, 0);
        toggle.syncState();
        binding.drawerLayout.setScrimColor(0);
        binding.drawerLayout.addDrawerListener(toggle);

        initToolBar();
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                binding.drawerLayout.getChildAt(0).setTranslationX(binding.drawerLayout.getChildAt(1).getWidth() * slideOffset);
            }
        });

        mProjectInfo = getIntent().getParcelableExtra("projectInfo");

        TextView subTitleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbarParent.toolbar, "mSubtitleTextView");

        editorManager = new EditorManager(this, mProjectInfo, binding);

        editorManager.setOpenFileCallBack((str) -> {
            getSupportActionBar().setSubtitle(str);
            assert subTitleView != null;
            if (subTitleView.getVisibility() == View.GONE) {
                getHandler().postDelayed(() -> subTitleView.setVisibility(View.VISIBLE), 400);
            }
            return null;
        });


        binding.symbolView.setView(editorManager);

        editorManager.open(mProjectInfo.getProjectPath() + "/main.lua");
        editorManager.open(mProjectInfo.getProjectPath() + "/layout.aly");


        initFragment();


    }


    private void initFragment() {
        getViewBinding().slide.page.setAdapter(mSlideAdapter);
        getViewBinding().slide.page.setUserInputEnabled(true);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("editor_file_tree", false)) {
            FileTreeFragment fileTreeFragment = new FileTreeFragment();
            mSlideAdapter.add(fileTreeFragment);
            fileTreeFragment.setEvent(() -> {
                fileTreeFragment.initView(editorManager, mProjectInfo);
            });
        } else {
            FileListFragment fileListFragment = new FileListFragment();
            mSlideAdapter.add(fileListFragment);
            fileListFragment.setEvent(() -> fileListFragment.initView(editorManager, mProjectInfo));

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.run:
                editorManager.saveAll();
                ProjectUtil.runProject(this, mProjectInfo);
                break;
            case R.id.undo:
                editorManager.undo();
                break;
            case R.id.redo:
                editorManager.redo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int code, KeyEvent event) {
        if (code == KeyEvent.KEYCODE_BACK && getViewBinding().drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getViewBinding().drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyDown(code, event);
    }
}
