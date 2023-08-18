plugins {
    id("com.android.application")
}

android {
    namespace = "com.alcherainc.app.firescout"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.alcherainc.app.firescout"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // navigation UI 버전은 2.4.1로 유지해야 함 (업그레이드 시 중복 이슈로 빌드 안됨)
    implementation("androidx.navigation:navigation-fragment:2.4.1")
    implementation("androidx.navigation:navigation-ui:2.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}