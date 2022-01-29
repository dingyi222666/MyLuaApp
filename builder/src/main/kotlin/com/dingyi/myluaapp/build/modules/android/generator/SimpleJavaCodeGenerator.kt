package com.dingyi.myluaapp.build.modules.android.generator

class SimpleJavaCodeGenerator(
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

    data class Field(
        val fieldName: String,
        val fieldType: String,
        val fieldValue:Any?
    )

    fun generate():String {
        TODO("Generate Class")
    }


}