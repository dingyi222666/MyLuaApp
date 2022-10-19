package com.dingyi.myluaapp.openapi.roots.libraries;


import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public abstract class LibraryTablePresentation {

    @NotNull

    public abstract String getDisplayName(boolean plural);

    @NotNull

    public abstract String getDescription();

    @NotNull
    public abstract String getLibraryTableEditorTitle();

}
