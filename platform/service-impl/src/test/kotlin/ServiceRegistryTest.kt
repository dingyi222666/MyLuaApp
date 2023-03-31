import com.dingyi.myluaapp.openapi.annotation.Inject
import com.dingyi.myluaapp.openapi.service.BaseServiceRegistry
import kotlin.test.Test

class TestServiceRegistry : BaseServiceRegistry(null)

class ServiceRegistryTest {

    private val serviceRegistry = TestServiceRegistry()


    init {
        serviceRegistry.registerService(PrintServiceImpl())
        serviceRegistry.registerService(NetWorkServiceImpl())
    }

    class PrintServiceImpl : PrintService {
        override fun print(string: String) {
            println("call print service $string")
        }
    }

    class NetWorkServiceImpl : NetWorkService {
        override fun load(url: String): String {
            return "load url: $url".apply {
                println(this)
            }
        }
    }

    @Test
    fun createTestService() {
        val testService = serviceRegistry.createService(TestService::class.java) as TestService
        testService.print("test")
        testService.load("123")
    }

}

class TestService @Inject constructor(
    service: PrintService,
    //serviceRegistry: ServiceRegistry
) {
    private val printService: PrintService = service

    @field:Inject
    private lateinit var networkService: NetWorkService

    fun print(string: String) {
        printService.print(string)
    }

    fun load(url: String) {
        networkService.load(url)
    }
}

interface PrintService {
    fun print(string: String)
}

interface NetWorkService {
    fun load(url: String): String
}

