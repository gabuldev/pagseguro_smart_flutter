package dev.gabul.pagseguro_smart_flutter.nfc;

import java.util.ArrayList;
import java.util.List;
import io.flutter.plugin.common.MethodChannel;
import dev.gabul.pagseguro_smart_flutter.helpers.Utils;
import dev.gabul.pagseguro_smart_flutter.user.UserData;


public class NFCFragment implements NFCContract {

    final MethodChannel channel;

    public NFCFragment(MethodChannel channel) {
        this.channel = channel;
    }

    //METHODS SUCCESS

    private static final String ON_SUCCESS = "showSuccess";
    private static final String ON_SUCCESS_WRITE = "showSuccessWrite";
    private static final String ON_SUCCESS_RE_WRITE = "showSuccessReWrite";
    private static final String ON_SUCCESS_REFUND_NFC = "showSuccessRefundNfc";
    private static final String ON_SUCCESS_FORMAT = "showSuccessFormat";
    private static final String ON_SUCCESS_DEBIT_NFC = "showSuccessDebitNfc";

    //METHODS ERROR
    private static final String ON_ERROR = "onError";
    private static final String ON_ERROR_READ = "showErrorRead";
    private static final String ON_ERROR_WRITE = "showErrorWrite";
    private static final String ON_ERROR_RE_WRITE = "showErrorReWrite";
    private static final String ON_ERROR_FORMAT = "showErrorFormat";
    private static final String ON_ERROR_DEBIT_NFC = "showErrorDebitNfc";
    private static final String ON_ERROR_REFUND_NFC = "showErrorRefundNfc";

    private static final String ON_READ_CARD = "onReadCard";
    private static final String ON_WRITE_CARD = "onWriteCard";
    private static final String ON_ABORT = "onAbort";


    //Success methods
    @Override
    public void showSuccess(UserData result) {

        final List<String> results = new ArrayList<>();
        results.add(Utils.removeAsterisco(result.getValue()));
        results.add(Utils.removeAsterisco(result.getName()));
        results.add(Utils.removeAsterisco(result.getCpf()));
        results.add(Utils.removeAsterisco(result.getNumberTag()));
        results.add(Utils.removeAsterisco(result.getCellPhone()));
        results.add(Utils.removeAsterisco(result.getActive()));
        results.add(Utils.removeAsterisco(result.getOpenValue()));

        this.channel.invokeMethod(ON_SUCCESS, results);
    }

    @Override
    public void showSuccessWrite(Object result) {
        this.channel.invokeMethod(ON_SUCCESS_WRITE, result);
    }

    @Override
    public void showSuccessReWrite(Object result) {
        this.channel.invokeMethod(ON_SUCCESS_RE_WRITE, result);
    }

    @Override
    public void showSuccessFormat(Object result) {
        this.channel.invokeMethod(ON_SUCCESS_FORMAT, result);
    }

    @Override
    public void showSuccessDebitNfc(Object result) {
        this.channel.invokeMethod(ON_SUCCESS_DEBIT_NFC, result);
    }

    @Override
    public void showSuccessRefundNfc(Object result) {
        this.channel.invokeMethod(ON_SUCCESS_REFUND_NFC, result);
    }

    //Error methods
    @Override
    public void showErrorRead(String message) {
        this.channel.invokeMethod(ON_ERROR_READ, message);

    }

    @Override
    public void showErrorWrite(String message) {
        this.channel.invokeMethod(ON_ERROR_WRITE, message);
    }

    @Override
    public void showErrorReWrite(String message) {
        this.channel.invokeMethod(ON_ERROR_RE_WRITE, message);
    }

    @Override
    public void showErrorRefundNfc(String message) {
        this.channel.invokeMethod(ON_ERROR_REFUND_NFC, message);
    }

    @Override
    public void showErrorFormat(String message) {
        this.channel.invokeMethod(ON_ERROR_FORMAT, message);
    }

    @Override
    public void showErrorDebitNfc(String message) {
        this.channel.invokeMethod(ON_ERROR_DEBIT_NFC, message);
    }

    @Override
    public void showError(String message) {
        this.channel.invokeMethod(ON_ERROR,message);

    }

    @Override
    public void onReadCard() {
        this.channel.invokeMethod(ON_READ_CARD,true);

    }

    @Override
    public void onWriteCard() {
        this.channel.invokeMethod(ON_WRITE_CARD,true);

    }

    @Override
    public void onAbort() {
        this.channel.invokeMethod(ON_ABORT,true);

    }
}