import 'package:flutter/material.dart';
import 'package:flutter_adview/flutter_adview.dart';

class BannerAdHome extends StatefulWidget {
  @override
  _BannerAdHomeState createState() => _BannerAdHomeState();
}

class _BannerAdHomeState extends State<BannerAdHome> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("banner"),),
      body: Text(
        "streamController: _streamController",
      ),
    );
  }
  void initState() {
    super.initState();
    FlutterAdview.showBannerAD(
        posId: "POSIDgwib87tv3erx");
  }
  @override
  void dispose() {
    FlutterAdview.disposeBannerAD(
        posId: "POSIDgwib87tv3erx");
    super.dispose();
  }
}
