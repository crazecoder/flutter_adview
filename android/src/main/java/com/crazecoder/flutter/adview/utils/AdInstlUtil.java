package com.crazecoder.flutter.adview.utils;

import android.app.Activity;
import android.util.Log;

import com.kuaiyou.open.AdManager;
import com.kuaiyou.open.InstlManager;
import com.kuaiyou.open.interfaces.AdViewInstlListener;


public class AdInstlUtil implements AdViewInstlListener {
    private static AdInstlUtil instance;
    private static InstlManager instlManager = null;
    private Activity activity;


    private AdInstlUtil() {
        instlManager = AdManager.createInstlAd();
    }

    public static AdInstlUtil getInstance() {
        if (instance == null) {
            instance = new AdInstlUtil();
        }
        return instance;
    }

    public AdInstlUtil setActivity(Activity activity) {
        this.activity = activity;
        return instance;
    }

    public void loadAd(String appId, String posId, boolean isCloseable) {
        instlManager.loadInstlAd(activity, appId, posId, isCloseable);//有关闭按钮：true，无关闭按钮：false
        instlManager.setInstlListener(this);
    }

    @Override
    public void onAdClicked() {
        LogUtil.i("AdViewDemo", "onAdClicked");
    }

    @Override
    public void onAdClosed() {
        LogUtil.i("AdViewDemo", "onAdClosedAd");
    }

    @Override
    public void onAdReady() {
        LogUtil.i("AdViewDemo", "onAdReady");
        instlManager.showInstl(activity);
    }

    @Override
    public void onAdDisplayed() {
        LogUtil.i("AdViewDemo", "onDisplayed");
    }

    @Override
    public void onAdFailedReceived(String arg1) {
        LogUtil.i("AdViewDemo", "onAdRecieveFailed：" + arg1);
    }

    @Override
    public void onAdReceived() {
        LogUtil.i("AdViewDemo", "onAdRecieved");
    }
}
