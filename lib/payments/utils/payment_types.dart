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
  ON_FINISHED_RESPONSE
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
    }
  }
}

enum PaymentTypeCall {
  CREDIT,
  CREDIT_PARC,
  DEBIT,
  VOUCHER,
  ABORT,
  LAST_TRANSACTION,
  REFUND,
  ACTIVEPINPAD
}

enum PaymentTypeCredit { SALESMAN, CLIENT }

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
    }
  }
}
