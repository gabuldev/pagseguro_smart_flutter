abstract class PaymentCall {
  void creditPayment(int value);
  void debitPayment(int value);
  void voucherPayment(int value);

  //REFOUND
  void abortTransaction();
}
