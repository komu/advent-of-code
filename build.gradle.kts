import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    kotlin("jvm") version "1.2.0"
    id("org.junit.platform.gradle.plugin") version "1.0.0"
}

repositories {
    jcenter()
}

val junitVersion = "5.0.0"

dependencies {
    implementation(kotlin("stdlib-jre8"))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}
