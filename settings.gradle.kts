// Workaround to make the JUnit Platform Gradle Plugin available using the `plugins` DSL
// See https://github.com/junit-team/junit5/issues/768
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = ArtifactRepositoryContainer.MAVEN_CENTRAL_URL)
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.junit.platform.gradle.plugin") {
                useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
            }
        }
    }
}
