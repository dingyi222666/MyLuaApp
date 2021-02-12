package com.androlua;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;


import com.luajava.LuaJLuaState;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MyLuaEditor extends LinearLayout implements IEditor {

    private LuaEditor editor;

    private int selection;

    public MyLuaEditor(Context context) {
        super(context);
        initLuaEditor();
    }

    public MyLuaEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initLuaEditor();
    }

    private void initLuaEditor() {
        editor = new LuaEditor(getContext());
        editor.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        addView(editor);
        editor.requestFocus();
        initAndroidClass();
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
                String str = null;
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

            private String formatJavaString(String jstring) {
                if (jstring.contains("$")) {
                    return jstring.substring(jstring.lastIndexOf("$")+1);
                }
                return jstring.substring(jstring.lastIndexOf(".")+1);
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
