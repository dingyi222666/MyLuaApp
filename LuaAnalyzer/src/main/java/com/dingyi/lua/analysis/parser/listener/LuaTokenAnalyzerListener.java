package com.dingyi.lua.analysis.parser.listener;

import com.dingyi.lua.analysis.bean.Token;
import com.dingyi.lua.analysis.parser.LuaBaseListener;
import com.dingyi.lua.analysis.parser.LuaParser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dingyi
 * @date: 2021/9/6 16:37
 * @description:
 **/
public class LuaTokenAnalyzerListener extends LuaBaseListener {
    private final List<Token> tokens = new ArrayList<>();

    private final Map<String, List<Token>> tmpTokenMap = new HashMap<>();

    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public void enterChunk(LuaParser.ChunkContext ctx) {

        super.enterChunk(ctx);
        tokens.clear();
        tmpTokenMap.clear();

    }


    @Override
    public void enterVarListStat(LuaParser.VarListStatContext ctx) {
        super.enterVarListStat(ctx);


        LuaParser.VarlistContext varList = ctx.varlist();
        for (int i = 0; i < varList.var().size(); i++) {
            LuaParser.VarContext var = varList.var(i);
            TerminalNode node = var.NAME();
            if (node != null) {
                findOrNewToken(node.getText(), ctx, node.getSymbol())
                        .setLocal(false);
            }
        }


    }

    @Override
    public void enterExp(LuaParser.ExpContext ctx) {
        super.enterExp(ctx);


    }

    @Override
    public void enterLocalVarListStat(LuaParser.LocalVarListStatContext ctx) {
        super.enterLocalVarListStat(ctx);
        LuaParser.AttnamelistContext varList = ctx.attnamelist();


        for (int i = 0; i < varList.attrib().size(); i++) {
            LuaParser.AttribContext var = varList.attrib(i);
            TerminalNode node = var.NAME();
            if (node != null) {
                findOrNewToken(node.getText(), ctx, node.getSymbol())
                        .setLocal(true);
            }
        }

    }


    @Override
    public void enterLambdaStat(LuaParser.LambdaStatContext ctx) {
        super.enterLambdaStat(ctx);


    }


    @Override
    public void enterFuncbody(LuaParser.FuncbodyContext ctx) {
        super.enterFuncbody(ctx);

        if (ctx.parlist() == null || ctx.parlist().namelist() == null) {
            return;
        }


        for (TerminalNode node : ctx.parlist().namelist().NAME()) {
            findOrNewToken(node.getText(), ctx, node.getSymbol())
                    .setLocal(true);
        }


    }

    @Override
    public void exitChunk(LuaParser.ChunkContext ctx) {
        super.exitChunk(ctx);

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


    }


    @Override
    public void enterLocalFunctionDefStat(LuaParser.LocalFunctionDefStatContext ctx) {
        super.enterLocalFunctionDefStat(ctx);

        //作用域就是父区块

        findOrNewToken(ctx.NAME().getText(), ctx.getParent(), ctx.NAME().getSymbol())
                .setLocal(true);

    }


    @Override
    public void exitLambdaStat(LuaParser.LambdaStatContext ctx) {
        super.exitLambdaStat(ctx);

    }

    @Override
    public void enterLambdabody(LuaParser.LambdabodyContext ctx) {
        super.enterLambdabody(ctx);

        if (ctx.parlist() == null || ctx.parlist().namelist() == null) {
            return;
        }

        for (TerminalNode node : ctx.parlist().namelist().NAME()) {

        }
    }

    @Override
    public void enterLabelStat(LuaParser.LabelStatContext ctx) {
        super.enterLabelStat(ctx);

    }

    @Override
    public void exitFunctionDefStat(LuaParser.FunctionDefStatContext ctx) {
        super.exitFunctionDefStat(ctx);


    }


    @Override
    public void enterFunctiondef(LuaParser.FunctiondefContext ctx) {
        super.enterFunctiondef(ctx);


    }

    @Override
    public void exitFunctiondef(LuaParser.FunctiondefContext ctx) {
        super.exitFunctiondef(ctx);


    }

    @Override
    public void enterFunctionDefStat(LuaParser.FunctionDefStatContext ctx) {
        super.enterFunctionDefStat(ctx);


        if (ctx.funcname() == null || ctx.funcname().NAME() == null || ctx.funcname().NAME().isEmpty()) {
            return;
        }


    }

    @Override
    public void enterIfStat(LuaParser.IfStatContext ctx) {
        super.enterIfStat(ctx);

    }

    @Override
    public void enterIfbody(LuaParser.IfbodyContext ctx) {
        super.enterIfbody(ctx);

    }

    @Override
    public void exitIfbody(LuaParser.IfbodyContext ctx) {
        super.exitIfbody(ctx);

    }

    @Override
    public void enterElseifbody(LuaParser.ElseifbodyContext ctx) {
        super.enterElseifbody(ctx);

    }

    @Override
    public void exitElseifbody(LuaParser.ElseifbodyContext ctx) {
        super.exitElseifbody(ctx);

    }

    @Override
    public void enterElsebody(LuaParser.ElsebodyContext ctx) {
        super.enterElsebody(ctx);

    }

    @Override
    public void exitElsebody(LuaParser.ElsebodyContext ctx) {
        super.exitElsebody(ctx);

    }

    @Override
    public void enterWhileStat(LuaParser.WhileStatContext ctx) {
        super.enterWhileStat(ctx);

    }

    @Override
    public void exitWhileStat(LuaParser.WhileStatContext ctx) {
        super.exitWhileStat(ctx);

    }

    @Override
    public void enterDoStat(LuaParser.DoStatContext ctx) {
        super.enterDoStat(ctx);

    }

    @Override
    public void exitDoStat(LuaParser.DoStatContext ctx) {
        super.exitDoStat(ctx);

    }

    @Override
    public void enterForInStat(LuaParser.ForInStatContext ctx) {
        super.enterForInStat(ctx);

        if (ctx.namelist() == null) {
            return;
        }
        for (TerminalNode node : ctx.namelist().NAME()) {

        }
    }

    @Override
    public void exitForInStat(LuaParser.ForInStatContext ctx) {
        super.exitForInStat(ctx);

    }

    @Override
    public void enterForStat(LuaParser.ForStatContext ctx) {
        super.enterForStat(ctx);


    }

    @Override
    public void exitForStat(LuaParser.ForStatContext ctx) {
        super.exitForStat(ctx);


    }

    @Override
    public void enterRepeatStat(LuaParser.RepeatStatContext ctx) {
        super.enterRepeatStat(ctx);

    }

    @Override
    public void exitRepeatStat(LuaParser.RepeatStatContext ctx) {
        super.exitRepeatStat(ctx);

    }




    private ParserRuleContext getParent(ParserRuleContext ctx) {
        ParserRuleContext parent = ctx;
        while (!isParent(parent)) {
            parent = parent.getParent();
        }
        return parent;
    }

    private boolean isParent(ParserRuleContext peek) {
        return peek instanceof LuaParser.IfStatContext
                || peek instanceof LuaParser.ChunkContext
                || peek instanceof LuaParser.FunctionDefStatContext
                || peek instanceof LuaParser.LocalFunctionDefStatContext
                || peek instanceof LuaParser.DoStatContext
                || peek instanceof LuaParser.LambdaStatContext
                || peek instanceof LuaParser.DeferStatContext
                || peek instanceof LuaParser.SwitchStatContext
                || peek instanceof LuaParser.RepeatStatContext
                || peek instanceof LuaParser.FunctiondefContext
                || peek instanceof LuaParser.WhileStatContext
                || peek instanceof LuaParser.ForInStatContext
                || peek instanceof LuaParser.ForStatContext;
    }

    private Token findOrNewToken(String name, ParserRuleContext ctx, org.antlr.v4.runtime.Token token) {

        //map key 快速匹配
        List<Token> targetTokenList = tmpTokenMap.containsKey(name) ? tmpTokenMap.get(name) : null;
        Token targetToken;
        Token resultToken = null;
        if (targetTokenList != null) {

            for (Token value : targetTokenList) {
                if (value.getStartLine() <= token.getLine() &&
                        value.getEndLine() >= token.getLine()) {
                    resultToken = value;
                }
            }
        } else {
            targetTokenList = new ArrayList<>();
            tmpTokenMap.put(name, targetTokenList);
        }

        targetToken = new Token();
        targetToken.setLine(token.getLine());
        targetToken.setColumn(token.getCharPositionInLine());
        ctx = getParent(ctx);
        targetToken.setStartLine(ctx.start.getLine());
        targetToken.setEndLine(ctx.stop.getLine());
        targetToken.setEndColumn(ctx.stop.getCharPositionInLine());

        targetToken.setStartColumn(ctx.start.getCharPositionInLine());
        targetToken.setLocal(resultToken != null && resultToken.isLocal());//global

        tmpTokenMap.get(name).add(targetToken);
        tokens.add(targetToken);
        return targetToken;

    }

    @Override
    public void exitIfStat(LuaParser.IfStatContext ctx) {
        super.exitIfStat(ctx);

    }


}
