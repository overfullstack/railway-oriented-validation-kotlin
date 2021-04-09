import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.sonarqube") version "3.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
    id("com.adarshr.test-logger") version "3.0.0"
}

group = "io.overfullstack"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

val arrowSnapshotVersion = "latest.integration"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
    implementation("io.arrow-kt:arrow-core:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-coroutines:$arrowSnapshotVersion")
    implementation("io.github.microutils:kotlin-logging:+")
    // All other dependencies for log4j2 are taken care by kotlin-logging, so you don't need the below
    // implementation("org.slf4j:slf4j-api:+")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:+") // slf4j18 - 18 is required for newer version.

    testImplementation(platform("org.junit:junit-bom:5.8.0-M1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_15.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

testlogger {
    setTheme("mocha")
    showExceptions = true
    showStackTraces = true
    showFullStackTraces = true
    showCauses = true
    slowThreshold = 2000
    showSummary = true
    showSimpleNames = true
    showPassed = true
    showSkipped = true
    showFailed = true
    showStandardStreams = true
    showPassedStandardStreams = true
    showSkippedStandardStreams = true
    showFailedStandardStreams = true
}

detekt {
    baseline = file("${rootProject.projectDir}/config/baseline.xml")
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
