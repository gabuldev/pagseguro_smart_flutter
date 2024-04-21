package dev.gabul.pagseguro_smart_flutter;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagAppIdentification;
import dev.gabul.pagseguro_smart_flutter.core.PagSeguroSmart;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsFragment;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsPresenter;
import dev.gabul.pagseguro_smart_flutter.payments.PaymentsUseCase;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class PagseguroSmartFlutterPlugin
  implements FlutterPlugin, MethodCallHandler {
  private static final String CHANNEL_NAME = "pagseguro_smart_flutter";
  private MethodChannel channel;
  private Context context;
  private PagSeguroSmart pagSeguroSmart;
  
  public PagseguroSmartFlutterPlugin() {}

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    channel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL_NAME);
    //Get context to application
    context = binding.getApplicationContext();
    channel.setMethodCallHandler(this);
    //Create instance to PagSeguroSmart class
    pagSeguroSmart = new PagSeguroSmart(context, channel);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    //Function responsible for listening to methods called by flutter
    if (call.method.startsWith("payment") || call.method.equals("startPayment")) {
      //Call payment method
      pagSeguroSmart.initPayment(call, result);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    //Dispose plugin
    channel.setMethodCallHandler(null);
    channel = null;
    pagSeguroSmart.dispose(); //Dispose PagSeguroSmart
    pagSeguroSmart = null;
  }
}