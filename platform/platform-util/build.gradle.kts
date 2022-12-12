plugins {
    kotlin("jvm")
    id("java")
}


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    compileOnly(BuildConfig.Libs.Tools.intellij_platform_util)
    //compileOnly(BuildConfig.Libs.AndroidX.appcompat)
    implementation("org.slf4j:slf4j-api:2.0.3")
   // compileOnly(project(":platform-common"))
  //  implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)
    compileOnly(BuildConfig.Libs.Tools.commons_vfs)

    testImplementation(BuildConfig.Libs.Default.junit)
}