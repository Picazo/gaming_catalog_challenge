plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
}