# pagseguro_smart_flutter

A new Flutter plugin.

## Android Manifest

Para integrar a biblioteca a biblioteca PlugPagService em aplicativos para Android é
necessário adicionar a seguinte permissão ao AndroidManifest.xml.

```xml
<permission android:name="br.com.uol.pagseguro.permission.MANAGE_PAYMENTS"/>
```

## Intent-filter
Para que seu aplicativo possa ser escolhido como aplicativo padrão de pagamento e receber
Intents de inserção de cartão, é necessário adicionar o seguinte código em seu
AndroidManifest.xml dentro da sua Activity principal.

```xml
<intent-filter>
 <action android:name="br.com.uol.pagseguro.PAYMENT"/>
 <category android:name="android.intent.category.DEFAULT"/>
</intent-filter>
```

