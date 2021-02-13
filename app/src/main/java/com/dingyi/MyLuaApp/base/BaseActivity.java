package com.dingyi.MyLuaApp.base;

import android.animation.LayoutTransition;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.dingyi.MyLuaApp.core.theme.ThemeManager;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected Handler handler=new Handler(Looper.getMainLooper());

    private T binding;

    protected ThemeManager themeManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeManager=new ThemeManager(this);
        initView(getViewBinding());
        setContentView(getViewBinding().getRoot());
    }


    protected void initToolBar() {
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setSubtitle("test");
        getSupportActionBar().setTitle(getToolBarTitle());

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    protected void initView(T binding) {

    }

}
