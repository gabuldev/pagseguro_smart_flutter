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
  MoneyMaskedTextController moneyController = MoneyMaskedTextController(leftSymbol: "R\$ ", decimalSeparator: ",");

  @override
  void initState() {
    //Inicializar a classe handle para escutar os métodos e retornos da pagseguro
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
                        //Chamar o método de pagamento para transação no débito
                        PagseguroSmart.instance().payment.debitPayment(controller.saleValue);
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
                        //Chamar o método de pagamento para transação no crédito a vista
                        PagseguroSmart.instance().payment.creditPayment(controller.saleValue);
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
                        //Chamar o método de pagamento para transação no crédito parcelado em 2x
                        PagseguroSmart.instance().payment.creditPaymentParc(controller.saleValue, 2);
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
                        //Chamar o método de pagamento para transação no voucher
                        PagseguroSmart.instance().payment.voucherPayment(controller.saleValue);
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
                        //Chamar o método de pagamento para transação no pix
                        PagseguroSmart.instance().payment.pixPayment(controller.saleValue);
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
                  //Chamar o método para ativar o terminal (pinpad)
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
                    //Chamar o método para abortar uma transação em andamento (processamento)
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
              Future.delayed(const Duration(seconds: 3)).then((value) => setState(() {}));
              //Chamar o método para retornar a última transação realizada
              PagseguroSmart.instance().payment.lastTransaction();
            },
            child: const Text("Ultima transação"),
          ),
          const SizedBox(
            height: 20,
          ),
          ElevatedButton(
            onPressed: () {
              final list = List.generate(
                45,
                (index) {
                  final bool textDarkMode = index % 2 == 0;

                  final Color textBackgroundColor =
                      textDarkMode ? Colors.black : Colors.white;
                  final Color textColor =
                      textDarkMode ? Colors.white : Colors.black;

                  return Row(children: [
                    Container(
                        color: textBackgroundColor,
                        child: Text(
                          'Item ${index + 1}',
                          style: TextStyle(
                            fontSize: 20,
                            color: textColor,
                          ),
                        )),
                  ]);
                },
              );

              //Chamar o método para imprimir um widget
              PrintRenderWidget.print(
                context,
                pagseguroSmartInstance: PagseguroSmart.instance(),
                child: ListView.builder(
                  shrinkWrap: true,
                  itemCount: list.length,
                  itemBuilder: (context, index) {
                    return list[index];
                  },
                ),
              );
            },
            child: const Text("Imprimir componente"),
          ),
          if (controller.enableRefund)
            ElevatedButton(
              onPressed: () {
                //Chamar o método para estornar uma transação
                PagseguroSmart.instance().payment.refund(transactionCode: controller.transactionCode, transactionId: controller.transactionId);
              },
              child: const Text("Estornar transação"),
            ),
        ],
      ),
    );
  }
}
