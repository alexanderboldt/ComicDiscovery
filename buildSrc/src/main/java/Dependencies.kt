object Config {
    const val applicationId = "com.alex.comicdiscovery"
    const val minSdk = 23
    const val sdk = 31
}

object Deps {
    object Kotlin {
        const val version = "1.5.21"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    object AndroidX {
        object Compose {
            const val version = "1.0.2"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
        }

        const val core = "androidx.core:core-ktx:1.6.0"

        object DataStore {
            private const val version = "1.0.0-alpha06"
            const val preferences = "androidx.datastore:datastore-preferences:$version"
        }

        object LifeCycle {
            private const val version = "2.3.1"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        const val material = "com.google.android.material:material:1.4.0"

        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:2.4.0-alpha08"
        }

        object Room {
            private const val version = "2.3.0"
            const val room = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
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
    }

    object Libs {
        object Coil {
            private const val version = "1.3.2"
            const val compose = "io.coil-kt:coil-compose:$version"
        }

        object Coroutines {
            private const val version = "1.5.0"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }

        object Koin {
            private const val version = "3.1.2"
            const val koin = "io.insert-koin:koin-android:$version"
            const val compose = "io.insert-koin:koin-androidx-compose:$version"
        }

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"

        object Moshi {
            private const val version = "1.12.0"
            const val moshi = "com.squareup.moshi:moshi:$version"
            const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }

        object Retrofit {
            private const val version = "2.9.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
            const val okHttpLogging = "com.github.ihsanbal:LoggingInterceptor:3.1.0"
        }

        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }
}