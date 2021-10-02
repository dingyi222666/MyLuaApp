package com.dingyi.lua.analysis.parser.listener;


import com.dingyi.lua.analysis.declaration.Declaration;
import com.dingyi.lua.analysis.declaration.TypeDeclaration;
import com.dingyi.lua.analysis.parser.LuaBaseListener;
import com.dingyi.lua.analysis.parser.LuaParser;
import com.dingyi.lua.analysis.symbol.Range;
import com.dingyi.lua.analysis.symbol.Symbol;
import com.dingyi.lua.analysis.symbol.SymbolTable;
import com.dingyi.lua.analysis.util.Pair;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: dingyi
 * @date: 2021/9/20 11:01
 * @description:
 **/
public class LuaTypeAnalysisListener extends LuaBaseListener {

    public final SymbolTable symbolTable = new SymbolTable();

    private ParserRuleContext getParentScope(ParserRuleContext ctx) {
        ParserRuleContext parent = ctx;
        while (!isParentScope(parent)) {
            parent = parent.getParent();
        }
        return parent;
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

    private Pair<TypeDeclaration, Declaration> getExpStatTypeDeclaration(LuaParser.ExpContext expContext) {
        if (expContext.number() != null || isNumberByExpStat(expContext)) {
            return new Pair<>(TypeDeclaration.NUMBER, null);
        }
        if (expContext.string() != null) {
            return new Pair<>(TypeDeclaration.STRING, null);
        }
        if (expContext.getText().equals("false") || expContext.getText().equals("true")) {
            return new Pair<>(TypeDeclaration.BOOLEAN, null);
        }
        if (expContext.lambdadef() != null || expContext.functiondef() != null) {
            return new Pair<>(TypeDeclaration.FUNC, null);
        }
        if (expContext.tableconstructor() != null) {
            return new Pair<>(TypeDeclaration.TABLE, null);
        }
        if (expContext.prefixexp() != null) {
            if (expContext.prefixexp().varOrExp() != null) {
                if (expContext.prefixexp().varOrExp().var() != null) {
                    String name = expContext.prefixexp().varOrExp().var().NAME().getText();
                    if (name.equals("import")) {
                        return new Pair<>(TypeDeclaration.IMPORT, null);
                    } else if (name.equals("require")) {
                        return new Pair<>(TypeDeclaration.REQUIRE, null);
                    } else { //尝试获取符号

                        Token token = expContext.prefixexp().varOrExp().var().NAME().getSymbol();
                        Declaration declaration = symbolTable.getOrNewSymbol(name).findDeclaration(
                                buildRange(token, getParentScope(expContext)), token
                        );

                        if (declaration != null) {
                            return new Pair<>(declaration.type, declaration);
                        }

                    }
                }

            }
            return new Pair<>(TypeDeclaration.FUNCTIONCALL, null);
        }
        return new Pair<>(TypeDeclaration.UNKNOWN, null);
    }

    private boolean isNumberByExpStat(LuaParser.ExpContext ctx) {
        for (LuaParser.ExpContext context : ctx.exp()) {
            if (context.number() != null) {
                return true;
            }
        }
        return false;
    }


    private Range buildRange(Token token, ParserRuleContext ctx) {
        Range result = new Range();
        result.setStartLine(token.getLine());
        result.setStartColumn(token.getCharPositionInLine());
        try {
            result.setEndLine(ctx.stop.getLine());
            result.setEndColumn(ctx.stop.getCharPositionInLine());
        } catch (Exception e) {
            try {
                e.printStackTrace(System.err);
                result.setEndLine(ctx.start.getLine());
                result.setEndColumn(ctx.start.getCharPositionInLine());
            } catch (Exception ignore) {
            }
        }
        return result;
    }

    private String tokenToString(org.antlr.v4.runtime.Token token) {
        return token.getLine() + String.valueOf(token.getCharPositionInLine());
    }

    @Override
    public void enterLocalVarListStat(LuaParser.LocalVarListStatContext ctx) {
        super.enterLocalVarListStat(ctx);

        LuaParser.AttnamelistContext varListStat = ctx.attnamelist();


        ParserRuleContext parentScope = getParentScope(ctx);


        for (int i = 0; i < varListStat.NAME().size(); i++) {
            TerminalNode localVarName = varListStat.NAME(i);
            LuaParser.ExpContext localVarExp = ctx.explist().exp(i);
            //get for new symbol
            Symbol localVarSymbol = symbolTable.getOrNewSymbol(localVarName.getText());

            //get range
            Range scopeRange = buildRange(localVarName.getSymbol(), parentScope);

            //new a declaration
            Declaration declaration = new Declaration();

            List<Declaration> declarationList = null;

            if (!localVarSymbol.scopes.containsKey(scopeRange)) {
                declarationList = new ArrayList<>();
                localVarSymbol.scopes.put(scopeRange, declarationList);
            } else {
                declarationList = localVarSymbol.scopes.get(scopeRange);
            }

            Declaration lastDeclaration = declarationList.size() > 0 ? declarationList.get(declarationList.size() - 1) : null;

            declaration.isLocal = true;

            declaration.token = localVarName.getSymbol();

            declaration.link(lastDeclaration);

            declarationList.add(declaration);

            localVarSymbol.tokenLocations.put(
                    tokenToString(localVarName.getSymbol()), declaration
            );


            if (localVarExp != null) {
                Pair<TypeDeclaration, Declaration> pair = getExpStatTypeDeclaration(localVarExp);
                declaration.type = pair.first;
                if (pair.second != null) {
                    declaration.link(pair.second);
                }
                declaration.value = analyseTypeExpStat(declaration, localVarExp);
            }


        }

    }

    @Override
    public void enterLocalFunctionDefStat(LuaParser.LocalFunctionDefStatContext ctx) {
        super.enterLocalFunctionDefStat(ctx);

        TerminalNode localVarName = ctx.NAME();

        ParserRuleContext parentScope = getParentScope(ctx);

        //get for new symbol
        Symbol localVarSymbol = symbolTable.getOrNewSymbol(localVarName.getText());

        //get range
        Range scopeRange = buildRange(localVarName.getSymbol(), parentScope);

        //new a declaration
        Declaration declaration = new Declaration();


        declaration.type=TypeDeclaration.FUNC;

        declaration.isLocal = true;

        localVarSymbol.tokenLocations.put(
                tokenToString(localVarName.getSymbol()),declaration
        );

        List<Declaration> declarationList = null;

        if (!localVarSymbol.scopes.containsKey(scopeRange)) {
            declarationList = new ArrayList<>();
            localVarSymbol.scopes.put(scopeRange, declarationList);
        } else {
            declarationList = localVarSymbol.scopes.get(scopeRange);
        }

        declarationList.add(declaration);


    }




    private Declaration analyseTypeExpStat(Declaration declaration, LuaParser.ExpContext localVarExp) {
        return null;
    }

    private boolean isParentScope(ParserRuleContext peek) {
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


    @Override
    public void enterChunk(LuaParser.ChunkContext ctx) {
        super.enterChunk(ctx);
        symbolTable.clearSymbol();
    }
}
