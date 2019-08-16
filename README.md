# Android Flavors Using Gradle with Kotlin DSL

In this article I'll be showing the steps that I've followed to migrate Gradle build scripts from Groovy DSL to Kotlin DSL.

![Gradle + Kotlin](https://www.rootjunky.com/wp-content/uploads/2019/06/kotlin-gradle-3.jpg)

## Step By Step

Groovy DSL and Kotlin DSL have lots of similarities, therefor you do not have to re-write build scripts from scratch, in fact you can transform the existing Groovy scripts into Kotlin DSL by applying minimum amount of modification, once finished, only thing you have to do is rename build scripts file extension and by then you will be done.

### Preparation

---

- The right (and the latest) IDE — Make sure that you use one of these IDEs (I used Android Studio)
  - >![IDE's](https://miro.medium.com/max/700/1*WPEnZztGt7_vXLnagxEF3Q.jpeg)

- Update to Gradle Wrapper 5.0 or higher

  - >```properties
      >distributionBase=GRADLE_USER_HOME
      >distributionPath=wrapper/dists
      >distributionUrl=https\://services.gradle.org/distributions/gradle-5.0-all.zip
      >zipStoreBase=GRADLE_USER_HOME
      >zipStorePath=wrapper/dists  
      >```

---
>:white_check_mark: Tip : use gradle-5.0-all instead of gradle-5.0-bin. The “all” distribution contains sources that provides IDE with Gradle API and Groovy DSL documentation.

---
>:warning: On Android projects, it is recommended to use Gradle 5.0 with Android Gradle Plugin 3.x
---
>:warning: Gradle 5.0 is compatible only with Java 8 or higher !
---
>:heavy_exclamation_mark: Stop Kotlin Scripting Auto-Reload (optional) — Because of the small changes practically in every line of the script’s source code Kotlin will try to reload script dependencies each time on file change.(Preferences/Language & Frameworks/Kotlin/Kotlin Scripting)
---

- Fix ALL String quotes — In Groovy it’s possible to use both single quotes or double quotes for strings which is not the case in Kotlin DSL. You’ll need to put every-single-string in double quotes, otherwise it will be very tedious to finalize migration after renaming script files.

- Disambiguating Groovy DSL — Without knowing the context, the expression of type “xx.yy.zzz” in Groovy can represent a property assignment or a function invocation. These two cases will have different syntax for each in Kotlin DSL. Therefor we need to distinguish them by being more explicit.

>Before
>
>```properties
>include ':app'
>```
>
>After
>
>```kotlin
>include ":app"
>```

---
>Before
>
>```properties
>applicationId "com.hfrsoussama.animotion"
>implementation "androidx.lifecycle:lifecycle-viewmodel:2.0.0"
>```
>
>After
>
>```kotlin
>applicationId = "com.hfrsoussama.animotion"
>implementation("androidx.lifecycle:lifecycle-viewmodel:2.0.0")
>```

---
>Before
>
>```properties
>apply plugin: "com.android.application"
>apply plugin: "kotlin-android"
>apply plugin: "kotlin-android-extensions"
>```
>
>After
>
>```kotlin
>plugins {
>    id("com.android.application")
>    id("kotlin-android")
>    id("kotlin-android-extensions")
>}
>```

### Renaming Files / Conversion

---

- Proceed to the conversion of existing build scripts into Kotlin DSL by renaming them from `xx.gradle` to `xx.gradle.kts`
  >- Mixing Groovy scripts and Kotlin DSL scripts in the same project is possible :+1:

- Start by renaming `settings.gradle` files to `settings.gradle.kts` and re-syncing the project just after (Do this for all of your build settings files)

- Also do the same for the `build.gradle` and add extesion `build.gradle.kts`

After syncing the project there are parts that have been compiled properly and some others not.
>:warning:  Sometimes the IDE indicated visually a lot of errors whereas Gradle indicates much more less in the sync logs. Always use the log errors
---
>Sample `build.gradle.kts`
>
>```kotlin
>plugins{
>    id("xyz")
>    ...
>}
>
>android {
>    compileSdkVersion(x)
>    defaultConfig {
>        applicationId  ="xyz"
>        minSdkVersion(x)
>        targetSdkVersion(x)
>        versionCode = x
>        versionName =  "x"
>        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
>    }
>    buildTypes {
>        getByName("xyz") {
>            isMinifyEnabled = true
>            isShrinkResources = true
>            ...
>            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
>        }
>        ...
>    }
>
>    flavorDimensions("xyz")
>
>    productFlavors{
>        create("xyz"){
>            setDimension("xyz")
>            applicationIdSuffix = ".xyz"
>            ...
>            resValue("string","app_name","XYZ")
>            resValue("color","colorPrimary","#1ba378")
>            ...
>
>        }
>        ...
>    }
>
>}
>
>
>dependencies {
>    implementation(fileTree(mapOf("dir" to "libs", "include" to  listOf("*.jar"))))
>    ...
>    testImplementation("junit:junit:4.12")
>    ...
>    androidTestImplementation("androidx.test:runner:1.2.0")
>    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
>    ...
>}
>```
>
> :warning: If your build scripts still can’t compile, please keep runing ./gradlew tasks command and fix issues indicated in the log, and double check if your Gradle plugins are fully compatible with Kotlin DSL.
