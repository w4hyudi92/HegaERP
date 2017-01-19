package co.hega.hegaerp.core.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import com.odoo.mobile.R;

public class OdooWebChromeClient extends WebChromeClient {
    public static String TAG = OdooWebChromeClient.class.getCanonicalName();
    private Context mContext = this.odooWebView.getContext();
    private OdooWebView odooWebView;

    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$android$webkit$ConsoleMessage$MessageLevel = new int[MessageLevel.values().length];

        static {
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[MessageLevel.LOG.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[MessageLevel.DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[MessageLevel.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$webkit$ConsoleMessage$MessageLevel[MessageLevel.TIP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public OdooWebChromeClient(OdooWebView webView) {
        this.odooWebView = webView;
    }

    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        Builder builder = new Builder(this.mContext);
        builder.setTitle(2131165296);
        builder.setMessage(message);
        builder.setPositiveButton(17039370, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
        return true;
    }

    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        Builder builder = new Builder(this.mContext);
        builder.setTitle(2131165297);
        builder.setMessage(message);
        builder.setPositiveButton(17039370, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        });
        builder.setNegativeButton(17039360, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
        return true;
    }

    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        Builder builder = new Builder(this.mContext);
        builder.setTitle(2131165298);
        builder.setMessage(message);
        View dialogView = LayoutInflater.from(this.mContext).inflate(2130968621, null, false);
        final EditText inputView = (EditText) dialogView.findViewById(16908297);
        builder.setView(dialogView);
        builder.setCancelable(false);
        builder.setPositiveButton(17039370, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm(inputView.getText().toString());
            }
        });
        builder.setNegativeButton(17039360, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
                dialog.dismiss();
            }
        });
        builder.show();
        return true;
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        _onConsoleMessage(consoleMessage.message(), consoleMessage.messageLevel(), consoleMessage.lineNumber(), consoleMessage.sourceId());
        return super.onConsoleMessage(consoleMessage);
    }

    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        super.onConsoleMessage(message, lineNumber, sourceID);
        _onConsoleMessage(message, MessageLevel.LOG, lineNumber, sourceID);
    }

    private void _onConsoleMessage(String message, MessageLevel messageLevel, int lineNumber, String source) {
        int logType = 4;
        switch (AnonymousClass6.$SwitchMap$android$webkit$ConsoleMessage$MessageLevel[messageLevel.ordinal()]) {
            case R.styleable.View_android_focusable /*1*/:
                logType = 4;
                break;
            case R.styleable.View_paddingStart /*2*/:
                logType = 5;
                break;
            case R.styleable.View_paddingEnd /*3*/:
                logType = 3;
                break;
            case R.styleable.View_theme /*4*/:
                logType = 6;
                break;
            case R.styleable.Toolbar_contentInsetStart /*5*/:
                logType = 2;
                break;
        }
        Log.println(logType, TAG, "[" + source + "]" + message + " (" + lineNumber + ")");
    }
}
