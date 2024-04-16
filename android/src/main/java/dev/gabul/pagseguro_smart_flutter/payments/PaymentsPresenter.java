package dev.gabul.pagseguro_smart_flutter.payments;
import android.util.Log;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagEventData;
import dev.gabul.pagseguro_smart_flutter.core.ActionResult;
import com.google.gson.Gson;
import io.flutter.plugin.common.MethodChannel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
public class PaymentsPresenter {
  private PaymentsUseCase mUseCase;
  private PaymentsFragment mFragment;
  private Disposable mSubscribe;
  private Boolean hasAborted = false;
  private int countPassword = 0;
  @Inject
  public PaymentsPresenter(PlugPag plugPag, MethodChannel channel) {
    mUseCase = new PaymentsUseCase(plugPag);
    mFragment = new PaymentsFragment(channel);
  }

  public void rebootDevice() {mUseCase.rebootDevice();}

  public void beep() {mUseCase.beep();}

  public void creditPaymentParc(int value, int type, int parc, String userReference, Boolean printReceipt, Boolean partialPay, Boolean isCarne) {
    doAction(mUseCase.doCreditPaymentParc(value, type, parc, userReference, printReceipt, partialPay, isCarne), value);
  }

  public void creditPayment(int value, String userReference, Boolean printReceipt, Boolean partialPay, Boolean isCarne) {
    doAction(mUseCase.doCreditPayment(value, userReference, printReceipt, partialPay, isCarne), value);
  }

  public void doDebitPayment(int value, String userReference, Boolean printReceipt, Boolean partialPay, Boolean isCarne) {
    doAction(mUseCase.doDebitPayment(value, userReference, printReceipt, partialPay, isCarne), value);
  }

  public void doPixPayment(int value, String userReference, Boolean printReceipt, Boolean partialPay, Boolean isCarne) {
    doAction(mUseCase.doPixPayment(value, userReference, printReceipt, partialPay, isCarne), value);
  }

  public void doVoucherPayment(int value, String userReference, Boolean printReceipt, Boolean partialPay, Boolean isCarne) {
    doAction(mUseCase.doVoucherPayment(value, userReference, printReceipt, partialPay, isCarne), value);
  }

  public void doRefund(String transactionCode, String transactionId) {
    doAction(mUseCase.doRefund(transactionCode, transactionId), 0);
  }

  public void startPayment(int type, int amount, int installmentType, int installments, String userReference, Boolean printReceipt, Boolean partialPay, Boolean isCarne) {
    doAction(mUseCase.doStartPayment(type, amount, installmentType, installments, userReference, printReceipt, partialPay, isCarne), amount);
  }  

  private void doAction(Observable<ActionResult> action, int value) {
    mSubscribe =
      mUseCase
        .isAuthenticated()
        .filter(aBoolean -> {
          if (!aBoolean) {
            mFragment.onActivationDialog();
            mSubscribe.dispose();
          }
          return aBoolean;
        })
        .flatMap(
          (Function<Boolean, ObservableSource<ActionResult>>) aBoolean -> action
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete(() -> mFragment.onTransactionSuccess())
        .doOnDispose(() -> mFragment.disposeDialog())
        .subscribe(
          (ActionResult result) -> {
            writeToFile(result);
            if (
              result.getEventCode() ==
              PlugPagEventData.EVENT_CODE_NO_PASSWORD ||
              result.getEventCode() ==
              PlugPagEventData.EVENT_CODE_DIGIT_PASSWORD
            ) {
              mFragment.onMessage(
                checkMessagePassword(result.getEventCode(), value)
              );
            } else {
              mFragment.onMessage(checkMessage(result.getMessage()));
            }
            checkResponse(result);
          },
          throwable -> {
            mFragment.onMessage(throwable.getMessage());
            mFragment.onError(throwable.getMessage());
            mFragment.disposeDialog();
          }
        );
  }
  private void checkResponse(ActionResult result) {
    if (result.getBuildResponse() != 0) {
      String response = new Gson().toJson(result);
      mFragment.onFinishedResponse(response);
    }
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
    return String.format(
      "VALOR: %.2f\nSENHA: %s",
      (value / 100.0),
      strPassword.toString()
    );
  }
  private String checkMessage(String message) {
    if (message != null && !message.isEmpty() && message.contains("SENHA")) {
      String[] strings = message.split("SENHA");
      return strings[0].trim();
    }
    return message;
  }
  private void writeToFile(ActionResult result) {
    if (
      result.getTransactionCode() != null && result.getTransactionId() != null
    ) {
      String response;
      response = new Gson().toJson(result);
      mFragment.writeToFile(
        result.getTransactionCode(),
        result.getTransactionId(),
        response
      );
    }
  }
  public void abortTransaction() {
    mSubscribe =
      mUseCase
        .abort()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();
  }
  public void activate(String activationCode) {
    Log.d("print", "*** ATIVANDO PINPAD: " + activationCode);
    mFragment.onMessage("Ativando terminal");
    mSubscribe =
      mUseCase
        .initializeAndActivatePinpad(activationCode)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> mFragment.onLoading(true))
        .doOnComplete(() -> {
            mFragment.onLoading(false);
            mFragment.onMessage("Terminal ativado");
            mFragment.onAuthProgress("Terminal ativado");
            mFragment.disposeDialog();
            Log.d("print", "*** pinpad ativado ");
        })
        .doOnDispose(() -> mFragment.disposeDialog())
        .subscribe(
          actionResult -> mFragment.onAuthProgress(actionResult.getMessage()),
          throwable -> {
              mFragment.onLoading(false);
              mFragment.onMessage("Error ao ativar terminal");
              mFragment.disposeDialog();
              mFragment.onError(throwable.getMessage());
              Log.d("print", "*** throw: " + throwable.getMessage());
          }
        );
    Log.d("print", "*** FIM ATIVAÇÃO: ");
  }
  public void isAuthenticate() {
        mFragment.onMessage("Obtendo status do terminal");
        mSubscribe = mUseCase.isAuthenticated()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mFragment.onLoading(true))
                .doOnComplete(()-> {
                    mFragment.onLoading(false);
                    mFragment.disposeDialog();
                    Log.d("print", "*** pinpad ativado ");
                })
                .doOnDispose(() -> mFragment.disposeDialog())
                .subscribe(
                        result -> {
                            if(result){
                                mFragment.onAuthProgress("Terminal ativado");
                            }else {
                                mFragment.onAuthProgress("Terminal não ativado");
                            }
                        },
                        throwable -> {
                            mFragment.onLoading(false);
                            mFragment.onMessage("Error ao obter status do terminal");
                            mFragment.disposeDialog();
                            mFragment.onError(throwable.getMessage());
                            Log.d("print", "*** throw: " + throwable.getMessage());
                        });
        Log.d("print", "*** FIM ATIVAÇÃO: ");
    }
  public void getLastTransaction() {
    mSubscribe =
      mUseCase
        .getLastTransaction()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(
          actionResult -> {
            String response;
            response = new Gson().toJson(actionResult);
            mFragment.onTransactionInfo(
              actionResult.getTransactionCode(),
              actionResult.getTransactionId(),
              response
            );
          },
          throwable -> mFragment.onError(throwable.getMessage())
        );
  }
  public void dispose() {
    if (mSubscribe != null) {
      mSubscribe.dispose();
    }
  }
}