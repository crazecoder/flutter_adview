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


    private AdInstlUtil(Activity activity) {
        this.activity = activity;
        instlManager = AdManager.createInstlAd();
    }

    public static AdInstlUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new AdInstlUtil(activity);
        }
        return instance;
    }

    public void loadAd(String appId, String posId, boolean isCloseable) {
        Log.i("AdViewDemo", "appId:"+appId+"  posId:"+posId);

        instlManager.loadInstlAd(activity, appId, posId, isCloseable);//有关闭按钮：true，无关闭按钮：false
        instlManager.setInstlListener(this);
    }

    @Override
    public void onAdClicked() {
        Log.i("AdViewDemo", "onAdClicked");
    }

    @Override
    public void onAdClosed() {
        Log.i("AdViewDemo", "onAdClosedAd");
    }

    @Override
    public void onAdReady() {
        Log.i("AdViewDemo", "onAdReady");
        instlManager.showInstl(activity);
    }

    @Override
    public void onAdDisplayed() {
        Log.i("AdViewDemo", "onDisplayed");
    }

    @Override
    public void onAdFailedReceived(String arg1) {
        Log.i("AdViewDemo", "onAdRecieveFailed：" + arg1);
    }

    @Override
    public void onAdReceived() {
        Log.i("AdViewDemo", "onAdRecieved");
    }
}
