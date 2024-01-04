import 'package:flutter/material.dart';

import 'package:flutter_masked_text2/flutter_masked_text2.dart';

import 'package:pagseguro_smart_flutter/pagseguro_smart_flutter.dart';

import 'payment_controller.dart';

class PaymentPage extends StatefulWidget {
  const PaymentPage({Key? key}) : super(key: key);

  @override
  _PaymentPageState createState() => _PaymentPageState();
}

class _PaymentPageState extends State<PaymentPage> {
  final PaymentController controller = PaymentController();

  double? saleValue;
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
          const SizedBox(
            height: 20,
          ),
          TextField(
            onChanged: (value) => setState(() {
              controller.setSaleValue(moneyController.numberValue);
            }),
            keyboardType: TextInputType.number,
            decoration: const InputDecoration(hintText: "Digite o valor"),
            controller: moneyController,
          ),
          const SizedBox(
            height: 20,
          ),
          const Text(
            "Selecione o método de pagamento",
            style: TextStyle(fontSize: 16),
          ),
          const SizedBox(
            height: 20,
          ),
          Wrap(
            spacing: 10.0,
            children: <Widget>[
              ElevatedButton(
                child: const Text("Débito"),
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
              ElevatedButton(
                child: const Text("Crédito"),
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
              ElevatedButton(
                child: const Text("Crédito Parc- 2"),
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
              ElevatedButton(
                child: const Text("Voucher"),
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
              ),
              ElevatedButton(
                child: const Text("PIX"),
                onPressed: controller.enable
                    ? () {
                        FocusScope.of(context).unfocus();
                        setState(() {
                          controller.clickPayment = true;
                        });
                        PagseguroSmart.instance()
                            .payment
                            .pixPayment(controller.saleValue);
                      }
                    : null,
              ),
              ElevatedButton(
                child: const Text("ATIVAR PINPAD"),
                onPressed: () async {
                  FocusScope.of(context).unfocus();
                  setState(() {
                    controller.clickPayment = true;
                  });
                  PagseguroSmart.instance().payment.activePinpad('');
                },
              ),
            ],
          ),
          const SizedBox(
            height: 20,
          ),
          ElevatedButton(
            onPressed: controller.clickPayment
                ? () {
                    controller.setSaleValue(0.0);
                    PagseguroSmart.instance().payment.abortTransaction();
                  }
                : null,
            child: const Text("Cancelar Operação"),
          ),
          const SizedBox(
            height: 20,
          ),
          ElevatedButton(
            onPressed: () {
              Future.delayed(const Duration(seconds: 3))
                  .then((value) => setState(() {}));
              PagseguroSmart.instance().payment.lastTransaction();
            },
            child: const Text("Ultima transação"),
          ),
          const SizedBox(
            height: 20,
          ),
          if (controller.enableRefund)
            ElevatedButton(
              onPressed: () {
                PagseguroSmart.instance().payment.refund(
                    transactionCode: controller.transactionCode,
                    transactionId: controller.transactionId);
              },
              child: const Text("Estornar transação"),
            ),
        ],
      ),
    );
  }
}
