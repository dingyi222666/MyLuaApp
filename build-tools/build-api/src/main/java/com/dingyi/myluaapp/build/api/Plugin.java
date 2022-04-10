package com.dingyi.myluaapp.build.api;

public interface Plugin<T> {

    void apply(T target);
}