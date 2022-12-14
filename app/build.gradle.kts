import Versions.APP_MAJOR_VERSION
import Versions.APP_MINOR_VERSION
import Versions.APP_PATCH_VERSION
import Versions.COMPILE_SDK
import Versions.MIN_SDK
import Versions.TARGET_SDK

plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = COMPILE_SDK

    defaultConfig {
        applicationId = "com.mohfahmi.storyapp"
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
        versionCode =
            APP_MAJOR_VERSION * 100 + APP_MINOR_VERSION * 10 + APP_PATCH_VERSION
        versionName =
            "$APP_MAJOR_VERSION.$APP_MINOR_VERSION.$APP_PATCH_VERSION"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:auth"))
    implementation(project(":features:home"))
    implementation(project(":features:detail"))
    implementation(project(":features:add-story"))
    implementation(project(":features:map-story"))
    testImplementation(Dependencies.Paging.pagingKtx)
    androidTestImplementation(Dependencies.Paging.pagingKtx)
    androidTestImplementation(Dependencies.Paging.pagingKtx)
    androidTestImplementation(Dependencies.Room.roomRuntime)
    androidTestImplementation(Dependencies.Networking.retrofit2)
    commonAndroidLibrary()
}