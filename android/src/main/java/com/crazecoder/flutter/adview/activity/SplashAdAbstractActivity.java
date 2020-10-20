package com.crazecoder.flutter.adview.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.crazecoder.flutter.adview.FlutterAdviewPlugin;
import com.crazecoder.flutter.adview.R;
import com.kuaiyou.open.AdManager;
import com.kuaiyou.open.InitSDKManager;
import com.kuaiyou.open.SpreadManager;
import com.kuaiyou.open.interfaces.AdViewSpreadListener;

import java.util.ArrayList;
import java.util.List;

import io.flutter.Log;

public abstract class SplashAdAbstractActivity extends Activity {
    private SpreadManager spreadManager = null;

    public boolean canJump = false;

    private String appId;
    private String adId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分（电池等图标和一起修饰部分）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);
        InitSDKManager.getInstance().init(getApplicationContext());
        //下载类广告默认弹出二次确认框，如需关闭提示请设置如下；设置后对全部广告生效
        InitSDKManager.setDownloadNotificationEnable(true);

        appId = getIntent().getStringExtra("appId");
        adId = getIntent().getStringExtra("posId");
        if (TextUtils.isEmpty(appId)) {
            appId = getAppId();
            FlutterAdviewPlugin.appId = appId;
        }
        if (TextUtils.isEmpty(adId)) {
            adId = getPosId();
        }
        // 如果targetSDKVersion >= 23，就要申请好权限。如果您的App没有适配到Android6.0（即targetSDKVersion < 23），那么只需要在这里直接调用fetchSplashAD接口。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            fetchSplashAD(appId, adId, splashADListener);
        }

    }

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            fetchSplashAD(appId, adId, splashADListener);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            fetchSplashAD(appId, adId, splashADListener);
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param appId      应用ID
     * @param posId      广告位ID
     * @param adListener 广告状态监听器
     */
    private void fetchSplashAD(
            String appId, String posId, AdViewSpreadListener adListener) {
        spreadManager = AdManager.createSpreadAd();
        spreadManager.loadSpreadAd(this, appId, posId,
                (RelativeLayout) findViewById(R.id.spreadlayout));
        spreadManager.setBackgroundColor(getAdBackgroundColor() == null || getAdBackgroundColor() == 0 ? Color.WHITE : getAdBackgroundColor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getAdBackgroundDrawableId() != null)
            spreadManager.setBackgroundDrawable(getDrawable(getAdBackgroundDrawableId()));
        spreadManager.setSpreadNotifyType(AdManager.NOTIFY_COUNTER_NUM);
        spreadManager.setSpreadListener(adListener);
    }

    private AdViewSpreadListener splashADListener = new AdViewSpreadListener() {
        @Override
        public void onAdClicked() {
            Log.i("AdViewDemo", "onAdClicked");
        }

        @Override
        public void onAdClosed() {
            Log.i("AdViewDemo", "onAdClosedAd");
            next();
        }

        @Override
        public void onAdClosedByUser() {
            Log.i("AdViewDemo", "onAdClosedByUser");
            next();
        }

        @Override
        public void onRenderSuccess() {

        }

        @Override
        public void onAdDisplayed() {
            Log.i("AdViewDemo", "onAdDisplayed");
        }

        @Override
        public void onAdFailedReceived(String arg1) {
            Log.i("AdViewDemo", "onAdRecieveFailed");
            next();
        }

        @Override
        public void onAdReceived() {
            Log.i("AdViewDemo", "onAdRecieved");
        }

        @Override
        public void onAdSpreadPrepareClosed() {
            Log.i("AdViewDemo", "onAdSpreadPrepareClosed");
            next();
        }

    };


    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            this.finish();
        } else {
            canJump = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != spreadManager)
            spreadManager.destroy();

    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract String getAppId();

    protected abstract String getPosId();

    protected abstract @ColorInt
    Integer getAdBackgroundColor();

    protected abstract @DrawableRes
    Integer getAdBackgroundDrawableId();

}