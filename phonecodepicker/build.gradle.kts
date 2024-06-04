plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.nt.phonecodepicker"
    compileSdk = 34
    defaultPublishConfig = "debug"
    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {}
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    //Core
    implementation("androidx.core:core-ktx:1.13.1")

    //Preview
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.7")

    //Phone Number
    implementation("com.googlecode.libphonenumber:libphonenumber:8.12.48")

    //JSON Parser
    implementation("com.google.code.gson:gson:2.10.1")

    //Material components
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.compose.material:material:1.6.7")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.nt.phonecodepicker"
                artifactId = "PhoneCodePicker"
                version = "1.0"
            }
        }
    }
}