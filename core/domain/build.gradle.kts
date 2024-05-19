plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.android.hilt)
}

android {
    namespace = "com.weather.core.domain"
}

dependencies {
    implementation(projects.core.data)
}
