plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
dependencies {
    testImplementation("junit:junit:4.13.2")

    testImplementation(BuildConfig.Libs.Google.guava)
    implementation(BuildConfig.Libs.Google.guava)

}
