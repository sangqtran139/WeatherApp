import com.sangtq.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("vehiclemanagement.android.library")
// apply("vehiclemanagement.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:tag"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            }
        }
    }
}