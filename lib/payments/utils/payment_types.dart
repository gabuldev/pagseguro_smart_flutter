//Fixed payment type hanldle from return functions
enum PaymentTypeHandler {
  ON_TRANSACTION_SUCCESS,
  ON_ERROR,
  ON_MESSAGE,
  ON_LOADING,
  WRITE_TO_FILE,
  ON_ABORTED_SUCCESSFULLY,
  DISPOSE_DIALOG,
  ACTIVE_DIALOG,
  ON_AUTH_PROGRESS,
  ON_TRANSACTION_INFO,
  ON_FINISHED_RESPONSE,
  SHOW_SUCCESS,
  SHOW_SUCCESS_WRITE,
  SHOW_SUCCESS_RE_WRITE,
  SHOW_SUCCESS_FORMAT,
  SHOW_SUCCESS_DEBIT_NFC,
  SHOW_SUCCESS_REFUND_NFC,
  SHOW_ERROR_READ,
  SHOW_ERROR_WRITE,
  SHOW_ERROR_RE_WRITE,
  SHOW_ERROR_FORMAT,
  SHOW_ERROR_DEBIT_NFC,
  SHOW_ERROR_REFUND_NFC,
}

extension StringPaymentHandlerExt on String {
  get handler {
    switch (this) {
      case "onTransactionSucess":
        return PaymentTypeHandler.ON_TRANSACTION_SUCCESS;
      case "onError":
        return PaymentTypeHandler.ON_ERROR;
      case "onMessage":
        return PaymentTypeHandler.ON_MESSAGE;
      case "onFinishedResponse":
        return PaymentTypeHandler.ON_FINISHED_RESPONSE;
      case "onLoading":
        return PaymentTypeHandler.ON_LOADING;
      case "writeToFile":
        return PaymentTypeHandler.WRITE_TO_FILE;
      case "onAbortedSuccessfully":
        return PaymentTypeHandler.ON_ABORTED_SUCCESSFULLY;
      case "disposeDialog":
        return PaymentTypeHandler.DISPOSE_DIALOG;
      case "activeDialog":
        return PaymentTypeHandler.ACTIVE_DIALOG;
      case "onAuthProgress":
        return PaymentTypeHandler.ON_AUTH_PROGRESS;
      case "onTransactionInfo":
        return PaymentTypeHandler.ON_TRANSACTION_INFO;
      case "showSuccess":
        return PaymentTypeHandler.SHOW_SUCCESS;
      case "showSuccessWrite":
        return PaymentTypeHandler.SHOW_SUCCESS_WRITE;
      case "showSuccessReWrite":
        return PaymentTypeHandler.SHOW_SUCCESS_RE_WRITE;
      case "showSuccessFormat":
        return PaymentTypeHandler.SHOW_SUCCESS_FORMAT;
      case "showSuccessDebitNfc":
        return PaymentTypeHandler.SHOW_SUCCESS_DEBIT_NFC;
      case "showSuccessRefundNfc":
        return PaymentTypeHandler.SHOW_SUCCESS_REFUND_NFC;
      case "showErrorRead":
        return PaymentTypeHandler.SHOW_ERROR_READ;
      case "showErrorWrite":
        return PaymentTypeHandler.SHOW_ERROR_WRITE;
      case "showErrorReWrite":
        return PaymentTypeHandler.SHOW_ERROR_RE_WRITE;
      case "showErrorFormat":
        return PaymentTypeHandler.SHOW_ERROR_FORMAT;
      case "showErrorDebitNfc":
        return PaymentTypeHandler.SHOW_ERROR_DEBIT_NFC;
      case "showErrorRefundNfc":
        return PaymentTypeHandler.SHOW_ERROR_REFUND_NFC;
      default:
        throw "NOT IMPLEMENTED";
    }
  }
}

extension PaymentTypeHandlerExt on PaymentTypeHandler {
  get method {
    switch (this) {
      case PaymentTypeHandler.ON_TRANSACTION_SUCCESS:
        return "onTransactionSucess";
      case PaymentTypeHandler.ON_ERROR:
        return "onError";
      case PaymentTypeHandler.ON_MESSAGE:
        return "onMessage";
      case PaymentTypeHandler.ON_LOADING:
        return "onLoading";
      case PaymentTypeHandler.WRITE_TO_FILE:
        return "writeToFile";
      case PaymentTypeHandler.ON_ABORTED_SUCCESSFULLY:
        return "onAbortedSuccessfully";
      case PaymentTypeHandler.DISPOSE_DIALOG:
        return "disposeDialog";
      case PaymentTypeHandler.ACTIVE_DIALOG:
        return "activeDialog";
      case PaymentTypeHandler.ON_AUTH_PROGRESS:
        return "onAuthProgress";
      case PaymentTypeHandler.ON_TRANSACTION_INFO:
        return "onTransactionInfo";
      case PaymentTypeHandler.ON_FINISHED_RESPONSE:
        return "onFinishedResponse";
      case PaymentTypeHandler.SHOW_SUCCESS:
        return "showSuccess";
      case PaymentTypeHandler.SHOW_SUCCESS_WRITE:
        return "showSuccessWrite";
      case PaymentTypeHandler.SHOW_SUCCESS_RE_WRITE:
        return "showSuccessReWrite";
      case PaymentTypeHandler.SHOW_SUCCESS_FORMAT:
        return "showSuccessFormat";
      case PaymentTypeHandler.SHOW_SUCCESS_DEBIT_NFC:
        return "showSuccessDebitNfc";
      case PaymentTypeHandler.SHOW_SUCCESS_REFUND_NFC:
        return "showSuccessRefundNfc";
      case PaymentTypeHandler.SHOW_ERROR_READ:
        return "showErrorRead";
      case PaymentTypeHandler.SHOW_ERROR_WRITE:
        return "showErrorWrite";
      case PaymentTypeHandler.SHOW_ERROR_RE_WRITE:
        return "showErrorReWrite";
      case PaymentTypeHandler.SHOW_ERROR_FORMAT:
        return "showErrorFormat";
      case PaymentTypeHandler.SHOW_ERROR_DEBIT_NFC:
        return "showErrorDebitNfc";
      case PaymentTypeHandler.SHOW_ERROR_REFUND_NFC:
    }
  }
}

//fixed payment type to call from channel
enum PaymentTypeCall { CREDIT, CREDIT_PARC, DEBIT, PIX, VOUCHER, ABORT, LAST_TRANSACTION, REFUND, ACTIVEPINPAD, PINPAD_AUTHENTICATED, READ_NFC, WRITE_NFC, REWRITE_NFC, REFUND_NFC, DEBIT_NFC, FORMAT_NFC, PRINTER_FILE, START_PAYMENT, PRINTER, PRINTER_BASIC, PRINTER_FILE_PATH, REBOOT, BEEP }

enum PaymentTypeCredit {
  SALESMAN,
  CLIENT,
}

extension PaymentTypeCreditExt on PaymentTypeCredit {
  get value {
    switch (this) {
      case PaymentTypeCredit.SALESMAN:
        return 2;
      case PaymentTypeCredit.CLIENT:
        return 3;
    }
  }
}

//Fixed method to call on methodChannel
extension PaymentTypeCallExt on PaymentTypeCall {
  get method {
    switch (this) {
      case PaymentTypeCall.CREDIT:
        return "paymentCredit";
      case PaymentTypeCall.CREDIT_PARC:
        return "paymentCreditParc";
      case PaymentTypeCall.DEBIT:
        return "paymentDebit";
      case PaymentTypeCall.VOUCHER:
        return "paymentVoucher";
      case PaymentTypeCall.ABORT:
        return "paymentAbort";
      case PaymentTypeCall.LAST_TRANSACTION:
        return "paymentLastTransaction";
      case PaymentTypeCall.REFUND:
        return "paymentRefund";
      case PaymentTypeCall.ACTIVEPINPAD:
        return "paymentActivePinpad";
      case PaymentTypeCall.PINPAD_AUTHENTICATED:
        return "paymentIsAuthenticated";
      case PaymentTypeCall.PIX:
        return "paymentPix";
      case PaymentTypeCall.READ_NFC:
        return "paymentReadNfc";
      case PaymentTypeCall.WRITE_NFC:
        return "paymentWriteNfc";
      case PaymentTypeCall.REWRITE_NFC:
        return "paymentReWriteNfc";
      case PaymentTypeCall.DEBIT_NFC:
        return "paymentDebitNfc";
      case PaymentTypeCall.FORMAT_NFC:
        return "paymentFormatNfc";
      case PaymentTypeCall.REFUND_NFC:
        return "paymentReFundNfc";
      case PaymentTypeCall.PRINTER_FILE:
        return "paymentPrinterFile";
      case PaymentTypeCall.PRINTER:
        return "paymentPrinter";
      case PaymentTypeCall.PRINTER_BASIC:
        return "paymentPrinterBasic";
      case PaymentTypeCall.START_PAYMENT:
        return "startPayment";
      case PaymentTypeCall.PRINTER_FILE_PATH:
        return "paymentPrinterFilePath";
      case PaymentTypeCall.REBOOT:
        return 'paymentReboot';
      case PaymentTypeCall.BEEP:
        return 'paymentBeep';
    }
  }
}

enum PaymentType {
  TYPE_CREDITO,
  TYPE_DEBITO,
  TYPE_VOUCHER,
  TYPE_QRCODE,
  TYPE_PIX,
  TYPE_PREAUTO_CARD,
  TYPE_QRCODE_CREDITO,
  TYPE_PREAUTO_KEYED,
}

extension PaymentTypeExt on PaymentType {
  get value {
    switch (this) {
      case PaymentType.TYPE_CREDITO:
        return 1;
      case PaymentType.TYPE_DEBITO:
        return 2;
      case PaymentType.TYPE_VOUCHER:
        return 3;
      case PaymentType.TYPE_QRCODE:
        return 4;
      case PaymentType.TYPE_PIX:
        return 5;
      case PaymentType.TYPE_PREAUTO_CARD:
        return 6;
      case PaymentType.TYPE_QRCODE_CREDITO:
        return 7;
      case PaymentType.TYPE_PREAUTO_KEYED:
        return 8;
    }
  }
}
