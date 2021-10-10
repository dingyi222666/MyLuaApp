package com.dingyi.editor.language.textmate

import org.eclipse.tm4e.core.registry.Registry

/**
 * @author: dingyi
 * @date: 2021/10/10 17:20
 * @description:
 **/
object  TextMateGlobal {

    val registry = Registry()

    val themes = mutableMapOf<String,BaseTextMateTheme>()

}