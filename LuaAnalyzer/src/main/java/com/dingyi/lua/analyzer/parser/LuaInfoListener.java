package com.dingyi.lua.analyzer.parser;

import com.dingyi.lua.analyzer.info.BaseInfo;
import com.dingyi.lua.analyzer.info.FunctionCallInfo;
import com.dingyi.lua.analyzer.info.InfoTable;
import com.dingyi.lua.analyzer.info.Range;
import com.dingyi.lua.analyzer.info.TableInfo;
import com.dingyi.lua.analyzer.info.TokenInfo;
import com.dingyi.lua.analyzer.info.Type;
import com.dingyi.lua.analyzer.info.VarInfo;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.List;


/**
 * @author: dingyi
 * @date: 2021/8/14 13:41
 * @description:
 **/
public class LuaInfoListener extends LuaBaseListener {
    private final InfoTable infoTable = new InfoTable();

    private LuaParser.ChunkContext chunkContext;
    private final ArrayDeque<ParserRuleContext> blockContextDeque = new ArrayDeque<>();

    public InfoTable getInfoTable() {
        return infoTable;
    }

    @Override
    public void enterChunk(LuaParser.ChunkContext ctx) {
        super.enterChunk(ctx);
        chunkContext = ctx;
        infoTable.clearInfo();
        blockContextDeque.clear();
        blockContextDeque.push(ctx);
    }


    private boolean isNumberByExp(LuaParser.ExpContext ctx) {
        for (LuaParser.ExpContext context : ctx.exp()) {
            if (context.number() != null) {
                return true;
            }
        }
        return false;
    }


    private Type getExpType(LuaParser.ExpContext expContext) {
        if (expContext.number() != null || isNumberByExp(expContext)) {
            return Type.NUMBER;
        }
        if (expContext.string() != null) {
            return Type.STRING;
        }
        if (expContext.getText().equals("false") || expContext.getText().equals("true")) {
            return Type.BOOLEAN;
        }
        if (expContext.lambdadef() != null || expContext.functiondef() != null) {
            return Type.FUNC;
        }
        if (expContext.tableconstructor() != null) {
            return Type.TABLE;
        }
        if (expContext.prefixexp() != null) {

            if (expContext.prefixexp().varOrExp() != null) {
                if (expContext.prefixexp().varOrExp().var() != null) {
                    String name = expContext.prefixexp().varOrExp().var().NAME().getText();

                    if (name.equals("import")) {
                        return Type.IMPORT;
                    } else if (name.equals("require")) {
                        return Type.REQUIRE;
                    }

                }
            }

            return Type.FUNCTIONCALL;
        }
        return Type.UNKNOWN;
    }


    @Override
    public void enterVarListStat(LuaParser.VarListStatContext ctx) {
        super.enterVarListStat(ctx);


        LuaParser.VarlistContext varList = ctx.varlist();

        VarInfo[] infoArray = new VarInfo[varList.var().size()];

        for (int i = 0; i < infoArray.length; i++) {
            LuaParser.VarContext var = varList.var(i);
            VarInfo varInfo = (VarInfo) findOrNewInfo(var.NAME().getText(), newRange(var.NAME().getSymbol(), blockContextDeque.peek()));
            infoArray[i] = varInfo;


            newTokenInfo(varInfo, var.NAME().getSymbol());
            //不会渲染这里 节约下性能
            if (var.varSuffix() != null) {
                TableInfo childInfo = ((VarInfo) varInfo).getValue();
                if (childInfo == null) {
                    childInfo = new TableInfo();
                    ((VarInfo) varInfo).setValue(childInfo);
                }
                for (LuaParser.VarSuffixContext context : var.varSuffix()) {
                    if (context.NAME()==null) {continue;}
                    if (childInfo.getMember(context.NAME().getText()) != null) {
                        childInfo = childInfo.getMember(context.NAME().getText()).getValue();
                    } else {
                        VarInfo info = new VarInfo();
                        info.setName(context.NAME().getText());
                        info.setLocal(true);
                        info.setType(Type.FIELD);
                        info.setValue(new TableInfo());
                        childInfo.addMember(info);
                        childInfo = info.getValue();
                    }

                }
                infoArray[i] = childInfo.getParent();
            }


        }

        LuaParser.ExplistContext expList = ctx.explist();

        for (int i = 0; i < expList.exp().size(); i++) {
            System.out.println(infoArray[i].toString());
            infoArray[i].setType(getExpType(expList.exp(i)));
        }

    }

    @Override
    public void enterExp(LuaParser.ExpContext ctx) {
        super.enterExp(ctx);


        Type type = getExpType(ctx);

        if (type == Type.FUNCTIONCALL) {
            LuaParser.VarContext var = ctx.prefixexp().varOrExp().var();

            if (var == null || var.NAME() == null) {
                return;
            }

            VarInfo varInfo = (VarInfo) findOrNewInfo(var.NAME().getText(), newRange(var.NAME().getSymbol(), blockContextDeque.peek()));



            //不会渲染这里 节约下性能
            if (var.varSuffix() != null) {
                TableInfo childInfo = ((VarInfo) varInfo).getValue();
                if (childInfo == null) {
                    childInfo = new TableInfo();
                    ((VarInfo) varInfo).setValue(childInfo);
                }
                for (LuaParser.VarSuffixContext context : var.varSuffix()) {
                    if (context.NAME() != null) {
                        VarInfo info = new VarInfo();
                        info.setName(context.NAME().getText());
                        info.setLocal(true);
                        info.setType(Type.FIELD);
                        info.setValue(new TableInfo());
                        childInfo.addMember(info);
                        varInfo = info;
                        childInfo = info.getValue();
                    }
                }
            }

            newTokenInfo(varInfo, var.NAME().getSymbol());

        }
    }

    @Override
    public void enterLocalVarListStat(LuaParser.LocalVarListStatContext ctx) {
        super.enterLocalVarListStat(ctx);
        LuaParser.AttnamelistContext varList = ctx.attnamelist();

        VarInfo[] infoArray = new VarInfo[varList.NAME().size()];

        for (int i = 0; i < infoArray.length; i++) {
            TerminalNode name = varList.NAME(i);
            VarInfo varInfo = newLocalVarInfo(name.getText(), blockContextDeque.peek(), name.getSymbol());
            infoArray[i] = varInfo;
            newTokenInfo(varInfo, name.getSymbol());
        }

        LuaParser.ExplistContext expList = ctx.explist();

        if (expList == null) {
            return;
        }
        for (int i = 0; i < expList.exp().size(); i++) {
            Type expType = getExpType(expList.exp(i));
            infoArray[i].setType(expType);
            infoArray[i].setValue(analysisExp(expList.exp(i)));
        }


    }

    private TableInfo analysisExp(LuaParser.ExpContext exp) {
        switch (getExpType(exp)) {
            case FUNCTIONCALL:
                FunctionCallInfo info = new FunctionCallInfo();
                info.setValue(exp.prefixexp().getText());
                return info;
            case TABLE:
                return analysisTable(exp);
            default:
                return null;
        }
    }

    private TableInfo analysisTable(LuaParser.ExpContext table) {
        TableInfo info = new TableInfo();
        if (table.tableconstructor().fieldlist() != null) {
            List<LuaParser.FieldContext> fields = table.tableconstructor().fieldlist().field();
            VarInfo[] bArr = new VarInfo[fields.size()];
            info.setMembers(bArr);
            for (int i = 0; i < fields.size(); i++) {
                LuaParser.FieldContext field = fields.get(i);
                String name = null;
                LuaParser.ExpContext context = null;
                if (field.NAME() != null) {
                    name = field.NAME().getText();
                    context = field.exp(0);
                } else if (field.exp() != null && field.exp().size() == 2) {
                    if (field.exp(0).string() != null) {
                        name = getStringContent(field.exp(0).string());
                        context = field.exp(1);
                    }
                }
                if (name != null) {
                    Type type = getExpType(context);
                    VarInfo varInfo = new VarInfo();
                    varInfo.setType(type);
                    varInfo.setName(name);
                    varInfo.setValue(analysisExp(context));
                    bArr[i] = varInfo;
                }
            }
        }
        return info;

    }

    private String getStringContent(String text) {
        //char or default
        if (text.charAt(0) == '"' || text.charAt(0) == '\'') {
            return text.substring(1, text.length() - 1);
        } else { //[[
            int startLength = 1;
            char nowChar;
            while (true) {
                nowChar = text.charAt(startLength + 1);
                if (nowChar == '=' || nowChar == '[') {
                    startLength++;
                } else {
                    break;
                }
            }
            return text.substring(startLength + 1, text.length() - startLength - 1);
        }
    }

    private String getStringContent(LuaParser.StringContext string) {
        if (string.CHARSTRING() != null) {
            return getStringContent(string.CHARSTRING().getText());
        }
        if (string.LONGSTRING() != null) {
            return getStringContent(string.LONGSTRING().getText());
        }
        if (string.NORMALSTRING() != null) {
            return getStringContent(string.NORMALSTRING().getText());
        }
        return "";
    }

    private VarInfo newLocalVarInfo(String name, ParserRuleContext context, Token symbol) {
        VarInfo info = new VarInfo();
        info.setType(Type.UNKNOWN);
        info.setName(name);
        info.setRange(newRange(symbol, context));
        if (context.equals(chunkContext)) {
            info.getRange().setEndColumn(999);
            info.getRange().setEndLine(info.getRange().getEndLine() + 1);
        }
        infoTable.addInfo(info);
        return info;
    }

    private VarInfo newGlobalVarInfo(String name, Token startToken) {
        VarInfo info = new VarInfo();
        info.setType(Type.UNKNOWN);
        info.setName(name);
        info.setLocal(false);
        info.setRange(newRange(startToken, chunkContext));
        info.getRange().setEndColumn(999);
        info.getRange().setEndLine(info.getRange().getEndLine() + 1);
        infoTable.addInfo(info);
        return info;
    }

    private void newTokenInfo(BaseInfo info, Token token) {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setLine(token.getLine());
        tokenInfo.setColumn(token.getCharPositionInLine());
        tokenInfo.setInfo(info);
        infoTable.addToken(tokenInfo);
    }

    private Range newRange(Token token, ParserRuleContext ctx) {
        Range result = new Range();
        result.setStartLine(token.getLine());
        result.setStartColumn(token.getCharPositionInLine());
        try {
            result.setEndLine(ctx.stop.getLine());
            result.setEndColumn(ctx.stop.getCharPositionInLine());
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
            result.setEndLine(ctx.start.getLine());
            result.setEndColumn(ctx.start.getCharPositionInLine());
        }
        return result;
    }


    @Override
    public void enterLambdaStat(LuaParser.LambdaStatContext ctx) {
        super.enterLambdaStat(ctx);
        blockContextDeque.push(ctx);

    }


    @Override
    public void enterFuncbody(LuaParser.FuncbodyContext ctx) {
        super.enterFuncbody(ctx);

        if (ctx.parlist() == null) {
            return;
        }
        if (ctx.parlist().namelist() == null) {
            return;
        }

        for (TerminalNode node : ctx.parlist().namelist().NAME()) {
            VarInfo info = newLocalVarInfo(node.getText(), blockContextDeque.peek(), node.getSymbol());
            info.setType(Type.ARG);
            info.setArg(true);
            newTokenInfo(info, node.getSymbol());
        }
    }

    @Override
    public void exitChunk(LuaParser.ChunkContext ctx) {
        super.exitChunk(ctx);
        blockContextDeque.pop();
        //walk end
    }

    @Override
    public void enterRetstat(LuaParser.RetstatContext ctx) {
        super.enterRetstat(ctx);
        if (ctx.explist() != null) {
            for (LuaParser.ExpContext exp : ctx.explist().exp()) {
                if (exp.prefixexp() == null || exp.prefixexp().varOrExp() == null) {
                    continue;
                }
                LuaParser.VarContext var = exp.prefixexp().varOrExp().var();
                TerminalNode name = var.NAME();
                BaseInfo info = findOrNewInfo(name.getText(),
                        newRange(name.getSymbol(), blockContextDeque.peek()));
                newTokenInfo(info, name.getSymbol());
            }
        }
    }

    @Override
    public void enterFunctionCallStat(LuaParser.FunctionCallStatContext ctx) {
        super.enterFunctionCallStat(ctx);

        //get left

        LuaParser.VarContext left = ctx.functioncall().varOrExp().var();

        //get name
        TerminalNode name = left.NAME();


        Range nowRange = newRange(name.getSymbol(), blockContextDeque.peek());

        BaseInfo info = findOrNewInfo(name.getText(), nowRange);

        newTokenInfo(info, name.getSymbol());


        //不会渲染这里 节约下性能
        if (left.varSuffix() != null) {
            TableInfo childInfo = ((VarInfo) info).getValue();
            if (childInfo == null) {
                childInfo = new TableInfo();
                ((VarInfo) info).setValue(childInfo);
            }
            for (LuaParser.VarSuffixContext context : left.varSuffix()) {
                if (context.NAME() != null) {
                    if (childInfo.getMember(context.NAME().getText()) != null) {
                        childInfo = childInfo.getMember(context.getText()).getValue();
                    } else {
                        VarInfo varInfo = new VarInfo();
                        varInfo.setName(context.NAME().getText());
                        varInfo.setLocal(true);
                        varInfo.setType(Type.FIELD);
                        varInfo.setValue(new TableInfo());
                        childInfo.addMember(varInfo);
                        childInfo = varInfo.getValue();
                    }
                }
            }
        }


    }


    private BaseInfo findOrNewInfo(String name, Range range) {
        BaseInfo argInfo = this.infoTable.findArgInfoByNameAndRange(name, range);
        if (argInfo != null) {
            return argInfo;
        }
        BaseInfo localInfo = this.infoTable.findLocalInfoByNameAndRange(name, range);
        if (localInfo != null) {
            return localInfo;
        }

        BaseInfo globalInfo = this.infoTable.findGlobalInfoByName(name);

        if (globalInfo == null) {
            //未知的作用域 直接全局
            globalInfo = newGlobalVarInfo(name, chunkContext.start);
        }
        return globalInfo;

    }

    @Override
    public void enterLocalFunctionDefStat(LuaParser.LocalFunctionDefStatContext ctx) {
        super.enterLocalFunctionDefStat(ctx);

        //作用域就是父区块
        VarInfo info = newLocalVarInfo(ctx.NAME().getText(), blockContextDeque.peek(), ctx.NAME().getSymbol());
        info.setType(Type.FUNC);
        newTokenInfo(info, ctx.NAME().getSymbol());
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitLocalFunctionDefStat(LuaParser.LocalFunctionDefStatContext ctx) {
        super.exitLocalFunctionDefStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void exitLambdaStat(LuaParser.LambdaStatContext ctx) {
        super.exitLambdaStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterLambdabody(LuaParser.LambdabodyContext ctx) {
        super.enterLambdabody(ctx);

        if (ctx.parlist() == null || ctx.parlist().namelist() == null) {
            return;
        }

        for (TerminalNode node : ctx.parlist().namelist().NAME()) {
            VarInfo info = newLocalVarInfo(node.getText(), blockContextDeque.peek(), node.getSymbol());
            info.setType(Type.ARG);
            info.setArg(true);
            newTokenInfo(info, node.getSymbol());
        }
    }

    @Override
    public void enterLabelStat(LuaParser.LabelStatContext ctx) {
        super.enterLabelStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitFunctionDefStat(LuaParser.FunctionDefStatContext ctx) {
        super.exitFunctionDefStat(ctx);
        blockContextDeque.pop();


    }


    @Override
    public void enterFunctiondef(LuaParser.FunctiondefContext ctx) {
        super.enterFunctiondef(ctx);
        blockContextDeque.push(ctx);

    }

    @Override
    public void exitFunctiondef(LuaParser.FunctiondefContext ctx) {
        super.exitFunctiondef(ctx);
        blockContextDeque.pop();


    }

    @Override
    public void enterFunctionDefStat(LuaParser.FunctionDefStatContext ctx) {
        super.enterFunctionDefStat(ctx);
        blockContextDeque.push(ctx);

        if (ctx.funcname() == null || ctx.funcname().NAME() == null || ctx.funcname().NAME().isEmpty()) {
            return;
        }

        String text = ctx.funcname().getText();
        boolean self = text.indexOf(':') != -1;

        TerminalNode rootName = ctx.funcname().NAME(0);
        VarInfo varInfo = (VarInfo) findOrNewInfo(rootName.getText(), newRange(rootName.getSymbol(), blockContextDeque.peek()));


        newTokenInfo(varInfo, rootName.getSymbol());
        //不会渲染这里 节约下性能
        if (ctx.funcname().NAME().size() > 1) {
            TableInfo childInfo = ((VarInfo) varInfo).getValue();
            if (childInfo == null) {
                childInfo = new TableInfo();
                ((VarInfo) varInfo).setValue(childInfo);
            }
            for (int i = 1; i < ctx.funcname().NAME().size(); i++) {
                TerminalNode name = ctx.funcname().NAME(i);
                if (childInfo.getMember(name.getText()) != null) {
                    childInfo = childInfo.getMember(name.getText()).getValue();
                } else {
                    VarInfo info = new VarInfo();
                    info.setName(name.getText());
                    info.setLocal(true);
                    info.setType(Type.FIELD);
                    info.setValue(new TableInfo());
                    childInfo.addMember(info);
                    childInfo = info.getValue();
                }

            }
            childInfo.getParent().setType(Type.FUNC);
        } else {
            varInfo.setType(Type.FUNC);
        }

        if (self) {
            VarInfo selfInfo = newLocalVarInfo("self", ctx, rootName.getSymbol());
            selfInfo.setType(Type.SELF);
            selfInfo.setValue(varInfo.getValue());
        }

    }

    @Override
    public void enterIfStat(LuaParser.IfStatContext ctx) {
        super.enterIfStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void enterIfbody(LuaParser.IfbodyContext ctx) {
        super.enterIfbody(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitIfbody(LuaParser.IfbodyContext ctx) {
        super.exitIfbody(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterElseifbody(LuaParser.ElseifbodyContext ctx) {
        super.enterElseifbody(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitElseifbody(LuaParser.ElseifbodyContext ctx) {
        super.exitElseifbody(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterElsebody(LuaParser.ElsebodyContext ctx) {
        super.enterElsebody(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitElsebody(LuaParser.ElsebodyContext ctx) {
        super.exitElsebody(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterWhileStat(LuaParser.WhileStatContext ctx) {
        super.enterWhileStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitWhileStat(LuaParser.WhileStatContext ctx) {
        super.exitWhileStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterDoStat(LuaParser.DoStatContext ctx) {
        super.enterDoStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitDoStat(LuaParser.DoStatContext ctx) {
        super.exitDoStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterForInStat(LuaParser.ForInStatContext ctx) {
        super.enterForInStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitForInStat(LuaParser.ForInStatContext ctx) {
        super.exitForInStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterForStat(LuaParser.ForStatContext ctx) {
        super.enterForStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitForStat(LuaParser.ForStatContext ctx) {
        super.exitForStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void enterRepeatStat(LuaParser.RepeatStatContext ctx) {
        super.enterRepeatStat(ctx);
        blockContextDeque.push(ctx);
    }

    @Override
    public void exitRepeatStat(LuaParser.RepeatStatContext ctx) {
        super.exitRepeatStat(ctx);
        blockContextDeque.pop();
    }

    @Override
    public void exitIfStat(LuaParser.IfStatContext ctx) {
        super.exitIfStat(ctx);
        blockContextDeque.pop();
    }
}
