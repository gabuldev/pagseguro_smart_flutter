import 'package:bot_toast/bot_toast.dart';

import 'package:pagseguro_smart_flutter/payments/handler/payment_handler.dart';

class PaymentController extends PaymentHandler {
  int saleValue = 0;
  bool enable = false;
  bool clickPayment = false;
  bool enableRefund = false;
  String? transactionCode;
  String? transactionId;
  String? response;

  void setSaleValue(double value) {
    if (value > 0.0) {
      saleValue = (value * 100).toInt();
      clickPayment = false;
      enable = true;
    } else {
      clickPayment = false;
      enable = false;
    }
  }

  @override
  void disposeDialog() {
    //BotToast.cleanAll();
  }

  @override
  void onAbortedSuccessfully() {
    BotToast.showText(text: "Operação cancelada");
  }

  @override
  void onActivationDialog() {}

  @override
  void onAuthProgress(String message) {
    BotToast.showLoading();
  }

  @override
  void onError(String message) {
    BotToast.showText(text: message);
  }

  @override
  void onMessage(String message) {
    BotToast.showText(text: message);
  }

  @override
  void onFinishedResponse(String message) {
    BotToast.showText(text: message);
  }

  @override
  void onTransactionSuccess() {
    BotToast.showText(text: "Transação com successo!");
  }

  @override
  void writeToFile({
    String? transactionCode,
    String? transactionId,
    String? response,
  }) {}

  @override
  void onLoading(bool show) {
    if (show) {
      BotToast.showLoading();
      return;
    }
    BotToast.closeAllLoading();
  }

  @override
  void onTransactionInfo({
    String? transactionCode,
    String? transactionId,
    String? response,
  }) {
    this.transactionCode = transactionCode;
    this.transactionId = transactionId;
    this.response = response;
    BotToast.showText(
        text:
            "{transactionCode: $transactionCode \n transactionId: $transactionId}");
    enableRefund = true;
  }
}
