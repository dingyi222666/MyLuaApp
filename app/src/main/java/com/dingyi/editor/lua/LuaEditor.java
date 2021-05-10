package com.dingyi.editor.lua;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;


import com.androlua.LuaActivity;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.utils.TextUtils;
import com.dingyi.MyLuaApp.utils.ViewUtils;
import com.dingyi.MyLuaApp.widget.views.Magnifier;
import com.dingyi.editor.IEditorView;
import com.luajava.LuaJLuaState;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import b.b.a.a.t;


public class LuaEditor extends IEditorView  {

    private com.androlua.LuaEditor mEditor;


    private Magnifier mMagnifier;

    private boolean isSelected=false;

    public LuaEditor(Context context) {
        super(context);
        initLuaEditor();
    }

    public LuaEditor(Context context,Magnifier magnifier) {
        super(context);
        initLuaEditor();
        this.mMagnifier =magnifier;
    }

    public LuaEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initLuaEditor();
    }

    private  float preZoom(float zoom) {
        if (zoom>1) {
            return zoom-1f;
        }else if (zoom<1) {
            return zoom+1f;
        }
        return  zoom;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initLuaEditor() {
        mEditor = new com.androlua.LuaEditor(getContext());
        mEditor.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        addView(mEditor);
        mEditor.requestFocus();
        initAndroidClass();


        b.b.a.a.t oldListener= (t) ReflectionUtils.getPrivateField(b.b.a.a.q.class, mEditor,"y");

        mEditor.setOnSelectionChangedListener((b, i, i1) -> {
            isSelected=b;
            oldListener.a(b,i,i1);
        });

        mEditor.setOnTouchListener((v, e)->{
            if (mMagnifier ==null) {
                return false;
            }

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    mMagnifier.preShow();
                    if (isSelected) {
                        mMagnifier.show(mEditor.getCaretX(), mEditor.getCaretY(),
                                Math.abs(mEditor.getCaretX() - ViewUtils.dp2px(getContext(),86 / 2)),
                                Math.abs(mEditor.getCaretY()- ViewUtils.dp2px(getContext(),
                                        16))

                        );
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mMagnifier.dismiss();
                    break;
            }
            return false;
        });




    }


    private void initAndroidClass() {
        //init activity methods
        new Thread(() -> {
            Class<?> clazz = LuaActivity.class;
            Method[] methods = clazz.getMethods();
            String[] strings = new String[methods.length];
            for (int i = 0; i < methods.length; i++) {
                strings[i] = methods[i].getName();
            }
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).runOnUiThread(() -> mEditor.addPackage("activity", strings));
            }
        }).start();


        //init android.lua packages


        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] resultStr;
                try {
                    LuaJLuaState state = new LuaJLuaState();
                    LuaValue result = state.getGlobals().load(getContext().getAssets().open("plugin/javaApi/android.lua"), "", "bt", null).call();
                    resultStr=new String[result.length()];
                    for (int i=1;i<=result.length();i++) {
                        resultStr[i-1]=formatJavaString(result.get(i).tojstring());
                    }
                    state.close();//
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).runOnUiThread(() -> mEditor.addNames(resultStr));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            private String formatJavaString(String string) {
                if (string.contains("$")) {
                    return string.substring(string.lastIndexOf("$")+1);
                }
                return string.substring(string.lastIndexOf(".")+1);
            }
        }).start();


    }


    @Override
    public String getError(){
        Class<?> clazz= null;
        try {
            clazz = Class.forName("c.a.a.a.f");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field field= null;
        try {
            field = clazz.getDeclaredField("h");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String errorMsg= "";
        try {
            errorMsg = (String) field.get(mEditor);
            errorMsg = errorMsg==null ? "" :errorMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMsg;

    }

    @Override
    public String getText() {
        return mEditor.getText().toString();
    }

    @Override
    public void setText(String str) {
        mEditor.setText(str);
    }

    @Override
    public void paste(@NotNull String it) {
        mEditor.paste(it);
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void paste() {
        mEditor.paste();
    }

    @Override
    public void undo() {
        mEditor.undo();
    }

    @Override
    public void redo() {
        mEditor.redo();
    }

    @Override
    public void format() {
        mEditor.format();
    }

    @Override
    public void gotoLine() {
        mEditor.gotoLine();
    }

    @Override
    public void gotoLine(int i) {
        mEditor.gotoLine(i);
    }

    @Override
    public void search() {
        mEditor.search();
    }
}
