import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4-M1"
    id("org.sonarqube") version "2.8"
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
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
    implementation("org.apache.logging.log4j:log4j-api:+")
    implementation("org.apache.logging.log4j:log4j-core:+")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:+")

    testImplementation("org.junit.jupiter:junit-jupiter:+") {
        exclude("junit", "junit")
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_13.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

detekt {
    baseline = file("${rootProject.projectDir}/config/baseline.xml")
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
