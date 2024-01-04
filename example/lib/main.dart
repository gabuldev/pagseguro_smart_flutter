import 'package:flutter/material.dart';

import 'package:bot_toast/bot_toast.dart';

import 'payment/payment_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorObservers: [BotToastNavigatorObserver()],
      builder: BotToastInit(), //1. call BotToastInit

      home: DefaultTabController(
        length: 2,
        child: Scaffold(
          appBar: AppBar(
            title: const Text('Pagseguro Smart Flutter'),
            bottom: const TabBar(tabs: [
              Tab(
                child: Text("Vender"),
              ),
              Tab(
                child: Text("Transações"),
              )
            ]),
          ),
          body: TabBarView(
            children: [
              const PaymentPage(),
              Container(),
            ],
          ),
        ),
      ),
    );
  }
}
