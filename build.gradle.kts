import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.spring") version "1.4.31"
	id("com.google.protobuf") version "0.8.8"
}

group = "com.br"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val krotoPlusVersion="0.6.1"
val protobufVersion="3.9.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.1.0")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

	compileOnly("com.google.api.grpc:proto-google-common-protos:1.16.0")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion"
	}
	plugins {
//		id("grpc") {
//			artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpcVersion"]}"
//		}
//		id("grpckt") {
//			artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.ext["grpcKotlinVersion"]}:jdk7@jar"
//		}
		id("kroto") {
			artifact = "com.github.marcoferrer.krotoplus:protoc-gen-kroto-plus:$krotoPlusVersion"
		}
	}
	generateProtoTasks {
		val krotoConfig = file("src/krotoPlusConfig.yml")

		all().forEach {
			it.plugins {
//				id("grpc")
//				id("grpckt")
				id("kroto") {
					//outputSubDir("java")
					option("ConfigPath=$krotoConfig")
				}
			}
		}
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
