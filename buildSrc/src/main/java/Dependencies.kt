object Dependencies {
    object Core {
        const val coreKtx = "androidx.core:core-ktx:${Versions.CORE_VERSION}"
        const val appCompat = "androidx.appcompat:appcompat:1.4.2"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES_VERSION}"
        const val coroutinesCoreJvm =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Versions.COROUTINES_VERSION}"
    }

    object UiCore {
        const val core = "androidx.core:core:${Versions.CORE_VERSION}"
        const val material = "com.google.android.material:material:1.6.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    }

    object UiLibs {
        const val androidVeil = "com.github.skydoves:androidveil:1.1.2"
        const val fancyToast = "io.github.shashank02051997:FancyToast:2.0.1"
        const val sdp = "com.intuit.sdp:sdp-android:${Versions.SDP_SSP_VERSION}"
        const val ssp = "com.intuit.ssp:ssp-android:${Versions.SDP_SSP_VERSION}"
        const val coil = "io.coil-kt:coil:${Versions.COIL_VERSION}"
        const val coilBase = "io.coil-kt:coil-base:${Versions.COIL_VERSION}"
        const val lottieAnimation = "com.airbnb.android:lottie:5.0.3"
        const val vBindingDelegate = "com.github.yogacp:android-viewbinding:1.0.4"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val expandableFab = "com.nambimobile.widgets:expandable-fab:1.2.1"
    }

    object Lifecycle {
        const val lifecycleCommon =
            "androidx.lifecycle:lifecycle-common:${Versions.LIFECYCLE_VERSION}"
        const val lifecycleKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_VERSION}"
        const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_VERSION}"
        const val liveDataCore =
            "androidx.lifecycle:lifecycle-livedata-core:${Versions.LIFECYCLE_VERSION}"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.LIFECYCLE_VERSION}"
        const val viewmodelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VERSION}"
        const val viewmodelSavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE_VERSION}"
    }

    object NavComponent {
        const val navUi =
            "androidx.navigation:navigation-ui:${Versions.NAVIGATION_COMPONENT_VERSION}"
        const val navUiKtx =
            "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_COMPONENT_VERSION}"
        const val navFragment =
            "androidx.navigation:navigation-fragment:${Versions.NAVIGATION_COMPONENT_VERSION}"
        const val navFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_COMPONENT_VERSION}"
        const val navCommon =
            "androidx.navigation:navigation-common:${Versions.NAVIGATION_COMPONENT_VERSION}"
        const val navRuntime =
            "androidx.navigation:navigation-runtime:${Versions.NAVIGATION_COMPONENT_VERSION}"
    }

    object Koin {
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.KOIN_VERSION}"
        const val koinCore = "io.insert-koin:koin-core:${Versions.KOIN_VERSION}"
        const val koinJvm = "io.insert-koin:koin-core-jvm:${Versions.KOIN_VERSION}"
    }

    object Permission {
        const val easyPermissionKtx = "com.vmadalin:easypermissions-ktx:1.0.0"
    }

    object Testing {
        const val junit = "junit:junit:4.13.2"
        const val chuckerDebug =
            "com.github.chuckerteam.chucker:library:${Versions.CHUCKER_VERSION}"
        const val chuckerRelease =
            "com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER_VERSION}"
        const val testRunner = "androidx.test.ext:junit:1.1.3"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object DataStore {
        const val dataStorePreferences =
            "androidx.datastore:datastore-preferences:${Versions.DATA_STORE_VERSION}"
        const val dataStoreCore = "androidx.datastore:datastore-core:${Versions.DATA_STORE_VERSION}"
        const val dataStorePreferenceCore =
            "androidx.datastore:datastore-preferences-core:${Versions.DATA_STORE_VERSION}"
    }

    object Networking {
        const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
        const val retrofitConverterMoshi =
            "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}"
        const val moshi = "com.squareup.moshi:moshi:${Versions.MOSHI_VERSION}"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}"
        const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}"
        const val okhttp3Log =
            "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}"
    }
}