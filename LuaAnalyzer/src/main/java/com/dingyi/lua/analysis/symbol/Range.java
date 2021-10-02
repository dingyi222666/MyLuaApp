package com.dingyi.lua.analysis.symbol;

/**
 * @author: dingyi
 * @date: 2021/9/19 20:19
 * @description:
 **/
public class Range {

    private int startLine = 0;
    private int startColumn = 0;

    private int endLine = 0;
    private int endColumn = 0;

    @Override
    public String toString() {
        return "Range[" +
                "startLine=" + startLine +
                ", startColumn=" + startColumn +
                ", endLine=" + endLine +
                ", endColumn=" + endColumn +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (startLine != range.startLine) return false;
        if (startColumn != range.startColumn) return false;
        if (endLine != range.endLine) return false;
        return endColumn == range.endColumn;
    }

    @Override
    public int hashCode() {
        int result = startLine;
        result = 31 * result + startColumn;
        result = 31 * result + endLine;
        result = 31 * result + endColumn;
        return result;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int endColumn) {
        this.startColumn = endColumn;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    // 8:2 - 12:4
    // 7:2 - 10:2
    public boolean checkRange(Range range) {
        return range.startLine >= startLine && range.endLine >= endLine;
    }
    public static Range build(int startLine,int startColumn,int endLine,int endColumn) {
        Range result = new Range();
        result.endLine = endLine;
        result.endColumn = endColumn;
        result.startColumn = startColumn;
        result.startLine = startLine;
        return result;
    }

}
