package com.dingyi.MyLuaApp.ui.activitys;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.base.BaseActivity;
import com.dingyi.MyLuaApp.beans.ProjectInfo;
import com.dingyi.MyLuaApp.core.project.ProjectUtil;
import com.dingyi.MyLuaApp.core.project.helper.ProjectCreateHelper;
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding;
import com.dingyi.MyLuaApp.databinding.DialogLayoutInput2Binding;
import com.dingyi.MyLuaApp.network.builder.HttpClientBuilder;
import com.dingyi.MyLuaApp.ui.adapters.MainProjectListAdapter;
import com.dingyi.MyLuaApp.utils.FileUtils;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;
import com.dingyi.MyLuaApp.utils.ViewUtils;
import com.dingyi.MyLuaApp.widget.dialogs.BaseDialog;
import com.dingyi.MyLuaApp.widget.dialogs.InputDialog;
import com.dingyi.luaj.LuaJ;
import com.hjq.permissions.XXPermissions;

import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.LuaTable;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    protected String getToolBarTitle() {
        return "MyLuaApp";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtils.init();
    }

    @Override
    protected void initView(ActivityMainBinding binding) {
        setSupportActionBar(binding.toolbarParent.toolbar);

        binding.refresh.setColorSchemeColors(mThemeManager.getThemeColors().getColorPrimary());

        binding.refresh.setOnRefreshListener(() -> {
            ((MainProjectListAdapter) binding.mainListview.getAdapter()).clear();
            getHandler().postDelayed(() -> {
                refreshList(binding);
                binding.refresh.setRefreshing(false);
            }, 590);
        });
        try {
            checkPermission(() -> {
                refreshList(binding);
                initToolBar();
                showPoem();
            });
        } catch (PackageManager.NameNotFoundException e) {
            //自己的包名肯定不会报错
        }


    }

    public void checkPermission(Runnable runnable) throws PackageManager.NameNotFoundException {
        TextUtils.printDebug(getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions.toString());
        if (XXPermissions.isPermanentDenied(this, getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions)) {
            new BaseDialog(this)
                    .setTitle(R.string.requestPermissionsTitle)
                    .setMessage(R.string.requestPermissionsMessage)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            requestPermissions(runnable);
                        } catch (PackageManager.NameNotFoundException e) {
                            //...
                        }
                    })
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {
                        onCancelRequestPermissions();
                    })
                    .show();

        } else {
            runnable.run();
        }

    }

    private void onCancelRequestPermissions() {
        Toast.makeText(this, R.string.cancelRequestPermissionsMessage, Toast.LENGTH_LONG).show();
        getHandler().postDelayed(MainActivity.this::finish, 1000);
    }

    private void requestPermissions(Runnable runnable) throws PackageManager.NameNotFoundException {
        XXPermissions.with(this)
                .permission(getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions)
                .request((permissions, all) -> {
                    if (!all) {
                        onCancelRequestPermissions();
                    } else {
                        runnable.run();
                    }
                });
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        try {
            TextView titleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbarParent.toolbar, "mTitleTextView");
            TextView subTitleView = (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbarParent.toolbar, "mSubtitleTextView");
            subTitleView.setVisibility(View.GONE);
            subTitleView.setSingleLine(false);
            subTitleView.setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);
            LayoutTransition transition = new LayoutTransition();
            transition.enableTransitionType(LayoutTransition.CHANGING);
            ((ViewGroup) titleView.getParent()).setLayoutTransition(transition);
        } catch (Exception e) {
            TextUtils.printError(e.toString());
        }

    }

    private TextView getSubTitleView() {
        return (TextView) ReflectionUtils.getPrivateField(getViewBinding().toolbarParent.toolbar, "mSubtitleTextView");
    }

    private void showPoem() {
        TextView subTitleView = getSubTitleView();
        subTitleView.setVisibility(View.GONE);

        new HttpClientBuilder()
                .get()
                .url("https://v1.jinrishici.com/shuqing/aiqing.json")
                .execute((r, e) -> {
                    if (e != null) {
                        String[] array = MainActivity.this.getResources().getStringArray(R.array.main_poem_array);
                        int random = new Random().nextInt(array.length);
                        runOnUiThread(() -> subTitleView.setText(array[random]));
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(r.body().string());
                            String content = jsonObject.getString("content");
                            runOnUiThread(() -> subTitleView.setText(content));
                        } catch (IOException | JSONException ignored) {

                        }

                    }
                    getHandler().postDelayed(() -> subTitleView.setVisibility(View.VISIBLE), 190);
                });
    }


    private void refreshList(ActivityMainBinding binding) {
        LuaJ luaJ = new LuaJ();
        if (binding.mainListview.getAdapter() == null) {
            MainProjectListAdapter adapter = new MainProjectListAdapter(this);
            binding.mainListview.setAdapter(adapter);
        }

        MainProjectListAdapter adapter = (MainProjectListAdapter) binding.mainListview.getAdapter();

        adapter.clear();

        FileUtils.forEachDir(Objects.requireNonNull(FileUtils.getUsePaths().get("projectPath")), file -> {
            if (ProjectUtil.getProjectType(file) == ProjectUtil.LUA_PROJECT) {
                LuaTable infoTable = luaJ.loadFile(file.getPath() + "/init.lua");
                try {
                    ProjectInfo info = new ProjectInfo(file.getAbsolutePath(), ProjectUtil.LUA_PROJECT,
                            infoTable.get("appname").tojstring(), infoTable.get("appver").tojstring(),
                            infoTable.get("appcode").tojstring(), infoTable.get("packagename").tojstring());
                    adapter.add(info);
                } catch (Exception ignored) {
                    ViewUtils.showSnackbar(getViewBinding().getRoot(), R.string.find_project_error);
                }

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_menu_settings:
                newActivity(SettingsActivity.class);
                break;
            case R.id.main_action_menu_newProject:
                createChooseProjectDialog();
                break;
            case R.id.main_action_menu_about:
                new BaseDialog(this)
                        .setTitle(this.getString(R.string.about))
                        .setMessage(FileUtils.readAssetString(this, "res/txt/updateLog.txt"))
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createProjectSuccess(ProgressDialog dialog) {
        getHandler().postDelayed(() -> {
            dialog.dismiss();
            ViewUtils.showSnackbar(getViewBinding().getRoot(), R.string.createProjectSuccess);
            refreshList(getViewBinding());
        }, 400);
    }


    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return getViewBinding().getRoot();
    }

    private void createProjectDialog(String type, int pos) {
        ProgressDialog progressDialog = ViewUtils.createProgressBarDialog(this, getString(R.string.tips), getString(R.string.createProject));

        new InputDialog(this)
                .setMode(InputDialog.Mode.INPUT_2)
                .setTitle(getString(R.string.newProjectSub, type))
                .with((dialog) -> {
                    DialogLayoutInput2Binding binding = dialog.getBinding();
                    dialog.setPositiveButton(android.R.string.ok, (w, e) -> {
                        progressDialog.show();

                        new ProjectCreateHelper(this)
                                .setProjectType(pos)
                                .setProjectPath(FileUtils.getUsePaths().get("projectPath") + "/" + binding.name.getText().toString())
                                .setProjectPackageName(binding.packageName.getText().toString())
                                .setProjectName(binding.name.getText().toString())
                                .execute(() -> {
                                    createProjectSuccess(progressDialog);
                                });
                    });

                    dialog.addListener(AlertDialog.BUTTON_POSITIVE, (str) -> {
                        String path = FileUtils.getUsePaths().get("projectPath") + "/" + binding.name.getText().toString();
                        return str.replaceAll(" ", "").length() > 0 && (!FileUtils.toFile(path).exists());
                    }, binding.name);

                    dialog.addListener(AlertDialog.BUTTON_POSITIVE, (str) -> str.replaceAll(" ", "").length() > 0
                            && str.charAt(str.length() - 1) != '.', binding.packageName);

                    binding.name.setText(ProjectUtil.getProjectName());
                    binding.packageName.setText("com.MyLuaApp.MyApplication");//todo 包名可设置


                    return null;
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();

    }

    private void createChooseProjectDialog() {
        AtomicInteger nowChooseProject = new AtomicInteger();

        CharSequence[] projectTemplateNames = ProjectUtil.getProjectTemplate(this);

        new BaseDialog(this)
                .setTitle(R.string.newProject)
                .setSingleChoiceItems(projectTemplateNames, 0, (dialog, which) -> nowChooseProject.set(which))
                .setPositiveButton(android.R.string.ok, (w, s) -> {
                    createProjectDialog(projectTemplateNames[nowChooseProject.get()].toString(), nowChooseProject.get());
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
