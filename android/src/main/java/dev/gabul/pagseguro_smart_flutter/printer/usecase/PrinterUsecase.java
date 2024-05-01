package dev.gabul.pagseguro_smart_flutter.printer.usecase;


import java.io.File;

import javax.inject.Inject;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrintResult;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrinterData;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrinterListener;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsFragment;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class PrinterUsecase {

    private PlugPag mPlugpag;
    private PaymentsFragment mFragment;

    @Inject
    public PrinterUsecase(PlugPag plugPag, MethodChannel channel) {
        this.mPlugpag = plugPag;
        mFragment = new PaymentsFragment(channel);
    }

    public void printerFromFile(String path) {
        try {
            mFragment.onAuthProgress("Iniciando impressão");
            mFragment.onMessage("Iniciando impressão");
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/teste.jpg";
            File file = new File(path);
            if(!file.exists()) {
                mFragment.onMessage("O arquivo informado não foi encontrado.");
                mFragment.onError("Arquivo não encontrado no diretório: " + file.getAbsolutePath());
                return;
            }

            PlugPagPrinterData printerData = new PlugPagPrinterData(file.getAbsolutePath(), 4, 0);
            PlugPagPrinterListener listener = new PlugPagPrinterListener() {
                @Override
                public void onSuccess(PlugPagPrintResult plugPagPrintResult) {
                    String message = plugPagPrintResult.getMessage();
                    int resultPrinter = plugPagPrintResult.getResult();
                    mFragment.onMessage(resultPrinter + message);
                    mFragment.onAuthProgress(resultPrinter + message);

                }
                @Override
                public void onError(PlugPagPrintResult plugPagPrintResult) {
                    String errorMessage = "Error message: " + plugPagPrintResult.getMessage();
                    String errorCode = "Error code" + plugPagPrintResult.getErrorCode();
                    mFragment.onError(errorCode + errorMessage);
                    mFragment.onMessage("Erro ao realizar impressão");
                }
            };

            mPlugpag.setPrinterListener(listener);
            mPlugpag.printFromFile(printerData);

            mFragment.onMessage("Impressão finalizada");
            mFragment.onAuthProgress("Impressão finalizada");
        }catch(Exception e) {
            mFragment.onMessage("Erro: " + e.getMessage());
            mFragment.onAuthProgress("Erro ao realizar impressão");
            mFragment.onError("Erro impressão: " + e.getMessage());
        }
    }

}
