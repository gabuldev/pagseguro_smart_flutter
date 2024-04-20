package dev.gabul.pagseguro_smart_flutter.printer;

import javax.inject.Inject;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import dev.gabul.pagseguro_smart_flutter.printer.usecase.PrinterUsecase;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class PrinterPresenter {
    private PrinterUsecase mUseCase;

    @Inject
    public PrinterPresenter(PlugPag plugPag, MethodChannel channel) {
        mUseCase = new PrinterUsecase(plugPag, channel);
    }

    public void printerFromFile(String path) {
        mUseCase.printerFromFile(path);
    }
}
