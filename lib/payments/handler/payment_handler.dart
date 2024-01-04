//Contract payment handler
abstract class PaymentHandler {
  void onTransactionSuccess();

  void onError(String message);

  void onMessage(String message);

  void onFinishedResponse(String message);

  void onLoading(bool show);

  void writeToFile({
    String transactionCode,
    String transactionId,
    String response,
  });

  void onAbortedSuccessfully();

  void disposeDialog();

  void onActivationDialog();

  void onAuthProgress(String message);

  void onTransactionInfo({
    String transactionCode,
    String transactionId,
    String response,
  });
}
