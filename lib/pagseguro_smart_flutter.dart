import 'package:flutter/services.dart';

import 'package:pagseguro_smart_flutter/payments/handler/nfc_handler.dart';
import 'package:pagseguro_smart_flutter/payments/nfc.dart';

import 'payments/handler/payment_handler.dart';
import 'payments/payment.dart';

export 'package:pagseguro_smart_flutter/payments/print_render_widget.dart';

class PagseguroSmart {
  final MethodChannel _channel;
  Payment? _payment;
  Nfc? _nfc;

  static PagseguroSmart? _instance;

  PagseguroSmart(this._channel);

//GET instance from PagseguroSmart
  static PagseguroSmart instance() {
    _instance ??= PagseguroSmart(const MethodChannel(CHANNEL_NAME));
    //Create instance from channel
    return _instance!;
  }

//Function to init payment and register handler from notify
  void initPayment(PaymentHandler handler) {
    _payment = Payment(channel: _channel, paymentHandler: handler);
    //Register handler from notify
  }

  //Function to init nfc and register handler from notify
  void initNfc(NfcHandler handler) {
    _nfc = Nfc(channel: _channel, nfcHandler: handler);
    //Register handler from notify
  }

  Payment get payment {
    if (_payment == null) {
      throw "PAYMENT NEED INITIALIZE! \n TRY: PagseguroSmart._instance.initPayment(handler)";
    }
    return _payment!;
  }

  Nfc get nfc {
    if (_nfc == null) {
      throw "NFC NEED INITIALIZE! \n TRY: PagseguroSmart._instance.initNfc(handler)";
    }
    return _nfc!;
  }
}
