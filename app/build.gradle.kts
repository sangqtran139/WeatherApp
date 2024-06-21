plugins {
    alias(libs.plugins.weather.android.application)
    alias(libs.plugins.weather.android.application.compose)
}

android {
    namespace = "com.sangtq.weather"

    defaultConfig {
        applicationId = "com.sangtq.weather"
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
    implementation(projects.feature.weatherdetail)
}