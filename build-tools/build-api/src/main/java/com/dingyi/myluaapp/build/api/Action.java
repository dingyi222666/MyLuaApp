package com.dingyi.myluaapp.build.api;


public interface Action<T> {
    void execute(T t);
}