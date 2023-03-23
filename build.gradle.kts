import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    kotlin("plugin.serialization") version "1.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.slack.api:slack-api-client:1.28.0")
    implementation("com.slack.api:slack-api-model-kotlin-extension:1.28.0")
    implementation("com.slack.api:slack-api-client-kotlin-extension:1.28.0")
    implementation("io.ktor:ktor-server-core:2.2.4")
    implementation ("io.ktor:ktor-server-netty:2.2.4")
    implementation ("io.ktor:ktor-server-status-pages:2.2.4")
    implementation ("io.ktor:ktor-server-default-headers:2.2.4")
    implementation("io.ktor:ktor-server-content-negotiation:2.2.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
    implementation("com.deepl.api:deepl-java:1.1.0")
    implementation("io.insert-koin:koin-ktor:3.3.1")
    implementation("io.insert-koin:koin-logger-slf4j:3.3.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}