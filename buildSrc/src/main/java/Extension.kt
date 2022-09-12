import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Common android library
 * This extension could be use inside dependencies {...}
 * It's good to be used in every feature module.
 */
fun DependencyHandler.commonAndroidLibrary() {
    api(Dependencies.Core.appCompat)
    implementation(Dependencies.Core.coreKtx)
    implementation(Dependencies.UiCore.material)
    implementation(Dependencies.UiCore.constraintLayout)
    implementation(Dependencies.UiLibs.vBindingDelegate)
    implementation(Dependencies.UiLibs.sdp)
    implementation(Dependencies.UiLibs.ssp)
    implementation(Dependencies.NavComponent.navUi)
    implementation(Dependencies.NavComponent.navUiKtx)
    implementation(Dependencies.NavComponent.navFragmentKtx)
    implementation(Dependencies.NavComponent.navFragment)
    implementation(Dependencies.NavComponent.navCommon)
    implementation(Dependencies.NavComponent.navRuntime)
    implementation(Dependencies.Core.coroutinesCore)
    implementation(Dependencies.Core.coroutinesCoreJvm)
    implementation(Dependencies.Lifecycle.lifecycleKtx)
    implementation(Dependencies.Lifecycle.lifecycleCommon)
    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.testRunner)
    androidTestImplementation(Dependencies.Testing.espressoCore)
}

private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)