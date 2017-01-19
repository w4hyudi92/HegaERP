package co.hega.hegaerp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.hega.hegaerp.Activity.HegaAppCompactActivity;
import co.hega.hegaerp.Main_User;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import co.hega.hegaerp.utils.OdooAccountManager;
import co.hega.hegaerp.utils.OdooUser;
import co.hega.hegaerp.utils.OdooIntentService;

import android.os.Build.VERSION;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import com.odoo.mobile.accounts.OdooAccountManager;
import com.odoo.mobile.accounts.OdooUser;
import com.odoo.mobile.core.service.OdooIntentService;
import com.odoo.mobile.core.widgets.OdooWebChromeClient;
import com.odoo.mobile.core.widgets.OdooWebClient;
import com.odoo.mobile.core.widgets.OdooWebView;

public class Staff extends AppCompatActivity {

    private TextView t, t1, t2;
    private Button button1;
    private EditText cmpny, emUser, psUser;
    WebView webview_staff;
    String et_cmpny, et_user, e_passwd;

    Typeface textfont;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private BroadcastReceiver userSessionReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            String action = data.getString("service_action");
            if (data.containsKey("error_type")) {
                Staff.this.handleError(action, data);
            }
            if (data.containsKey("service_response")) {
                Object obj = -1;
                switch (action.hashCode()) {
                    case 644143781:
                        if (action.equals("user_from_session_id")) {
                            obj = null;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case R.styleable.View_android_theme /*0*/:
                        Staff.this.createAccount((OdooUser) data.getParcelable("service_response"));
                        return;
                    default:
                        return;
                }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        toggleProgress(true);
        webview_staff = (OdooWebView) findViewById(2131558520);
        webview_staff.setActivity(this);
        webview_staff.setWebViewClient(new OdooWebClient(webView) {
            public void onPageFinished(WebView view, String url) {
                CreateAccount.this.toggleProgress(false);
            }

            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return VERSION.SDK_INT >= 21 && shouldOverrideURL(view, request.getUrl().toString());
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideURL(view, url);
            }

            private boolean shouldOverrideURL(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getHost().endsWith(".odoo.com") && uri.getPath().startsWith("/web")) {
                    for (String cookie : CookieManager.getInstance().getCookie(url).split(";")) {
                        String[] cookiePart = cookie.split("=");
                        if (cookiePart.length == 2 && cookiePart[0].trim().equals("session_id")) {
                            CreateAccount.this.createUserAccount(uri.getScheme() + "://" + uri.getHost(), cookiePart[1].trim());
                        }
                    }
                }
                return false;
            }
        });
        webView.setWebChromeClient(new OdooWebChromeClient(webView) {
            public void onProgressChanged(WebView view, int newProgress) {
                if (CreateAccount.this.progressBar != null) {
                    CreateAccount.this.progressBar.setProgress(newProgress);
                }
            }
        });
        webView.loadUrl("https://www.odoo.com/trial?form_light=true&selected_app=base");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.userSessionReceiver, new IntentFilter(OdooIntentService.TAG));
    }

    private void toggleProgress(boolean show) {
        findViewById(2131558521).setVisibility(show ? 0 : 8);
    }

    private void toggleCreateAccountProgress(boolean show) {
        findViewById(2131558558).setVisibility(show ? 0 : 8);
    }

    private void createUserAccount(String host, String session_id) {
        Bundle data = new Bundle();
        data.putString("host", host);
        data.putString("session_id", session_id);
        data.putString("service_action", "user_from_session_id");
        Intent intent = new Intent(this, OdooIntentService.class);
        intent.putExtras(data);
        startService(intent);
    }

    private void handleError(String action, Bundle data) {
        toggleCreateAccountProgress(false);
        String errorMessage = null;
        if (action.equals("error_response")) {
            String string = data.getString("error_type");
            boolean z = true;
            switch (string.hashCode()) {
                case -2077031716:
                    if (string.equals("time_out")) {
                        z = true;
                        break;
                    }
                    break;
                case -1397380782:
                    if (string.equals("database_not_found")) {
                        z = true;
                        break;
                    }
                    break;
                case -592873500:
                    if (string.equals("authentication_failed")) {
                        z = true;
                        break;
                    }
                    break;
                case -548362035:
                    if (string.equals("no_network_connection")) {
                        z = false;
                        break;
                    }
                    break;
                case 1784121061:
                    if (string.equals("server_not_reachable")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case R.styleable.View_android_theme /*0*/:
                    errorMessage = getString(2131165247);
                    break;
                case R.styleable.View_android_focusable /*1*/:
                    errorMessage = getString(2131165244);
                    break;
                case R.styleable.View_paddingStart /*2*/:
                    errorMessage = getString(2131165266);
                    break;
                case R.styleable.View_paddingEnd /*3*/:
                    errorMessage = getString(2131165293);
                    break;
                case R.styleable.View_theme /*4*/:
                    errorMessage = data.getString("error_message");
                    break;
            }
        }
        if (errorMessage != null) {
            Snackbar.make(findViewById(16908290), errorMessage, 0).show();
        }
    }

    private void createAccount(OdooUser user) {
        OdooAccountManager odooAccountManager = new OdooAccountManager(this);
        if (odooAccountManager.createAccount(user)) {
            odooAccountManager.makeActive(user);
            startWebViewActivity(user);
            return;
        }
        Toast.makeText(this, 2131165313, 0).show();
        toggleCreateAccountProgress(false);
    }

    private void startWebViewActivity(OdooUser user) {
        user.getSession(this).registerSession(user.session_id);
        Intent intent = new Intent(this, Main_User.class);
        intent.setFlags(268468224);
        startActivity(intent);
        overridePendingTransition(2131034129, 2131034130);
        finish();
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.userSessionReceiver);
        super.onDestroy();
    }
}
