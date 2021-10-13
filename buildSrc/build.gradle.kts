// 导入 Kotlin 插件
import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
    maven ("https://maven.aliyun.com/nexus/content/groups/public" )
}

dependencies {

}

/**
 * 禁用测试报告（Gradle 默认会自动创建测试报告）
 */
tasks.withType<Test> {
    reports.html.isEnabled = false
    reports.junitXml.isEnabled = false
}

/**
 *  isFork：将编译器作为单独的进程运行。
 *  该过程在构建期间将被重用，因此分叉开销很小。分叉的好处是，内存密集型编译是在不同的过程中进行的，从而导致主 Gradle 守护程序中的垃圾回收量大大减少。
 *  守护程序中较少的垃圾收集意味着 Gradle 的基础架构可以运行得更快，尤其是在您还使用的情况下 --parallel。
 *
 *  isIncremental：增量编译。Gradle 可以分析直至单个类级别的依赖关系，以便仅重新编译受更改影响的类。自 Gradle 4.10 起，增量编译是默认设置。
 */
tasks.withType<JavaCompile> {
    options.isFork = true
    options.isIncremental = true
}

