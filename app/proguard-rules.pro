# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-dontpreverify
-verbose
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/,!class/merging/
-keepattributes Annotation


-keepattributes Signature
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.preference.PreferenceActivity
-keep public class * extends android.accessibilityservice.AccessibilityService


# okhttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**


-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**



-keep,allowoptimization class com.google.android.libraries.maps.** { *; }
-keep,allowoptimization class com.google.android.apps.gmm.renderer.** { *; }


-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}

-dontwarn android.security.NetworkSecurityPolicy

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-dontwarn android.content.**
-keep class android.content.**

-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Firebase Authentication
-keepattributes Signature
-keepattributes *Annotation*


#Kotlin

-dontwarn kotlin.**
-dontwarn org.jetbrains.annotations.NotNull