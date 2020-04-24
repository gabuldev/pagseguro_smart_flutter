package dev.gabul.pagseguro_smart_flutter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagAppIdentification;
import dev.gabul.pagseguro_smart_flutter.core.PagSeguroSmart;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsFragment;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsPresenter;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsUseCase;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** PagseguroSmartFlutterPlugin */
public class PagseguroSmartFlutterPlugin implements FlutterPlugin, MethodCallHandler {

  static private final String CHANNEL_NAME = "pagseguro_smart_flutter";

  private MethodChannel channel;
  private Context context;
  private PagSeguroSmart pagSeguroSmart;


  public PagseguroSmartFlutterPlugin() {}

/*
  public static void registerWith(Registrar registrar) {
        PagseguroSmartFlutterPlugin instance = new PagseguroSmartFlutterPlugin();
        instance.channel = new MethodChannel(registrar.messenger(),CHANNEL_NAME);
        instance.context = registrar.context();
        instance.channel.setMethodCallHandler(instance);
        instance.pagSeguroSmart = new PagSeguroSmart(instance.context,instance.channel);
        }**/

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
       if (call.method.startsWith("payment")) {
          pagSeguroSmart.initPayment(call,result);
    }

       else {
      result.notImplemented();
    }
  }


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
      channel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL_NAME);
      context = binding.getApplicationContext();
      channel.setMethodCallHandler(this);
      pagSeguroSmart = new PagSeguroSmart(context,channel);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        channel = null;
        pagSeguroSmart.dispose();
        pagSeguroSmart = null;
    }
}
