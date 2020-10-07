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
    compileSdkVersion(Deps.Config.sdk)
    defaultConfig {
        applicationId = Deps.Config.applicationId
        minSdkVersion(Deps.Config.minSdk)
        targetSdkVersion(Deps.Config.sdk)
        versionCode = getCommitCount()
        versionName = getTag()

        ndk?.abiFilters("armeabi-v7a", "arm64-v8a", "x86", "x86_64")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            applicationIdSuffix = ".development"

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
    jcenter()
    maven { setUrl("https://www.jitpack.io") }
}

dependencies {

    // testing
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.testRunner)
    testImplementation(Deps.Test.mockitoCore)
    testImplementation(Deps.Test.coreTesting)
    testImplementation(Deps.Libs.coroutinesCore)
    testImplementation(Deps.Test.coroutinesTest)

    // android-testing
    androidTestImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.mockitoCore)
    androidTestImplementation(Deps.Test.mockitoAndroid)
    androidTestImplementation(Deps.Test.coreTesting)
    androidTestImplementation(Deps.Test.testRunner)
    androidTestImplementation(Deps.Test.activityTestRule)
    androidTestImplementation(Deps.Test.espressoCore)

    // kotlin-std-lib
    implementation(Deps.Libs.kotlinStdLib)

    // androidx
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.constraintLayout)

    implementation(Deps.AndroidX.lifecycleRuntimeKtx)
    kapt(Deps.AndroidX.lifecycleCompiler)
    implementation(Deps.AndroidX.lifecycleViewModelKtx)

    implementation(Deps.AndroidX.fragmentsExt)

    implementation(Deps.AndroidX.navigationFragment)
    implementation(Deps.AndroidX.navigationUi)

    implementation(Deps.AndroidX.room)
    implementation(Deps.AndroidX.roomKtx)
    kapt(Deps.AndroidX.roomCompiler)

    // 3rd-party libraries

    // coroutines
    implementation(Deps.Libs.coroutinesCore)

    // logging
    implementation(Deps.Libs.timber)

    // network
    implementation(Deps.Libs.retrofit)
    implementation(Deps.Libs.retrofitMoshiConverter)
    implementation(Deps.Libs.okHttpLogging)
    implementation(Deps.Libs.moshi)
    kapt(Deps.Libs.moshiCodeGen)

    // image
    implementation(Deps.Libs.glide)
    kapt(Deps.Libs.glideCompiler)
    implementation(Deps.Libs.glideOkHttpIntegration)

    implementation(Deps.Libs.liveEvent)

    // leak-detection
    debugImplementation(Deps.Libs.leakCanary)

    // dependency injection
    implementation(Deps.Libs.koin)
    implementation(Deps.Libs.koinViewModel)

    // view-binding with flow/coroutines
    implementation(Deps.Libs.flowBinding)
    implementation(Deps.Libs.flowBindingAppCompat)
}