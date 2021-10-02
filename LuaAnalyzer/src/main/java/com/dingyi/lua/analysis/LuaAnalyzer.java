package com.dingyi.lua.analysis;

import com.dingyi.lua.analysis.bean.Token;
import com.dingyi.lua.analysis.info.InfoTable;
import com.dingyi.lua.analysis.parser.listener.LuaTokenAnalyzerListener;
import com.dingyi.lua.analysis.parser.listener.LuaInfoListener;
import com.dingyi.lua.analysis.parser.LuaLexer;
import com.dingyi.lua.analysis.parser.LuaParser;
import com.dingyi.lua.analysis.parser.listener.LuaTypeAnalysisListener;
import com.dingyi.lua.analysis.symbol.SymbolTable;
import com.dingyi.lua.analysis.util.DefaultUtils;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.BitSet;
import java.util.List;

public class LuaAnalyzer {

    private final LuaInfoListener walk = new LuaInfoListener();


    private final LuaTokenAnalyzerListener tokenAnalyzerListener = new LuaTokenAnalyzerListener();

    private final LuaTypeAnalysisListener typeAnalyzerListener =  new LuaTypeAnalysisListener();

    private ErrorListener listener;


    public LuaAnalyzer setErrorListener(ErrorListener listener) {
        this.listener = listener;
        return this;
    }

    public InfoTable analysisTmp() {
        return walk.getInfoTable();
    }

    private class ErrorHandler implements ANTLRErrorListener {

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            if (listener != null) {

                listener.error(line + ":" + charPositionInLine + " " + msg);
            }
        }

        @Override
        public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {

        }

        @Override
        public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {

        }

        @Override
        public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {

        }
    }


    public InfoTable analysis(String code) {
        LuaLexer lexer = new LuaLexer(CharStreams.fromString(code));
        CommonTokenStream stream = new CommonTokenStream(lexer);

        LuaParser parser = new LuaParser(stream);
        parser.addErrorListener(new ErrorHandler());
        walk(parser);
        return walk.getInfoTable();
    }

    public List<Token> analysisToken(String code) {
        LuaLexer lexer=new LuaLexer(CharStreams.fromString(code));
        CommonTokenStream stream= new CommonTokenStream(lexer);
        LuaParser parser=new LuaParser(stream);
        parser.addErrorListener(new ErrorHandler());
        long useTime=DefaultUtils.measureTime( () -> {
            ParseTreeWalker.DEFAULT.walk(tokenAnalyzerListener, parser.chunk());
        });
        System.out.println("use time:"+useTime+" ms");
        return tokenAnalyzerListener.getTokens();
    }

    private void walk(LuaParser parser) {
        long startTime=System.currentTimeMillis();
        ParseTreeWalker.DEFAULT.walk(walk, parser.chunk());
        long endTime=System.currentTimeMillis();
        //walk

    }


    public SymbolTable analyserCode(String code) {
        LuaLexer lexer=new LuaLexer(CharStreams.fromString(code));
        CommonTokenStream stream= new CommonTokenStream(lexer);
        LuaParser parser=new LuaParser(stream);
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        long useTime=DefaultUtils.measureTime( () -> {
            ParseTreeWalker.DEFAULT.walk(typeAnalyzerListener, parser.chunk());
        });
        System.out.println("use time:"+useTime+" ms");
        return typeAnalyzerListener.symbolTable;
    }


}
