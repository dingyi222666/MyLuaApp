package com.dingyi.MyLuaApp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.adapters.MainFileListAdapter;
import com.dingyi.MyLuaApp.base.BaseActivity;
import com.dingyi.MyLuaApp.bean.ProjectInfo;
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding;
import com.dingyi.MyLuaApp.databinding.ActivityMainDialogNewProjectBinding;
import com.dingyi.MyLuaApp.dialogs.MyDialog;
import com.dingyi.MyLuaApp.impl.TextWatcherImpl;
import com.dingyi.MyLuaApp.utils.CallBack;
import com.dingyi.MyLuaApp.utils.FileUtilsKt;
import com.dingyi.MyLuaApp.utils.LogUtilsKt;
import com.dingyi.MyLuaApp.utils.ProjectUtilKt;
import com.dingyi.MyLuaApp.utils.ReflectionUtilsKt;
import com.dingyi.MyLuaApp.utils.SimpleOkHttpUiltsKt;
import com.dingyi.MyLuaApp.utils.TextUtilsKt;
import com.dingyi.MyLuaApp.utils.ViewUtilsKt;
import com.dingyi.luaj.LuaJ;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.LuaTable;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Response;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    LuaJ luaJ = new LuaJ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.toolbar);

        setContentView(binding.getRoot());

        startToolBarAnim();

        FileUtilsKt.init();

        initView();
    }

    private void initView() {

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle("test");//


        binding.refresh.setColorSchemeColors(themeUtil.getColorPrimary());

        binding.refresh.setProgressViewEndTarget(true, (int) (getHeight()*0.1));

        binding.refresh.setOnRefreshListener(() -> {
            ((MainFileListAdapter) binding.mainListview.getAdapter()).clear();
            new Handler(Looper.getMainLooper()).postDelayed(()->{  initProjectListView(); binding.refresh.setRefreshing(false);},200);
        });

        initProjectListView();
    }

    private void startToolBarAnim() {
        try {
            TextView titleView = (TextView) ReflectionUtilsKt.getPrivateField(binding.toolbar, "mTitleTextView");
            TextView subTitleView = (TextView) ReflectionUtilsKt.getPrivateField(binding.toolbar, "mSubtitleTextView");
            subTitleView.setVisibility(View.GONE);

            ((ViewGroup) titleView.getParent()).setLayoutTransition(ViewUtilsKt.getLayoutTransition());

            setSubTitlePoems(subTitleView);
        } catch (Exception e) {
            LogUtilsKt.e(e.toString());
        }
    }

    private void setSubTitlePoems(TextView tv) {
        SimpleOkHttpUiltsKt.get(this, "https://v1.jinrishici.com/shuqing/aiqing.json", new CallBack() {
            @Override
            public void fail() {
                String[] array = MainActivity.this.getResources().getStringArray(R.array.main_poem_array);
                int random = new Random().nextInt(array.length);
                tv.setText(array[random]);
                new Handler(Looper.getMainLooper()).postDelayed(() -> tv.setVisibility(View.VISIBLE), 190);
            }

            @Override
            public void successful(@NotNull Response result) {
                try {
                    JSONObject jsonObject = new JSONObject(result.body().string());
                    //TextUtilsKt.showSnackBar(MainActivity.this.binding.getRoot(),jsonObject.getString("content"));
                    tv.setText(jsonObject.getString("content"));
                    new Handler(Looper.getMainLooper()).postDelayed(() -> tv.setVisibility(View.VISIBLE), 190);
                } catch (IOException | JSONException e) {
                    LogUtilsKt.e(e.toString());
                }
            }
        });
    }

    private void initProjectListView() {

        if (binding.mainListview.getAdapter()==null) {
            MainFileListAdapter adapter = new MainFileListAdapter(this);

            binding.mainListview.setAdapter(adapter);

            adapter.setOnClickListener(info -> {
                //TextUtilsKt.showSnackBar(binding.getRoot(),info.getName());

                Intent intent=new Intent(this,EditorActivity.class);
                intent.putExtra("info",info);
                startActivity(intent);
            });

            binding.mainListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.getOnClickListener().onClick((ProjectInfo) adapter.getItem(position));
                }
            });
        }

        MainFileListAdapter adapter = (MainFileListAdapter) binding.mainListview.getAdapter();

        adapter.clear();


        FileUtilsKt.forEachDir(Objects.requireNonNull(FileUtilsKt.getUsePaths().get("projectPath")), file -> {
            if (ProjectUtilKt.getProjectType(file) == ProjectUtilKt.LUA_PROJECT) {
                LuaTable infoTable = luaJ.loadFile(file.getPath() + "/init.lua");
                if (infoTable.keyCount()==0) {
                     TextUtilsKt.showSnackBar(binding.getRoot(),R.string.find_project_error);
                } else {
                    ProjectInfo info = new ProjectInfo(file.getAbsolutePath(), ProjectUtilKt.LUA_PROJECT,
                            infoTable.get("appname").tojstring(), infoTable.get("appver").tojstring(),
                            infoTable.get("appcode").tojstring(), infoTable.get("packagename").tojstring());
                    adapter.add(info);
                }

            }
            return null;
        });
    }

    private void showChooseProjectDialog() {
        AtomicInteger nowChooseProject = new AtomicInteger();

        CharSequence[] projectTemplateNames=ProjectUtilKt.getProjectTemplate(this);

        new MyDialog(this, themeUtil)
                .setTitle(R.string.newProject)
                .setSingleChoiceItems(projectTemplateNames, 0, (dialog, which) -> nowChooseProject.set(which))
                .setPositiveButton(android.R.string.ok, (w, s) -> showCreateProjectDialog(projectTemplateNames[nowChooseProject.get()].toString(),nowChooseProject.get()))
                .setNeutralButton(android.R.string.cancel,null)
                .show();

    }

    private void showCreateProjectDialog(String name,int choose) {

        ActivityMainDialogNewProjectBinding binding=ActivityMainDialogNewProjectBinding.inflate(LayoutInflater.from(this));

        ProgressDialog dialog = ViewUtilsKt.createProgressBarDialog(this,getString(R.string.tips),getString(R.string.createProject));

        AlertDialog newProject=new MyDialog(this,themeUtil)
                .setTitle(getString(R.string.newProjectSub,name))
                .setView(binding.getRoot())
                .setPositiveButton(android.R.string.ok,(a,which)-> {
                    dialog.show();
                    new Thread(()->{
                       ProjectUtilKt.createProject(MainActivity.this,choose,FileUtilsKt.getUsePaths().get("projectPath")+"/"+
                               binding.name.getText().toString(),binding.name.getText().toString(),binding.packageName.getText().toString());
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MainActivity.this.runOnUiThread(()->{
                            dialog.dismiss();
                            TextUtilsKt.showSnackBar(MainActivity.this.binding.getRoot(),R.string.createProjectSuccess);
                            initProjectListView();
                        });
                    }).start();
                })
                .show();

        View buttonView=newProject.getButton(AlertDialog.BUTTON1);

        binding.name.addTextChangedListener(new TextWatcherImpl(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                buttonView.setEnabled(!ProjectUtilKt.hasProject(s.toString()) && binding.packageName.getText().length()>0 &&!binding.packageName.getText().toString().substring(binding.packageName.getText().toString().length()-1).equals("."));
            }
        });

        binding.packageName.addTextChangedListener(new TextWatcherImpl(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                buttonView.setEnabled(!ProjectUtilKt.hasProject(binding.name.getText().toString()) && binding.packageName.getText().length()>0 && !binding.packageName.getText().toString().substring(binding.packageName.getText().toString().length()-1).equals("."));
            }
        });

        binding.name.setText(ProjectUtilKt.smartGetProjectName());
        binding.packageName.setText("com.MyLuaApp.MyApplication");//todo

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!TextUtilsKt.binSlideOutToRight(keyCode, this, binding.getRoot())) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        luaJ.close();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initProjectListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        ViewUtilsKt.foreachSetMenuIconColor(menu, themeUtil.getImageColorFilter());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_menu_about:
                new MyDialog(this, themeUtil)
                        .setTitle(this.getString(R.string.about))
                        .setMessage(FileUtilsKt.getAssetString(this, "res/txt/updateLog.txt"))
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                break;
            case R.id.main_action_menu_newProject:
                showChooseProjectDialog();
                break;

        }
        return true;
    }
}