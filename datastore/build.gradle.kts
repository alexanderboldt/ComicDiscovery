import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protoc

plugins {
    android()
    kotlin()
    protobuf()
}

android {
    compileSdk = Config.sdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.sdk
    }

    buildTypes {
        getByName("release") {
            // enables code shrinking, obfuscation and optimization
            isMinifyEnabled = true

            // rules for R8
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

protobuf {
    protobuf.protoc {
        artifact = Deps.Libs.ProtoBuf.protoc
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    protobuf.generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(Deps.AndroidX.DataStore.datastore)
    implementation(Deps.Libs.ProtoBuf.javaLite)
}