package com.dingyi.lua.analysis.declaration;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: dingyi
 * @date: 2021/9/19 20:21
 * @description:
 **/
public class Declaration {

    public Token token = null;

    /**
     * isLocal
     */
    public boolean isLocal = false;
    /**
     * declaration type
     */
    public TypeDeclaration type = TypeDeclaration.UNKNOWN;

    public List<Declaration> declarations;

    /**
     * return child declaration
     * @return  child declaration
     */
    public List<Declaration> declarations() {
        if (declarations == null) {
            declarations = new ArrayList<>();
        }
        return declarations;
    }

    public Declaration value;

    /**
     * link to the declaration if not null (must be a same scope)
     * @param declaration link target
     */
    public void link(Declaration declaration) {
        if (declaration == null) {
            return;
        }
        if (declaration.declarations != null) {
            if (declaration.isLocal) {
                declarations().addAll(declaration.declarations);
            } else if (!isLocal) {
                declarations = declaration.declarations; //全局直接软引用
            }
        }
        this.type = declaration.type;
        this.isLocal = declaration.isLocal;
        this.value = declaration.value;
    }

    @Override
    public String toString() {
        return "Declaration{" +
                "isLocal=" + isLocal +
                ", type=" + type +
                ", declarations=" + declarations +
                ", value=" + value +
                '}';
    }
}
