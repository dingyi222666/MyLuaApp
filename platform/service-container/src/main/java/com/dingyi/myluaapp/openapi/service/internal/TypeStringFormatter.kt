package com.dingyi.myluaapp.openapi.service.internal

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



internal object TypeStringFormatter {
    fun format(type: Type): String {
        if (type is Class<*>) {
            val aClass = type
            val enclosingClass = aClass.enclosingClass
            return if (enclosingClass != null) {
                format(enclosingClass) + "$" + aClass.simpleName
            } else {
                aClass.simpleName
            }
        } else if (type is ParameterizedType) {
            val builder = StringBuilder()
            builder.append(format(type.rawType))
            builder.append("<")
            for (i in type.actualTypeArguments.indices) {
                val typeParam = type.actualTypeArguments[i]
                if (i > 0) {
                    builder.append(", ")
                }
                builder.append(format(typeParam))
            }
            builder.append(">")
            return builder.toString()
        }
        return type.toString()
    }
}