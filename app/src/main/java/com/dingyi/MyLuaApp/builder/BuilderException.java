package com.dingyi.MyLuaApp.builder;

public class BuilderException extends RuntimeException {
    public BuilderException(Throwable error) {
        addSuppressed(error);
    }
}
