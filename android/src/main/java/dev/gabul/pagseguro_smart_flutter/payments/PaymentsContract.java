package dev.gabul.pagseguro_smart_flutter.payments;

interface PaymentsContract {

    void onTransactionSuccess();

    void onError(String message);

    void onMessage(String message);

    void onLoading(boolean show);

    void writeToFile(String transactionCode, String transactionId);

    void onAbortedSuccessfully();

    void disposeDialog();

    void onActivationDialog();

    void onAuthProgress(String message);

    void onTransactionInfo(String transactionCode, String transactionId);
}