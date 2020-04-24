package dev.gabul.pagseguro_smart_flutter.transactions;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class TransactionsFragment implements  TransactionsContract{

    final MethodChannel channel;

    public TransactionsFragment(MethodChannel channel) {
        this.channel = channel;
    }

    //METHODS
    private static final String ON_TRANSACTION_SUCCESS = "onTransactionSucess";
    private static final String ON_TRANSACTION_SUCCESS_MESSAGE = "onTransactionSuccessMessage";
    private static final String ON_ERROR = "onError";
    private static final String ON_MESSAGE = "onMessage";
    private static final String ON_LOADING = "onLoading";
    private static final String WRITE_TO_FILE = "writeToFile";
    private static final String ON_ABORTED_SUCCESSFULLY = "onAbortedSuccessfully";
    private static final String ON_PRINT_ERROR = "onPrintError";
    private static final String ON_LAST_TRANSACTION = "onLastTransaction";



    @Override
    public void onTransactionSuccess() {
        this.channel.invokeMethod(ON_TRANSACTION_SUCCESS,true);

    }

    @Override
    public void onTransactionSuccess(String message) {
        this.channel.invokeMethod(ON_TRANSACTION_SUCCESS_MESSAGE,message);

    }

    @Override
    public void onError(String message) {
        this.channel.invokeMethod(ON_ERROR,message);

    }

    @Override
    public void onMessage(String message) {
        this.channel.invokeMethod(ON_MESSAGE,message);

    }

    @Override
    public void onLoading(boolean show) {
        this.channel.invokeMethod(ON_LOADING,show);

    }

    @Override
    public void writeToFile(String transactionCode, String transactionId) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("transactionCode",transactionCode);
        map.put("transactionId",transactionId);
        this.channel.invokeMethod(WRITE_TO_FILE,map);

    }

    @Override
    public void onAbortedSuccessfully() {
        this.channel.invokeMethod(ON_ABORTED_SUCCESSFULLY,true);
    }

    @Override
    public void onPrintError(String message) {
        this.channel.invokeMethod(ON_PRINT_ERROR,message);

    }

    @Override
    public void onLastTransaction(String transactionCode) {
        this.channel.invokeMethod(ON_LAST_TRANSACTION,transactionCode);

    }
}
