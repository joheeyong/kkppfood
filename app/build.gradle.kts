plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)

    id("com.google.dagger.hilt.android") version "2.51.1"
    kotlin("kapt")
}

hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.luxrobo.smartparing.kkppfood"
    compileSdk = 36

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.squareup" && requested.name == "javapoet") {
                useVersion("1.13.0")
                because("Hilt가 기대하는 javapoet 버전과 일치시키기 위함")
            }
        }
    }


    defaultConfig {
        applicationId = "com.luxrobo.smartparing.kkppfood"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // 기존 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // BOM (버전 한 번에 관리)
    implementation(platform("androidx.compose:compose-bom:2025.12.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))

    // 기본 Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material3
    implementation("androidx.compose.material3:material3")

    // Activity + Compose
    implementation("androidx.activity:activity-compose:1.9.3")

    // 디버그용 툴링
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // UI 테스트 (나중에 쓰고 싶으면)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")


    // JavaPoet 최신 버전 강제 추가 (중요)
    implementation("com.squareup:javapoet:1.13.0")
    kapt("com.squareup:javapoet:1.13.0")

// Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// OkHttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.14")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

}
