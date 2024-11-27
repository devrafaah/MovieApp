plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
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

    val versionSplashScreen = "1.0.1"
    val versionFirebaseBom = "33.6.0"
    val versionHilt = "2.51.1"
    val versionNavigation = "2.8.4"
    val versionLottieAnimation = "6.5.0"
    val versionViewmodelLiveData = "2.8.7"
    val versionGlider = "4.16.0"
    val versionOkhttp = "4.12.0"
    val versionRetrofit2 = "2.11.0"
    val versionSimpleSearchView = "0.2.1"
    val versionRoom = "2.6.1"
    val versionPaging = "3.3.4"
    val versionShimmer = "0.5.0"
    val versionSwipeRefresh = "1.1.0"


    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    // SimpleSearchView
    implementation("com.github.Ferfalk:SimpleSearchView:$versionSimpleSearchView")

    // Splash API
    implementation("androidx.core:core-splashscreen:$versionSplashScreen")

    // firebase // firebase authentication // firebase realtime database // firebase storage
    implementation(platform("com.google.firebase:firebase-bom:$versionFirebaseBom"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")

    // hilt
    implementation("com.google.dagger:hilt-android:$versionHilt")
    ksp("com.google.dagger:hilt-android-compiler:$versionHilt")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment-ktx:$versionNavigation")
    implementation("androidx.navigation:navigation-ui-ktx:$versionNavigation")

    // lottieAnimation
    implementation("com.airbnb.android:lottie:$versionLottieAnimation")

    // ViewModel / LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$versionViewmodelLiveData")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$versionViewmodelLiveData")

    //Glider
    implementation("com.github.bumptech.glide:glide:$versionGlider")
    ksp("com.github.bumptech.glide:compiler:$versionGlider")

    // Okhttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$versionOkhttp"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:$versionRetrofit2")
    implementation("com.squareup.retrofit2:converter-gson:$versionRetrofit2")

    // room
    implementation("androidx.room:room-runtime:$versionRoom")
    implementation("androidx.room:room-ktx:$versionRoom")
    ksp("androidx.room:room-compiler:$versionRoom")


    // pagination
    implementation("androidx.paging:paging-runtime-ktx:$versionPaging")


    //https://github.com/facebookarchive/shimmer-android/issues/121
    // Shimmer facebook
    implementation("com.facebook.shimmer:shimmer:$versionShimmer")


    // SwipeRefresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$versionSwipeRefresh")

}