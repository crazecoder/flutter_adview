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


    private AdVideoUtil(Activity activity) {
        this.activity = activity;
        videoManager = AdManager.createVideoAd();
    }

    public static AdVideoUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new AdVideoUtil(activity);
        }
        return instance;
    }

    public void loadAd(String appId, String posId) {
        videoManager = AdManager.createVideoAd();
        videoManager.loadVideoAd(activity, appId, posId);
        videoManager.setVideoListener(this);
//        videoManager.autoCloseEnable(true);
        // 设置屏幕方向，取值可参照ActivityInfo.SCREEN_XXXXXX 定义的常量
        videoManager.setVideoOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onReceivedVideo() {
        Log.i(TAG, "onReceivedVideo");
    }

    @Override
    public void onFailedReceivedVideo(String error) {
        Log.i(TAG, "onFailedRecievedVideo:" + error);
    }

    @Override
    public void onVideoStartPlayed() {
        Log.i(TAG, "onVideoStartPlayed");
    }

    @Override
    public void onVideoFinished() {
        Log.i(TAG, "onVideoFinished");
    }

    @Override
    public void onVideoClicked() {
        Log.i(TAG, "onVideoClicked");
    }

    @Override
    public void onVideoClosed() {
        Log.i(TAG, "onVideoClosed");
    }

    @Override
    public void onPlayedError(String arg0) {
        Log.i(TAG, "onPlayedError:" + arg0);
    }

    @Override
    public void onVideoReady() {
        Log.i(TAG, "onVideoReady");
        videoManager.playVideo(activity);
    }
}
