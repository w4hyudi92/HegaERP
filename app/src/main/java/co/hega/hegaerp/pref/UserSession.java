/*package co.hega.hegaerp.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;


public class UserSession {

    private HegaUser hegaUser;
    private SharedPreferences preferences;

    public UserSession(Context context, OdooUser user) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.odooUser = user;
    }

    public void removeSession() {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove(key("host_name"));
        editor.remove(key("session_id"));
        editor.apply();
    }

    private String key(String key) {
        return key + "_" + this.odooUser.getAccountName();
    }

    public void registerSession(String session_id) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(key("host_name"), this.odooUser.host);
        editor.putString(key("session_id"), session_id);
        editor.apply();
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.preferences.getString(key("session_id"), null));
    }

    public String getSessionId() {
        return this.preferences.getString(key("session_id"), "");
    }

    public void injectSession() {
        Log.d("Host:", this.HegaUser.host);
        Log.d("User: ", this.HegaUser.name);
        Log.d("DB: ", this.HegaUser.database);
        Log.d("Injecting session:", getSessionId() + " to host :" + this.odooUser.host);
        CookieManager.getInstance().setCookie(this.odooUser.host, "session_id=" + getSessionId());
    }
}*/
