package dev.gabul.pagseguro_smart_flutter.payments;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class PaymentsFragment implements PaymentsContract  {

    final MethodChannel channel;

    public PaymentsFragment(MethodChannel channel) {
        this.channel = channel;
    }

    //METHODS
    private static final String ON_TRANSACTION_SUCCESS = "onTransactionSuccess";
    private static final String ON_ERROR = "onError";
    private static final String ON_MESSAGE = "onMessage";
    private static final String ON_LOADING = "onLoading";
    private static final String WRITE_TO_FILE = "writeToFile";
    private static final String ON_ABORTED_SUCCESSFULLY = "onAbortedSuccessfully";
    private static final String DISPOSE_DIALOG = "disposeDialog";
    private static final String ACTIVE_DIALOG = "activeDialog";
    private static final String ON_AUTH_PROGRESS = "onAuthProgress";
    private static final String ON_TRANSACTION_INFO = "onTransactionInfo";



    @Override
    public void onTransactionSuccess() {
        this.channel.invokeMethod(ON_TRANSACTION_SUCCESS,true);

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
    public void disposeDialog() {
        this.channel.invokeMethod(DISPOSE_DIALOG,true);
    }

    @Override
    public void onActivationDialog() {
        this.channel.invokeMethod(ACTIVE_DIALOG,true);
    }

    @Override
    public void onAuthProgress(String message) {
        this.channel.invokeMethod(ON_AUTH_PROGRESS,message);
    }

    @Override
    public void onTransactionInfo(String transactionCode, String transactionId) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("transactionCode",transactionCode);
        map.put("transactionId",transactionId);
        this.channel.invokeMethod(ON_TRANSACTION_INFO,map);
    }

}
