package dev.gabul.pagseguro_smart_flutter.user.usecase;

import android.util.Pair;



import java.util.Arrays;
import java.util.List;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagNFCResult;
import dev.gabul.pagseguro_smart_flutter.helpers.NFCConstants;
import dev.gabul.pagseguro_smart_flutter.nfc.usecase.NFCUseCase;
import dev.gabul.pagseguro_smart_flutter.user.UserData;
import dev.gabul.pagseguro_smart_flutter.helpers.Utils;
import io.reactivex.Observable;
import io.reactivex.Single;

public class GetUserUseCase {

    private final NFCUseCase mNFCUseCase;

    public GetUserUseCase(NFCUseCase nfcUseCase) {
        this.mNFCUseCase = nfcUseCase;
    }

    public Single<UserData> getUser(String idEvento) {
        // List Of Observables for concatenate
        final List<Observable<Pair<UserFieldEnum, String>>> observableSources = Arrays.asList(
               // readEventIdFromNfc(idEvento),
                readValueFromNfc(),
               // readNameFromNfc(),
               // readCpfFromNfc(),
                readTagFromNfc(),
               // readCellPhoneFromNfc(),
                readCardActiveFromNfc(),
                readOpenValueFromNfc()

        );
        return Observable.concat(observableSources).collect(UserData::new, (user, value) -> {
            switch (value.first){
                case VALUE: user.setValue(value.second); break;
                case NAME: user.setName(value.second); break;
                case CPF: user.setCpf(value.second); break;
                case TAG: user.setNumberTag(value.second); break;
                case CELL_PHONE: user.setCellPhone(value.second); break;
                case CARD_OPENED: user.setActive(value.second); break;
                case OPEN_VALUE: user.setOpenValue(value.second); break;
            }
        });
    }

    private Observable<Pair<UserFieldEnum, String>> readValueFromNfc() {
        return mNFCUseCase.readNfc(NFCConstants.VALUE_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.VALUE, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readOpenValueFromNfc() {
        return mNFCUseCase.readNfc(NFCConstants.OPEN_VALUE_CARD_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.OPEN_VALUE, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readNameFromNfc(){
        return mNFCUseCase.readNfc(NFCConstants.NAME_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.NAME, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readCpfFromNfc(){
        return mNFCUseCase.readNfc(NFCConstants.CPF_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.CPF, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readTagFromNfc(){
        return mNFCUseCase.readNfc(NFCConstants.TAG_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.TAG, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readCellPhoneFromNfc(){
        return mNFCUseCase.readNfc(NFCConstants.CELL_PHONE_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.CELL_PHONE, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readCardActiveFromNfc(){
        return mNFCUseCase.readNfc(NFCConstants.CARD_OPENED_BLOCK, null).map(
                result -> new Pair<>(UserFieldEnum.CARD_OPENED, getStringFromResult(result))
        );
    }

    private Observable<Pair<UserFieldEnum, String>> readEventIdFromNfc(String idEvento) {
        return mNFCUseCase.readNfc(NFCConstants.EVENT_ID_BLOCK, idEvento).map(
                result -> new Pair<>(UserFieldEnum.EVENT_ID, getStringFromResult(result))
        );
    }

    private String getStringFromResult(PlugPagNFCResult result){
        return Utils.convertBytes2String(result.getSlots()[result.getStartSlot()].get("data"), false);
    }

}
