import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4-M1"
    id("org.sonarqube") version "2.8"
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
    id("com.adarshr.test-logger") version "2.0.0"
}

group = "io.overfullstack"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    jcenter()
}

val arrowSnapshotVersion = "0.11.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt:arrow-core:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowSnapshotVersion")
    implementation("io.github.microutils:kotlin-logging:+")

    runtimeOnly("org.apache.logging.log4j:log4j-core:+")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_13.toString()
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
