package co.hega.hegaerp.core.coupler;

import android.content.Context;
import android.util.Log;
import com.odoo.mobile.accounts.OdooAccountManager;
import com.odoo.mobile.accounts.OdooUser;
import com.odoo.mobile.core.coupler.helper.WebPluginArgs;
import com.odoo.mobile.core.coupler.utils.PluginMethodMeta;
import com.odoo.mobile.core.widgets.OdooWebView;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class WebPlugin {
    public static final String TAG = WebPlugin.class.getCanonicalName();
    private String aliasName;
    private Context mContext;
    private List<String> permissions = new ArrayList();
    private OdooWebView webView;

    public WebPlugin(Context context, String alias) {
        this.mContext = context;
        this.aliasName = alias;
    }

    protected void requirePermissions(String... permissions) {
        if (permissions.length > 0) {
            this.permissions.addAll(Arrays.asList(permissions));
        }
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public String aliasName() {
        return this.aliasName;
    }

    public Context getContext() {
        return this.mContext;
    }

    public OdooWebView getWebView() {
        return this.webView;
    }

    public OdooUser getUser() {
        return new OdooAccountManager(this.mContext).getActiveAccount();
    }

    public void setWebView(OdooWebView webView) {
        this.webView = webView;
    }

    public HashMap<String, PluginMethodMeta> getMethods() {
        Log.d(TAG, "Getting method for plugin " + aliasName());
        HashMap<String, PluginMethodMeta> methods = new HashMap();
        for (Method method : getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (Modifier.isPublic(method.getModifiers())) {
                Class<?>[] paramTypes = method.getParameterTypes();
                int paramLength = paramTypes.length;
                if (paramLength > 0 && paramTypes[0].isAssignableFrom(WebPluginArgs.class)) {
                    Log.d(TAG, "Registering method : " + method.getName());
                    PluginMethodMeta methodMeta = new PluginMethodMeta();
                    methodMeta.methodName = method.getName();
                    methodMeta.method = method;
                    int i = paramLength > 1 ? 1 : method.getReturnType().equals(Void.TYPE) ? 2 : 3;
                    methodMeta.method_type = i;
                    methods.put(method.getName(), methodMeta);
                }
            }
        }
        return methods;
    }
}
