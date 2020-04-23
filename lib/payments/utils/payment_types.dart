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
    }
  }
}

enum PaymentTypeCall { CREDIT, DEBIT, VOUCHER, ABORT }

extension PaymentTypeCallExt on PaymentTypeCall {
  get method {
    switch (this) {
      case PaymentTypeCall.CREDIT:
        return "paymentCredit";
      case PaymentTypeCall.DEBIT:
        return "paymentDebit";
      case PaymentTypeCall.VOUCHER:
        return "paymentVoucher";
      case PaymentTypeCall.ABORT:
        return "paymentAbort";
    }
  }
}
