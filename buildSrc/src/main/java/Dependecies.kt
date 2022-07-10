import org.gradle.api.JavaVersion

object Modules {
    const val domain = ":domain"
    const val data = ":data"
}

object Config {
    const val application_id = "com.example.firestoreapp"
    const val compile_sdk = 32
    const val min_sdk = 27
    const val target_sdk = 32
    const val jvm_target = "1.8"
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Dependencies {

    const val dagger = "com.google.dagger:dagger:2.41"
    const val dagger_comp = "com.google.dagger:dagger-compiler:2.41"

    const val coil = "io.coil-kt:coil:2.0.0"

    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:2.9.0"

    const val room_runtime = "androidx.room:room-runtime:2.4.2"
    const val room_ktx = "androidx.room:room-ktx:2.4.2"
    const val room_compiler = "androidx.room:room-compiler:2.4.2"

    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1"

    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:2.4.2"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:2.4.2"

    const val paging_3 = "androidx.paging:paging-runtime-ktx:3.1.0"

    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
    const val core_ktx = "androidx.core:core-ktx:1.7.0"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"

    const val appcompat = "androidx.appcompat:appcompat:1.4.1"
    const val android_material = "com.google.android.material:material:1.6.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.3"
}

object TestDependencies{

    const val junit = "junit:junit:4.13.2"
    const val ext_junit = "androidx.test.ext:junit:1.1.3"

    const val mockito_core = "org.mockito:mockito-core:4.0.0"
    const val mockito_kotlin = "org.mockito.kotlin:mockito-kotlin:4.0.0"
    const val mock_web_server = "com.squareup.okhttp3:mockwebserver:4.9.1"

    const val espresso_core = "androidx.test.espresso:espresso-core:3.4.0"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:3.4.0"
}
