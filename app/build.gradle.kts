import org.apache.commons.io.output.ByteArrayOutputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}
apply {
    plugin("kotlin-android")
    plugin("androidx.navigation.safeargs.kotlin")
}

fun getCommitCount(): Int {
    return try {
        val stdout = ByteArrayOutputStream()
        exec {
            commandLine("git", "rev-list", "--count", "HEAD")
            standardOutput = stdout
        }
        Integer.parseInt(stdout.toString().trim())
    } catch (exception: Exception) {
        1
    }
}

fun getTag(): String? {
    return try {
        val stdout = ByteArrayOutputStream()
        exec {
            commandLine("git", "describe", "--tags", "--dirty")
            standardOutput = stdout
        }
        stdout.toString().trim()
    } catch (exception: Exception) {
        null
    }
}

android {
    compileSdkVersion(Config.sdk)
    defaultConfig {
        applicationId = Config.applicationId
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.sdk)
        versionCode = getCommitCount()
        versionName = getTag()

        ndk?.abiFilters("armeabi-v7a", "arm64-v8a", "x86", "x86_64")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        // only use the following resources
        resConfigs("en", "de")
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            applicationIdSuffix = ".development"

            // disable png-optimization for faster builds
            isCrunchPngs = false

            buildConfigField("String", "BASE_URL", "\"${LocalProperties.BASE_URL}\"")
            buildConfigField("String", "API_KEY", "\"${LocalProperties.API_KEY}\"")

            resValue("string", "app_name", "ComicDiscovery (debug)")
        }

        getByName("release") {
            isDebuggable = false

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

            resValue("string", "app_name", "ComicDiscovery")
        }
    }

    lintOptions {
        disable("ContentDescription")

        isAbortOnError = false
    }

    buildFeatures {
        viewBinding = true
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

    // testing
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.mockitoCore)
    testImplementation(Deps.Test.archCoreTesting)
    testImplementation(Deps.Test.coroutinesTest)

    // android-testing
    androidTestImplementation(Deps.Test.junit)

    // kotlin-std-lib
    implementation(Deps.Kotlin.stdLib)

    // androidx
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.constraintLayout)

    implementation(Deps.AndroidX.LifeCycle.runtimeKtx)
    kapt(Deps.AndroidX.LifeCycle.compiler)
    implementation(Deps.AndroidX.LifeCycle.viewModelKtx)

    implementation(Deps.AndroidX.fragmentsKtx)

    implementation(Deps.AndroidX.Navigation.fragmentKtx)
    implementation(Deps.AndroidX.Navigation.uiKtx)

    implementation(Deps.AndroidX.Room.room)
    implementation(Deps.AndroidX.Room.ktx)
    kapt(Deps.AndroidX.Room.compiler)

    implementation(Deps.AndroidX.DataStore.preferences)

    // 3rd-party libraries

    // coroutines
    implementation(Deps.Libs.Coroutines.core)
    implementation(Deps.Libs.Coroutines.android)

    // logging
    implementation(Deps.Libs.timber)

    // network
    implementation(Deps.Libs.Retrofit.retrofit)
    implementation(Deps.Libs.Retrofit.moshiConverter)
    implementation(Deps.Libs.Retrofit.okHttpLogging)
    implementation(Deps.Libs.Moshi.moshi)
    kapt(Deps.Libs.Moshi.codeGen)

    // image
    implementation(Deps.Libs.coil)

    implementation(Deps.Libs.liveEvent)

    // leak-detection
    debugImplementation(Deps.Libs.leakCanary)

    // dependency injection
    implementation(Deps.Libs.Koin.koin)

    // view-binding with flow/coroutines
    implementation(Deps.Libs.Corbind.corbind)
    implementation(Deps.Libs.Corbind.appCompat)
}