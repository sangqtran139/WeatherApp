plugins {
    alias(libs.plugins.weather.android.application)
    alias(libs.plugins.weather.android.application.compose)
    alias(libs.plugins.weather.android.hilt)
}

android {
    namespace = "com.weather"

    defaultConfig {
        applicationId = "com.weather"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
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
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isDebuggable = false
            proguardFiles("benchmark-rules.pro")
        }
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)

// Compose
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
}