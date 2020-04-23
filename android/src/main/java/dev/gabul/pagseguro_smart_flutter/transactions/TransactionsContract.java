package dev.gabul.pagseguro_smart_flutter.transactions;

public interface TransactionsContract {

    void onTransactionSuccess();

    void onTransactionSuccess(String message);

    void onError(String message);

    void onMessage(String message);

    void onLoading(boolean show);

    void writeToFile(String transactionCode, String transactionId);

    void onAbortedSuccessfully();

    void onPrintError(String message);

    void onLastTransaction(String transactionCode);
}