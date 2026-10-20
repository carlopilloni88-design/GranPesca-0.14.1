plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.carlo.granpesca"
    compileSdk = 36

    defaultConfig {
        applicationId = "it.granpesca.game"
        minSdk = 26
        targetSdk = 36
        versionCode = 29
        versionName = "0.14.1"
    }

    // chiave di firma fissa: ogni nuovo APK si installa sopra quello precedente senza disinstallare
    // (custodisci una copia di app/granpesca.jks: senza di essa gli aggiornamenti non si installano sopra)
    signingConfigs {
        create("granpesca") {
            storeFile = file("granpesca.jks")
            storePassword = providers.gradleProperty("GP_STORE_PASSWORD").get()
            keyAlias = "granpesca"
            keyPassword = providers.gradleProperty("GP_STORE_PASSWORD").get()
            // firma esplicita: la v1 (jar) è quella che il Play Store controlla sull'app bundle
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        // versione di prova: si installa accanto a quella del Play Store (pacchetto .debug, nome «GranPesca debug»)
        // e contiene gli strumenti di debug (app/src/debug/assets/index.html)
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            signingConfig = signingConfigs.getByName("granpesca")
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
            signingConfig = signingConfigs.getByName("granpesca")
        }
    }
    // la build di rilascio non si deve fermare per avvisi di lint
    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-ktx:1.9.1")
}
