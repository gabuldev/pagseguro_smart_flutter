<h1 align="center">Pagseguro Smart Flutter</h1>

<p align="center">
  <a href="#dart-sobre">Sobre</a> &#xa0; | &#xa0; 
  <a href="#rocket-tecnologias">Tecnologias</a> &#xa0; | &#xa0;
  <a href="#checkered_flag-configuração">Configuração</a> &#xa0; | &#xa0;
  <a href="#memo-autores">Autores</a> &#xa0; | &#xa0;
</p>

<br>

## :dart: Sobre ##

Projeto destinado a facilitar a integração com o SDK da PagSeguro Smart no Flutter.
Funciona somente com máquinas smarts

## :rocket: Tecnologias ##

As seguintes ferramentas foram usadas na construção do projeto:

- [Flutter](https://flutter.dev/)
- [PlugPagServiceWrapper]()

## :checkered_flag: Configuração ##

### # Android Manifest

Para integrar a biblioteca a biblioteca PlugPagService em aplicativos para Android é
necessário adicionar a seguinte permissão ao AndroidManifest.xml.

```xml
<permission android:name="br.com.uol.pagseguro.permission.MANAGE_PAYMENTS"/>
```

### # Intent-filter
Para que seu aplicativo possa ser escolhido como aplicativo padrão de pagamento e receber
Intents de inserção de cartão, é necessário adicionar o seguinte código em seu
AndroidManifest.xml dentro da sua Activity principal.

```xml
<intent-filter>
 <action android:name="br.com.uol.pagseguro.PAYMENT"/>
 <category android:name="android.intent.category.DEFAULT"/>
</intent-filter>
```

### # Build.gradle

Em seu build.gradle a nivel do app, a propriedade `minSdkVersion` precisa ser level 23. Recurso este exigido pela versão 1.22.0 do plugpagservice da PagSeguro.

```xml 
...
defaultConfig {
        applicationId "com.example.pagseguro_example"
        minSdkVersion 23
        targetSdkVersion flutter.targetSdkVersion
        versionCode flutterVersionCode.toInteger()
        versionName flutterVersionName
    }
...
```

### # Implementação

Para começar é necessário criar uma classe que implemente ´PaymentHandler´, sendo que essa é a responsável por monitorar e retornar os dados da pagseguro.

### Criando classe PaymentController 

```
class PaymentController extends PaymentHandler {
  int saleValue = 0;
  bool enable = false;
  bool clickPayment = false;
  bool enableRefund = false;
  String? transactionCode;
  String? transactionId;
  String? response;

  void setSaleValue(double value) {
    if (value > 0.0) {
      saleValue = (value * 100).toInt();
      clickPayment = false;
      enable = true;
    } else {
      clickPayment = false;
      enable = false;
    }
  }

  @override
  void disposeDialog() {
    BotToast.cleanAll();
  }

  @override
  void onAbortedSuccessfully() {
    BotToast.showText(text: "Operação cancelada");
  }

  @override
  void onActivationDialog() {}

  @override
  void onAuthProgress(String message) {
    BotToast.showLoading();
  }

  @override
  void onError(String message) {
    BotToast.showText(text: message);
  }

  @override
  void onMessage(String message) {
    BotToast.showText(text: message);
  }

  @override
  void onFinishedResponse(String message) {
    BotToast.showText(text: message);
  }

  @override
  void onTransactionSuccess() {
    BotToast.showText(text: "Transacao com successo!");
  }

  @override
  void writeToFile({
    String? transactionCode,
    String? transactionId,
    String? response,
  }) {}

  @override
  void onLoading(bool show) {
    if (show) {
      BotToast.showLoading();
      return;
    }
    BotToast.closeAllLoading();
  }

  @override
  void onTransactionInfo({
    String? transactionCode,
    String? transactionId,
    String? response,
  }) {
    this.transactionCode = transactionCode;
    this.transactionId = transactionId;
    this.response = response;
    BotToast.showText(
        text:
            "{transactionCode: $transactionCode \n transactionId: $transactionId}");
    enableRefund = true;
  }
}

```

#### Métodos da ´PaymentHandler´
##### onAbortedSuccessfully
Acionado quando uma transação de abort é concluída com sucesso.

##### onAuthProgress
Acionado quando uma transação está em progresso.

##### onError
Acionado quando uma transação retorna um estado de ´Erro´, devolvendo como parâmetro uma ´String´ com a mensagem.

##### onMessage
Método responsável por devolver para o usuário uma mensagem retornada da PagSeguro.

##### onFinishedResponse
Método responsável por devolver uma response da transação.

##### onTransactionSuccess
Método acionado quando a transação foi concluída com sucesso.

##### onTransactionInfo
Método resposável por devolver uma response completa da transação, sendo possível mapear vários campos retornados.


#### Iniciar transação
Para iniciar a transação é necessário primeiro chamar a função de ativação do PinPad, passando como parâmetro o código de ativação daquele POS (código este informado na sua conta PagBank).

`PagseguroSmart.instance().payment.activePinpad('12345');`

Logo após ativação, o SDK da PagSeguro fornece algumas opções de transação como:
- Crédito = `PagseguroSmart.instance().payment.creditPayment(12.50)`

- Crédito Parcelado = `PagseguroSmart.instance().payment.creditPaymentParc(controller.saleValue, 2)`

- Débito = `PagseguroSmart.instance().payment.debitPayment(12.50)`

- PIX = `PagseguroSmart.instance().payment.pixPayment.(12.50)`

- Voucher (alimentação) = `PagseguroSmart.instance().payment.voucherPayment(12.50)`

- Estorno = `PagseguroSmart.instance().payment.refund(transactionCode: controller.transactionCode, transactionId: controller.transactionId)`

- Abortar transação = `PagseguroSmart.instance().payment.abortTransaction()`

- Última transação = `PagseguroSmart.instance().payment.lastTransaction()`


**Obs: Por padrão o SDK da PagSeguro SEMPRE imprime a via do estabelecimento. Após a impressão da via do estabelecimento, um popUp é exibido perguntando se deseja a via do consumidor.



## :memo: Autores ##

Este projeto foi desenvolvido por:
<a href="https://github.com/gabuldev" target="_blank">Gabul Dev</a> e
<a href="https://github.com/jhonathanqz" target="_blank">Jhonathan Queiroz</a>

&#xa0;

<a href="#top">Voltar para o topo</a>
