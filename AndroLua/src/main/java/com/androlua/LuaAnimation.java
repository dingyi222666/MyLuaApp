package com.androlua;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.luajava.LuaException;
import com.luajava.LuaFunction;

/**
 * Created by Administrator on 2016/12/08 0008.
 */

public class LuaAnimation extends Animation {

    private final LuaContext mContext;
    private final LuaFunction mAnimation;
    private LuaFunction mApplyTransformation;

    public LuaAnimation(LuaFunction animation) {
        mAnimation = animation;
        mContext = mAnimation.getLuaState().getContext();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mAnimation.call(interpolatedTime, t);
        if (mApplyTransformation == null) {
            Object r = mAnimation.call(interpolatedTime, t, this);
            if (r != null && r instanceof LuaFunction)
                mApplyTransformation = (LuaFunction) r;
        }
        if (mApplyTransformation != null) {
            mApplyTransformation.call(interpolatedTime, t);
        }
    }

    @Override
    protected float resolveSize(int type, float value, int size, int parentSize) {
        return super.resolveSize(type, value, size, parentSize);
    }
}
