package com.dingyi.editor;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dingyi.editor.lua.android.OnTextChangerListener;

import org.jetbrains.annotations.NotNull;

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


    public static class MySavedState extends BaseSavedState {

        private String code;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public MySavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags)
        {
            super.writeToParcel(out, flags);
            out.writeString(code);

        }

        public static final Parcelable.Creator<MySavedState> CREATOR = new Parcelable.Creator<MySavedState>()
        {
            public MySavedState[] newArray(int size)
            {
                return new MySavedState[size];
            }

            @Override
            public MySavedState createFromParcel(Parcel in)
            {
                return new MySavedState(in);
            }
        };

        @SuppressWarnings("unused")
        public MySavedState(Parcel in)
        {
            super(in);
            this.code= in.readString();
        }

    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superParcelable=super.onSaveInstanceState();
        MySavedState childParcelable=new MySavedState(superParcelable);
        childParcelable.setCode(getText().toString());
        return childParcelable;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if (state instanceof MySavedState) {
            setText(((MySavedState) state).getCode());
        }
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
    public abstract void paste(@NotNull String second);
    public abstract void setOnTextChangeListener(OnTextChangerListener listener);
    public abstract String getSelectedText();
}
