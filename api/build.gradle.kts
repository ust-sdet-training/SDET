plugins {
    java
    id("io.qameta.allure") version "2.12.0"
}

group = "com.travelautomation"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

dependencies {

    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Rest Assured
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("io.rest-assured:json-path:5.5.6")
    testImplementation("io.rest-assured:xml-path:5.5.6")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")

    // MySQL JDBC
    implementation("com.mysql:mysql-connector-j:9.4.0")

    // Dotenv
    implementation("io.github.cdimascio:dotenv-java:3.2.0")

    // Allure
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("io.qameta.allure:allure-rest-assured:2.29.1")

    // Assertions
    testImplementation("org.assertj:assertj-core:3.27.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {

    useJUnitPlatform()

    systemProperty(
        "allure.results.directory",
        "allure-results"
    )

    testLogging {
        events("passed", "failed", "skipped")
    }
}