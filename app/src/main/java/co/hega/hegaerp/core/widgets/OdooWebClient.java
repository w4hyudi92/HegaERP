package co.hega.hegaerp.core.widgets;

import android.webkit.WebViewClient;

public class OdooWebClient extends WebViewClient {
    private OdooWebView odooWebView;

    public OdooWebClient(OdooWebView webView) {
        this.odooWebView = webView;
    }
}
