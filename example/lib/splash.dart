import 'dart:async';

import 'package:flutter/material.dart';

class SplashAdHome extends StatefulWidget {
  @override
  _SplashAdHomeState createState() => _SplashAdHomeState();
}

class _SplashAdHomeState extends State<SplashAdHome> {
//  static final StreamController<String> _streamController =
//  StreamController<String>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
    );
  }
  void initState() {
    super.initState();
//    _streamController.stream.listen((_data) {
//      print(_data);
//    });
  }
  @override
  void dispose() {
    // TODO: implement dispose
//    _streamController.sink.close();
//    _streamController.close();
    super.dispose();
  }
}
