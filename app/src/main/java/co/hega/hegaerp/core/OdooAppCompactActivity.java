package co.hega.hegaerp.core;

import android.support.v7.app.AppCompatActivity;

public class OdooAppCompactActivity extends AppCompatActivity {
    private PermissionResultListener permissionResultListener;

    public interface PermissionResultListener {
        void onResult(int i, String[] strArr, int[] iArr);
    }

    public void setPermissionResultListener(PermissionResultListener callback) {
        this.permissionResultListener = callback;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.permissionResultListener != null) {
            this.permissionResultListener.onResult(requestCode, permissions, grantResults);
        }
    }
}
