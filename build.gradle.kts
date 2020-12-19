import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.sonarqube") version "3.0"
    id("io.gitlab.arturbosch.detekt") version "1.14.1"
    id("com.adarshr.test-logger") version "2.1.1"
}

group = "io.overfullstack"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
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

    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_15.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform {
        excludeEngines("junit-vintage")
    }
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
