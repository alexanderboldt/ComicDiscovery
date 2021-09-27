import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protoc
import org.apache.commons.io.output.ByteArrayOutputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.protobuf")
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
    compileSdk = Config.sdk

    defaultConfig {
        applicationId = Config.applicationId

        minSdk = Config.minSdk
        targetSdk = Config.sdk

        versionCode = getCommitCount()
        versionName = getTag()

        ndk.abiFilters.addAll(mutableSetOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

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
    }

    implementation(Deps.AndroidX.LifeCycle.viewModelKtx)

    Deps.AndroidX.Room.apply {
        implementation(room)
        implementation(ktx)
        kapt(compiler)
    }

    implementation(Deps.AndroidX.DataStore.datastore)

    Deps.AndroidX.Compose.apply {
        implementation(ui)
        implementation(uiTooling)
        implementation(foundation)
        implementation(material)
    }

    implementation(Deps.AndroidX.Navigation.compose)

    // 3rd-party libraries

    // image
    implementation(Deps.Libs.Coil.compose)

    // coroutines
    Deps.Libs.Coroutines.apply {
        implementation(core)
        implementation(android)
    }

    // logging
    implementation(Deps.Libs.timber)

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

    // leak-detection
    debugImplementation(Deps.Libs.leakCanary)

    // dependency injection
    Deps.Libs.Koin.apply {
        implementation(koin)
        implementation(compose)
    }
}