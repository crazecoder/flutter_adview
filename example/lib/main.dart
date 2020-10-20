import 'package:flutter/material.dart';
import 'package:flutter_adview/flutter_adview.dart';

import 'banner.dart';
import 'splash.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MyApp() {
    FlutterAdview.initSdk("SDK20202220101051w961v1jtnexaluz");
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: MainHome());
  }
}

class MainHome extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Center(
        child: Column(
          children: [
            RaisedButton(
              child: Text("banner"),
              onPressed: () {
                Navigator.push(
                    context, MaterialPageRoute(builder: (_) => BannerAdHome()));
              },
            ),
            RaisedButton(
              child: Text("instl"),
              onPressed: () {
                FlutterAdview.loadInstlAd(
                    posId: "POSIDg8si72fdhlqx", isCloseable: true);
              },
            ),
            RaisedButton(
              child: Text("Video"),
              onPressed: () {
                FlutterAdview.loadVideoAd(
                    posId: "POSIDrcvtzgcjo2ah");
              },
            )
          ],
        ),
      ),
    );
  }
}
