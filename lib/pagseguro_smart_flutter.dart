import 'package:flutter/services.dart';

import 'payments/handler/payment_handler.dart';
import 'payments/payment.dart';

class PagseguroSmart {
  final MethodChannel _channel;
  Payment? _payment;

  static PagseguroSmart? _instance;

  PagseguroSmart(this._channel);

  static PagseguroSmart instance() {
    _instance ??= PagseguroSmart(const MethodChannel(CHANNEL_NAME));
    return _instance!;
  }

  void initPayment(PaymentHandler handler) {
    _payment = Payment(channel: _channel, paymentHandler: handler);
  }

  Payment get payment {
    if (_payment == null) {
      throw "PAYMENT NEED INITIALIZE! \n TRY: PagseguroSmart._instance.initPayment(handler)";
    }
    return _payment!;
  }
}
