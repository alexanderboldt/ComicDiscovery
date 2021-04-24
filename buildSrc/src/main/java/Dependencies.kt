object Config {
    const val applicationId = "com.alex.comicdiscovery"
    const val minSdk = 21
    const val sdk = 30
}

object Deps {
    object Kotlin {
        const val version = "1.4.32"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val material = "com.google.android.material:material:1.3.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val fragmentsKtx = "androidx.fragment:fragment-ktx:1.3.3"

        object LifeCycle {
            private const val version = "2.3.1"

            // coroutineScope() on Lifecycle, lifecycleScope on LifecycleOwner
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"

            // Annotation processor
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"

            // viewModelScope for coroutines
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.5"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Room {
            private const val version = "2.3.0"
            const val room = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object DataStore {
            private const val version = "1.0.0-alpha02"
            const val preferences = "androidx.datastore:datastore-preferences:$version"
        }
    }

    object Test {
        const val junit = "androidx.test.ext:junit:1.1.2"
        const val testRunner = "androidx.test:runner:1.1.0"

        // testing with mocked instances
        const val mockitoCore = "org.mockito:mockito-core:3.5.13"

        // testing LiveData
        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

        // testing with coroutines
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9"

        const val activityTestRule = "androidx.test:rules:1.3.0"
    }

    object Libs {
        object Coroutines {
            private const val version = "1.3.9"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }

        const val timber = "com.jakewharton.timber:timber:4.7.1"

        object Retrofit {
            private const val version = "2.9.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
            const val okHttpLogging = "com.github.ihsanbal:LoggingInterceptor:3.0.0"
        }

        object Moshi {
            private const val version = "1.10.0"
            const val moshi = "com.squareup.moshi:moshi:$version"
            const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }

        const val coil = "io.coil-kt:coil:1.0.0"

        const val liveEvent = "com.github.hadilq.liveevent:liveevent:1.2.0"

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.3"

        object Koin {
            private const val version = "2.1.6"
            const val koin = "org.koin:koin-android:$version"
            const val viewModel = "org.koin:koin-androidx-viewmodel:$version"
        }

        object Corbind {
            private const val version = "1.4.0"
            const val corbind = "ru.ldralighieri.corbind:corbind-core:$version"
            const val appCompat = "ru.ldralighieri.corbind:corbind-appcompat:$version"
        }
    }
}