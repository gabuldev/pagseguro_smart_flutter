package dev.gabul.pagseguro_smart_flutter.transactions;

import javax.inject.Inject;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagEventData;
import dev.gabul.pagseguro_smart_flutter.core.ActionResult;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionsPresenter {

    private TransactionsUseCase mUseCase;
    private TransactionsFragment mFragment;
    private Disposable mSubscribe;
    private Boolean hasAborted = false;
    private int countPassword = 0;

    @Inject
    public TransactionsPresenter(TransactionsUseCase useCase, TransactionsFragment fragment) {
        mUseCase = useCase;
        mFragment = fragment;
    }

    public void creditPayment() {
        doAction(mUseCase.doCreditPayment());
    }

    public void pixPayment() {
        doAction(mUseCase.doPixPayment());
    }

    public void doCreditPaymentWithSellerInstallments() {
        doAction(mUseCase.doCreditPaymentWithSellerInstallments());
    }

    public void doCreditPaymentWithBuyerInstallments() {
        doAction(mUseCase.doCreditPaymentWithBuyerInstallments());
    }

    public void doDebitPayment() {
        doAction(mUseCase.doDebitPayment());
    }

    public void doVoucherPayment() {
        doAction(mUseCase.doVoucherPayment());
    }

    public void doRefundPayment(ActionResult actionResult) {
        if(actionResult.getMessage() != null)  {
           // getView().onError(actionResult.getMessage());
        } else {
            doAction(mUseCase.doRefundPayment(actionResult));
        }
    }

    private void doAction(Observable<ActionResult> action) {
        mSubscribe = action.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mFragment.onTransactionSuccess())
                .subscribe((ActionResult result) -> {
                            writeToFile(result);

                            if (result.getEventCode() == PlugPagEventData.EVENT_CODE_DIGIT_PASSWORD ||
                                    result.getEventCode() == PlugPagEventData.EVENT_CODE_NO_PASSWORD) {
                                mFragment.onMessage(checkMessagePassword(result.getEventCode()));
                            } else if (result.getErrorCode() != null) {
                                mFragment.onError(result.getMessage());
                            } else {
                                mFragment.onMessage(result.getMessage());
                            }

                        },
                        throwable -> {
                            hasAborted = true;
                            mFragment.onError(throwable.getMessage());
                        });
    }

    private String checkMessagePassword(int eventCode) {
        StringBuilder strPassword = new StringBuilder();

        int value = mUseCase.getEventPaymentData() != null ? mUseCase.getEventPaymentData().getAmount() : 0;

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

    private void writeToFile(ActionResult result) {
        if (result.getTransactionCode() != null && result.getTransactionId() != null) {
            mFragment.writeToFile(result.getTransactionCode(), result.getTransactionId());
        }
    }


    public void abortTransaction() {
        if (hasAborted) {
            hasAborted = false;
            return;
        }

        mSubscribe = mUseCase.abort()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> hasAborted = true)
                .doOnDispose(() -> hasAborted = true)
                .subscribe(o -> mFragment.onAbortedSuccessfully(),
                        throwable -> mFragment.onError(throwable.getMessage()));
    }



    public void printStablishmentReceipt() {
        mSubscribe = mUseCase.printStablishmentReceipt()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> mFragment.onLoading(false))
                .doOnSubscribe(disposable -> mFragment.onLoading(true))
                .subscribe(message -> mFragment.onTransactionSuccess(message.getMessage()),
                        throwable -> mFragment.onError(throwable.getMessage()));
    }

    public void printCustomerReceipt() {
        mSubscribe = mUseCase.printCustomerReceipt()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> mFragment.onLoading(false))
                .doOnSubscribe(disposable -> mFragment.onLoading(true))
                .doOnError(throwable -> mFragment.onError(throwable.getMessage()))
                .subscribe(message -> mFragment.onTransactionSuccess(message.getMessage()),
                        throwable -> mFragment.onError(throwable.getMessage()));
    }


    public void getLastTransaction() {
        mSubscribe = mUseCase.getLastTransaction()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(actionResult -> mFragment.onLastTransaction(actionResult.getTransactionCode()),
                        throwable -> mFragment.onError(throwable.getMessage()));
    }

    public void dispose(){
        if(mSubscribe != null){
            mSubscribe.dispose();
        }
    }
}