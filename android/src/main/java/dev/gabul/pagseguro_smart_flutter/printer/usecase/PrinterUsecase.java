package dev.gabul.pagseguro_smart_flutter.printer.usecase;

import javax.inject.Inject;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrinterData;
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
            PlugPagPrinterData printerData = new PlugPagPrinterData(path, 1, 1);
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
