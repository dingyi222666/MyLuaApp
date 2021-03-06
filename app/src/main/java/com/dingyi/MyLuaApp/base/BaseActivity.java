package com.dingyi.MyLuaApp.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.core.task.SimpleCoroutineManager;
import com.dingyi.MyLuaApp.core.theme.ThemeManager;
import com.dingyi.MyLuaApp.utils.TextUtils;
import com.dingyi.MyLuaApp.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected ThemeManager mThemeManager;
    private T mBinding;
    private long mLastExitTime = System.currentTimeMillis();



    private SimpleCoroutineManager coroutineManager=new SimpleCoroutineManager(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThemeManager = new ThemeManager(this);
        initView(getViewBinding());
        setContentView(getViewBinding().getRoot());
    }


    protected Handler getHandler() {
        return mHandler;
    }

    protected void initToolBar() {
        getSupportActionBar().setElevation(ViewUtils.dp2px(this, 2));
        getSupportActionBar().setSubtitle("test");
        getSupportActionBar().setTitle(getToolBarTitle());

    }

    public SimpleCoroutineManager getCoroutineManager() {
        return coroutineManager;
    }

    public ThemeManager getThemeManager() {
        return mThemeManager;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //横屏适配
        mBinding = null;//置空当前binding
        //重新获取binding
        initView(getViewBinding());
        setContentView(getViewBinding().getRoot());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ViewUtils.menuIconColor(menu, mThemeManager.getThemeColors().getImageColorFilter());
        return super.onCreateOptionsMenu(menu);
    }

    protected abstract String getToolBarTitle();

    protected abstract Class<T> getViewBindingClass();

    public abstract CoordinatorLayout getCoordinatorLayout();

    public T getViewBinding() {
        if (mBinding == null) {
            try {
                Method method = getViewBindingClass().getDeclaredMethod("inflate", LayoutInflater.class);
                method.setAccessible(true);
                mBinding = (T) method.invoke(null, getLayoutInflater());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                TextUtils.printError(e.toString());
            }
        }
        return mBinding;
    }

    protected abstract void initView(T binding);

    private boolean checkCanExit(int code) {
        if (code == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mLastExitTime < 1500) {
                finishAndRemoveTask();
            } else {
                ViewUtils.showSnackbar(mBinding.getRoot(), R.string.toast_exit);
            }

            mLastExitTime = System.currentTimeMillis();
            return true;

        }
        mLastExitTime = System.currentTimeMillis();
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
        startActivity(new Intent(this, clazz));
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        coroutineManager.cancelAllTask();
        coroutineManager=null;
    }
}
