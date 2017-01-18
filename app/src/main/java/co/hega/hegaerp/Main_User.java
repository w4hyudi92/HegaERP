package co.hega.hegaerp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;

import co.hega.hegaerp.ui.Login;
import co.hega.hegaerp.ui.Main;
import co.hega.hegaerp.ui.Staff;

public class Main_User extends AppCompatActivity {

    private WebView viewWeb;

    ProgressDialog prgsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__user);

        Intent intent = getIntent();
        String et_cmpny = intent.getStringExtra("company");
        String et_usr = intent.getStringExtra("user");
        String et_passwd = intent.getStringExtra("pass");

        viewWeb = (WebView) this.findViewById(R.id.webview2);
        viewWeb.getSettings().setJavaScriptEnabled(true);
        viewWeb.getSettings().setSupportZoom(false);
        viewWeb.getSettings().setBuiltInZoomControls(false);
        viewWeb.getSettings().setLoadsImagesAutomatically(true);
        viewWeb.getSettings().setLoadWithOverviewMode(true);
        viewWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        viewWeb.setWebViewClient(new Main_User.MyBrowse_user());
        viewWeb.clearCache(true);
        WebViewDatabase.getInstance(this).clearHttpAuthUsernamePassword();
        //view.loadUrl(URL + username + URLPass + "YGpZWgNpaIFtGmlAmy86kiB535ItPNzr");
        viewWeb.loadUrl("http://" + et_cmpny + ".hega.pro/web/login?login=" + et_usr +"&password=" + et_passwd);
    }

    private class MyBrowse_user extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            view.loadUrl(url);

            if (uri.getPath().contains("/web/login") || uri.getPath().startsWith("/web/session/logout")) {
                Intent intent = new Intent(Main_User.this, Staff.class);
                startActivity(intent);
                //finish();
                return true;
            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prgsDialog = new ProgressDialog(Main_User.this);
            prgsDialog.setCancelable(false);
            prgsDialog.setMessage("Loading, Please wait ...");
            prgsDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (prgsDialog != null) {
                prgsDialog.dismiss();
            }
        }


    }

    @Override
    public void onBackPressed() {
        if (viewWeb.canGoBack())
            viewWeb.goBack();
        else
            super.onBackPressed();
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Main.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/

    }
}
