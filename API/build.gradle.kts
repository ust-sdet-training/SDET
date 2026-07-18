plugins {
    id("java")
}

group = "com.ust.sdet"
version = "1.0-SNAPSHOT"

val restAssuredVersion = "5.5.3"
val junitVersion = "5.14.4"
val allureVersion = "2.33.0"
val slf4jVersion = "2.0.17"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("io.github.cdimascio:java-dotenv:5.2.2")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    systemProperty(
        "baseUrl",
        providers.gradleProperty("baseUrl")
            .orElse("https://api.tripstack.doomple.com/api")
            .get()
    )

    systemProperty(
        "headless",
        providers.gradleProperty("headless")
            .orElse("false")
            .get()
    )

    systemProperty(
        "browser",
        providers.gradleProperty("browser")
            .orElse("chrome")
            .get()
    )

    systemProperty(
        "build.label",
        providers.gradleProperty("buildLabel")
            .orElse("gradle-local")
            .get()
    )

    systemProperty("cucumber.publish.quiet", "true")

    systemProperty(
        "allure.results.directory",
        layout.buildDirectory
            .dir("allure-results")
            .get()
            .asFile
            .absolutePath
    )

    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat =
            org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
        showStandardStreams = true
    }
    
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

val BusAPITest by tasks.registering(Test::class) {
    description = "Runs the Bus booking API test"
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/BusAPITest.class")
}

val PerformanceTest by tasks.registering(Test::class) {
    description = "Runs the performance test"
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/PerformanceTest.class")
}

tasks.test {
    useJUnitPlatform()
}