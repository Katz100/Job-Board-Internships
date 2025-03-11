// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.apply {
        set("room_version", "2.6.0")
    }
}

plugins {
    //id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
   // id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false


        id("com.google.devtools.ksp") version "2.1.0-1.0.29"

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}