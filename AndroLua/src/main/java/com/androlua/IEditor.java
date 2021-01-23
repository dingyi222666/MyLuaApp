package com.androlua;

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

    default String getError() {
        return null;
    }
}
