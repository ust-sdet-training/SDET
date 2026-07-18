plugins {
    java
}

group = "com.tripstack"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
}

val restAssuredVersion = "5.4.0"
val junitVersion = "5.10.2"
val allureVersion = "2.27.0"

dependencies {
    // Used by src/main classes (AuthClient, BookingClient, BusClient, OpsClient)
    // - MUST be implementation, not testImplementation, or compileJava fails
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.rest-assured:json-path:$restAssuredVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    // Only used at runtime by DatabaseManager - not needed to compile main
    runtimeOnly("org.postgresql:postgresql:42.7.3")

    // Test-only dependencies
    testImplementation("io.rest-assured:json-schema-validator:$restAssuredVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
    implementation("io.qameta.allure:allure-java-commons:${allureVersion}")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = false
    }
    systemProperty("allure.results.directory", "${rootDir}/allure-results")
}