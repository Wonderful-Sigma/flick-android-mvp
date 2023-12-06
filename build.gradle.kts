buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
    }
}

plugins {
    id("com.android.application").version("8.0.0").apply(false)
    id("org.jetbrains.kotlin.jvm").version("1.9.10").apply(false)
    id("com.android.library").version("8.0.0").apply(false)
    id("org.jetbrains.kotlin.android").version("1.9.10").apply(false)
    id("com.google.dagger.hilt.android").version("2.46.1").apply(false)
    id("com.google.gms.google-services") version "4.4.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}