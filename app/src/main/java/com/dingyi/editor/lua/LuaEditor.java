package com.dingyi.editor.lua;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


import com.androlua.LuaActivity;
import com.dingyi.MyLuaApp.utils.ReflectionUtils;
import com.dingyi.MyLuaApp.widget.views.Magnifier;
import com.dingyi.editor.IEditor;
import com.dingyi.editor.IEditorView;
import com.luajava.LuaJLuaState;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import b.b.a.a.t;


public class LuaEditor extends IEditorView  {

    private com.androlua.LuaEditor editor;

    private int selection;

    private Magnifier magnifier;

    private boolean isSelected=false;

    private GestureDetector detector=new GestureDetector(new GestureDetectorListener());

    public LuaEditor(Context context) {
        super(context);
        initLuaEditor();
    }

    public LuaEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initLuaEditor();
    }

    private class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            LuaEditor.this.magnifier.preShow();
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            magnifier.close();
            return super.onSingleTapUp(e);
        }



        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            magnifier.show((int)e2.getX(),(int)e2.getY());
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            magnifier.show((int)e2.getX(),(int)e2.getY());
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initLuaEditor() {
        editor = new com.androlua.LuaEditor(getContext());
        editor.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        addView(editor);
        editor.requestFocus();
        initAndroidClass();

        magnifier=new Magnifier((Activity)getContext(),editor);
        b.b.a.a.t oldListener= (t) ReflectionUtils.getPrivateField(b.b.a.a.q.class,editor,"y");

        editor.setOnSelectionChangedListener((b, i, i1) -> {
            isSelected=b;
            if (b) {
                magnifier.preShow();
            }else {
                magnifier.close();
            }
            oldListener.a(b,i,i1);
        });

        editor.setOnTouchListener((v,e)-> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (isSelected) {
                        magnifier.preShow();
                        magnifier.show((int)e.getX(),(int)e.getY());
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    magnifier.close();
                    return false;
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
                ((Activity) getContext()).runOnUiThread(() -> editor.addPackage("activity", strings));
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
                        ((Activity) getContext()).runOnUiThread(() -> editor.addNames(resultStr));
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
            errorMsg = (String) field.get(editor);
            errorMsg = errorMsg==null ? "" :errorMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMsg;
    }

    @Override
    public String getText() {
        return editor.getText().toString();
    }

    @Override
    public void setText(String str) {
        editor.setText(str);
    }

    @Override
    public void paste(@NotNull String it) {
        editor.paste(it);
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void paste() {
        editor.paste();
    }

    @Override
    public void undo() {
        editor.undo();
    }

    @Override
    public void redo() {
        editor.redo();
    }

    @Override
    public void format() {
        editor.format();
    }

    @Override
    public void gotoLine() {
        editor.gotoLine();
    }

    @Override
    public void gotoLine(int i) {
        editor.gotoLine(i);
    }

    @Override
    public void search() {
        editor.search();
    }
}
