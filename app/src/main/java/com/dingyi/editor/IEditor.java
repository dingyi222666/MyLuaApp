package com.dingyi.editor;

import android.view.View;

import org.jetbrains.annotations.NotNull;

public interface IEditor {
    String getText();
    void setText(String str);

    void paste(@NotNull String it);
    void paste();
    void undo();
    void redo();
    void format();
    void gotoLine();
    void gotoLine(int i);
    void search();
    boolean isSelected();
    default String getError() {
        return null;
    }

}
