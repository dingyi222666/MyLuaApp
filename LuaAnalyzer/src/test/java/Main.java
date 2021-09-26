import com.dingyi.lua.analysis.LuaAnalyzer;
import com.dingyi.lua.analysis.parser.LuaLexer;
import com.dingyi.lua.analysis.parser.LuaParser;
import com.dingyi.lua.analysis.parser.listener.LuaTypeAnalysisListener;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.HashMap;

/**
 * @author: dingyi
 * @date: 2021/9/12 20:15
 * @description:
 **/
public class Main {

    public static void main(String[] args) {


        LuaLexer lexer = new LuaLexer(CharStreams.fromString("local a,b,c = 1,2,3 local d = a"));
        CommonTokenStream stream = new CommonTokenStream(lexer);

        LuaParser parser = new LuaParser(stream);
        LuaTypeAnalysisListener typeAnalysisListener = new LuaTypeAnalysisListener();
        ParseTreeWalker.DEFAULT.walk(typeAnalysisListener,parser.chunk());
        System.out.println(typeAnalysisListener.symbolTable.getOrNewSymbol("a"));

    }
}
