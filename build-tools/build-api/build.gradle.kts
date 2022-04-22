plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}
dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation(BuildConfig.Libs.Google.guava)
    implementation(BuildConfig.Libs.Google.guava)
    implementation (BuildConfig.Libs.Tools.jgrapht)
}
