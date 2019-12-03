import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.60"
    id("org.junit.platform.gradle.plugin") version "1.0.0"
}

repositories {
    jcenter()
}

val junitVersion = "5.0.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
