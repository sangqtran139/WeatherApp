import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.weather.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "weather.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "weather.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "weather.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "weather.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "weather.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "weather.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "weather.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidTest") {
            id = "weather.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidHilt") {
            id = "weather.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "weather.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFirebase") {
            id = "weather.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("androidFlavors") {
            id = "weather.android.application.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidLint") {
            id = "weather.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("jvmLibrary") {
            id = "weather.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}