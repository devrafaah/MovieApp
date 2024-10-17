plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 24
        targetSdk = 34
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
    viewBinding {
        enable = true
    }
}

dependencies {

    val splashscreen_version = "1.0.1"
    val firebase_bom = "33.4.0"
    val hilt = "2.51.1"
    val navigation_ui = "2.8.3"
    val lottie_animation = "6.5.0"
    val view_model_live_data = "2.8.6"
    val glider = "4.16.0"



    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


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

}
kapt {
    correctErrorTypes = true
}