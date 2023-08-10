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

## :memo: Autores ##

Este projeto foi desenvolvido por:
<a href="https://github.com/gabuldev" target="_blank">Gabul Dev</a> e
<a href="https://github.com/jhonathanqz" target="_blank">Jhonathan Queiroz</a>

&#xa0;

<a href="#top">Voltar para o topo</a>
