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

public class EditUserUseCase {

    private final NFCUseCase mNfcUseCase;

    public EditUserUseCase(NFCUseCase nfcUseCase) {
        this.mNfcUseCase = nfcUseCase;
    }

    public Observable<String> reWriteUserInNFcCard(UserData userData){

        final List<Observable<String>> observableSources = Arrays.asList(

                reWriteValueInNfcCard(userData),
//                writeNameInNfcCard(userData),
//                writeCpfInNfcCard(userData),
                reWriteTagInNfcCard(userData),
                reWriteOpenValueInNfcCard(userData)
//                writeCellPhoneInNfcCard(userData),
//                writeCardActiveInNfcCard(userData)

        );

        return Observable.concat(observableSources);
    }

    private Observable<String> reWriteValueInNfcCard(UserData userData) {
        return mNfcUseCase.reWriteNfc(buildCardData(NFCConstants.VALUE_BLOCK, userData.getValue()));
    }

    private Observable<String> reWriteOpenValueInNfcCard(UserData userData) {
        return mNfcUseCase.reWriteNfc(buildCardData(NFCConstants.OPEN_VALUE_CARD_BLOCK, userData.getValue()));
    }

    private Observable<String> reWriteTagInNfcCard(UserData userData){
        return mNfcUseCase.reWriteNfc(buildCardData(NFCConstants.TAG_BLOCK, userData.getValue()));
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
//    private Observable<Integer> writeTagInNfcCard(UserData userData){
//        return mNfcUseCase.writeNfc(buildCardData(NFCConstants.TAG_BLOCK, userData.getNumberTag()));
//    }
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
