package co.hega.hegaerp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog.Builder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import co.hega.hegaerp.Main_User;
import co.hega.hegaerp.R;
import co.hega.hegaerp.accounts.OdooAccountManager;
import co.hega.hegaerp.accounts.OdooUser;
import co.hega.hegaerp.core.OdooAppCompactActivity;
import co.hega.hegaerp.core.service.OdooIntentService;
import co.hega.hegaerp.core.utils.OControlUtils;
import co.hega.hegaerp.core.utils.UserSession;

import java.util.List;
import java.util.regex.Pattern;

public class User_Login extends OdooAppCompactActivity implements OnClickListener, OnKeyListener, OnTouchListener {
    private EditText edtLogin;
    private EditText edtPassword;
    private EditText edtServerUrl;
    private BroadcastReceiver loginResponseReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            String action = data.getString("service_action");
            if (data.containsKey("error_type")) {
                User_Login.this.handleError(action, data);
            }
            if (data.containsKey("service_response")) {
                boolean z = true;
                switch (action.hashCode()) {
                    case -1875150091:
                        if (action.equals("authenticate_user")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1449490702:
                        if (action.equals("odoo_version")) {
                            z = false;
                            break;
                        }
                        break;
                    case 1452768191:
                        if (action.equals("db_list")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case R.styleable.View_android_theme /*0*/:
                        if (Boolean.valueOf(data.getBoolean("service_response")).booleanValue()) {
                            Bundle extra = new Bundle();
                            extra.putString("host", User_Login.this.getHost());
                            User_Login.this.requestRPCService("db_list", extra);
                            return;
                        }
                        User_Login.this.toggleProgressBarView(false);
                        //Snackbar.make(User_Login.this.findViewById(16908290), 2131165315, 0).show();
                        return;
                    case R.styleable.View_android_focusable /*1*/:
                        List<String> databases = data.getStringArrayList("service_response");
                        if (databases.size() > 1) {
                            User_Login.this.showDatabaseDialog(databases);
                            return;
                        } else if (databases.size() == 1) {
                            User_Login.this.login((String) databases.get(0));
                            return;
                        } else {
                            Uri uri = Uri.parse(User_Login.this.getHost());
                            if (uri.getHost().equals("www.odoo.com")) {
                                User_Login.this.login("openerp");
                                return;
                            } else if (uri.getHost().endsWith(".odoo.com")) {
                                User_Login.this.login(uri.getHost().replace(".odoo.com", ""));
                                return;
                            } else {
                                User_Login.this.askUserForDatabase();
                                return;
                            }
                        }
                    case R.styleable.View_paddingStart /*2*/:
                        User_Login.this.createAccount((OdooUser) data.getParcelable("service_response"));
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private OdooAccountManager odooAccountManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);
        this.odooAccountManager = new OdooAccountManager(this);
        this.edtLogin = (EditText) findViewById(R.id.edtUsername);
        this.edtPassword = (EditText) findViewById(R.id.edtPassword);
        this.edtServerUrl = (EditText) findViewById(R.id.edtServerURL);
        findViewById(R.id.edtUsername).setOnClickListener(this);
        findViewById(R.id.edtPassword).setOnClickListener(this);
        findViewById(R.id.edtUsername).setOnTouchListener(this);
        this.edtPassword.setOnKeyListener(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.loginResponseReceiver, new IntentFilter(OdooIntentService.TAG));
    }

    protected void onStart() {
        super.onStart();
        boolean forceNewLogin = getIntent().getExtras() != null && getIntent().getExtras().containsKey("force_new_login");
        if (!forceNewLogin) {
            OdooUser odooUser = this.odooAccountManager.getActiveAccount();
            if (odooUser != null) {
                toggleProgressBarView(true);
                if (odooUser.getSession(this).isValid()) {
                    Intent intent = new Intent(this, Main_User.class);
                    Bundle data = getIntent().getExtras();
                    if (data != null && data.containsKey("link")) {
                        intent.putExtra("link", data.getString("link"));
                    }
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131558528:
                login();
                return;
            default:
                return;
        }
    }

    private void login() {
        if (OControlUtils.isVisible((View) this.edtServerUrl.getParent()) && this.edtServerUrl.getText().toString().trim().isEmpty()) {
            this.edtServerUrl.setError("Masukan Url");
            this.edtServerUrl.requestFocus();
        } else if (this.edtLogin.getText().toString().trim().isEmpty()) {
            this.edtLogin.setError("Masukan Username");
            this.edtLogin.requestFocus();
        } else if (this.edtPassword.getText().toString().trim().isEmpty()) {
            this.edtPassword.setError("Masukan Password");
            this.edtPassword.requestFocus();
        } else {
            toggleProgressBarView(true);
            Bundle data = new Bundle();
            data.putString("host", getHost());
            requestRPCService("odoo_version", data);
        }
    }

    private boolean isRunbotURL(String host) {
        return Pattern.compile(".runbot.{1,2}\\.odoo\\.com").matcher(host).find();
    }

    private String getHost() {
        String url = this.edtServerUrl.getText().toString().trim();
        boolean isRunbotURL = isRunbotURL(url);
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        if (isRunbotURL) {
            return url;
        }
        Uri uri = Uri.parse(url);
        if (uri.getHost().equals("odoo.com")) {
            return "https://www." + uri.getHost();
        }
        if (uri.getHost().endsWith(".odoo.com")) {
            return "https://" + uri.getHost();
        }
        return url;
    }

    private void toggleProgressBarView(boolean display) {
        int i;
        int i2 = 0;
        View findViewById = findViewById(R.id.progressLayout);
        if (display) {
            i = 8;
        } else {
            i = 0;
        }
        findViewById.setVisibility(View.GONE);
        View findViewById2 = findViewById(R.id.progressLayout);
        if (!display) {
            i2 = 8;
        }
        findViewById2.setVisibility(View.GONE);
        ((ProgressBar) findViewById(R.id.progressBarLogin)).getIndeterminateDrawable().setColorFilter(-1, Mode.SRC_IN);
    }

    private void startWebViewActivity(OdooUser user) {
        //user.getSession(this).registerSession(user.session_id);
        Intent intent = new Intent(this, Main_User.class);
        intent.setFlags(268468224);
        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey("link")) {
            intent.putExtra("link", data.getString("link"));
        }
        startActivity(intent);
        overridePendingTransition(2131034129, 2131034130);
        finish();
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.loginResponseReceiver);
        super.onDestroy();
    }

    private void requestRPCService(String action, Bundle extra) {
        Bundle data = extra != null ? extra : new Bundle();
        data.putString("service_action", action);
        Intent intent = new Intent(this, OdooIntentService.class);
        intent.putExtras(data);
        startService(intent);
    }

    private void login(String database) {
        String host = getHost();
        String username = this.edtLogin.getText().toString();
        String password = this.edtPassword.getText().toString();
        if (this.odooAccountManager.hasAccount(OdooUser.createAccountName(username, host, database))) {
            toggleProgressBarView(false);
            return;
        }
        Bundle data = new Bundle();
        data.putString("host", host);
        data.putString("username", username);
        data.putString("password", password);
        data.putString("database", database);
        requestRPCService("authenticate_user", data);
    }

    private void handleError(String action, Bundle data) {
        toggleProgressBarView(false);
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
                case 267114723:
                    if (string.equals("certificate_not_trusted")) {
                        z = true;
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
            /*switch (z) {
                case R.styleable.View_android_theme /*0:
                    errorMessage = getString(2131165247);
                    break;
                case R.styleable.View_android_focusable /*1:
                    errorMessage = getString(2131165244);
                    break;
                case R.styleable.View_paddingStart /*2:
                    errorMessage = getString(2131165266);
                    break;
                case R.styleable.View_paddingEnd /*3:
                    errorMessage = getString(2131165293);
                    break;
                case R.styleable.View_theme /*4:
                    errorMessage = data.getString("error_message");
                    break;
                case R.id. :
                    errorMessage = data.getString("error_message");
                    break;
            }*/
        }
        /*if (errorMessage != null) {
        }*/
    }

    private void createAccount(OdooUser user) {
        if (this.odooAccountManager.createAccount(user)) {
            this.odooAccountManager.makeActive(user);
            startWebViewActivity(user);
            return;
        }
        toggleProgressBarView(false);
    }

    private void askUserForDatabase() {
        Builder builder = new Builder(this);
        builder.setTitle("Database");
        View view = LayoutInflater.from(this).inflate(co.hega.hegaerp.R.layout.snackbar_layout, null, false);
        //final EditText edtInput = (EditText) view.findViewById(16908297);
        //edtInput.setHint("Masih Kosong");
        builder.setView(view);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(User_Login.this, "Berhasil", Toast.LENGTH_LONG).show();
                    User_Login.this.toggleProgressBarView(false);
                    return;
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                User_Login.this.toggleProgressBarView(false);
            }
        });
        builder.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                User_Login.this.toggleProgressBarView(false);
            }
        });
        builder.show();
    }

    private void showDatabaseDialog(final List<String> databases) {
        Builder builder = new Builder(this);
        builder.setTitle("Pilih database");
        builder.setSingleChoiceItems((CharSequence[]) databases.toArray(new String[databases.size()]), -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                User_Login.this.login((String) databases.get(which));
                dialog.dismiss();
            }
        });
        builder.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                User_Login.this.toggleProgressBarView(false);
            }
        });
        builder.show();
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 66) {
            login();
            //((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (1 == event.getAction()) {
            this.edtPassword.setInputType(129);
        } else {
            this.edtPassword.setInputType(145);
        }
        return true;
    }
}
