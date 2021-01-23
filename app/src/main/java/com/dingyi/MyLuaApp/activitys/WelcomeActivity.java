package com.dingyi.MyLuaApp.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androlua.LuaUtil;
import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.adapters.BaseViewPager2Adapter;
import com.dingyi.MyLuaApp.adapters.WelcomePermissionAdapter;
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding;
import com.dingyi.MyLuaApp.databinding.ActivityWelcomeBinding;
import com.dingyi.MyLuaApp.databinding.ActivityWelcomePermissionBinding;
import com.dingyi.MyLuaApp.databinding.ActivityWelcomePrivacyBinding;
import com.dingyi.MyLuaApp.databinding.ActivityWelcomeReadyassetsBinding;
import com.dingyi.MyLuaApp.utils.FileUtilsKt;
import com.dingyi.MyLuaApp.utils.LogUtilsKt;
import com.dingyi.MyLuaApp.utils.SharedPreferencesUtilsKt;
import com.dingyi.MyLuaApp.utils.TextUtilsKt;
import com.dingyi.MyLuaApp.utils.ViewUtilsKt;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class WelcomeActivity extends BaseActivity {

    private ActivityWelcomeBinding binding;

    private ActivityWelcomePrivacyBinding privacyBinding;

    private ActivityWelcomeReadyassetsBinding assetsBinding;

    private ActivityWelcomePermissionBinding permissionBinding;

    private BaseViewPager2Adapter adapter;

    private unZipTask task;

    private int step = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String isStart=SharedPreferencesUtilsKt.get(this, "isStart", "");
        String versionName=SharedPreferencesUtilsKt.get(this, "versionName", "");
        try {
            if (isStart.equals("true") && !versionName.equals(getPackageManager().getPackageInfo(getPackageName(), 0).versionName)) {
                initView();
                initClick();
                fastUpdate();
                return;
            } else if (isStart.equals("true") && versionName.equals(getPackageManager().getPackageInfo(getPackageName(), 0).versionName)) {
                startMain();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            startMain();
        }
        initView();
        initClick();
    }


    private void fastUpdate() {
        step = 2;//直接跳到第2步
        stepChange();//运行一次步骤更换
        binding.previousBotton.setVisibility(View.GONE);//隐藏上一步
        binding.nextBotton.setText(R.string.complete);
        binding.nextBotton.setOnClickListener(v -> startMain());//点击下一步直接启动
    }


    private void initView() {
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this));

        setSupportActionBar(binding.toolbar);

        binding.collapsingToolbar.setExpandedTitleColor(themeUtil.getColorPrimary());
        binding.collapsingToolbar.setCollapsedTitleTextColor(themeUtil.getColorPrimary());

        setContentView(binding.getRoot());

        binding.scrollView.setFillViewport(true);

        adapter = new BaseViewPager2Adapter(this);

        binding.welcomePageview.setAdapter(adapter);

        binding.welcomePageview.setUserInputEnabled(false);

        //构建开始view
        TextView startTextView = new TextView(this);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        marginLayoutParams.leftMargin = ViewUtilsKt.dp2px(this, 12);
        marginLayoutParams.rightMargin = ViewUtilsKt.dp2px(this, 12);
        marginLayoutParams.topMargin = ViewUtilsKt.dp2px(this, 12);
        startTextView.setLayoutParams(marginLayoutParams);
        startTextView.setTextColor(themeUtil.getTextColor());
        startTextView.setTextSize(16);//16sp
        startTextView.setText(getString(R.string.welcomeStart));

        adapter.add(startTextView);

        privacyBinding = ActivityWelcomePrivacyBinding.inflate(LayoutInflater.from(this));

        adapter.add(privacyBinding.getRoot());

        assetsBinding = ActivityWelcomeReadyassetsBinding.inflate(LayoutInflater.from(this));

        adapter.add(assetsBinding.getRoot());

        permissionBinding = ActivityWelcomePermissionBinding.inflate(LayoutInflater.from(this));

        adapter.add(permissionBinding.getRoot());


        WelcomePermissionAdapter permissionAdapter = new WelcomePermissionAdapter(this);

        permissionAdapter.add(getString(R.string.requestPhonePermissionTitle), getString(R.string.requestPhonePermission), R.drawable.ic_twotone_local_phone_24);
        permissionAdapter.add(getString(R.string.requestSdcardPermissionTitle), getString(R.string.requestSdcardPermission), R.drawable.ic_twotone_sd_card_24);
        permissionBinding.listview.setAdapter(permissionAdapter);
        permissionAdapter.notifyDataSetChanged();


        //构建结束view
        TextView endTextView = new TextView(this);
        endTextView.setLayoutParams(marginLayoutParams);
        endTextView.setTextColor(themeUtil.getTextColor());
        endTextView.setTextSize(16);//16sp
        endTextView.setText(getString(R.string.welcomeEnd));

        adapter.add(endTextView);

    }

    private void stepChange() {
        if (step == 2 && task != null) {
            task.canCancel = true;
            task = null;
        }
        if (step != 1) {
            binding.nextBotton.setVisibility(View.VISIBLE);
        }
        switch (step) {
            case 0:
                binding.collapsingToolbar.setTitle(getString(R.string.welcomeTitle));
                break;
            case 1:
                binding.collapsingToolbar.setTitle(getString(R.string.readPrivacyTitle));
                readPrivacyPolicy();
                break;
            case 2:
                binding.collapsingToolbar.setTitle(getString(R.string.readyAssetsTitle));
                readyAssets();
                break;
            case 3:
                binding.collapsingToolbar.setTitle(getString(R.string.requestPermissionTitle));
                break;
            case 4:
                requestPermissions();
                break;
            case 5:

                startMain();
                break;
        }

        binding.welcomePageview.setCurrentItem(step);
        binding.scrollView.smoothScrollTo(0, 0);
        binding.appBar.setExpanded(true);
    }

    private void startMain() {
        try {
            SharedPreferencesUtilsKt.put(this, "versionName", getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferencesUtilsKt.put(this, "isStart", "true");
        Intent intent=new Intent(this.getApplicationContext(), MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//新建
        startActivity(intent);
        new Handler(Looper.getMainLooper()).postDelayed(this::finish,200);
    }

    private void requestPermissions() {
        binding.welcomePageview.setCurrentItem(3);//先设置回3页

        List<String> permission = new ArrayList<>();

      //  if (Build.VERSION.SDK_INT>29) {
       //     permission.add(Permission.MANAGE_EXTERNAL_STORAGE);
       // } else {
            permission.addAll(Arrays.asList(Permission.Group.STORAGE));
       // }

        permission.add(Permission.READ_PHONE_STATE);

        XXPermissions.with(this)
                .permission(permission)

                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> granted, boolean all) {
                        if (all) {
                            binding.welcomePageview.setCurrentItem(4);
                            binding.nextBotton.setText(R.string.complete);
                            return;
                        }
                        TextUtilsKt.showToast(WelcomeActivity.this, R.string.exitMessage);
                        try {
                            Runtime.getRuntime().exec("pm clear " + WelcomeActivity.this.getPackageName()).waitFor();
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDenied(List<String> denied, boolean quick) {

                        TextUtilsKt.showToast(WelcomeActivity.this, R.string.exitMessage);
                        try {
                            Runtime.getRuntime().exec("pm clear " + WelcomeActivity.this.getPackageName()).waitFor();
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }

                        step = 3;
                        stepChange();
                    }
                });

    }


    private void readyAssets() {
        assetsBinding.getRoot().setVisibility(View.VISIBLE);
        assetsBinding.progressBar.setVisibility(View.VISIBLE);
        binding.previousBotton.setVisibility(View.GONE); //设置肯定不显示
        task = new unZipTask();
        task.onCallBack(s -> {
            binding.previousBotton.setVisibility(View.VISIBLE);
            assetsBinding.progressBar.setVisibility(View.GONE);
            assetsBinding.textView.setText(getString(R.string.readyAssetsUnZipOkTitle));
            return null;
        });
        task.execute();
    }

    private void initClick() {
        binding.nextBotton.setOnClickListener(v -> {
            step++;

            if (step > 0) {
                binding.previousBotton.setVisibility(View.VISIBLE);
            }
            stepChange();
        });

        binding.previousBotton.setOnClickListener(v -> {
            step--;

            if (step < 1) {
                v.setVisibility(View.GONE);
            }

            stepChange();
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void readPrivacyPolicy() {
        privacyBinding.webView.loadUrl("file:///android_asset/res/html/privacypolicy.html");
        privacyBinding.checkBox.setOnCheckedChangeListener((v, s) -> {
            binding.nextBotton.setVisibility(s ? View.VISIBLE : View.GONE);
        });

        privacyBinding.checkBox.setChecked(false);
        binding.nextBotton.setVisibility(View.GONE);
    }

    @SuppressWarnings("all")
    private class unZipTask extends AsyncTask<String, String, String> {

        Function1 function;

        boolean canCancel = false;

        @Override
        protected void onCancelled(String s) {
            function.invoke(s);
        }

        @Override
        protected void onPostExecute(String s) {
            function.invoke(s);
        }

        private Object unApk(@NotNull String dir, String extDir) throws IOException {
            int i = dir.length() + 1;
            ZipFile zip = new ZipFile(WelcomeActivity.this.getApplicationInfo().publicSourceDir);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {

                if (canCancel == true) {
                    return null;
                }
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.indexOf(dir) != 0)
                    continue;
                String path = name.substring(i);
                if (entry.isDirectory()) {
                    File f = new File(extDir + File.separator + path);
                    if (!f.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        f.mkdirs();
                    }
                } else {
                    String fname = extDir + File.separator + path;
                    File ff = new File(fname);
                    File temp = new File(fname).getParentFile();
                    if (!temp.exists()) {
                        if (!temp.mkdirs()) {
                            throw new RuntimeException("create file " + temp.getName() + " fail");
                        }
                    }
                    this.publishProgress(WelcomeActivity.this.getString(R.string.readyAssetsUnZipTitle) + name);
                    try {
                        if (ff.exists() && entry.getSize() == ff.length() && LuaUtil.getFileMD5(zip.getInputStream(entry)).equals(LuaUtil.getFileMD5(ff)))
                            continue;
                    } catch (NullPointerException ignored) {
                    }
                    FileOutputStream out = new FileOutputStream(extDir + File.separator + path);
                    InputStream in = zip.getInputStream(entry);
                    byte[] buf = new byte[4096];
                    int count = 0;
                    while ((count = in.read(buf)) != -1) {
                        out.write(buf, 0, count);
                    }
                    out.close();
                    in.close();
                }
            }
            zip.close();
            return null;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                unApk("assets", WelcomeActivity.this.getApplicationInfo().dataDir + File.separator + "assets");
                unApk("lua", WelcomeActivity.this.getApplicationInfo().dataDir + File.separator + "app_lua");
                //unZipAssets("main.alp", extDir);
            } catch (IOException e) {
                LogUtilsKt.e(e.getMessage());
                return "失败";
            }
            return "完成";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            assetsBinding.textView.setText(values[0]);
        }


        public void onCallBack(Function1<? extends Object, Object> function) {
            this.function = function;

        }
    }

}
