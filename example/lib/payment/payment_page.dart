import 'package:example/payment/payment_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_masked_text/flutter_masked_text.dart';
import 'package:pagseguro_smart_flutter/pagseguro_smart_flutter.dart';

class PaymentPage extends StatefulWidget {
  PaymentPage({Key key}) : super(key: key);

  @override
  _PaymentPageState createState() => _PaymentPageState();
}

class _PaymentPageState extends State<PaymentPage> {
  final PaymentController controller = PaymentController();

  double saleValue;
  MoneyMaskedTextController moneyController =
      MoneyMaskedTextController(leftSymbol: "R\$ ", decimalSeparator: ",");

  @override
  void initState() {
    PagseguroSmart.instance().initPayment(controller);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child: Column(
        children: <Widget>[
          SizedBox(
            height: 20,
          ),
          TextField(
            onChanged: (value) => setState(() {
              controller.setSaleValue(moneyController.numberValue);
            }),
            keyboardType: TextInputType.number,
            decoration: InputDecoration(hintText: "Digite o valor"),
            controller: moneyController,
          ),
          SizedBox(
            height: 20,
          ),
          Text(
            "Selecione o método de pagamento",
            style: TextStyle(fontSize: 16),
          ),
          SizedBox(
            height: 20,
          ),
          Wrap(
            spacing: 10.0,
            children: <Widget>[
              RaisedButton(
                child: Text("Débito"),
                colorBrightness: Brightness.dark,
                color: Colors.green,
                onPressed: controller.enable
                    ? () {
                        FocusScope.of(context).unfocus();
                        setState(() {
                          controller.clickPayment = true;
                        });
                        PagseguroSmart.instance()
                            .payment
                            .debitPayment(controller.saleValue);
                      }
                    : null,
              ),
              RaisedButton(
                child: Text("Crédito"),
                colorBrightness: Brightness.dark,
                color: Colors.green,
                onPressed: controller.enable
                    ? () {
                        FocusScope.of(context).unfocus();
                        setState(() {
                          controller.clickPayment = true;
                        });
                        PagseguroSmart.instance()
                            .payment
                            .creditPayment(controller.saleValue);
                      }
                    : null,
              ),
              RaisedButton(
                child: Text("Crédito Parc- 2"),
                colorBrightness: Brightness.dark,
                color: Colors.green,
                onPressed: controller.enable
                    ? () {
                        FocusScope.of(context).unfocus();
                        setState(() {
                          controller.clickPayment = true;
                        });
                        PagseguroSmart.instance()
                            .payment
                            .creditPaymentParc(controller.saleValue, 2);
                      }
                    : null,
              ),
              RaisedButton(
                child: Text("Voucher"),
                colorBrightness: Brightness.dark,
                color: Colors.green,
                onPressed: controller.enable
                    ? () {
                        FocusScope.of(context).unfocus();
                        setState(() {
                          controller.clickPayment = true;
                        });
                        PagseguroSmart.instance()
                            .payment
                            .voucherPayment(controller.saleValue);
                      }
                    : null,
              )
            ],
          ),
          SizedBox(
            height: 20,
          ),
          RaisedButton(
            colorBrightness: Brightness.dark,
            color: Colors.red,
            onPressed: controller.clickPayment
                ? () {
                    controller.setSaleValue(0.0);
                    PagseguroSmart.instance().payment.abortTransaction();
                  }
                : null,
            child: Text("Cancelar Operação"),
          ),
          SizedBox(
            height: 20,
          ),
          RaisedButton(
            colorBrightness: Brightness.dark,
            color: Colors.blue,
            onPressed: () {
              Future.delayed(Duration(seconds: 3))
                  .then((value) => setState(() {}));
              PagseguroSmart.instance().payment.lastTransaction();
            },
            child: Text("Ultima transação"),
          ),
          SizedBox(
            height: 20,
          ),
          if (controller.enableRefund)
            RaisedButton(
              colorBrightness: Brightness.dark,
              color: Colors.blue,
              onPressed: () {
                PagseguroSmart.instance().payment.refund(
                    transactionCode: controller.transactionCode,
                    transactionId: controller.transactionId);
              },
              child: Text("Estornar transação"),
            ),
        ],
      ),
    );
  }
}
