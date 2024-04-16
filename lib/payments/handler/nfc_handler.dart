abstract class NfcHandler {

  void onTransactionSuccess();

  void onError(String message);

  void onMessage(String message);

  void showSuccess(List<dynamic> result);
  void showSuccessWrite(int result);
  void showSuccessReWrite(String result);
  void showSuccessFormat(int result);
  void showSuccessDebitNfc(String result);
  void showSuccessRefundNfc(String result);

  void showErrorRead(String message);
  void showErrorWrite(String message);
  void showErrorReWrite(String message);
  void showErrorFormat(String message);
  void showErrorDebitNfc(String message);
  void showErrorRefundNfc(String message);

  void onLoading(bool show);

  void writeToFile({String transactionCode, String transactionId});

  void onAbortedSuccessfully();

  void disposeDialog();

  void onActivationDialog();

  void onAuthProgress(String message);

  void onTransactionInfo({String transactionCode, String transactionId});
}
