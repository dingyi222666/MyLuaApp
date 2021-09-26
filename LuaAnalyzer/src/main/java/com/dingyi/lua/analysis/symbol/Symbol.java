package com.dingyi.lua.analysis.symbol;

import com.dingyi.lua.analysis.declaration.Declaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dingyi
 * @date: 2021/9/19 20:16
 * @description:
 **/
public class Symbol {


    public String name;

    public Map<String,Declaration> tokenLocations= new HashMap<>();

    //考虑到同一个作用域里面，变量的状态是不同的
    // a = {a=25}
    // a.c= 25
    //a.c之前是没法访问到a.c的，只能访问到a.a
    public Map<Range, List<Declaration>> scopes= new HashMap<>();

    public Symbol(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", tokenLocations=" + tokenLocations +
                ", scopes=" + scopes +
                '}';
    }
}
