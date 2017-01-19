package co.hega.hegaerp.core.coupler.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.odoo.mobile.R;
import com.odoo.mobile.core.OdooAppCompactActivity;
import com.odoo.mobile.core.coupler.WebPlugin;
import com.odoo.mobile.core.coupler.helper.WebPluginArgs;
import com.odoo.mobile.core.coupler.helper.WebPluginCallback;
import com.odoo.mobile.core.permissions.OPermissionManager;
import com.odoo.mobile.core.permissions.PermissionStatusListener;
import com.odoo.mobile.core.service.OJSONUtils;
import com.odoo.mobile.core.widgets.OdooWebView;
import dalvik.system.DexFile;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class WebPluginUtils {
    public static final String TAG = WebPluginUtils.class.getSimpleName();
    private Context mContext;
    private OdooWebView odooWebView;
    private HashMap<String, PluginMeta> plugins = new HashMap();

    public WebPluginUtils(OdooWebView view) throws IOException {
        this.odooWebView = view;
        this.mContext = view.getContext();
        Enumeration<String> item = new DexFile(this.mContext.getPackageCodePath()).entries();
        while (item.hasMoreElements()) {
            String element = (String) item.nextElement();
            if (element.startsWith("com.odoo.mobile.plugins")) {
                try {
                    Class<?> cls = Class.forName(element);
                    if (cls.getSuperclass() == WebPlugin.class) {
                        WebPlugin plugin = (WebPlugin) cls.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{this.mContext});
                        PluginMeta meta = new PluginMeta();
                        meta.alias = plugin.aliasName();
                        meta.classObj = cls;
                        meta.methods = plugin.getMethods();
                        this.plugins.put(meta.alias, meta);
                        Log.i(TAG, "Plugin registered :" + meta.alias);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HashMap<String, PluginMeta> getPlugins() {
        return this.plugins;
    }

    public void exec(String pluginAlias, String method, JSONObject params, String callbackID) {
        Log.v(TAG, "exec=> " + pluginAlias + ":" + method + "()");
        PluginMeta meta = (PluginMeta) this.plugins.get(pluginAlias);
        if (meta != null) {
            try {
                final WebPlugin plugin = (WebPlugin) meta.classObj.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{this.mContext});
                plugin.setWebView(this.odooWebView);
                final PluginMethodMeta methodMeta = (PluginMethodMeta) meta.methods.get(method);
                if (methodMeta != null) {
                    final Method pluginMethod = methodMeta.method;
                    final WebPluginArgs arguments = OJSONUtils.toWebPluginArgs(params);
                    final WebPluginCallback pluginCallback = callbackID != null ? new WebPluginCallback(this.odooWebView, callbackID) : null;
                    if (plugin.getPermissions().size() > 0) {
                        Log.v(TAG, "Checking for permission required for plugin " + pluginAlias);
                        final String[] reqPermissions = (String[]) plugin.getPermissions().toArray(new String[plugin.getPermissions().size()]);
                        final OPermissionManager permissionManager = new OPermissionManager((OdooAppCompactActivity) this.odooWebView.getActivity());
                        final String failReason = "Permission required : " + TextUtils.join(",", reqPermissions);
                        if (!permissionManager.hasPermissions(reqPermissions)) {
                            permissionManager.getPermissions(new PermissionStatusListener() {
                                public void requestRationale(String permission) {
                                    permissionManager.showRequestRationale(permission);
                                    if (pluginCallback != null) {
                                        pluginCallback.permissionFail(WebPluginUtils.this.failResponse(failReason));
                                    }
                                }

                                public void granted(String[] permissions) {
                                    if (permissions.length == reqPermissions.length) {
                                        WebPluginUtils.this.invokeMethod(plugin, methodMeta.method_type, pluginMethod, arguments, pluginCallback);
                                    }
                                }

                                public void denied(String[] permissions) {
                                    if (pluginCallback != null && permissions.length > 0) {
                                        pluginCallback.permissionFail(WebPluginUtils.this.failResponse(failReason));
                                    }
                                }
                            }, reqPermissions);
                            if (pluginCallback != null) {
                                pluginCallback.permissionFail(failResponse(failReason));
                            }
                        }
                        invokeMethod(plugin, methodMeta.method_type, pluginMethod, arguments, pluginCallback);
                        return;
                    }
                    invokeMethod(plugin, methodMeta.method_type, pluginMethod, arguments, pluginCallback);
                    return;
                }
                Log.w(TAG, "No method found " + method + " for plugin " + pluginAlias);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Log.w(TAG, "No plugin found for " + pluginAlias);
    }

    private JSONObject failResponse(String reason) {
        try {
            return new JSONObject().put("success", false).put("data", new JSONObject().put("error", reason));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void invokeMethod(WebPlugin plugin, int method_type, Method method, WebPluginArgs args, WebPluginCallback callback) {
        switch (method_type) {
            case R.styleable.View_android_focusable /*1*/:
                if (callback != null) {
                    try {
                        method.invoke(plugin, new Object[]{args, callback});
                        return;
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        return;
                    }
                }
                Log.w(TAG, method + " trigger as asynchronous with null callback id");
                return;
            case R.styleable.View_paddingStart /*2*/:
                method.invoke(plugin, new Object[]{args});
                callback.success(Boolean.valueOf(true));
                return;
            case R.styleable.View_paddingEnd /*3*/:
                callback.success(method.invoke(plugin, new Object[]{args}) + "");
                return;
            default:
                return;
        }
    }
}
