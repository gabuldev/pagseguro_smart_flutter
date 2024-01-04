package dev.gabul.pagseguro_smart_flutter.transactions;

//Contract class
public interface TransactionsContract {
  void onTransactionSuccess();

  void onTransactionSuccess(String message);

  void onError(String message);

  void onMessage(String message);

  void onLoading(boolean show);

  void writeToFile(
    String transactionCode,
    String transactionId,
    String response
  );

  void onAbortedSuccessfully();

  void onPrintError(String message);

  void onLastTransaction(String transactionCode);
}
