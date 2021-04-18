package com.dingyi.MyLuaApp.base;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.core.theme.ThemeManager;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;
import com.dingyi.MyLuaApp.utils.ViewUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected Handler handler=new Handler(Looper.getMainLooper());

    private T binding;

    protected ThemeManager themeManager;

    private long lastListenerTime = System.currentTimeMillis();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeManager=new ThemeManager(this);
        initView(getViewBinding());
        setContentView(getViewBinding().getRoot());
    }


    protected Handler getHandler() {
        return handler;
    }

    protected void initToolBar() {
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setSubtitle("test");
        getSupportActionBar().setTitle(getToolBarTitle());

    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //横屏适配
        binding=null;//置空当前binding
        //重新获取binding
        initView(getViewBinding());
        setContentView(getViewBinding().getRoot());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ViewUtils.menuIconColor(menu,themeManager.getColors().getImageColorFilter());
        return super.onCreateOptionsMenu(menu);
    }

    protected abstract String getToolBarTitle();

    protected abstract Class<T> getViewBindingClass();

    protected T getViewBinding() {
        if (binding == null) {
            try {
                Method method=getViewBindingClass().getDeclaredMethod("inflate", LayoutInflater.class);
                method.setAccessible(true);
                binding= (T) method.invoke(null,getLayoutInflater());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                TextUtils.printError(e.toString());
            }
        }
        return binding;
    }

    protected abstract void initView(T binding);

    private boolean checkCanExit(int code) {
        if (code == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastListenerTime < 1500) {
               finishAndRemoveTask();
            } else {
                ViewUtils.showSnackbar(binding.getRoot(), R.string.toast_exit);
            }

            lastListenerTime = System.currentTimeMillis();
            return true;

        }
        lastListenerTime = System.currentTimeMillis();
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!checkCanExit(keyCode)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public void newActivity(Class<?> clazz) {
        startActivity(new Intent(this,clazz));
    }

}
