import com.dingyi.lsp.lua.common.ast.ASTGenerator
import com.dingyi.lsp.lua.common.ast.ChunkNode
import com.dingyi.lsp.lua.common.ast.LocalStatement

/**
 * @author: dingyi
 * @date: 2021/10/7 20:08
 * @description:
 **/


fun Any.println() = println(this)

fun main() {
    val code="""
        local a = 12
    """.trimIndent()

    val root = ASTGenerator(code).generate()

    (root.body.statements[0] as LocalStatement).apply {
        variables.println()
        init.println()
    }


}