package dev.gabul.pagseguro_smart_flutter.nfc.usecase;

import android.nfc.tech.MifareClassic;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagNFCResult;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagNearFieldCardData;
import br.com.uol.pagseguro.plugpagservice.wrapper.data.request.PlugPagLedData;
import br.com.uol.pagseguro.plugpagservice.wrapper.data.request.PlugPagSimpleNFCData;
import br.com.uol.pagseguro.plugpagservice.wrapper.exception.PlugPagException;
import br.com.uol.pagseguro.plugpagservice.wrapper.data.request.PlugPagNFCAuth;
import dev.gabul.pagseguro_smart_flutter.helpers.NFCConstants;
import dev.gabul.pagseguro_smart_flutter.helpers.Utils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class NFCUseCase {

    private final PlugPag mPlugPag;

    public NFCUseCase(PlugPag plugPag) {
        mPlugPag = plugPag;
    }

    public Observable<Integer> writeNfc(PlugPagSimpleNFCData cardData){
        return Observable.create(emitter -> {
            try {

                int resultStartNfc = mPlugPag.startNFCCardDirectly();
                if (resultStartNfc != 1) {
                    emitter.onError(new PlugPagException("Ocorreu um erro ao iniciar serviço nfc"));
                    emitter.onComplete();
                    return;
                }

                PlugPagNFCAuth auth = new PlugPagNFCAuth(PlugPagNearFieldCardData.ONLY_M, (byte) cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                int resultAuth = mPlugPag.authNFCCardDirectly(auth);
                if (resultAuth != 1) {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Erro ao autenticar bloco [ %s ]", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                if(cardData.getSlot() == NFCConstants.CARD_OPENED_BLOCK) {

                    PlugPagSimpleNFCData readCardData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                    PlugPagNFCResult resultRead = mPlugPag.readNFCCardDirectly(readCardData);

                    if (resultRead.getResult() == 1) {

                        String isOpened = Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data")));

                        if(isOpened.equals("1")) {

                            mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                            emitter.onError(new PlugPagException(String.format("cartao_ja_aberto", cardData.getSlot())));
                            emitter.onComplete();
                            return;
                        }

                    } else {

                        mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                        emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc para verificar se o cartão está aberto", cardData.getSlot())));
                        emitter.onComplete();
                        return;
                    }

                }

                mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_YELLOW));
                Integer result = mPlugPag.writeToNFCCardDirectly(cardData);

                if (result == 1) {
                    emitter.onNext(result);
                } else {
                    emitter.onError(new PlugPagException(String.format("Ocorreu um erro ao escrever no bloco [ %s ]  do cartão nfc", cardData.getSlot())));
                }

                mPlugPag.stopNFCCardDirectly();
            } catch (Exception e){
                e.printStackTrace();
                emitter.onError(e);
            }

            emitter.onComplete();
        });
    }

    public Observable<String> reWriteNfc(PlugPagSimpleNFCData cardData) {
        return Observable.create(emitter -> {

            try {

                int resultStartNfc = mPlugPag.startNFCCardDirectly();

                if (resultStartNfc != 1) {
                    emitter.onError(new PlugPagException("Ocorreu um erro ao iniciar serviço nfc"));
                    emitter.onComplete();
                    return;
                }

                PlugPagNFCAuth auth = new PlugPagNFCAuth(PlugPagNearFieldCardData.ONLY_M, (byte) cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                int resultAuth = mPlugPag.authNFCCardDirectly(auth);
                if (resultAuth != 1) {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Erro ao autenticar bloco [ %s ]", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                PlugPagSimpleNFCData readCardData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                PlugPagNFCResult resultRead = mPlugPag.readNFCCardDirectly(readCardData);
                

                PlugPagSimpleNFCData readCardDataTag = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, NFCConstants.TAG_BLOCK, MifareClassic.KEY_DEFAULT);
                PlugPagNFCResult resultReadTag = mPlugPag.readNFCCardDirectly(readCardDataTag);

               
                String resultReadstring = "";

                if(cardData.getSlot() == NFCConstants.TAG_BLOCK) {
                    resultReadstring = Utils.removeAsterisco(new String(resultReadTag.getSlots()[cardData.getSlot()].get("data")));
                }

                System.out.println("resultReadstring");
                System.out.println(resultReadstring);

                if (resultRead.getResult() == 1) {

                } else {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

              /*  if(cardData.getSlot() == NFCConstants.EVENT_ID_BLOCK) {

                    String idEventoCard = Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data")));

                    System.out.println("idEventoCard");
                    System.out.println(idEventoCard);



                    if(!idEventoCard.equals(new String(cardData.getValue()))) {
                        mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                        emitter.onError(new PlugPagException(String.format("evento_nao_compativel", cardData.getSlot())));
                        emitter.onComplete();
                        return;
                    }
                } */


                Integer resultWrite = 1;
                if((NFCConstants.VALUE_BLOCK == cardData.getSlot()) || (NFCConstants.OPEN_VALUE_CARD_BLOCK == cardData.getSlot())) {

                    //Soma valor atual com o valor de recarga
                    Double valorAtual = Double.parseDouble(Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data"))));
                    Double valorRecarga = Double.parseDouble(Utils.removeAsterisco(new String(cardData.getValue())));
                    Double valorNovo = (valorAtual + valorRecarga);
                    String valorNovoString = Utils.adicionaAsterisco(valorNovo.toString());
                    cardData.setValue(valorNovoString.getBytes());

                    resultWrite = mPlugPag.writeToNFCCardDirectly(cardData);

                }



//                StringBuffer resultReadstring = new StringBuffer("");
//
//                for (Integer block : blocks ) {
//
//                    PlugPagSimpleNFCData readCardDataFinal = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, block, MifareClassic.KEY_DEFAULT);
//                    PlugPagNFCResult resultReadFinal = mPlugPag.readNFCCardDirectly(readCardDataFinal);
//
//                    resultReadstring.append(Utils.removeAsterisco(new String(resultReadFinal.getSlots()[block].get("data"))));
//                }



                if (resultRead.getResult() == 1) {

                } else {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                if (resultWrite == 1) {
                    emitter.onNext(resultReadstring);
                } else {
                    emitter.onError(new PlugPagException(String.format("Ocorreu um erro ao escrever no bloco [ %s ]  do cartão nfc", cardData.getSlot())));
                }


                mPlugPag.stopNFCCardDirectly();
            } catch (Exception e){
                e.printStackTrace();
                emitter.onError(e);
            }

            emitter.onComplete();
        });
    }

    public Observable<String> reFundNfc(PlugPagSimpleNFCData cardData) {
        return Observable.create(emitter -> {


            try {

                int resultStartNfc = mPlugPag.startNFCCardDirectly();
                if (resultStartNfc != 1) {
                    emitter.onError(new PlugPagException("Ocorreu um erro ao iniciar serviço nfc"));
                    emitter.onComplete();
                    return;
                }

                PlugPagNFCAuth auth = new PlugPagNFCAuth(PlugPagNearFieldCardData.ONLY_M, (byte) cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                int resultAuth = mPlugPag.authNFCCardDirectly(auth);
                if (resultAuth != 1) {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Erro ao autenticar bloco [ %s ]", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                PlugPagSimpleNFCData readCardData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                PlugPagNFCResult resultRead = mPlugPag.readNFCCardDirectly(readCardData);

                String resultReadstring = Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data")));

                if (resultRead.getResult() == 1) {

                } else {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

              /*  if(cardData.getSlot() == NFCConstants.EVENT_ID_BLOCK) {

                    String idEventoCard = Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data")));

                    System.out.println("idEventoCard");
                    System.out.println(idEventoCard);



                    if(!idEventoCard.equals(new String(cardData.getValue()))) {
                        mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                        emitter.onError(new PlugPagException(String.format("evento_nao_compativel", cardData.getSlot())));
                        emitter.onComplete();
                        return;
                    }
                } */


                Integer resultWrite = 1;
                if(NFCConstants.VALUE_BLOCK == cardData.getSlot()) {

                    //Soma valor atual com o valor de recarga
                    Double valorAtual = Double.parseDouble(Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data"))));
                    Double valorRecarga = Double.parseDouble(Utils.removeAsterisco(new String(cardData.getValue())));
                    Double valorNovo = (valorAtual + valorRecarga);
                    String valorNovoString = Utils.adicionaAsterisco(valorNovo.toString());
                    cardData.setValue(valorNovoString.getBytes());

                    resultWrite = mPlugPag.writeToNFCCardDirectly(cardData);

                }



//                StringBuffer resultReadstring = new StringBuffer("");
//
//                for (Integer block : blocks ) {
//
//                    PlugPagSimpleNFCData readCardDataFinal = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, block, MifareClassic.KEY_DEFAULT);
//                    PlugPagNFCResult resultReadFinal = mPlugPag.readNFCCardDirectly(readCardDataFinal);
//
//                    resultReadstring.append(Utils.removeAsterisco(new String(resultReadFinal.getSlots()[block].get("data"))));
//                }



                if (resultRead.getResult() == 1) {

                } else {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                if (resultWrite == 1) {
                    emitter.onNext(resultReadstring);
                } else {
                    emitter.onError(new PlugPagException(String.format("Ocorreu um erro ao escrever no bloco [ %s ]  do cartão nfc", cardData.getSlot())));
                }


                mPlugPag.stopNFCCardDirectly();
            } catch (Exception e){
                e.printStackTrace();
                emitter.onError(e);
            }

            emitter.onComplete();
        });
    }

    public Observable<String> debitNfc(PlugPagSimpleNFCData cardData) {
        return Observable.create(emitter -> {
            try {

                int resultStartNfc = mPlugPag.startNFCCardDirectly();
                if (resultStartNfc != 1) {
                    emitter.onError(new PlugPagException("Ocorreu um erro ao iniciar serviço nfc"));
                    emitter.onComplete();
                    return;
                }

                PlugPagNFCAuth auth = new PlugPagNFCAuth(PlugPagNearFieldCardData.ONLY_M, (byte) cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                int resultAuth = mPlugPag.authNFCCardDirectly(auth);
                if (resultAuth != 1) {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Erro ao autenticar bloco [ %s ]", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                PlugPagSimpleNFCData readCardData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, cardData.getSlot(), MifareClassic.KEY_DEFAULT);
                PlugPagNFCResult resultRead = mPlugPag.readNFCCardDirectly(readCardData);

                String resultReadstring = "";
                if (resultRead.getResult() == 1) {

                 resultReadstring = Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data")));

                } else {

                    mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                    emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

//                if(cardData.getSlot() == NFCConstants.EVENT_ID_BLOCK) {
//
//                    String idEventoReadCard = Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data")));
//                    boolean vazio = Arrays.equals(idEventoReadCard.getBytes(), new byte[16]);
//                    String idEventoWriteCard = new String(cardData.getValue());
//
//                    System.out.println("idEventoCard");
//                    System.out.println(idEventoReadCard);
//
//                    if(!vazio) {
//                        if(!idEventoReadCard.equals(idEventoWriteCard)) {
//                            mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
//                            emitter.onError(new PlugPagException(String.format("evento_nao_compativel", cardData.getSlot())));
//                            emitter.onComplete();
//                            return;
//                        }
//                    }
//                }

                //Soma valor atual com o valor de recarga

                Integer resultWrite = 1;
                if(NFCConstants.VALUE_BLOCK == cardData.getSlot()) {

                    Double valorAtual = Double.parseDouble(Utils.removeAsterisco(new String(resultRead.getSlots()[cardData.getSlot()].get("data"))));
                    Double valorProdutos = Double.parseDouble(Utils.removeAsterisco(new String(cardData.getValue())));
                    Double novoSaldo = 0.00;

                    if (valorAtual < valorProdutos) {
                        mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
                        emitter.onError(new PlugPagException(String.format("saldo_insuficiente", cardData.getSlot())));
                        emitter.onComplete();
                        return;
                    } else {
                        novoSaldo = (valorAtual - valorProdutos);
                    }

                    String valorNovoString = Utils.adicionaAsterisco(novoSaldo.toString());
                    cardData.setValue(valorNovoString.getBytes());

                    resultWrite = mPlugPag.writeToNFCCardDirectly(cardData);

                }

                if (resultWrite == 1) {
                    emitter.onNext(resultReadstring);
                } else {
                    emitter.onError(new PlugPagException(String.format("Ocorreu um erro ao escrever no bloco [ %s ]  do cartão nfc", cardData.getSlot())));
                }


                mPlugPag.stopNFCCardDirectly();
            } catch (Exception e){
                e.printStackTrace();
                emitter.onError(e);
            }

            emitter.onComplete();
        });
    }


    public Observable<PlugPagNFCResult> readNfc(Integer block, String idEvento) {
        return Observable.create(emitter -> {
            int resultStartNfc = mPlugPag.startNFCCardDirectly();
            if (resultStartNfc != 1){
                emitter.onError(new PlugPagException("Ocorreu um erro ao iniciar serviço nfc"));
                emitter.onComplete();
                return;
            }

            PlugPagNFCAuth auth = new PlugPagNFCAuth(PlugPagNearFieldCardData.ONLY_M, block.byteValue(), MifareClassic.KEY_DEFAULT);
            int resultAuth = mPlugPag.authNFCCardDirectly(auth);
            if (resultAuth != 1){
                emitter.onError(new PlugPagException(String.format("Erro ao autenticar bloco [ %s ]", block)));
                emitter.onComplete();
                return;
            }

            PlugPagSimpleNFCData cardData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, block, MifareClassic.KEY_DEFAULT);

            PlugPagNFCResult result = mPlugPag.readNFCCardDirectly(cardData);

//            if(block.equals(NFCConstants.EVENT_ID_BLOCK)) {
//
//                String idEventoCard = Utils.removeAsterisco(new String(result.getSlots()[block].get("data")));
//
//                boolean vazio = Arrays.equals(idEventoCard.getBytes(), new byte[16]);
//
//                if(!vazio) {
//                    if(!idEventoCard.equals(idEvento)) {
//                        mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_RED));
//                        emitter.onError(new PlugPagException(String.format("evento_nao_compativel", cardData.getSlot())));
//                        emitter.onComplete();
//                        return;
//                    }
//                }
//
//            }

            if (result.getResult() == 1){
                Log.d(NFCUseCase.class.getSimpleName(), Utils.convertBytes2String(result.getSlots()[result.getStartSlot()].get("data"), false));
                emitter.onNext(result);
            } else {
                emitter.onError(new PlugPagException(String.format("Ocoreu um erro ao ler bloco [ %s ] do cartão nfc", block)));
            }

            mPlugPag.setLed(new PlugPagLedData(PlugPagLedData.LED_YELLOW));
            mPlugPag.stopNFCCardDirectly();

            emitter.onComplete();
        });
    }

    public Observable<Integer> formatNfc(PlugPagSimpleNFCData cardData) {
        return Observable.create(emitter -> {
            try {

                int resultStartNfc = mPlugPag.startNFCCardDirectly();
                if (resultStartNfc != 1) {
                    emitter.onError(new PlugPagException("Ocorreu um erro ao iniciar serviço nfc"));
                    emitter.onComplete();
                    return;
                }

                PlugPagNFCAuth auth = new PlugPagNFCAuth(PlugPagNearFieldCardData.ONLY_M, (byte) cardData.getSlot(), MifareClassic.KEY_DEFAULT);

                int resultAuth = mPlugPag.authNFCCardDirectly(auth);
                if (resultAuth != 1) {


                    emitter.onError(new PlugPagException(String.format("Erro ao autenticar bloco [ %s ]", cardData.getSlot())));
                    emitter.onComplete();
                    return;
                }

                Integer result = mPlugPag.writeToNFCCardDirectly(cardData);

                if (result == 1) {
                    emitter.onNext(result);
                } else {
                    emitter.onError(new PlugPagException(String.format("Ocorreu um erro ao escrever no bloco [ %s ]  do cartão nfc", cardData.getSlot())));
                }

                mPlugPag.stopNFCCardDirectly();

            } catch (Exception e){
                e.printStackTrace();
                emitter.onError(e);
            }

            emitter.onComplete();
        });
    }


    public Observable<Object> controlLed(PlugPagLedData plugPagLedData) {

        return Observable.create(emitter -> {
            int result = mPlugPag.setLed(plugPagLedData);
            if (result == 1) {
                emitter.onNext(result);
            } else {
                emitter.onError(new PlugPagException());
            }
            emitter.onComplete();
        });
    }

    public Completable abort(){
        return Completable.create(emitter -> mPlugPag.abortNFC());
    }

    public Observable<Integer> clearBlocks(List<Integer> blocks) {
        return Observable.create((ObservableOnSubscribe<Integer>) emitter -> {

            for (Integer trailerBlock : getSectorTrailerBlocks()) {
                if (blocks.contains(trailerBlock)){
                    emitter.onError(new PlugPagException(String.format("O bloco [ %s ] é de permissão de acesso e não pode ser limpo!", trailerBlock)));
                    emitter.onComplete();
                    return;
                }
            }

            for ( Integer block : blocks ){
                emitter.onNext(block);
            }

            if (!emitter.isDisposed()){
                emitter.onComplete();
            }
        })
                .concatMap(block -> { // Using concatMap to ensure that observables are not called at the same time
                    PlugPagSimpleNFCData emptyData = new PlugPagSimpleNFCData(PlugPagNearFieldCardData.ONLY_M, block, MifareClassic.KEY_DEFAULT);
                    emptyData.setValue(new byte[16]);
                    return formatNfc(emptyData);
                }).distinct();

    }

    public Observable<Integer> writePermissions(@NonNull byte[] keyA, @NonNull byte[] permissions, @Nullable byte[] keyB){
        return Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            for (Integer i : getSectorTrailerBlocks()){
                emitter.onNext(i);
            }

            if (!emitter.isDisposed()){
                emitter.onComplete();
            }
        })
                .concatMap(sectorTrailerBlock -> {
                    PlugPagSimpleNFCData cardData = new PlugPagSimpleNFCData(
                            PlugPagNearFieldCardData.ONLY_M,
                            sectorTrailerBlock,
                            MifareClassic.KEY_DEFAULT);
                    cardData.setValue(buildDataAccess(keyA, permissions, keyB));
                    return writeNfc(cardData);
                });
    }

    private List<Integer> getSectorTrailerBlocks(){
        final List<Integer> ret = new ArrayList<>();
        for (int i = 7; i < 64; i += 4){
            ret.add(i);
        }
        return ret;
    }

    private byte[] buildDataAccess(@NonNull byte[] keyA, @NonNull byte[] permissions, @Nullable byte[] keyB){
        byte[] data = new byte[16];
        System.arraycopy(keyA, 0, data, 0, 6);
        System.arraycopy(permissions, 0, data, 6, 4);
        if (keyB != null){
            System.arraycopy(keyB, 0, data, 10, 6);
        }
        return data;
    }


}