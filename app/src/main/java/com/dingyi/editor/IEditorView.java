package com.dingyi.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


public abstract class IEditorView extends LinearLayout implements IEditor{
    public IEditorView(Context context) {
        super(context);
    }

    public IEditorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IEditorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
