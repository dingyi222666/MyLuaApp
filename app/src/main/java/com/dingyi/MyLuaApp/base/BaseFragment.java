package com.dingyi.MyLuaApp.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.viewbinding.ViewBinding;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.core.theme.ThemeManager;
import com.dingyi.MyLuaApp.utils.TextUtils;
import com.dingyi.MyLuaApp.utils.ViewUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected Handler handler=new Handler(Looper.getMainLooper());

    private T binding;

    private Event events;

    public void setEvent(Event event) {
        this.events=event;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(getViewBinding(inflater,container));
        if (events!=null) {
            events.onCreateView();
        }
        return getViewBinding(inflater,container).getRoot();
    }

    protected Handler getHandler() {
        return handler;
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected abstract Class<T> getViewBindingClass();

    public T getViewBinding(LayoutInflater inflater,ViewGroup root) {
        if (binding == null) {
            try {
                Method method=getViewBindingClass().getDeclaredMethod("inflate", LayoutInflater.class,ViewGroup.class,Boolean.TYPE);
                method.setAccessible(true);
                binding= (T) method.invoke(null,inflater,root,false);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                TextUtils.printError(e.toString());
            }
        }
        return binding;
    }

    public  T getViewBinding() {
        return binding;
    }

    protected abstract void initView(T binding);


    public interface Event {
        void onCreateView();
    }

}
