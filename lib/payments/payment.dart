import 'package:flutter/services.dart';
import 'package:pagseguro_smart_flutter/payments/handler/payment_handler.dart';
import 'package:pagseguro_smart_flutter/payments/utils/payment_types.dart';

const CHANNEL_NAME = "pagseguro_smart_flutter";

class Payment {
  MethodChannel channel;
  PaymentHandler paymentHandler;

  Payment({Function(dynamic message) onMessage}) {
    channel = MethodChannel(CHANNEL_NAME);
    channel.setMethodCallHandler((call) async {
      onMessage(call.arguments.toString());
    });
  }
/*
  void _callHandler(MethodCall call){

    switch (call.method) {
      case :
        
        break;
      default:
    }
  }*/

  Future<bool> abortTransaction() async {
    return channel.invokeMethod(PaymentTypeCall.ABORT.method);
  }

  Future<bool> creditPayment(int value) async {
    return channel
        .invokeMethod(PaymentTypeCall.CREDIT.method, {"value": value});
  }

  Future<bool> debitPayment(int value) async {
    return channel.invokeMethod(PaymentTypeCall.DEBIT.method, {"value": value});
  }

  Future<bool> voucherPayment(int value) async {
    return channel
        .invokeMethod(PaymentTypeCall.VOUCHER.method, {"value": value});
  }
}
