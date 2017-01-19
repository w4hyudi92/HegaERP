package co.hega.hegaerp.core.coupler;

import android.util.Log;
import android.webkit.JavascriptInterface;
import com.odoo.mobile.core.coupler.utils.PluginMeta;
import com.odoo.mobile.core.coupler.utils.PluginMethodMeta;
import com.odoo.mobile.core.coupler.utils.WebPluginUtils;
import com.odoo.mobile.core.widgets.OdooWebView;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebJSCoupler {
    public static final String ALIAS = "OdooDeviceUtility";
    private WebPluginUtils pluginUtils;

    public WebJSCoupler(OdooWebView webView) throws IOException {
        this.pluginUtils = new WebPluginUtils(webView);
    }

    @JavascriptInterface
    public void execute(String action, String args, String callbackID) {
        Log.v(ALIAS, "executing=> " + action + "; Callback ID: " + callbackID);
        try {
            String[] actions = action.split("\\.");
            if (actions.length >= 2) {
                this.pluginUtils.exec(actions[0], actions[1], new JSONObject(args), callbackID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public String list_plugins() {
        JSONArray pluginList = new JSONArray();
        HashMap<String, PluginMeta> plugins = this.pluginUtils.getPlugins();
        for (String key : plugins.keySet()) {
            PluginMeta meta = (PluginMeta) plugins.get(key);
            try {
                HashMap<String, PluginMethodMeta> methods = meta.methods;
                for (String methodKey : methods.keySet()) {
                    JSONObject data = new JSONObject();
                    data.put("action", meta.alias + "." + ((PluginMethodMeta) methods.get(methodKey)).methodName);
                    data.put("name", ((PluginMethodMeta) methods.get(methodKey)).methodName);
                    pluginList.put(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pluginList.toString();
    }
}
