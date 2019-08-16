
plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId  ="com.example.gradleflavor"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName =  "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("default")

    productFlavors{
        create("appA"){
            setDimension("default")
            applicationIdSuffix = ".appA"
            resValue("string","app_name","APP A")
            resValue("string","welcome_message","This Is Application A")
            resValue("color","app_color","#BFFFCF")
            resValue("color","colorPrimary","#1ba378")

        }
        create("appB"){
            setDimension("default")
            applicationIdSuffix = ".appB"
            resValue("string","app_name","APP B")
            resValue("string","welcome_message","This Is Application B")
            resValue("color","app_color","#FFBF7C")
            resValue("color","colorPrimary","#ed8f15")

        }
    }

}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to  listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.41")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
