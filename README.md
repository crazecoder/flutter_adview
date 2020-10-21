# flutter_adview

addview广告联盟flutter插件，目前仅支持android，欢迎fork开发ios。目前支持开屏、banner、视频、插屏

## Getting Started
```yaml
dependencies:
  flutter_adview:
    git:
      url: git://github.com/crazecoder/flutter_adview.git
```
```gradle
android {
    lintOptions {
        disable 'InvalidPackage'
        //打包报Failed to transform libs.jar to match attributes时添加
        checkReleaseBuilds false
    }
    defaultConfig {
        ndk {
            abiFilters 'armeabi-v7a', 'x86', 'arm64-v8a', 'armeabi'
        }
        repositories {
            flatDir {
                dirs project(':flutter_adview').file('libs'), 'libs'
            }
        }
    }
}
```
### 开屏
因考虑视觉效果，不用flutter代码调用，直接选用MainActivity里start
#### 新建SplashActivity
```java
public class ADSplashActivity extends SplashAdAbstractActivity {
    @Override
    protected String getAppId() {
        return "xxxxx";
    }

    @Override
    protected String getPosId() {
        return "xxxx";
    }

    @Override
    protected Integer getAdBackgroundColor() {
        return null;
    }

    @Override
    protected Integer getAdBackgroundDrawableId() {
        return null;
    }
}
```
#### MainActivity里startActivity
```java
public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ADSplashActivity.class);
        startActivity(intent);
    }
}
```
#### banner、插屏、视频调用
```dart
//初始化，如果你已经接入了开屏广告，此方法可以略过，否则可能会出现重复申请权限问题
FlutterAdview.initSdk("xxxxx");

//展示banner
FlutterAdview.showBannerAD(posId: "xxxxx");
//销毁、关闭banner
 FlutterAdview.disposeBannerAD(posId: "xxxxx");

//插屏广告
FlutterAdview.loadInstlAd(posId: "xxxxx", isCloseable: true);

//视频广告
FlutterAdview.loadVideoAd(posId: "xxxxx");
```
#### release打包（Android）
64-bit
```
flutter build apk --release --target-platform android-arm64
```
32-bit（目前配合armeabi-v7a可以打出32位64位通用包）
```
flutter build apk --release --target-platform android-arm
```

