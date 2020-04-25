import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:pagseguro_smart_flutter/payments/handler/payment_handler.dart';
import 'package:pagseguro_smart_flutter/payments/utils/payment_types.dart';

const CHANNEL_NAME = "pagseguro_smart_flutter";

class Payment {
  final MethodChannel channel;
  final PaymentHandler paymentHandler;

  Payment({@required this.channel, @required this.paymentHandler}) {
    channel.setMethodCallHandler(_callHandler);
  }
  //TYPES

  Future<bool> creditPayment(int value) async {
    return channel
        .invokeMethod(PaymentTypeCall.CREDIT.method, {"value": value});
  }

  Future<bool> creditPaymentParc(int value, int parc,
      {PaymentTypeCredit type = PaymentTypeCredit.CLIENT}) async {
    return channel.invokeMethod(PaymentTypeCall.CREDIT_PARC.method,
        {"value": value, "parc": parc, "type": type.value});
  }

  Future<bool> debitPayment(int value) async {
    return channel.invokeMethod(PaymentTypeCall.DEBIT.method, {"value": value});
  }

  Future<bool> voucherPayment(int value) async {
    return channel
        .invokeMethod(PaymentTypeCall.VOUCHER.method, {"value": value});
  }

  //OPERATIONS
  Future<bool> abortTransaction() async {
    return channel.invokeMethod(PaymentTypeCall.ABORT.method);
  }

  Future<bool> lastTransaction() async {
    return channel.invokeMethod(PaymentTypeCall.LAST_TRANSACTION.method);
  }

  Future<bool> refund({String transactionCode, String transactionId}) async {
    return channel.invokeMethod(PaymentTypeCall.REFUND.method,
        {"transactionCode": transactionCode, "transactionId": transactionId});
  }

  Future<dynamic> _callHandler(MethodCall call) async {
    switch (call.method.handler) {
      case PaymentTypeHandler.ON_TRANSACTION_SUCCESS:
        {
          paymentHandler.onTransactionSuccess();
        }
        break;
      case PaymentTypeHandler.ON_ERROR:
        {
          paymentHandler.onError(call.arguments);
        }
        break;
      case PaymentTypeHandler.ON_MESSAGE:
        {
          paymentHandler.onMessage(call.arguments);
        }
        break;
      case PaymentTypeHandler.ON_LOADING:
        {
          paymentHandler.onLoading(call.arguments);
        }
        break;
      case PaymentTypeHandler.WRITE_TO_FILE:
        {
          paymentHandler.writeToFile(
              transactionCode: call.arguments['transactionCode'],
              transactionId: call.arguments['transactionId']);
        }
        break;
      case PaymentTypeHandler.ON_ABORTED_SUCCESSFULLY:
        {
          paymentHandler.onAbortedSuccessfully();
        }
        break;
      case PaymentTypeHandler.DISPOSE_DIALOG:
        {
          paymentHandler.disposeDialog();
        }
        break;
      case PaymentTypeHandler.ACTIVE_DIALOG:
        {
          paymentHandler.onActivationDialog();
        }
        break;
      case PaymentTypeHandler.ON_AUTH_PROGRESS:
        {
          paymentHandler.onAuthProgress(call.arguments);
        }
        break;

      case PaymentTypeHandler.ON_TRANSACTION_INFO:
        {
          paymentHandler.onTransactionInfo(
              transactionCode: call.arguments['transactionCode'],
              transactionId: call.arguments['transactionId']);
        }
        break;
      default:
        throw "METHOD NOT IMPLEMENTED";
    }
    return true;
  }
}
