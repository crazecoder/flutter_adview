package com.crazecoder.flutter.adview.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;

import com.kuaiyou.open.AdManager;
import com.kuaiyou.open.VideoManager;
import com.kuaiyou.open.interfaces.AdViewVideoListener;

public class AdVideoUtil implements AdViewVideoListener {
    private static final String TAG = "AdVideoUtil";
    private static AdVideoUtil instance;
    private static VideoManager videoManager = null;
    private Activity activity;


    private AdVideoUtil() {
        videoManager = AdManager.createVideoAd();
    }

    public static AdVideoUtil getInstance() {
        if (instance == null) {
            instance = new AdVideoUtil();
        }
        return instance;
    }

    public AdVideoUtil setActivity(Activity activity) {
        this.activity = activity;
        return instance;
    }

    public void loadAd(String appId, String posId) {
        videoManager = AdManager.createVideoAd();
        videoManager.loadVideoAd(activity.getApplicationContext(), appId, posId);
        videoManager.setVideoListener(this);
//        videoManager.autoCloseEnable(true);
        // 设置屏幕方向，取值可参照ActivityInfo.SCREEN_XXXXXX 定义的常量
        videoManager.setVideoOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onReceivedVideo() {
        LogUtil.i(TAG, "onReceivedVideo");
    }

    @Override
    public void onFailedReceivedVideo(String error) {
        LogUtil.i(TAG, "onFailedRecievedVideo:" + error);
    }

    @Override
    public void onVideoStartPlayed() {
        LogUtil.i(TAG, "onVideoStartPlayed");
    }

    @Override
    public void onVideoFinished() {
        LogUtil.i(TAG, "onVideoFinished");
    }

    @Override
    public void onVideoClicked() {
        LogUtil.i(TAG, "onVideoClicked");
    }

    @Override
    public void onVideoClosed() {
        LogUtil.i(TAG, "onVideoClosed");
    }

    @Override
    public void onPlayedError(String arg0) {
        LogUtil.i(TAG, "onPlayedError:" + arg0);
    }

    @Override
    public void onVideoReady() {
        LogUtil.i(TAG, "onVideoReady");
        videoManager.playVideo(activity.getApplicationContext());
    }
}
