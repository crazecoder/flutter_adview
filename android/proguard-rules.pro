-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable


#-ignorewarnings
-keep class com.kuaiyou.** {*;}
-keep public class com.kyview.** {*;}
-keepclassmembers class * {public *;}
-keep public class  com.baidu.** {*;}
-keep class com.qq.e.** {
   public protected *;
}
-keep class android.support.v4.**{
   public *;
}
-keep class android.support.v7.**{
    public *;
}
-keep class com.bun.** {*;}
-keep class com.asus.msa.** {*;}
-keep class com.heytap.openid.** {*;}
-keep class com.huawei.android.hms.pps.** {*;}
-keep class com.meizu.flyme.openidsdk.** {*;}
-keep class com.samsung.android.deviceidservice.** {*;}
-keep class com.zui.** {*;}
-keep class com.huawei.hms.ads.** {*; }
-keep interface com.huawei.hms.ads.** {*; }
-keepattributes *Annotation*
-keep @android.support.annotation.Keep class **{
     @android.support.annotation.Keep <fields>;
     @android.support.annotation.Keep <methods>;
}

-keep class com.pgl.sys.ces.* {*;}
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.androidquery.callback.** {*;}
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.my.sxg.** { *; }
-keep class com.xm.** { *; }


-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.* { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

# okhttp 3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontnote okhttp3.**
-dontwarn okhttp3.**

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Okio
-keep class okio.** { *; }
-keep interface okio.** { *; }
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }
-dontwarn com.squareup.**
-dontwarn okio.**




