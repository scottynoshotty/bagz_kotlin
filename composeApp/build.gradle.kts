import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeArgs)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "com.eridiumcorp.bagz")
        }
    }

    cocoapods {
        version = "1.16.2"
        summary = "CocoaPods library"
        ios.deploymentTarget = "13"
        homepage = "https://github.com/JetBrains/kotlin"

        pod("FirebaseAuth") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("GoogleSignIn")
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.firebase.auth)
            implementation(libs.androidx.credentials)
            implementation(libs.googleid)
            implementation(libs.play.services.auth)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.firebase.functions)
            implementation(libs.sdk.core)
            implementation(libs.compose.navigation)
            implementation(libs.json.serialization)
            implementation(libs.firebase.firestore)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.androidx.paging.runtime)
            implementation(libs.androidx.paging.compose)
            implementation(libs.androidx.material3)
            implementation(libs.androidx.material3.window.size)
            implementation(libs.androidx.material3.adaptive.navigation.suite)
            implementation(libs.androidx.ui.text.google.fonts)
            implementation (libs.compose.charts)
            implementation(libs.firebase.vertexai)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            implementation(libs.io.insert.koin.koin.compose)
            api(libs.koin.annotations)
        }
    }

    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
}

android {
    namespace = "com.eridiumcorp.bagz"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.eridiumcorp.bagz"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    add("kspCommonMainMetadata", libs.ksp.compiler)
}
tasks.withType<KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
afterEvaluate {
    tasks.filter {
        it.name.contains("SourcesJar", true)
    }.forEach {
        println("SourceJarTask====>${it.name}")
        it.dependsOn("kspCommonMainKotlinMetadata")
    }
}
