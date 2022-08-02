import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

fun PluginDependenciesSpec.application() { id("com.android.application") }
fun PluginDependenciesSpec.android() { id("com.android.library") }
fun PluginDependenciesSpec.kotlin() { id("kotlin-android") }
fun PluginDependenciesSpec.ksp() { id("com.google.devtools.ksp") version "1.7.0-1.0.6" }
fun PluginDependenciesSpec.protobuf() { id("com.google.protobuf") }