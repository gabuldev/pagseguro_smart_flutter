package dev.gabul.pagseguro_smart_flutter.nfc;

import com.hannesdorfmann.mosby.mvp.MvpView;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagNFCResult;


 public interface NFCContract  {
    void showSuccess(PlugPagNFCResult result);

    void showError(String message);

     void onReadCard();

    void onWriteCard();

    void onAbort();
}