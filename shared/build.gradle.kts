plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

kotlin {
    jvm()  // For Android app to use via JVM

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-client-json:2.3.7")
                implementation("io.ktor:ktor-client-serialization:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation("io.ktor:ktor-client-okhttp:2.3.7")

            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.7")
            }
        }
    }
}
