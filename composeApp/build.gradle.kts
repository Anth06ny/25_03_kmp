import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    //kotlinxSerialization : kotlinversion
    kotlin("plugin.serialization") version "2.1.0"

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

            //Client de requêtes spécifique à Android
            implementation("io.ktor:ktor-client-okhttp:3.0.0")

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        iosMain.dependencies {
            //Client de requêtes spécifique à iOS
            implementation("io.ktor:ktor-client-darwin:3.0.0")
        }
        commonMain.dependencies {
            // (les interfaces en gros)
            implementation("io.ktor:ktor-client-core:3.0.0")
            //Intégration avec la bibliothèque de serialisation, gestion des headers
            implementation("io.ktor:ktor-client-content-negotiation:3.0.0")
            //Serialisation JSON
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")

            //Même librairie que pour Android sauf qu'il y a org.jetbrains. avant
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.+")

            implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.0")
            implementation("io.coil-kt.coil3:coil-compose:3.0.0")

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            //Client de requêtes spécifique au bureau sur JVM donc même qu'Android
            implementation("io.ktor:ktor-client-okhttp:3.0.0")

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
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
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}
