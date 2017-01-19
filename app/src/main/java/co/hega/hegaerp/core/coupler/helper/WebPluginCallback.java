package co.hega.hegaerp.core.coupler.helper;

import android.util.Log;
import com.odoo.mobile.core.widgets.OdooWebView;
import org.json.JSONException;
import org.json.JSONObject;

public class WebPluginCallback {
    public static final String TAG = WebPluginCallback.class.getCanonicalName();
    private String callbackID;
    private OdooWebView webView;

    public WebPluginCallback(OdooWebView view, String callbackID) {
        this.webView = view;
        this.callbackID = callbackID;
    }

    public void success(Object data) {
        doCallback(data, true);
    }

    public void fail(Object error) {
        doCallback(error, false);
    }

    private void doCallback(Object data, boolean success) {
        Log.d(TAG, "Returning result: " + data + " : " + success);
        try {
            JSONObject result = new JSONObject();
            result.put("success", success);
            result.put("data", data);
            if (this.callbackID != null) {
                post(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void post(final Object result) {
        this.webView.post(new Runnable() {
            public void run() {
                StringBuilder callbackJS = new StringBuilder("javascript:odoo.native_notify('");
                callbackJS.append(WebPluginCallback.this.callbackID).append("', ").append(result).append(")");
                WebPluginCallback.this.webView.loadUrl(callbackJS.toString());
            }
        });
    }

    public void permissionFail(JSONObject failReason) {
        post(failReason);
    }
}
