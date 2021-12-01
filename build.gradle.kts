plugins {
    id("org.springframework.boot") version "2.5.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

group = "icu.windea"
version = "1.0.0"

buildscript {
    repositories {
        maven("https://maven.aliyun.com/nexus/content/groups/public")
        mavenCentral()
    }
}

repositories {
    maven("https://maven.aliyun.com/nexus/content/groups/public")
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.shell:spring-shell-starter:2.0.0.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.oracle.database.jdbc:ojdbc8")
    runtimeOnly("mysql:mysql-connector-java")

    implementation("com.alibaba:druid:1.2.8")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

kotlin {
    jvmToolchain {
        this as JavaToolchainSpec
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

val projectCompiler = javaToolchains.compilerFor {
    languageVersion.set(JavaLanguageVersion.of(8))
}

tasks {
    compileJava {
        javaCompiler.set(projectCompiler)
    }
    compileTestJava {
        javaCompiler.set(projectCompiler)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}