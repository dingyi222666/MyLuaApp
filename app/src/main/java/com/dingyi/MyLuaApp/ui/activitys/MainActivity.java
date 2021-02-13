package com.dingyi.MyLuaApp.ui.activitys;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.adapters.MainProjectListAdapter;
import com.dingyi.MyLuaApp.base.BaseActivity;
import com.dingyi.MyLuaApp.bean.ProjectInfo;
import com.dingyi.MyLuaApp.core.project.ProjectUtil;
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding;
import com.dingyi.MyLuaApp.network.builder.HttpClientBuilder;
import com.dingyi.MyLuaApp.utils.FileUtils;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;
import com.dingyi.luaj.LuaJ;

import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.LuaTable;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    protected String getToolBarTitle() {
        return "MyLuaApp";
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtils.init();
    }

    @Override
    protected void initView(ActivityMainBinding binding) {
        super.initView(binding);
        setSupportActionBar(binding.toolbar);
        binding.refresh.setColorSchemeColors(themeManager.getColors().getColorPrimary());

        binding.refresh.setOnRefreshListener(()->{
            ((MainProjectListAdapter) binding.mainListview.getAdapter()).clear();
            handler.postDelayed(() -> {
                refreshList(binding);
                binding.refresh.setRefreshing(false);
            }, 590);
        });
        refreshList(binding);
        initToolBar();
        showPoem();
    }

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

    private TextView getSubTitleView() {
        return (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbar, "mSubtitleTextView");
    }

    private void showPoem() {
        TextView subTitleView = getSubTitleView();
        subTitleView.setVisibility(View.GONE);

        new HttpClientBuilder()
                .get()
                .url("https://v1.jinrishici.com/shuqing/aiqing.json")
                .enqueue((r,e)->{
                    if (e==null) {
                        String[] array = MainActivity.this.getResources().getStringArray(R.array.main_poem_array);
                        int random = new Random().nextInt(array.length);
                        runOnUiThread(()->subTitleView.setText(array[random]));
                    }else {
                        try {
                            JSONObject jsonObject = new JSONObject(r.body().string());
                            String content=jsonObject.getString("content");
                            runOnUiThread(()->subTitleView.setText(content));
                        } catch (IOException | JSONException ignored) {

                        }

                    }
                    handler.postDelayed(() -> subTitleView.setVisibility(View.VISIBLE), 190);
                });
    }



    private void refreshList(ActivityMainBinding binding) {
        LuaJ luaJ=new LuaJ();
        if (binding.mainListview.getAdapter()==null) {
            MainProjectListAdapter adapter = new MainProjectListAdapter(this);
            binding.mainListview.setAdapter(adapter);
        }

        MainProjectListAdapter adapter = (MainProjectListAdapter) binding.mainListview.getAdapter();

        adapter.clear();

        FileUtils.forEachDir(Objects.requireNonNull(FileUtils.getUsePaths().get("projectPath")), file -> {
            if (ProjectUtil.getProjectType(file) == ProjectUtil.LUA_PROJECT) {
                LuaTable infoTable = luaJ.loadFile(file.getPath() + "/init.lua");

                ProjectInfo info = new ProjectInfo(file.getAbsolutePath(), ProjectUtil.LUA_PROJECT,
                        infoTable.get("appname").tojstring(), infoTable.get("appver").tojstring(),
                        infoTable.get("appcode").tojstring(), infoTable.get("packagename").tojstring());
                adapter.add(info);

            }
            return null;
        });

        luaJ.close();

    }

    @Override
    protected Class<ActivityMainBinding> getViewBindingClass() {
        return ActivityMainBinding.class;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
