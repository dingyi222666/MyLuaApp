package com.dingyi.MyLuaApp.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.dingyi.MyLuaApp.R;

//不依赖任何库的BottomDialog
public class BottomDialog extends Dialog {
    private final Activity activity;
    private float offsety, lasty, y,childHeight = 0;
    private int height, width;
    private WindowManager.LayoutParams params;
    private boolean canclose, isbottom = true;
    private View CustomView;
    private float minHeight = 0;


    public void setMinHeight(float mjnHeight) {
        this.minHeight = activity.getWindowManager().getDefaultDisplay().getHeight() - mjnHeight;
    }

    public void setHeight(int height) {
        this.height = height;

        params = this.getWindow().getAttributes();
        params.width = this.width;
        params.height = this.height;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        this.canclose = cancel;
        super.setCanceledOnTouchOutside(cancel);
    }


    public int getHeight() {

        return height;
    }

    public void setWidth(int width) {
        this.width = width;
        this.getWindow().getAttributes().width = width;

        params = this.getWindow().getAttributes();
        params.width = this.width;
        params.height = this.height;
        this.getWindow().setAttributes(params);

    }

    public float getWidth() {
        return width;
    }

    private int getNavigationBarHeight() {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    //获取是否存在NavigationBar
    public boolean isNavigationBarShow() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        Point realSize = new Point();
        display.getSize(size);
        display.getRealSize(realSize);
        return realSize.y != size.y;
    }

    public void setGravity(int z) {
        this.getWindow().setGravity(z);
    }

    public void setView(View view) {
        CustomView = view;
        super.setContentView(view);
    }

    private void onTouch() {
        this.getWindow().getDecorView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View p1, MotionEvent p2) {

                switch (p2.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        y = p2.getRawY();
                        offsety = y - lasty;
                        lasty = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        y = p2.getRawY();
                        offsety = y - lasty;
                        if (p1.getY() + offsety >= 0 && activity.getWindowManager().getDefaultDisplay().getHeight() - minHeight < activity.getWindowManager().getDefaultDisplay().getHeight() - (p1.getY() + offsety)) {
                            if (BottomDialog.this.isNavigationBarShow() && (activity.getWindowManager().getDefaultDisplay().getHeight() - (p1.getY() + offsety)) <= getNavigationBarHeight()) {
                                dismiss();
                            }
                            p1.setY(p1.getY() + offsety);
                            isbottom = false;
                        } else if (p1.getY() + offsety >= 0 && activity.getWindowManager().getDefaultDisplay().getHeight() - minHeight >= activity.getWindowManager().getDefaultDisplay().getHeight() - (p1.getY() + offsety)) {
                            isbottom = true;
                        }

                        lasty = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (canclose && p2.getY() < p1.getY()) {
                            dismiss();
                            return true;
                        }
                        if (p1.getY() >= p1.getHeight() * 0.7 && !isbottom) {
                            BottomDialog.this.dismiss();
                        } else if (p1.getY() <= p1.getHeight() * 0.7 && !isbottom) {
                            ObjectAnimator.ofFloat(p1, "y", p1.getY(), 0).setDuration(200).start();
                        }

                        break;
                }
                return true;
            }

        });
    }


    private void d() {
        super.dismiss();
    }

    public void setView(int resId) {
        this.setView(LayoutInflater.from(activity).inflate(resId, null, false));
    }

    private void showAnim() {
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator m = ObjectAnimator.ofFloat(this.getWindow().getDecorView(), "y", activity.getWindowManager().getDefaultDisplay().getHeight(), 0)
                .setDuration(200);

        ObjectAnimator a= ObjectAnimator.ofFloat(getWindow().getDecorView(),"alpha",0,1)
                .setDuration(200);
        m.start();
        //set.playTogether(m,a);
    }

    private void closeAnim() {
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator zs = ObjectAnimator.ofFloat(this.getWindow().getDecorView(), "y", getWindow().getDecorView().getY(), activity.getWindowManager().getDefaultDisplay().getHeight())
                .setDuration(200);
        zs.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator m) {
                BottomDialog.this.d();
            }

        });
        ObjectAnimator a= ObjectAnimator.ofFloat(getWindow().getDecorView(),"alpha",1,0)
                .setDuration(200);
        zs.start();
        //set.playTogether(zs,a);
    }

    public BottomDialog(Activity a) {
        super(a, R.style.Theme_AppCompat_Dialog);
        activity=a;
        onTouch();
        setGravity(Gravity.BOTTOM);

    }

    @Override
    public void show() {
        if (CustomView != null) {
            CustomView.post(() -> {
                BottomDialog.this.childHeight = BottomDialog.this.CustomView.getHeight();
                if (BottomDialog.this.height == -2) {
                    BottomDialog.this.setHeight(BottomDialog.this.getWindow().getDecorView().getHeight());
                    BottomDialog.this.setMinHeight(0);//BottomDialog.this.CustomView.getHeight());
                }
            });
        }
        super.show();
        showAnim();
    }


    @Override
    public void dismiss() {
        closeAnim();
    }


    public void setRadius(int a, int c) {
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(c);
        draw.setCornerRadii(new float[]{a, a, a, a, 0, 0, 0, 0});
        this.getWindow().getDecorView().setPadding(0,0,0,0);
        this.getWindow().setBackgroundDrawable(draw);
    }


}
