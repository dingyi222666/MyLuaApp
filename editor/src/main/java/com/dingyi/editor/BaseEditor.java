package com.dingyi.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dingyi.editor.lua.android.OnTextChangerListener;

/**
 * @author: dingyi
 * @date: 2021/8/8 22:43
 * @description:
 **/
public abstract class BaseEditor extends View {
    public BaseEditor(Context context) {
        super(context);
    }

    public BaseEditor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEditor(Context context, @Nullable AttributeSet attrs,int defStyle) {
        super(context, attrs,defStyle);

    }


    public abstract boolean findNext(String keyword);
    public abstract void undo();
    public abstract void redo();
    public abstract void gotoLine(int line);
    public abstract void setSelection(int index);
    public abstract int getSelectionEnd();
    public abstract void setText(CharSequence c);
    public abstract CharSequence getText();
    public abstract void setWordWrap(boolean enable);
    public abstract void gotoLine();
    public abstract void search();
    public abstract void setOnTextChangeListener(OnTextChangerListener listener);
    public abstract String getSelectedText();
}
