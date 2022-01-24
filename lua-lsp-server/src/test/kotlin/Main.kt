import com.dingyi.lsp.lua.common.ast.ASTGenerator

/**
 * @author: dingyi
 * @date: 2021/10/20 8:45
 * @description:
 **/


fun main(args: Array<String>) {
    val generator = ASTGenerator()
    generator.generate(
        """
        local d = 12
        local s = d
        while (a) do
          a = 25
        end  
    """.trimIndent()
    ).run {
        println(this.body)
    }
}