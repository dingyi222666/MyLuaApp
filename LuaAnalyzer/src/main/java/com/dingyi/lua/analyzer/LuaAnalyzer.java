package com.dingyi.lua.analyzer;

import com.dingyi.lua.analyzer.info.InfoTable;
import com.dingyi.lua.analyzer.parser.LuaInfoListener;
import com.dingyi.lua.analyzer.parser.LuaLexer;
import com.dingyi.lua.analyzer.parser.LuaParser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class LuaAnalyzer {

    private final LuaInfoListener walk = new LuaInfoListener();


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
        System.gc();
        return walk.getInfoTable();
    }

    private void walk(LuaParser parser) {
        ParseTreeWalker.DEFAULT.walk(walk, parser.chunk());
    }


    public InfoTable analysis(File file) throws IOException {
        LuaLexer lexer = new LuaLexer(CharStreams.fromFileName(file.getAbsolutePath()));
        CommonTokenStream stream = new CommonTokenStream(lexer);
        LuaParser parser = new LuaParser(stream);
        parser.addErrorListener(new ErrorHandler());
        walk(parser);
        System.gc();
        return walk.getInfoTable();
    }

}
