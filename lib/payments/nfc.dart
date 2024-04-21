import 'package:flutter/services.dart';
import 'package:pagseguro_smart_flutter/payments/handler/nfc_handler.dart';
import 'package:pagseguro_smart_flutter/payments/utils/payment_types.dart';

const CHANNEL_NFC_NAME = "pagseguro_smart_flutter";

class Nfc {
  final MethodChannel channel;
  final NfcHandler nfcHandler;

  Nfc({required this.channel, required this.nfcHandler}) {
    channel.setMethodCallHandler(_callHandler);
  }

  Future<void> closeMethodCallHandler() async {
    return channel.setMethodCallHandler(null);
  }

  //OPERATIONS
  Future<bool> abortTransaction() async {
    await channel.invokeMethod(PaymentTypeCall.ABORT.method);
    return true;
  }

  Future<bool> lastTransaction() async {
    await channel.invokeMethod(PaymentTypeCall.LAST_TRANSACTION.method);
    return true;
  }

  Future<bool> refund({required String transactionCode, required String transactionId}) async {
    await channel.invokeMethod(PaymentTypeCall.REFUND.method, {"transactionCode": transactionCode, "transactionId": transactionId});
    return true;
  }

  Future<bool> readNfc(idEvento) async {
    await channel.invokeMethod(PaymentTypeCall.READ_NFC.method, {"idEvento": idEvento});
    return true;
  }

  Future<bool> writeNfc(valor, nome, cpf, numeroTag, celular, aberto, idEvento) async {
    await channel.invokeMethod(PaymentTypeCall.WRITE_NFC.method, {"valor": valor, "nome": nome, "cpf": cpf, "numeroTag": numeroTag, "celular": celular, "aberto": aberto, "idEvento": idEvento});
    return true;
  }

  Future<bool> reWriteNfc(valor, idEvento) async {
    await channel.invokeMethod(PaymentTypeCall.REWRITE_NFC.method, {"valor": valor, "idEvento": idEvento});
    return true;
  }

  Future<bool> refundNfc(valor, idEvento) async {
    await channel.invokeMethod(PaymentTypeCall.REFUND_NFC.method, {"valor": valor, "idEvento": idEvento});
    return true;
  }

  Future<bool> formatNfc() async {
    await channel.invokeMethod(PaymentTypeCall.FORMAT_NFC.method);
    return true;
  }

  Future<bool> debitNfc(idEvento, valor) async {
    await channel.invokeMethod(PaymentTypeCall.DEBIT_NFC.method, {"idEvento": idEvento, "valor": valor});
    return true;
  }

  Future<dynamic> _callHandler(MethodCall call) async {
    switch (call.method.handler) {
      case PaymentTypeHandler.ON_TRANSACTION_SUCCESS:
        {
          nfcHandler.onTransactionSuccess();
        }
        break;
      case PaymentTypeHandler.ON_ERROR:
        {
          nfcHandler.onError(call.arguments);
        }
        break;
      case PaymentTypeHandler.ON_MESSAGE:
        {
          nfcHandler.onMessage(call.arguments);
        }
        break;
      case PaymentTypeHandler.ON_LOADING:
        {
          nfcHandler.onLoading(call.arguments);
        }
        break;
      case PaymentTypeHandler.WRITE_TO_FILE:
        {
          nfcHandler.writeToFile(transactionCode: call.arguments['transactionCode'], transactionId: call.arguments['transactionId']);
        }
        break;
      case PaymentTypeHandler.ON_ABORTED_SUCCESSFULLY:
        {
          nfcHandler.onAbortedSuccessfully();
        }
        break;
      case PaymentTypeHandler.DISPOSE_DIALOG:
        {
          nfcHandler.disposeDialog();
        }
        break;
      case PaymentTypeHandler.ACTIVE_DIALOG:
        {
          nfcHandler.onActivationDialog();
        }
        break;
      case PaymentTypeHandler.ON_AUTH_PROGRESS:
        {
          nfcHandler.onAuthProgress(call.arguments);
        }
        break;

      case PaymentTypeHandler.ON_TRANSACTION_INFO:
        {
          nfcHandler.onTransactionInfo(transactionCode: call.arguments['transactionCode'], transactionId: call.arguments['transactionId']);
        }
        break;

      case PaymentTypeHandler.SHOW_SUCCESS:
        {
          nfcHandler.showSuccess(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_SUCCESS_WRITE:
        {
          nfcHandler.showSuccessWrite(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_SUCCESS_RE_WRITE:
        {
          nfcHandler.showSuccessReWrite(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_SUCCESS_DEBIT_NFC:
        {
          nfcHandler.showSuccessDebitNfc(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_SUCCESS_REFUND_NFC:
        {
          nfcHandler.showSuccessRefundNfc(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_SUCCESS_FORMAT:
        {
          nfcHandler.showSuccessFormat(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_ERROR_READ:
        {
          nfcHandler.showErrorRead(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_ERROR_WRITE:
        {
          nfcHandler.showErrorWrite(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_ERROR_RE_WRITE:
        {
          nfcHandler.showErrorReWrite(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_ERROR_FORMAT:
        {
          nfcHandler.showErrorFormat(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_ERROR_DEBIT_NFC:
        {
          nfcHandler.showErrorDebitNfc(call.arguments);
        }
        break;

      case PaymentTypeHandler.SHOW_ERROR_REFUND_NFC:
        {
          nfcHandler.showErrorRefundNfc(call.arguments);
        }
        break;

      default:
        throw "METHOD NOT IMPLEMENTED";
    }
    return true;
  }
}
