package com.dingyi.myluaapp.configurationStore;


import com.dingyi.myluaapp.openapi.components.StateStorage;
import com.dingyi.myluaapp.openapi.components.Storage;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

@SuppressWarnings("ClassExplicitlyAnnotation")
public class FileStorageAnnotation implements Storage {
    protected final String path;

    private final boolean deprecated;

    public FileStorageAnnotation(String path, boolean deprecated) {
        this.path = path;
        this.deprecated = deprecated;
    }


    @Override
    public boolean exportable() {
        return false;
    }

    @Override
    public String value() {
        return path;
    }

    @Override
    public boolean deprecated() {
        return deprecated;
    }


    @Override
    public Class<? extends StateStorage> storageClass() {
        return StateStorage.class;
    }


    @NotNull
    @Override
    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Method annotationType not implemented in " + getClass());
    }

    @Override
    public boolean exclusive() {
        return false;
    }
}
