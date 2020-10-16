object Deps {
    object Config {
        val applicationId = "com.alex.comicdiscovery"
        val minSdk = 21
        val sdk = 30
    }

    object AndroidX {
        val core = "androidx.core:core-ktx:1.3.1"
        val appCompat = "androidx.appcompat:appcompat:1.2.0"
        val material = "com.google.android.material:material:1.2.1"
        val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"

        // lifecycle

        private val lifecycleVersion = "2.2.0"

        // LifecycleRegistry, LifecycleRegistryOwner
        val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:$lifecycleVersion"

        // coroutineScope() on Lifecycle, lifecycleScope on LifecycleOwner
        val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

        // Annotation processor
        val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"

        // viewModelScope for coroutines
        val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

        val fragmentsExt = "androidx.fragment:fragment-ktx:1.2.5"

        // navigation-component
        private val navVersion = "2.3.0"
        val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navVersion"
        val navigationUi = "androidx.navigation:navigation-ui-ktx:$navVersion"
        val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"

        // room
        private val roomVersion = "2.2.5"
        val room = "androidx.room:room-runtime:$roomVersion"
        val roomKtx = "androidx.room:room-ktx:$roomVersion"
        val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    }

    object Test {
        val junit = "androidx.test.ext:junit:1.1.2"
        val testRunner = "androidx.test:runner:1.1.0"

        // testing with mocked instances
        val mockitoCore = "org.mockito:mockito-core:3.5.13"

        // testing LiveData
        val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

        // testing with coroutines
        val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9"

        val activityTestRule = "androidx.test:rules:1.3.0"
    }

    object Libs {
        val kotlinVersion = "1.4.0"
        val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

        val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"

        val timber = "com.jakewharton.timber:timber:4.7.1"

        private val retrofitVersion = "2.9.0"
        val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
        val okHttpLogging = "com.github.ihsanbal:LoggingInterceptor:3.0.0"

        private val moshiVersion = "1.10.0"
        val moshi = "com.squareup.moshi:moshi:$moshiVersion"
        val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"

        private val glideVersion = "4.11.0"
        val glide = "com.github.bumptech.glide:glide:$glideVersion"
        val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
        val glideOkHttpIntegration = "com.github.bumptech.glide:okhttp3-integration:$glideVersion"
        val glideTransformations = "jp.wasabeef:glide-transformations:4.0.0"

        val liveEvent = "com.github.hadilq.liveevent:liveevent:1.2.0"

        val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.3"

        private val koinVersion = "2.1.6"
        val koin = "org.koin:koin-android:$koinVersion"
        val koinViewModel = "org.koin:koin-androidx-viewmodel:$koinVersion"

        private val flowBindingVersion = "1.4.0"
        val flowBinding = "ru.ldralighieri.corbind:corbind:$flowBindingVersion"
        val flowBindingAppCompat = "ru.ldralighieri.corbind:corbind-appcompat:$flowBindingVersion"
    }
}