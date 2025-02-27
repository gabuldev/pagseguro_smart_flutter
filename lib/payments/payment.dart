// ignore_for_file: constant_identifier_names
import 'package:flutter/services.dart';

import 'package:pagseguro_smart_flutter/payments/handler/payment_handler.dart';
import 'package:pagseguro_smart_flutter/payments/utils/payment_types.dart';

//Fixed channel name
const CHANNEL_NAME = "pagseguro_smart_flutter";
const USER_REFERENCE = "SMARTPOS"; //Only letters and numbers

class Payment {
  final MethodChannel channel;
  final PaymentHandler paymentHandler;

  Payment({
    required this.channel,
    required this.paymentHandler,
  }) {
    channel.setMethodCallHandler(_callHandler);
  }
  //Create external functions from invoke methodChannel
  //Function to active pinpad with sdk the PagSeguro
  Future<bool> activePinpad(String activationCode) async {
    try {
      await channel.invokeMethod(PaymentTypeCall.ACTIVEPINPAD.method, {"code": activationCode});
      return true;
    } catch (e) {
      return false;
    }
  }

//Function to invoke method from credit payment with sdk the PagSeguro
  Future<bool> creditPayment(int value, {String userReference = USER_REFERENCE, bool printReceipt = true, bool partialPay = false, bool isCarne = false}) async {
    return await channel.invokeMethod(PaymentTypeCall.CREDIT.method, {
      "value": value,
      "userReference": _sanitizeUserReference(userReference),
      "printReceipt": printReceipt,
      "partialPay": partialPay,
      "isCarne": isCarne,
    });
  }

//Function to invoke method from credit installment payment  with sdk the PagSeguro
  Future<bool> creditPaymentParc(int value, int parc, {PaymentTypeCredit type = PaymentTypeCredit.CLIENT, String userReference = USER_REFERENCE, bool printReceipt = true, bool partialPay = false, bool isCarne = false}) async {
    return await channel.invokeMethod(PaymentTypeCall.CREDIT_PARC.method, {
      "value": value,
      "parc": parc,
      "type": type.value,
      "userReference": _sanitizeUserReference(userReference),
      "printReceipt": printReceipt,
      "partialPay": partialPay,
      "isCarne": isCarne,
    });
  }

//Function to invoke method from debit payment with sdk the PagSeguro
  Future<bool> debitPayment(int value, {String userReference = USER_REFERENCE, bool printReceipt = true, bool partialPay = false, bool isCarne = false}) async {
    return await channel.invokeMethod(PaymentTypeCall.DEBIT.method, {
      "value": value,
      "userReference": _sanitizeUserReference(userReference),
      "printReceipt": printReceipt,
      "partialPay": partialPay,
      "isCarne": isCarne,
    });
  }

  //Function to invoke method from debit payment with sdk the PagSeguro
  Future<bool> pixPayment(int value, {String userReference = USER_REFERENCE, bool printReceipt = true, bool partialPay = false, bool isCarne = false}) async {
    return await channel.invokeMethod(PaymentTypeCall.PIX.method, {
      "value": value,
      "userReference": _sanitizeUserReference(userReference),
      "printReceipt": printReceipt,
      "partialPay": partialPay,
      "isCarne": isCarne,
    });
  }

//Function to invoke method from voucher payment with sdk the PagSeguro
  Future<bool> voucherPayment(int value, {String userReference = USER_REFERENCE, bool printReceipt = true, bool partialPay = false, bool isCarne = false}) async {
    return await channel.invokeMethod(PaymentTypeCall.VOUCHER.method, {
      "value": value,
      "userReference": _sanitizeUserReference(userReference),
      "printReceipt": printReceipt,
      "partialPay": partialPay,
      "isCarne": isCarne,
    });
  }

//Function to invoke method from credit installment payment  with sdk the PagSeguro
  Future<bool> startPayment(PaymentType type, int amount, {PaymentTypeCredit installmentType = PaymentTypeCredit.CLIENT, int installments = 1, String userReference = USER_REFERENCE, bool printReceipt = true, bool partialPay = false, bool isCarne = false}) async {
    return await channel.invokeMethod(PaymentTypeCall.START_PAYMENT.method, {
      "type": type.value,
      "amount": amount,
      "installmentType": installmentType.value,
      "installments": installments,
      "userReference": _sanitizeUserReference(userReference),
      "printReceipt": printReceipt,
      "partialPay": partialPay,
      "isCarne": isCarne,
    });
  }

  //OPERATIONS
  //Function to invoke method from abort current transaction with sdk the PagSeguro
  Future<bool> abortTransaction() async {
    return await channel.invokeMethod(PaymentTypeCall.ABORT.method);
  }

//Function to invoke method from get last transaction with sdk the PagSeguro
  Future<bool> lastTransaction() async {
    return await channel.invokeMethod(PaymentTypeCall.LAST_TRANSACTION.method);
  }

//Function to invoke method from refund transaction with sdk the PagSeguro
  Future<bool> refund({String? transactionCode, String? transactionId}) async {
    return await channel.invokeMethod(PaymentTypeCall.REFUND.method, {"transactionCode": transactionCode, "transactionId": transactionId});
  }

//Function to invoke method from return status of the pinpad
  Future<bool> isAuthenticated() async {
    return await channel.invokeMethod(PaymentTypeCall.PINPAD_AUTHENTICATED.method);
  }

  //Function to printer from file path
  Future<bool> printerfromFile(String path) async {
    try {
      await channel.invokeMethod(
        PaymentTypeCall.PRINTER_FILE.method,
        {
          "path": path,
        },
      );
      return true;
    } catch (e) {
      return false;
    }
  }

  //Function to printerFile from fileName
  Future<bool> printerFile(String fileName) async {
    try {
      await channel.invokeMethod(
        PaymentTypeCall.PRINTER.method,
        {
          "path": fileName,
        },
      );
      return true;
    } catch (e) {
      return false;
    }
  }

  //Function to printer from file path
  Future<bool> printer(String filePath) async {
    try {
      await channel.invokeMethod(
        PaymentTypeCall.PRINTER_BASIC.method,
        {
          "path": filePath,
        },
      );
      return true;
    } catch (e) {
      return false;
    }
  }

  Future<bool> printerFilePath(String filePath) async {
    try {
      await channel.invokeMethod(
        PaymentTypeCall.PRINTER_FILE_PATH.method,
        {
          "path": filePath,
        },
      );
      return true;
    } catch (e) {
      return false;
    }
  }

  //Reboot device
  Future<bool> rebootDevice() async {
    try {
      channel.invokeMethod(PaymentTypeCall.REBOOT.method);
      return true;
    } catch (e) {
      return false;
    }
  }

  //Emitter beep device
  Future<bool> beep() async {
    try {
      channel.invokeMethod(PaymentTypeCall.BEEP.method);
      return true;
    } catch (e) {
      return false;
    }
  }

//Function to listen to pagseguro returns in the native environment and notify Flutter
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
      case PaymentTypeHandler.ON_FINISHED_RESPONSE:
        {
          paymentHandler.onFinishedResponse(call.arguments);
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
            transactionId: call.arguments['transactionId'],
            response: call.arguments['response'],
          );
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
            transactionId: call.arguments['transactionId'],
            response: call.arguments['response'],
          );
        }
        break;

      default:
        throw "METHOD NOT IMPLEMENTED";
    }
    return true;
  }

  String _sanitizeUserReference(String value) {
    if (value.length <= 10) {
      return value;
    }
    return value.substring(0, 10);
  }
}
