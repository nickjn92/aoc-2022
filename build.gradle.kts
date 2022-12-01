plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jmailen.kotlinter") version "3.12.0"
}

repositories {
    mavenCentral()
}

dependencies {
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
