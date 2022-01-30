package com.dingyi.myluaapp.build.modules.public.generator

import java.sql.Statement

class SimpleJavaCodeGenerator(
    private val accessType: AccessType,
    private val className: String
) {

    var packageName: String = ""

    private val importClassList = mutableListOf<String>()

    private val fieldList = mutableListOf<Field>()



    fun addImportClass(className: String) {
        importClassList.add(className)
    }

    fun addImportClass(clazz: Class<*>) {
        importClassList.add(clazz.name)
    }

    fun addField(field: Field) {
        fieldList.add(field)
    }

    data class Method(
        val accessType: AccessType,
        val isStatic: Boolean = false,
        val methodName: String,
        val invokeArgs: String = "",
        val returnType: String = "void",
        val body: Body = Body()
    ) {
        fun generate(): String {
            val builder = StringBuilder()

            builder.append(
                accessType.name.lowercase()
            ).append(" ")

            if (isStatic) {
                builder.append("static").append(" ")
            }

            builder.append(methodName).append("(")
                .append(invokeArgs).append(")")

            builder.append("{")
                .append(body.generate())
                .append("}")
                .append("\n")

            return builder.toString()
        }
    }

    class Body {
        private val statementList = mutableListOf<Statement>()

        fun addStatement(statement: Statement) {
            statementList.add(statement)
        }

        fun removeStatement(statement: Statement) {
            statementList.remove(statement)
        }

        fun getAllStatement(): List<Statement> {
            return statementList
        }

        fun generate(): String {
            val builder = StringBuilder()
            statementList.forEach {
                builder.append("\n").append(it.generate()).append("\n")
            }

            return builder.toString()
        }
    }

    interface Statement {
        fun generate(): String
    }

    data class Field(
        val accessType: AccessType,
        val isStatic: Boolean = false,
        val fieldName: String,
        val fieldType: String,
        val fieldValue: Any?
    ) {
        fun generate(): String {
            val builder = StringBuilder()

            builder.append(
                accessType.name.lowercase()
            ).append(" ")

            if (isStatic) {
                builder.append("static").append(" ")
            }

            builder.append(fieldType).append(" ")

            builder.append(fieldName).append(" = ")
            builder.append(fieldValue)
                .append(";")

            return builder.toString()
        }
    }

    fun generate(): String {
        val builder = StringBuilder()

        // package
        if (packageName.isNotEmpty()) {
            builder.append("package").append(" ")
                .append(packageName)
                .append(";")
        }

        //import class
        importClassList.forEach {
            builder.append("import")
                .append(" ")
                .append(it)
                .append(";")
        }

        // class header

        builder.append(
            accessType.name.lowercase()
        ).append(" ")

        builder.append("class").append(" ")

        builder.append(className).append(" ")

        builder.append("{")

        //body


        //fields

        fieldList.forEach {
            builder.append(it.generate()).append("\n")
        }



        builder.append("}")

        return builder.toString()

    }


    enum class AccessType {
        PUBLIC, PRIVATE, PROTECTED
    }


}