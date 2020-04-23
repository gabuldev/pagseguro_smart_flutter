package dev.gabul.pagseguro_smart_flutter.payments;

import javax.inject.Inject;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagEventData;

import dev.gabul.pagseguro_smart_flutter.core.ActionResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PaymentsPresenter  {

    private PaymentsUseCase mUseCase;
    private PaymentsFragment mFragment;
    private Disposable mSubscribe;
    private Boolean hasAborted = false;
    private int countPassword = 0;

    @Inject
    public PaymentsPresenter(PaymentsUseCase useCase, PaymentsFragment fragment) {
        mUseCase = useCase;
        mFragment = fragment;
    }

    public void creditPayment(int value) {
        doAction(mUseCase.doCreditPayment(value), value);
    }

    public void doDebitPayment(int value) {
        doAction(mUseCase.doDebitPayment(value), value);
    }

    public void doVoucherPayment(int value) {
        doAction(mUseCase.doVoucherPayment(value), value);
    }

    public void doRefund(ActionResult actionResult) {
        if (actionResult.getMessage() != null) {
            mFragment.onError(actionResult.getMessage());
            mFragment.disposeDialog();
        } else {
            doAction(mUseCase.doRefund(actionResult), 0);
        }
    }

    private void doAction(Observable<ActionResult> action, int value) {
        mSubscribe = mUseCase.isAuthenticated()
                .filter(aBoolean -> {
                    if (!aBoolean) {
                        mFragment.onActivationDialog();
                        mSubscribe.dispose();
                    }
                    return aBoolean;
                })
                .flatMap((Function<Boolean, ObservableSource<ActionResult>>) aBoolean -> action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mFragment.onTransactionSuccess())
                .doOnDispose(() -> mFragment.disposeDialog())
                .subscribe((ActionResult result) -> {
                            writeToFile(result);

                            if (result.getEventCode() == PlugPagEventData.EVENT_CODE_NO_PASSWORD ||
                                    result.getEventCode() == PlugPagEventData.EVENT_CODE_DIGIT_PASSWORD) {
                                mFragment.onMessage(checkMessagePassword(result.getEventCode(), value));
                            } else {
                                mFragment.onMessage(checkMessage(result.getMessage()));
                            }
                        },
                        throwable -> {
                            mFragment.onMessage(throwable.getMessage());
                            mFragment.disposeDialog();
                        });
    }

    private String checkMessagePassword(int eventCode, int value) {
        StringBuilder strPassword = new StringBuilder();

        if (eventCode == PlugPagEventData.EVENT_CODE_DIGIT_PASSWORD) {
            countPassword++;
        }
        if (eventCode == PlugPagEventData.EVENT_CODE_NO_PASSWORD) {
            countPassword = 0;
        }

        for (int count = countPassword; count > 0; count--) {
            strPassword.append("*");
        }

        return String.format("VALOR: %.2f\nSENHA: %s", (value / 100.0), strPassword.toString());
    }

    private String checkMessage(String message) {
        if (message != null && !message.isEmpty() && message.contains("SENHA")) {
            String[] strings = message.split("SENHA");
            return strings[0].trim();
        }

        return message;
    }

    private void writeToFile(ActionResult result) {
        if (result.getTransactionCode() != null && result.getTransactionId() != null) {
            mFragment.writeToFile(result.getTransactionCode(), result.getTransactionId());
        }
    }

    public void abortTransaction() {
        mSubscribe = mUseCase.abort()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }



    public void activate(String activationCode) {
        mSubscribe = mUseCase.initializeAndActivatePinpad(activationCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->mFragment.onLoading(true))
                .doOnComplete(() -> {
                    mFragment.onLoading(false);
                    mFragment.disposeDialog();
                })
                .doOnDispose(() -> mFragment.disposeDialog())
                .subscribe(actionResult -> mFragment.onAuthProgress(actionResult.getMessage()),
                        throwable -> {
                            mFragment.onLoading(false);
                            mFragment.onError(throwable.getMessage());
                        });
    }

    public void getLastTransaction() {
        mSubscribe = mUseCase.getLastTransaction()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(actionResult -> mFragment.onMessage(actionResult.getTransactionCode()),
                        throwable -> mFragment.onError(throwable.getMessage()));
    }

    public void dispose(){
        if(mSubscribe != null){
            mSubscribe.dispose();
        }
    }
}