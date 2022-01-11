plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.sdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.sdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        // only use the following resources
        resourceConfigurations.addAll(listOf("en", "de"))
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
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

            // use the debug-signing-configuration as long there is no keystore
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    lint {
        disable("ContentDescription")

        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Deps.AndroidX.Compose.version
    }
}

repositories {
    google()
    mavenCentral()
    maven { setUrl("https://www.jitpack.io") }
}

dependencies {

    // testing
    Deps.Test.apply {
        testImplementation(junit)
        testImplementation(mockitoCore)
        testImplementation(coroutinesTest)

        androidTestImplementation(junit)
    }

    // kotlin-std-lib
    implementation(Deps.Kotlin.stdLib)

    // androidx
    Deps.AndroidX.apply {
        implementation(core)
        implementation(material)
        implementation(splashScreen)
    }

    implementation(Deps.AndroidX.LifeCycle.viewModelKtx)

    Deps.AndroidX.Compose.apply {
        implementation(ui)
        implementation(uiTooling)
        implementation(foundation)
        implementation(material)
    }

    implementation(Deps.AndroidX.Navigation.compose)

    // 3rd-party libraries

    // compose features
    implementation(Deps.Libs.Accompanist.systemUiController)
    implementation(Deps.Libs.Accompanist.navigationAnimation)

    // image
    implementation(Deps.Libs.Coil.compose)

    // coroutines
    Deps.Libs.Coroutines.apply {
        implementation(core)
        implementation(android)
    }

    // logging
    implementation(Deps.Libs.timber)

    // dependency injection
    Deps.Libs.Koin.apply {
        implementation(koin)
        implementation(compose)
    }

    implementation(project(":util"))
    implementation(project(":repository"))
}