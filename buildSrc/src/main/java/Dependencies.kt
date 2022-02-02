object Config {
    const val applicationId = "com.alex.comicdiscovery"
    const val minSdk = 26
    const val sdk = 31
}

object Deps {
    object Kotlin {
        const val version = "1.5.31"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    object AndroidX {
        object Compose {
            const val version = "1.0.5"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
        }

        const val core = "androidx.core:core-ktx:1.7.0"

        object DataStore {
            private const val version = "1.0.0"
            const val datastore = "androidx.datastore:datastore:$version"
        }

        object LifeCycle {
            private const val version = "2.4.0"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        const val material = "com.google.android.material:material:1.4.0"

        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:2.4.0-beta02"
        }

        object Room {
            private const val version = "2.3.0"
            const val room = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        const val splashScreen = "androidx.core:core-splashscreen:1.0.0-alpha02"
    }

    object Test {
        const val junit = "androidx.test.ext:junit:1.1.2"
        const val truth = "com.google.truth:truth:1.1.3"

        // testing with mocked instances
        const val mockitoCore = "org.mockito:mockito-core:4.3.1"

        // testing with coroutines
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
    }

    object Libs {
        object Accompanist {
            private val version = "0.20.2"
            val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
            val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$version"
        }

        object Coil {
            private const val version = "1.4.0"
            const val compose = "io.coil-kt:coil-compose:$version"
        }

        object Coroutines {
            private const val version = "1.5.2"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }

        object Koin {
            private const val version = "3.1.4"
            const val koin = "io.insert-koin:koin-android:$version"
            const val compose = "io.insert-koin:koin-androidx-compose:$version"
        }

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.8"

        object Moshi {
            private const val version = "1.12.0"
            const val moshi = "com.squareup.moshi:moshi:$version"
            const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }

        object ProtoBuf {
            private const val version = "3.19.1"
            const val protoc = "com.google.protobuf:protoc:$version"
            const val javaLite = "com.google.protobuf:protobuf-javalite:$version"
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