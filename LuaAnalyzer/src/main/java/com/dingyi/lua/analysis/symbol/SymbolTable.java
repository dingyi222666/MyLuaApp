package com.dingyi.lua.analysis.symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: dingyi
 * @date: 2021/9/19 20:15
 * @description:
 **/
public class SymbolTable {


    private final Map<String, Symbol> symbolTable = new HashMap<>();


    public synchronized Symbol getOrNewSymbol(String name) {
        synchronized (this) {
            if (!symbolTable.containsKey(name)) {
                symbolTable.put(name, new Symbol(name));
            }

            return symbolTable.get(name);
        }
    }


    public synchronized void clearSymbol() {
        synchronized (this) {
            symbolTable.clear();
        }
        System.gc();
    }

}
