package com.dingyi.MyLuaApp.activitys;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dingyi.MyLuaApp.utils.ThemeUtil;
import com.dingyi.MyLuaApp.utils.ViewUtilsKt;

public class BaseActivity extends AppCompatActivity {
    public ThemeUtil themeUtil;

    private int mWidth,mHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtil=new ThemeUtil(this);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = outMetrics.heightPixels;

    }


    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public String getAssetDir() {
        return "/data/data/"+getPackageName()+"/assets";
    }
}
