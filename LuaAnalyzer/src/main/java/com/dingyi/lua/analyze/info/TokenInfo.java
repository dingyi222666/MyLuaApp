package com.dingyi.lua.analyze.info;

/**
 * @author: dingyi
 * @date: 2021/8/13 10:21
 * @description:
 **/
public class TokenInfo {
    private int line=0;
    private int column=0;
    private BaseInfo info;

    public BaseInfo getInfo() {
        return info;
    }

    public void setInfo(BaseInfo info) {
        this.info = info;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
