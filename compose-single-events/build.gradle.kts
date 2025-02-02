plugins {
  alias(libs.plugins.multiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.compose)
  id("com.android.library")
}

kotlin {
  // Android
  androidTarget()
  jvm()
//  jvm {
//    compilations.all {
//      kotlinOptions.jvmTarget = "1.8"
//    }
//    testRuns["test"].executionTask.configure {
//      useJUnitPlatform()
//    }
//  }

  // iOS
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  // Desktop
//  mingwX64()
//  linuxX64()
//  linuxArm64()
  macosX64()
  macosArm64()

  // Web
  js {
    browser()
    nodejs()
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(compose.foundation)
        implementation(libs.lifecycle.runtime.compose)
      }
    }
  }

}

android {
  compileSdk = 35
  namespace = "sk.vivodik.compose.events"

  kotlin {
    jvmToolchain(jdkVersion = 11)
  }
}