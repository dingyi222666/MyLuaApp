package com.dingyi.lua.analysis.symbol;

import com.dingyi.lua.analysis.declaration.Declaration;

import org.antlr.v4.runtime.Token;

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

    public Map<String, Declaration> tokenLocations = new HashMap<>();

    //考虑到同一个作用域里面，变量的状态是不同的
    // a = {a=25}
    // a.c= 25
    //a.c之前是没法访问到a.c的，只能访问到a.a
    public Map<Range, List<Declaration>> scopes = new HashMap<>();

    public Symbol(String name) {
        this.name = name;
    }

    private List<Range> tmpRangeList = new ArrayList<>();

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", tokenLocations=" + tokenLocations +
                ", scopes=" + scopes +
                '}';
    }


    public Declaration findDeclaration(Range range, Token startToken) {
        if (scopes.size() == 0) {
            return null;
        }
        tmpRangeList.clear();


        for (Range targetRange : scopes.keySet()) {
            if (range.checkRange(targetRange)) {
                tmpRangeList.add(targetRange);
            }
        }

        //找到一个最小的 每次都拿它去比较 如果被比较的不是最小的就舍弃掉

        if (tmpRangeList.size() == 0) {
            return null;
        }

        Range nowRange = tmpRangeList.get(0);


        for (Range targetRange : tmpRangeList) {
            if (targetRange.getStartLine() >= nowRange.getStartLine()) {
                nowRange = targetRange;
            }
        }

        List<Declaration> declarationList = scopes.get(nowRange);

        if (declarationList.size()==0) {
            return null;
        }

        Declaration nowDeclaration = declarationList.get(0);


        for (Declaration targetDeclaration:declarationList) {
            if (targetDeclaration.token != null && nowDeclaration != null) {
                if (targetDeclaration.token.getLine()<=startToken.getLine()) {
                    if (targetDeclaration.token.getLine()>=nowDeclaration.token.getLine()) {
                        nowDeclaration = targetDeclaration;
                    }
                }
            }
        }


        return nowDeclaration;


    }
}
