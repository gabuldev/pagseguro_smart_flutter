package dev.gabul.pagseguro_smart_flutter.nfc;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagNFCResult;

import io.flutter.plugin.common.MethodChannel;

public class NFCFragment implements NFCContract {

     final MethodChannel channel;

    public NFCFragment(MethodChannel channel) {
        this.channel = channel;
    }

    //METHODS
    private static final String ON_ERROR = "onError";
    private static final String ON_SUCCESS = "onSuccess";
    private static final String ON_READ_CARD = "onReadCard";
    private static final String ON_WRITE_CARD = "onWriteCard";
    private static final String ON_ABORT = "onAbort";


    @Override
    public void showSuccess(PlugPagNFCResult result) {
        this.channel.invokeMethod(ON_SUCCESS,result);
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