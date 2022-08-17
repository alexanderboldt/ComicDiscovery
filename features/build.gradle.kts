plugins {
    android()
    dokka()
    kotlin()
    ksp()
}

android {
    compileSdk = Config.sdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.sdk

        vectorDrawables.useSupportLibrary = true

        // only use the following resources
        resourceConfigurations.addAll(listOf("en", "de"))
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Deps.AndroidX.Compose.version
    }
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

repositories {
    google()
    mavenCentral()
    maven { setUrl("https://www.jitpack.io") }
}

dependencies {
    // testing
    testImplementation(Deps.AndroidX.Test.junit)
    testImplementation(Deps.Libs.Mockito.core)
    testImplementation(Deps.Libs.Coroutines.test)
    testImplementation(Deps.Libs.truth)

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

    // 3rd-party libraries

    // compose features
    implementation(Deps.Libs.Accompanist.systemUiController)

    // image
    implementation(Deps.Libs.Coil.compose)

    // coroutines
    Deps.Libs.Coroutines.apply {
        implementation(core)
        implementation(android)
    }

    // logging
    implementation(Deps.Libs.logcat)

    // dependency injection
    Deps.Libs.Koin.apply {
        implementation(koin)
        implementation(compose)
    }

    // navigation
    Deps.Libs.ComposeDestinations.apply {
        implementation(core)
        this@dependencies.ksp(ksp)
    }

    implementation(project(":util"))
    implementation(project(":repository"))
}