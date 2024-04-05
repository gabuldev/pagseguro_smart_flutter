package dev.gabul.pagseguro_smart_flutter.core;

import android.content.Context;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagAppIdentification;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagCustomPrinterLayout;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsPresenter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class PagSeguroSmart {

  final PlugPag plugPag;
  final MethodChannel mChannel;

  // FUNCTIONS
  PaymentsPresenter payment;

  // METHODS
  private static final String PAYMENT_DEBIT = "paymentDebit";
  private static final String PAYMENT_CREDIT = "paymentCredit";
  private static final String PAYMENT_CREDIT_PARC = "paymentCreditParc";
  private static final String PAYMENT_VOUCHER = "paymentVoucher";
  private static final String PAYMENT_ABORT = "paymentAbort";
  private static final String LAST_TRANSACTION = "paymentLastTransaction";
  private static final String REFUND = "paymentRefund";
  private static final String PAYMENT_PIX = "paymentPix";
  private static final String START_PAYMENT = "startPayment";

  private static final String ACTIVE_PINPAD = "paymentActivePinpad";

  private static final String PINPAD_AUTHENTICATED = "paymentIsAuthenticated";

  public PagSeguroSmart(Context context, MethodChannel channel) {
    PlugPag instancePlugPag = new PlugPag(context);
    PlugPagCustomPrinterLayout customDialog = new PlugPagCustomPrinterLayout();
    customDialog.setMaxTimeShowPopup(30);
    instancePlugPag.setPlugPagCustomPrinterLayout(customDialog);
    this.plugPag = instancePlugPag;
    this.mChannel = channel;
  }

  public void initPayment(MethodCall call, MethodChannel.Result result) {
    if (this.payment == null) {
      this.payment = new PaymentsPresenter(this.plugPag, this.mChannel);
    }

    if (call.method.equals(PAYMENT_DEBIT)) {
      this.payment.doDebitPayment(
          (int) call.argument("value"),
          (String) call.argument("userReference"),
          (Boolean) call.argument("printReceipt"),
          (Boolean) call.argument("partialPay"),
          (Boolean) call.argument("isCarne")        
        );
    } else if (call.method.equals(ACTIVE_PINPAD)) {
      this.payment.activate(call.argument("code"));
    } else if (call.method.equals(PINPAD_AUTHENTICATED)) {
      this.payment.isAuthenticate();
    } else if (call.method.equals(PAYMENT_PIX)) {
      this.payment.doPixPayment(
          (int) call.argument("value"),
          (String) call.argument("userReference"),
          (Boolean) call.argument("printReceipt"),
          (Boolean) call.argument("partialPay"),
          (Boolean) call.argument("isCarne")        
        );
    } else if (call.method.equals(PAYMENT_CREDIT)) {
      this.payment.creditPayment(
          (int) call.argument("value"),
          (String) call.argument("userReference"),
          (Boolean) call.argument("printReceipt"),
          (Boolean) call.argument("partialPay"),
          (Boolean) call.argument("isCarne")        
        );
    } else if (call.method.equals(PAYMENT_CREDIT_PARC)) {
      this.payment.creditPaymentParc(
          (int) call.argument("value"),
          (int) call.argument("type"),
          (int) call.argument("parc"),
          (String) call.argument("userReference"),
          (Boolean) call.argument("printReceipt"),
          (Boolean) call.argument("partialPay"),
          (Boolean) call.argument("isCarne")
        );
    } else if (call.method.equals(PAYMENT_VOUCHER)) {
      this.payment.doVoucherPayment(
          (int) call.argument("value"),
          (String) call.argument("userReference"),
          (Boolean) call.argument("printReceipt"),
          (Boolean) call.argument("partialPay"),
          (Boolean) call.argument("isCarne")        
        );
    } else if (call.method.equals(START_PAYMENT)) {
      this.payment.startPayment(
          (int) call.argument("type"),
          (int) call.argument("amount"),
          (int) call.argument("installmentType"),
          (int) call.argument("installments"),
          (String) call.argument("userReference"),
          (Boolean) call.argument("printReceipt"),
          (Boolean) call.argument("partialPay"),
          (Boolean) call.argument("isCarne")
        );
    } else if (call.method.equals(PAYMENT_ABORT)) {
      this.payment.abortTransaction();
      result.success(true);
    } else if (call.method.equals(LAST_TRANSACTION)) {
      this.payment.getLastTransaction();
    } else if (call.method.equals(REFUND)) {
      this.payment.doRefund(
          call.argument("transactionCode"),
          call.argument("transactionId")
        );
      result.success(true);
    } else {
      result.notImplemented();
    }
  }

  public void dispose() {
    if (this.payment != null) {
      this.payment.dispose();
    }
  }
}
