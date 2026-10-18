settings.gradle
build.gradle
gradle.properties
app/build.gradle
app/src/main/AndroidManifest.xml
app/src/main/java/com/example/dadlocker/MainActivity.kt
app/src/main/java/com/example/dadlocker/LockActivity.kt
app/src/main/java/com/example/dadlocker/LockerService.kt
app/src/main/java/com/example/dadlocker/Prefs.kt
app/src/main/res/layout/activity_main.xml
app/src/main/res/layout/activity_lock.xml
app/src/main/res/drawable/bg_mic.xml
app/src/main/res/anim/shake.xml
app/src/main/res/values/strings.xml
app/src/main/res/values/themes.xml
app/src/main/res/xml/locker_service_config.xml
.github/workflows/build.ymlpluginManagement {
    repositories { google(); mavenCentral(); gradlePluginPortal() }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories { google(); mavenCentral() }
}
rootProject.name = "DadLocker"
include ':app'android.useAndroidX=true
android.nonTransitiveRClass=true
org.gradle.jvmargs=-Xmx2048mname: Build DadLocker APK
on: [workflow_dispatch]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: android-actions/setup-android@v3
      - name: Accept licenses
        run: yes | sdkmanager --licenses
      - name: Create Gradle wrapper
        run: |
          wget -q https://services.gradle.org/distributions/gradle-8.7-bin.zip
          unzip -q gradle-8.7-bin.zip
          ./gradle-8.7/bin/gradle wrapper --gradle-version 8.7
      - name: Build APK
        run: ./gradlew assembleDebug --stacktrace
      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: DadLocker
          path: app/build/outputs/apk/debug/app-debug.apk
