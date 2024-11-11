plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.movieapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    val splashscreen_version = "1.0.1"
    val firebase_bom = "33.5.1"
    val hilt = "2.51.1"
    val navigation_ui = "2.8.3"
    val lottie_animation = "6.5.0"
    val view_model_live_data = "2.8.7"
    val glider = "4.16.0"
    val okhttp = "4.12.0"



    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // SimpleSearchView
    implementation("com.github.Ferfalk:SimpleSearchView:0.2.1")

    // Splash API
    implementation("androidx.core:core-splashscreen:$splashscreen_version")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebase_bom"))
    // firebase authentication
    implementation("com.google.firebase:firebase-auth")
    // firebase realtime database
    implementation("com.google.firebase:firebase-database")
    // firebase storage
    implementation("com.google.firebase:firebase-storage")
    // hilt
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-android-compiler:$hilt")
    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_ui")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_ui")
    // lottieAnimation
    implementation("com.airbnb.android:lottie:$lottie_animation")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$view_model_live_data")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$view_model_live_data")
    //Glider
    implementation("com.github.bumptech.glide:glide:$glider")

    // SimpleSearchView

    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttp"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")


    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")



}
kapt {
    correctErrorTypes = true
}