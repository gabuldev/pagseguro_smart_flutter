package dev.gabul.pagseguro_smart_flutter.core;

import android.content.Context;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagAppIdentification;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsPresenter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class PagSeguroSmart {
    final PlugPag plugPag;
    final MethodChannel mChannel;

    //FUNCTIONS
     PaymentsPresenter payment;

     //METHODS
     private static final String PAYMENT_DEBIT = "paymentDebit";
     private static final String PAYMENT_CREDIT = "paymentCredit";
     private static final String PAYMENT_VOUCHER = "paymentVoucher";
     private static final String PAYMENT_ABORT = "paymentAbort";

    public PagSeguroSmart(Context context, MethodChannel channel) {
        this.plugPag = new PlugPag(context,new PlugPagAppIdentification("Pagseguro Smart Flutter","0.0.1"));
        this.mChannel = channel;
    }

    public void initPayment(MethodCall call, MethodChannel.Result result){
        if(this.payment == null)
        this.payment = new PaymentsPresenter(this.plugPag,this.mChannel);

        if(call.method.equals(PAYMENT_DEBIT)){
           this.payment.doDebitPayment(call.argument("value"));
           //result.success(true);
        }
        else if(call.method.equals(PAYMENT_CREDIT)){
            this.payment.creditPayment(call.argument("value"));
            //result.success(true);
        }
        else if(call.method.equals(PAYMENT_VOUCHER)){
            this.payment.doVoucherPayment(call.argument("value"));
            //result.success(true);
        }
       else if(call.method.equals(PAYMENT_ABORT)){
            this.payment.abortTransaction();
            result.success(true);
        }
        else{
            result.notImplemented();
        }
    }


   public void dispose(){
        if(this.payment != null){
            this.payment.dispose();
        }
    }
}


