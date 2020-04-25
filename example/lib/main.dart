import 'package:bot_toast/bot_toast.dart';
import 'package:example/payment/payment_page.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return BotToastInit(
      child: MaterialApp(
        navigatorObservers: [BotToastNavigatorObserver()],
        home: DefaultTabController(
          length: 2,
          child: Scaffold(
              appBar: AppBar(
                title: const Text('Pagseguro Smart Flutter'),
                bottom: TabBar(tabs: [
                  Tab(
                    child: Text("Vender"),
                  ),
                  Tab(
                    child: Text("Transações"),
                  )
                ]),
              ),
              body: TabBarView(children: [
                PaymentPage(),
                Container(),
              ])),
        ),
      ),
    );
  }
}
