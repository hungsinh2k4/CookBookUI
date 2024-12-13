import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization")
    alias(libs.plugins.compose.compiler)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.androidcookbook"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.androidcookbook"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.androidcookbook.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.kotlinx.serialization.json)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.airbnb.android:lottie-compose:6.5.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation(libs.firebase.messaging)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation (libs.androidx.navigation.testing)


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Hilt instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    // Mockk to mock objects in tests
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.mockk.android)

    // Reflect
    implementation(kotlin("reflect"))

    // Sandwich Adapter for Retrofit
    implementation(libs.sandwich.retrofit) // For Retrofit (Android)
    implementation(libs.sandwich.retrofit.serialization)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    //Icon extended
    implementation(libs.androidx.material.icons.extended)

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    // messaging
    implementation("com.google.firebase:firebase-messaging-ktx")
    // notification permission
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
