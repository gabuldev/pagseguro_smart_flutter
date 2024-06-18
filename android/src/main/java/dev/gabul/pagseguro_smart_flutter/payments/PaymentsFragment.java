package dev.gabul.pagseguro_smart_flutter.payments;

import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.Map;
import android.os.Handler;
import android.os.Looper;

public class PaymentsFragment implements PaymentsContract {

  private final Handler mainHandler = new Handler(Looper.getMainLooper());
  final MethodChannel channel;

  public PaymentsFragment(MethodChannel channel) {
    this.channel = channel;
  }

  private void runOnUiThread(Runnable runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable.run();
    } else {
      mainHandler.post(runnable);
    }
  }

  private static final String ON_TRANSACTION_SUCCESS = "onTransactionSuccess";
  private static final String ON_ERROR = "onError";
  private static final String ON_MESSAGE = "onMessage";
  private static final String ON_FINISHED_RESPONSE = "onFinishedResponse";
  private static final String ON_LOADING = "onLoading";
  private static final String WRITE_TO_FILE = "writeToFile";
  private static final String ON_ABORTED_SUCCESSFULLY = "onAbortedSuccessfully";
  private static final String DISPOSE_DIALOG = "disposeDialog";
  private static final String ACTIVE_DIALOG = "activeDialog";
  private static final String ON_AUTH_PROGRESS = "onAuthProgress";
  private static final String ON_TRANSACTION_INFO = "onTransactionInfo";

  @Override
  public void onTransactionSuccess() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_TRANSACTION_SUCCESS, true);
      }
    });
  }

  @Override
  public void onError(String message) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_ERROR, message);
      }
    });
  }

  @Override
  public void onMessage(String message) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_MESSAGE, message);
      }
    });
  }

  @Override
  public void onFinishedResponse(String message) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_FINISHED_RESPONSE, message);
      }
    });
  }

  @Override
  public void onLoading(boolean show) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_LOADING, show);
      }
    });
  }

  @Override
  public void writeToFile(
    String transactionCode,
    String transactionId,
    String response
  ) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("transactionCode", transactionCode);
    map.put("transactionId", transactionId);
    map.put("response", response);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(WRITE_TO_FILE, map);
      }
    });
  }

  @Override
  public void onAbortedSuccessfully() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_ABORTED_SUCCESSFULLY, true);
      }
    });
  }

  @Override
  public void disposeDialog() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(DISPOSE_DIALOG, true);
      }
    });
  }

  @Override
  public void onActivationDialog() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ACTIVE_DIALOG, true);
      }
    });
  }

  @Override
  public void onAuthProgress(String message) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_AUTH_PROGRESS, message);
      }
    });
  }

  @Override
  public void onTransactionInfo(
    String transactionCode,
    String transactionId,
    String response
  ) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("transactionCode", transactionCode);
    map.put("transactionId", transactionId);
    map.put("response", response);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        this.channel.invokeMethod(ON_TRANSACTION_INFO, map);
      }
    });
  }
}
