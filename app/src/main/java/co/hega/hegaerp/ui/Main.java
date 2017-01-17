package co.hega.hegaerp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;

import co.hega.hegaerp.R;

public class Main extends AppCompatActivity {

    private WebView view;
    private String URL = "http://hega.pro/web/signin1234?login=";
    private String URLPass = "&password=";

    ProgressDialog prDialog;

    //private  UserSession userSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        String username = intent.getStringExtra("usr");
        //String password = intent.getStringExtra("psw");

        view = (WebView) this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setSupportZoom(false);
        view.getSettings().setBuiltInZoomControls(false);
        view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        view.setWebViewClient(new MyBrowser());
        view.clearCache(true);
        WebViewDatabase.getInstance(this).clearHttpAuthUsernamePassword();
        //view.loadUrl(URL + username + URLPass + "YGpZWgNpaIFtGmlAmy86kiB535ItPNzr");
        view.loadUrl("http://hega.pro/web/signin1234?login=admin&password=YGpZWgNpaIFtGmlAmy86kiB535ItPNzr");
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            view.loadUrl(url);

            if(uri.getPath().startsWith("/web/session/logout")){
                //Main.this.userSession.removeSession();
                Intent logout = new Intent(Main.this, Login.class);
                startActivity(logout);
                finish();
                return true;
            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prDialog = new ProgressDialog(Main.this);
            prDialog.setCancelable(false);
            prDialog.setMessage("Loading, Please wait ...");
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(prDialog!=null){
                prDialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        alert.show();

    }
}
