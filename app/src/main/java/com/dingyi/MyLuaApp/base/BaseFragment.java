package com.dingyi.MyLuaApp.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.dingyi.MyLuaApp.utils.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    private T mBinding;

    private BaseActivity<?> mActivity;

    private Event mEvents;


    public BaseFragment(BaseActivity<?> activity) {
        mActivity = activity;
    }

    public void setEvent(Event event) {
        this.mEvents = event;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(getViewBinding(inflater, container));
        if (mEvents != null) {
            mEvents.onCreateView();
        }
        return getViewBinding(inflater, container).getRoot();
    }

    protected Handler getHandler() {
        return mHandler;
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    protected abstract Class<T> getViewBindingClass();

    public T getViewBinding(LayoutInflater inflater, ViewGroup root) {
        if (mBinding == null) {
            try {
                Method method = getViewBindingClass().getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
                method.setAccessible(true);
                mBinding = (T) method.invoke(null, inflater, root, false);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                TextUtils.printError(e.toString());
            }
        }
        return mBinding;
    }

    public T getViewBinding() {
        return mBinding;
    }

    protected abstract void initView(T binding);


    public interface Event {
        void onCreateView();
    }

}
