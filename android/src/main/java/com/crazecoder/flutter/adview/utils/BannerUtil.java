package com.crazecoder.flutter.adview.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kuaiyou.open.AdManager;
import com.kuaiyou.open.BannerManager;
import com.kuaiyou.open.interfaces.AdViewBannerListener;

import java.util.HashMap;
import java.util.Map;

import io.flutter.Log;

public class BannerUtil implements AdViewBannerListener {
    private static final String TAG = "BannerUtil";
    private Activity activity;
    private Map<String, View> cache;
    private String posId;
    private static BannerUtil instance;
    private static BannerManager bannerManager = null;


    private BannerUtil(Activity activity) {
        this.activity = activity;
        cache = new HashMap<>();
        bannerManager = AdManager.createBannerAd();
    }

    public static BannerUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new BannerUtil(activity);
        }
        return instance;
    }

    public View getBanner(String appId, String posId) {
        bannerManager.loadBannerAd(activity, appId, posId, 5);
        bannerManager.setShowCloseBtn(true);
        bannerManager.setRefreshTime(15);
        bannerManager.setBannerListener(this);
        View bv = bannerManager.getBannerLayout();
        cache.put(posId, bv);
        return bv;
    }

    public void show(String posId) {
        this.posId = posId;
        View bv = cache.get(posId);
        if (bv != null) {
            LinearLayout content = new LinearLayout(activity);
            content.setOrientation(LinearLayout.VERTICAL);
            content.setGravity(Gravity.BOTTOM);
            content.addView(bv, getUnifiedBannerLayoutParams());
            activity.addContentView(
                    content,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void dispose(String posId) {
        View bv = cache.get(posId);
        if (bv != null) {
            View contentView = (View) bv.getParent();
            if (contentView == null || !(contentView.getParent() instanceof ViewGroup)) return;

            ViewGroup contentParent = (ViewGroup) (contentView.getParent());
            contentParent.removeView(contentView);
        }
        this.posId = null;
    }

    private LinearLayout.LayoutParams getUnifiedBannerLayoutParams() {
        Point screenSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        return new LinearLayout.LayoutParams(screenSize.x, Math.round(screenSize.x / 6.4F));
    }


    @Override
    public void onAdClicked() {
        Log.i("AdViewDemo", "onAdClicked");
    }

    @Override
    public void onAdClosed() {
        Log.i("AdViewDemo", "onAdClosedAd");
        if (null != posId) {
            dispose(posId);
        }
    }

    @Override
    public void onAdDisplayed() {
        Log.i("AdViewDemo", "onAdDisplayed");
    }

    @Override
    public void onAdFailedReceived(String arg1) {
        Log.i("AdViewDemo", "onAdRecieveFailed");
    }

    @Override
    public void onAdReceived() {
        Log.i("AdViewDemo", "onAdRecieved");
    }

}
