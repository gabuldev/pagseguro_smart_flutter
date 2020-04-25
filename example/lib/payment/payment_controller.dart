import 'package:bot_toast/bot_toast.dart';
import 'package:pagseguro_smart_flutter/payments/handler/payment_handler.dart';

class PaymentController extends PaymentHandler {
  int saleValue = 0;
  bool enable = false;
  bool clickPayment = false;
  bool enableRefund = false;
  String transactionCode;
  String transactionId;

  void setSaleValue(double value) {
    if (value > 0.0) {
      saleValue = value.toInt() * 100;
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
  void onTransactionSuccess() {
    BotToast.showText(text: "Transacao com successo!");
  }

  @override
  void writeToFile({String transactionCode, String transactionId}) {
    // TODO: implement writeToFile
  }

  @override
  void onLoading(bool show) {
    BotToast.showLoading();
  }

  @override
  void onTransactionInfo({String transactionCode, String transactionId}) {
    this.transactionCode = transactionCode;
    this.transactionId = transactionId;
    BotToast.showText(
        text:
            "{transactionCode: $transactionCode \n transactionId: $transactionId}");
    enableRefund = true;
  }
}
