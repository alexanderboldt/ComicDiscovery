plugins {
    android()
    kotlin()
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

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(Deps.Kotlin.stdLib)
}