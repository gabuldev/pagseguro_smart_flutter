package dev.gabul.pagseguro_smart_flutter.printer;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsFragment;
import dev.gabul.pagseguro_smart_flutter.printer.usecase.PrinterUsecase;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class PrinterPresenter implements Disposable {
    private PrinterUsecase mUseCase;
    private PaymentsFragment mFragment;

    private Disposable mSubscribe;

    @Inject
    public PrinterPresenter(PlugPag plugPag, MethodChannel channel) {
        mUseCase = new PrinterUsecase(plugPag, channel);
        mFragment = new PaymentsFragment(channel);
    }

    public void printerFromFile(String path) {
        mUseCase.printerFromFile(path);
    }

    public void printFile(String fileName) {
        mSubscribe = mUseCase.printFile(fileName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mFragment.onLoading(true))
                .doFinally(() -> mFragment.onLoading(false))
                .subscribe(result -> {
                            if (result.getResult() == 0) {
                                mFragment.onMessage("Impressão finalizada");
                                mFragment.onAuthProgress("Impressão finalizada");
                            } else {
                                mFragment.onAuthProgress("Erro ao realizar impressão");
                                mFragment.onError("Erro impressão: " + result.getMessage());
                            }
                        },
                        throwable -> {
                            if (throwable instanceof FileNotFoundException) {
                                mFragment.onMessage("O arquivo informado não foi encontrado.");
                                mFragment.onError("Arquivo não encontrado no diretório base");
                            } else {
                                mFragment.onMessage("Erro: " + throwable.getMessage());
                                mFragment.onAuthProgress("Erro ao realizar impressão");
                                mFragment.onError("Erro impressão: " + throwable.getMessage());
                            }
                        });
    }

    @Override
    public void dispose() {
        if (mSubscribe != null) {
            mSubscribe.dispose();
        }
    }

    @Override
    public boolean isDisposed() {
        return false;
    }
}
