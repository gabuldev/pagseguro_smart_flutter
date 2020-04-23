abstract class PaymentHandler {
  void onTransactionSuccess();

  void onError(String message);

  void onMessage(String message);

  void onLoading(bool show);

  void writeToFile(String transactionCode, String transactionId);

  void onAbortedSuccessfully();

  void disposeDialog();

  void onActivationDialog();

  void onAuthProgress(String message);
}
