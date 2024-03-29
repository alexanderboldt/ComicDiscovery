import org.apache.commons.io.output.ByteArrayOutputStream

plugins {
    application()
    kotlin()
}

android {
    compileSdk = Config.sdk

    defaultConfig {
        applicationId = Config.applicationId

        minSdk = Config.minSdk
        targetSdk = Config.sdk

        versionCode = execute("git", "rev-list", "--count", "HEAD")?.let { Integer.parseInt(it) } ?: 1
        versionName = execute("git", "describe", "--tags", "--dirty")

        ndk.abiFilters.addAll(mutableSetOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))

        // only use the following resources
        resourceConfigurations.addAll(listOf("en", "de"))
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            applicationIdSuffix = ".development"

            // disable png-optimization for faster builds
            isCrunchPngs = false

            resValue("string", "app_name", "ComicDiscovery (debug)")
        }

        getByName("release") {
            isDebuggable = false

            // enables code shrinking, obfuscation and optimization
            isMinifyEnabled = true

            // enables resource shrinking, which is performed by the Android Gradle Plugin
            // this depends on isMinifyEnabled
            isShrinkResources = true

            // rules for R8
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // use the debug-signing-configuration as long there is no keystore
            signingConfig = signingConfigs.getByName("debug")

            resValue("string", "app_name", "ComicDiscovery")
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
    maven { setUrl("https://www.jitpack.io") }
}

dependencies {
    debugImplementation(Deps.Libs.leakCanary)

    implementation(Deps.Libs.Koin.koin)
    
    implementation(Deps.Libs.logcat)

    implementation(project(":features"))
}

fun execute(vararg args: Any) = try {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine(*args)
        standardOutput = stdout
    }
    stdout.toString().trim()
} catch (exception: Exception) {
    null
}