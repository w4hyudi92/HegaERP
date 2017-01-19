package co.hega.hegaerp.core.widgets;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import com.odoo.mobile.core.OdooAppCompactActivity;
import com.odoo.mobile.core.coupler.WebJSCoupler;
import com.odoo.mobile.core.permissions.OPermissionManager;
import com.odoo.mobile.core.permissions.PermissionStatusListener;
import java.io.IOException;

public class OdooWebView extends WebView implements DownloadListener {
    public static final String TAG = OdooWebView.class.getCanonicalName();
    private AppCompatActivity appCompatActivity;
    private Context mContext;

    public OdooWebView(Context context) {
        super(context);
        init(context);
    }

    public OdooWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OdooWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public OdooWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            this.mContext = context;
            setDefaultSettings();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setDefaultSettings() {
        int i;
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(-1);
        settings.setAppCachePath(this.mContext.getCacheDir().getAbsolutePath());
        if (VERSION.SDK_INT >= 19) {
            i = 2;
        } else {
            i = 1;
        }
        setLayerType(i, null);
        try {
            addJavascriptInterface(new WebJSCoupler(this), WebJSCoupler.ALIAS);
            addJavascriptInterface(Boolean.valueOf(false), "Notification");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDownloadListener(this);
        if (VERSION.SDK_INT >= 19) {
            ApplicationInfo applicationInfo = getContext().getApplicationInfo();
            int i2 = applicationInfo.flags & 2;
            applicationInfo.flags = i2;
            if (i2 != 0) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }

    public void setActivity(AppCompatActivity activity) {
        this.appCompatActivity = activity;
    }

    public AppCompatActivity getActivity() {
        return this.appCompatActivity;
    }

    public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Log.i(TAG, "onDownloadStart() " + url);
        final String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
        final OPermissionManager permissionMgr = new OPermissionManager((OdooAppCompactActivity) getActivity());
        if (permissionMgr.hasPermissions("android.permission.WRITE_EXTERNAL_STORAGE")) {
            downloadFile(url, fileName);
            return;
        }
        permissionMgr.getPermissions(new PermissionStatusListener() {
            public void requestRationale(String permission) {
                permissionMgr.showRequestRationale(permission);
            }

            public void granted(String[] permissions) {
                if (permissions.length > 0) {
                    OdooWebView.this.downloadFile(url, fileName);
                }
            }

            public void denied(String[] permissions) {
                if (permissions.length > 0) {
                    Toast.makeText(OdooWebView.this.mContext, 2131165314, 0).show();
                }
            }
        }, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public void downloadFile(String file_url, String fileName) {
        if (fileName.contains(";")) {
            fileName = fileName.replace(";", "");
        }
        Request request = new Request(Uri.parse(file_url));
        request.addRequestHeader("Connection", "keep-alive");
        request.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addRequestHeader("Cookie", CookieManager.getInstance().getCookie(file_url));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        ((DownloadManager) getContext().getSystemService("download")).enqueue(request);
        Toast.makeText(this.mContext, 2131165308, 0).show();
    }
}
