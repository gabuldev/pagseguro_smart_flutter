package dev.gabul.pagseguro_smart_flutter.core;
import android.content.Context;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagAppIdentification;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagCustomPrinterLayout;
import dev.gabul.pagseguro_smart_flutter.managers.UserDataManager;
import dev.gabul.pagseguro_smart_flutter.nfc.NFCFragment;
import dev.gabul.pagseguro_smart_flutter.nfc.NFCPresenter;
import dev.gabul.pagseguro_smart_flutter.nfc.usecase.NFCUseCase;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsPresenter;
import dev.gabul.pagseguro_smart_flutter.printer.PrinterPresenter;
import dev.gabul.pagseguro_smart_flutter.printer.usecase.PrinterUsecase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.DebitUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.EditUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.GetUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.NewUserUseCase;
import dev.gabul.pagseguro_smart_flutter.user.usecase.RefundUserUseCase;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
public class PagSeguroSmart {
  final PlugPag plugPag;
  final MethodChannel mChannel;
  PaymentsPresenter payment;

  private NFCUseCase mUseCase;
  private NFCFragment mFragment;
  UserDataManager mUserManager;
  NFCPresenter nfcPayment;
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
  private static final String REBOOT_DEVICE = "paymentReboot";
  private static final String BEEP_PAYMENT = "paymentBeep";

  //NFC
  private static final String WRITE_NFC = "paymentWriteNfc";
  private static final String READ_NFC = "paymentReadNfc";
  private static final String FORMAT_NFC = "paymentFormatNfc";
  private static final String REWRITE_NFC = "paymentReWriteNfc";
  private static final String REFUND_NFC = "paymentReFundNfc";
  private static final String DEBIT_NFC = "paymentDebitNfc";

  //Printer
  private static final String PRINTER_FILE = "paymentPrinterFile";
  private static final String PRINTER = "paymentPrinter";

  private static final String PRINTER_BASIC = "paymentPrinterBasic";
  private static final String PRINTER_FILE_PATH = "paymentPrinterFilePath";

  public PagSeguroSmart(Context context, MethodChannel channel) {
    PlugPag instancePlugPag = new PlugPag(context);
    PlugPagCustomPrinterLayout customDialog = new PlugPagCustomPrinterLayout();
    customDialog.setMaxTimeShowPopup(30);
    instancePlugPag.setPlugPagCustomPrinterLayout(customDialog);
    this.plugPag = instancePlugPag;
    this.mChannel = channel;
  }
  public void initPayment(MethodCall call, MethodChannel.Result result) {
    if(call.method.equals(PRINTER_FILE)) {
      PrinterPresenter printerPresenter = new PrinterPresenter(this.plugPag, this.mChannel);
      String filePath = call.argument("path");
      printerPresenter.printerFromFile(filePath);
    }

    if(call.method.equals(PRINTER)) {
      PrinterPresenter printerPresenter = new PrinterPresenter(this.plugPag, this.mChannel);
      String filePath = call.argument("path");
      printerPresenter.printFile(filePath);
    }

    if(call.method.equals(PRINTER_BASIC)) {
      PrinterPresenter printerPresenter = new PrinterPresenter(this.plugPag, this.mChannel);
      String filePath = call.argument("path");
      printerPresenter.printer(filePath);
    }

    if(call.method.equals(PRINTER_FILE_PATH)) {
      PrinterPresenter printerPresenter = new PrinterPresenter(this.plugPag, this.mChannel);
      String filePath = call.argument("path");
      printerPresenter.printerByFilePath(filePath);
    }

    if (this.payment == null) {
      this.payment = new PaymentsPresenter(this.plugPag, this.mChannel);
    }

    mUseCase = new NFCUseCase(plugPag);
    mFragment = new NFCFragment(mChannel);
    mUserManager = new UserDataManager(
            new GetUserUseCase(mUseCase),
            new NewUserUseCase(mUseCase),
            new EditUserUseCase(mUseCase),
            new DebitUserUseCase(mUseCase),
            new RefundUserUseCase(mUseCase)
    );

    this.nfcPayment = new NFCPresenter(mFragment, mUseCase, mUserManager);
    this.nfcPayment.dispose();

    if (call.method.equals(REBOOT_DEVICE)) {
      this.payment.rebootDevice();
      result.success(true);
    }

    if (call.method.equals(BEEP_PAYMENT)) {
      this.payment.beep();
      result.success(true);
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
      result.success(true);
    } else if (call.method.equals(PINPAD_AUTHENTICATED)) {
      this.payment.isAuthenticate();
      result.success(true);
    } else if (call.method.equals(PAYMENT_PIX)) {
      this.payment.doPixPayment(
              (int) call.argument("value"),
              (String) call.argument("userReference"),
              (Boolean) call.argument("printReceipt"),
              (Boolean) call.argument("partialPay"),
              (Boolean) call.argument("isCarne")
      );
      result.success(true);
    } else if (call.method.equals(PAYMENT_CREDIT)) {
      this.payment.creditPayment(
              (int) call.argument("value"),
              (String) call.argument("userReference"),
              (Boolean) call.argument("printReceipt"),
              (Boolean) call.argument("partialPay"),
              (Boolean) call.argument("isCarne")
      );
      result.success(true);
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
        result.success(true);
    } else if (call.method.equals(PAYMENT_VOUCHER)) {
      this.payment.doVoucherPayment(
              (int) call.argument("value"),
              (String) call.argument("userReference"),
              (Boolean) call.argument("printReceipt"),
              (Boolean) call.argument("partialPay"),
              (Boolean) call.argument("isCarne")
      );
      result.success(true);
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
      result.success(true);
    } else if (call.method.equals(PAYMENT_ABORT)) {
      this.payment.abortTransaction();
      result.success(true);
    } else if (call.method.equals(LAST_TRANSACTION)) {
      this.payment.getLastTransaction();
      result.success(true);
    } else if (call.method.equals(REFUND)) {
      this.payment.doRefund(
          call.argument("transactionCode"),
          call.argument("transactionId")
        );
      result.success(true);
    } else if(call.method.equals(READ_NFC)) {
      this.nfcPayment.readNFCCard(call.argument("idEvento"));
    }
    else if(call.method.equals(WRITE_NFC)) {
      this.nfcPayment.writeNFCCard(call.argument("valor"), call.argument("nome"), call.argument("cpf"), call.argument("numeroTag"), call.argument("celular"), call.argument("aberto"), call.argument("idEvento"));
    }
    else if(call.method.equals(REWRITE_NFC)) {
      this.nfcPayment.reWriteNFCCard(call.argument("valor"), call.argument("idEvento"));
    }

    else if(call.method.equals(REFUND_NFC)) {
      this.nfcPayment.reFundNFCCard(call.argument("valor"), call.argument("idEvento"));
    }
    else if(call.method.equals(FORMAT_NFC)) {
      this.nfcPayment.formatNFCCard();
    }
    else if(call.method.equals(DEBIT_NFC)) {
      this.nfcPayment.debitNFCCard(call.argument("idEvento"),call.argument("valor"));
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