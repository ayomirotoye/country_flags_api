plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.test"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-web:3.4.2"){
		exclude ("org.springframework.boot:spring-boot-starter-logging")
	}
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
	implementation("org.apache.commons:commons-lang3:3.17.0")
	implementation ("org.slf4j:slf4j-api:2.0.9")
	implementation ("ch.qos.logback:logback-classic")
	implementation("ch.qos.logback:logback-core")
	implementation("org.projectlombok:lombok:1.18.36")
	annotationProcessor ("org.projectlombok:lombok:1.18.36")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
