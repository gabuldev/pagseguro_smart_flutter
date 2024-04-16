package dev.gabul.pagseguro_smart_flutter.user.usecase;

import android.nfc.tech.MifareClassic;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagNearFieldCardData;
import br.com.uol.pagseguro.plugpagservice.wrapper.data.request.PlugPagSimpleNFCData;
import dev.gabul.pagseguro_smart_flutter.helpers.NFCConstants;
import dev.gabul.pagseguro_smart_flutter.helpers.Utils;
import dev.gabul.pagseguro_smart_flutter.nfc.usecase.NFCUseCase;
import dev.gabul.pagseguro_smart_flutter.user.UserData;
import io.reactivex.Observable;

public class DebitUserUseCase {

    private final NFCUseCase mNfcUseCase;

    public DebitUserUseCase(NFCUseCase nfcUseCase) {
        this.mNfcUseCase = nfcUseCase;
    }

    public Observable<String> debitInNFcCard(UserData userData){

        final List<Observable<String>> observableSources = Arrays.asList(

                debitValueInNfcCard(userData),
//                writeNameInNfcCard(userData),
//                writeCpfInNfcCard(userData),
                debitTagInNfcCard(userData)
//                writeCellPhoneInNfcCard(userData),
//                writeCardActiveInNfcCard(userData)

        );

        return Observable.concat(observableSources);
    }

    private Observable<String> debitValueInNfcCard(UserData userData) {
        return mNfcUseCase.debitNfc(buildCardData(NFCConstants.VALUE_BLOCK, userData.getValue()));
    }

       private Observable<String> debitTagInNfcCard(UserData userData) {
        return mNfcUseCase.debitNfc(buildCardData(NFCConstants.TAG_BLOCK, ""));
    }

//    private Observable<Integer> writeNameInNfcCard(UserData userData){
//        return mNfcUseCase.writeNfc(buildCardData(NFCConstants.NAME_BLOCK, userData.getName()));
//    }
//
//    private Observable<Integer> writeCpfInNfcCard(UserData userData){
//        return mNfcUseCase.writeNfc(buildCardData(NFCConstants.CPF_BLOCK, userData.getCpf()));
//    }
//
//

//
//    private Observable<Integer> writeCellPhoneInNfcCard(UserData userData){
//        return mNfcUseCase.writeNfc(buildCardData(NFCConstants.CELL_PHONE_BLOCK, userData.getCellPhone()));
//    }
//
//    private Observable<Integer> writeCardActiveInNfcCard(UserData userData){
//        return mNfcUseCase.writeNfc(buildCardData(NFCConstants.CARD_OPENED_BLOCK, userData.getActive()));
//    }



    private PlugPagSimpleNFCData buildCardData(@NonNull Integer block, @NonNull String value){
        PlugPagSimpleNFCData cardData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, block, MifareClassic.KEY_DEFAULT);
        cardData.setValue(Utils.convertString2Bytes(value));
        return cardData;
    }
}
