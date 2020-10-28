package dev.gabul.pagseguro_smart_flutter.nfc;
import javax.inject.Inject;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import io.flutter.plugin.common.MethodChannel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NFCPresenter  {

    private final NFCUseCase mUseCase;
    private final NFCFragment mFragment;

    private Disposable mSubscribe;

    @Inject
    public NFCPresenter(PlugPag plugPag, MethodChannel channel) {
        mUseCase = new NFCUseCase(plugPag);
        mFragment = new NFCFragment(channel);
    }

    public void readNFCCard() {
        dispose();
        mSubscribe = mUseCase.readNFCCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> mFragment.showSuccess(result),
                        throwable ->mFragment.showError(throwable.getMessage()));
    }

    public void writeNFCCard() {
        dispose();
        mSubscribe = mUseCase.writeNFCCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> mFragment.showSuccess(result),
                        throwable -> mFragment.showError(throwable.getMessage()));
    }


    public void dispose() {
        if (mSubscribe != null) {
            mSubscribe.dispose();
        }
    }

    public void abort() {
        mSubscribe = mUseCase.abort()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}