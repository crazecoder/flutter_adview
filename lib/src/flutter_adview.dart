import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAdview {
  static const MethodChannel _channel = const MethodChannel('flutter_adview');

  static Future<Null> initSdk(String appId,
      {bool downloadNotificationEnable = false}) async {
    assert(appId != null && appId.isNotEmpty);
    Map map = {
      "appId": appId,
      "downloadNotificationEnable": downloadNotificationEnable,
    };
    await _channel.invokeMethod('initAdSDK', map);
  }

  static Future<Null> loadInstlAd(
      {String posId, bool isCloseable = true}) async {
    assert(posId != null && posId.isNotEmpty);
    Map map = {
      "posId": posId,
      "isCloseable": isCloseable,
    };
    await _channel.invokeMethod('loadInstlAd', map);
  }

  static Future<Null> loadVideoAd(
      {String posId}) async {
    assert(posId != null && posId.isNotEmpty);
    Map map = {
      "posId": posId,
    };
    await _channel.invokeMethod('loadVideoAd', map);
  }

  static Future<Null> showBannerAD({String posId}) async {
    assert(posId != null);
    Map map = {
      "posId": posId,
    };
    await _channel.invokeMethod('showBannerAD', map);
  }

  static Future<Null> disposeBannerAD({String posId}) async {
    assert(posId != null);
    Map map = {
      "posId": posId,
    };
    await _channel.invokeMethod('disposeBannerAD', map);
  }
}
