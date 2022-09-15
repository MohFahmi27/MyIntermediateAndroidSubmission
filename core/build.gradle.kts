plugins {
    kotlin("android")
    id("com.android.library")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
            buildConfigField("String",
                "BASE_URL",
                project.properties["BASE_URL"] as String)
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
            buildConfigField("String",
                "BASE_URL",
                project.properties["BASE_URL"] as String)
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    commonAndroidLibrary()
    implementation(Dependencies.UiLibs.lottieAnimation)
    implementation(Dependencies.Networking.retrofit2)
    implementation(Dependencies.Networking.retrofitConverterMoshi)
    implementation(Dependencies.Networking.moshi)
    implementation(Dependencies.Networking.moshiKotlin)
    implementation(Dependencies.Networking.okhttp3)
    implementation(Dependencies.Networking.okhttp3Log)
    implementation(Dependencies.DataStore.dataStoreCore)
    implementation(Dependencies.DataStore.dataStorePreferences)
    implementation(Dependencies.DataStore.dataStorePreferenceCore)
    debugImplementation(Dependencies.Testing.chuckerDebug)
    releaseImplementation(Dependencies.Testing.chuckerRelease)
}