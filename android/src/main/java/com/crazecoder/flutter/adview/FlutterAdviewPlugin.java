package com.crazecoder.flutter.adview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.crazecoder.flutter.adview.utils.AdInstlUtil;
import com.crazecoder.flutter.adview.utils.AdVideoUtil;
import com.crazecoder.flutter.adview.utils.BannerUtil;
import com.kuaiyou.open.InitSDKManager;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterAdviewPlugin
 */
public class FlutterAdviewPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private Activity activity;
    private FlutterPluginBinding flutterPluginBinding;
    private static String appId;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.flutterPluginBinding = flutterPluginBinding;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("initAdSDK")) {
            checkAndRequestPermission();
            appId = call.argument("appId");
            InitSDKManager.getInstance().init(context.getApplicationContext());
            if (call.hasArgument("downloadNotificationEnable")) {
                boolean downloadNotificationEnable = call.argument("downloadNotificationEnable");
                InitSDKManager.setDownloadNotificationEnable(downloadNotificationEnable);
            }
            result.success(null);
        } else if (call.method.equals("showBannerAD")) {
            String posId = call.argument("posId").toString();
            BannerUtil.getInstance(activity).getBanner(appId, posId);
            BannerUtil.getInstance(activity).show(posId);
            result.success(null);
        } else if (call.method.equals("disposeBannerAD")) {
            String posId = call.argument("posId").toString();
            BannerUtil.getInstance(activity).dispose(posId);
            result.success(null);
        }else if (call.method.equals("loadInstlAd")) {
            String posId = call.argument("posId").toString();
            boolean isCloseable = call.argument("isCloseable");
            AdInstlUtil.getInstance(activity).loadAd(appId,posId,isCloseable);
            result.success(null);
        } else if (call.method.equals("loadVideoAd")) {
            String posId = call.argument("posId").toString();
            AdVideoUtil.getInstance(activity).loadAd(appId,posId);
            result.success(null);
        }else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_adview");
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {
        flutterPluginBinding = null;
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.REQUEST_INSTALL_PACKAGES) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);
        }
        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
//      fetchSplashAD(this, container, skipView, appId, adId, splashADListener, 0);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            ActivityCompat.requestPermissions(activity, requestPermissions, 1024);
        }
    }
}
