plugins {
    java
}

group = "com.tripstack"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")

    // Rest Assured
    implementation("io.rest-assured:rest-assured:5.5.1")

    // JSON Schema Validation
    implementation("io.rest-assured:json-schema-validator:5.5.1")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    // AssertJ
    testImplementation("org.assertj:assertj-core:3.27.3")

    // JDBC
    implementation("com.mysql:mysql-connector-j:9.3.0")

    // Allure
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
}

tasks.test {
    useJUnitPlatform()
}