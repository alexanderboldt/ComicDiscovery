plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

repositories {
    google()
    mavenCentral()
    maven { setUrl("https://www.jitpack.io") }
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

dependencies {
    Deps.Libs.Retrofit.apply {
        implementation(retrofit)
        implementation(moshiConverter)
        implementation(okHttpLogging)
    }

    Deps.Libs.Moshi.apply {
        implementation(moshi)
        kapt(codeGen)
    }

    // todo: use koin-core
    implementation(Deps.Libs.Koin.koin)
}

// todo: make a library module, create a task for own BuildConfig, add to sourceSets