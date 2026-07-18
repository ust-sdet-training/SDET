import org.gradle.api.tasks.testing.Test

        plugins {
            java
            id("io.qameta.allure") version "2.12.0"
            id("org.owasp.dependencycheck") version "12.1.8"
            id("io.gatling.gradle") version "3.13.4"
        }

group = "com.ust.sdet"
version = "0.1.0"

val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.14.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val slf4jVersion = "2.0.17"
val testcontainersVersion = "2.0.5"
val flywayVersion ="10.22.0"

val mysqlVersion = "8.4.0"

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}



dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    gatling("io.gatling:gatling-core:3.13.4")
    gatling("io.gatling:gatling-http:3.13.4")

    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testImplementation(platform ("org.junit:junit-bom:$junitVersion" ))
    testImplementation("org.testcontainers:testcontainers-junit-jupiter:$testcontainersVersion")
    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.testcontainers:testcontainers-mysql:${testcontainersVersion}")
    testImplementation("org.flywaydb:flyway-mysql:${flywayVersion}")
    testImplementation("com.mysql:mysql-connector-j:${mysqlVersion}")


    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.awaitility:awaitility:4.3.0")
    testImplementation("io.rest-assured:rest-assured:5.5.6")

}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
}

tasks.withType<Test>().configureEach {
useJUnitPlatform()
testLogging {
                events("passed", "skipped", "failed")
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
            }
}

        fun Test.useProjectTestClasses() {
            testClassesDirs = sourceSets.test.get().output.classesDirs
            classpath = sourceSets.test.get().runtimeClasspath
        }

        tasks.test{
            description = "Run the tests"
            include("**/BaseApiTest.class")
            include("**/BookingTestIT.class")
            maxParallelForks = 1
        }
