package com.dingyi.myluaapp.openapi.editor.highlighter;

import com.dingyi.myluaapp.openapi.editor.Document;
import com.dingyi.myluaapp.openapi.project.Project;

public interface HighlighterClient {
    Project getProject();

    void redraw();

    Document getDocument();
}

