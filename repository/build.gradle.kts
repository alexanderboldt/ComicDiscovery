import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.protobuf")
}

android {
    compileSdk = Config.sdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.sdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false

            buildConfigField("String", "BASE_URL", "\"${LocalProperties.BASE_URL}\"")
            buildConfigField("String", "API_KEY", "\"${LocalProperties.API_KEY}\"")
        }

        getByName("release") {
            // enables code shrinking, obfuscation and optimization
            // currently turned off, because of the huge effort for a stable build
            isMinifyEnabled = false

            // enables resource shrinking, which is performed by the Android Gradle Plugin
            // this depends on isMinifyEnabled
            isShrinkResources = false

            // rules for R8
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "BASE_URL", "\"${LocalProperties.BASE_URL}\"")
            buildConfigField("String", "API_KEY", "\"${LocalProperties.API_KEY}\"")

            // use the debug-signing-configuration as long there is no keystore
            signingConfig = signingConfigs.getByName("debug")
        }
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
    maven { setUrl("https://www.jitpack.io") }
}

dependencies {

    // kotlin-std-lib
    implementation(Deps.Kotlin.stdLib)

    Deps.AndroidX.Room.apply {
        implementation(room)
        implementation(ktx)
        kapt(compiler)
    }

    implementation(Deps.AndroidX.DataStore.datastore)

    // 3rd-party libraries

    // coroutines
    Deps.Libs.Coroutines.apply {
        implementation(core)
        implementation(android)
    }

    // protocol-buffer
    implementation(Deps.Libs.ProtoBuf.javaLite)

    // network
    Deps.Libs.Retrofit.apply {
        implementation(retrofit)
        implementation(moshiConverter)
        implementation(okHttpLogging)
    }

    Deps.Libs.Moshi.apply {
        implementation(moshi)
        kapt(codeGen)
    }

    // dependency injection
    implementation(Deps.Libs.Koin.koin)
}