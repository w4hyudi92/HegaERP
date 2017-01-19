package co.hega.hegaerp.core.permissions;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import com.odoo.mobile.core.OdooAppCompactActivity;
import com.odoo.mobile.core.OdooAppCompactActivity.PermissionResultListener;
import java.util.ArrayList;
import java.util.List;

public class OPermissionManager implements PermissionResultListener {
    private OdooAppCompactActivity mActivity;
    private PermissionStatusListener recentPermissionRequest;

    public OPermissionManager(OdooAppCompactActivity activity) {
        this.mActivity = activity;
        this.mActivity.setPermissionResultListener(this);
    }

    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.mActivity, permission) != 0) {
                return false;
            }
        }
        return true;
    }

    public void getPermissions(PermissionStatusListener callback, String... permissions) {
        this.recentPermissionRequest = callback;
        int length = permissions.length;
        int i = 0;
        while (i < length) {
            String permission = permissions[i];
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this.mActivity, permission)) {
                i++;
            } else if (callback != null) {
                callback.requestRationale(permission);
                return;
            } else {
                return;
            }
        }
        ActivityCompat.requestPermissions(this.mActivity, permissions, 123);
    }

    public void onResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123 && this.recentPermissionRequest != null && grantResults.length > 0) {
            List<String> granted = new ArrayList();
            List<String> denied = new ArrayList();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == 0) {
                    granted.add(permissions[i]);
                } else {
                    denied.add(permissions[i]);
                }
            }
            this.recentPermissionRequest.granted((String[]) granted.toArray(new String[granted.size()]));
            this.recentPermissionRequest.denied((String[]) denied.toArray(new String[denied.size()]));
        }
    }

    public void showRequestRationale(String permission) {
        String description = this.mActivity.getString(2131165284);
        String message = permissionReadable(permission);
        Builder mRationalDialog = new Builder(this.mActivity);
        mRationalDialog.setCancelable(false);
        mRationalDialog.setTitle(2131165289);
        mRationalDialog.setMessage(String.format(description, new Object[]{message}));
        mRationalDialog.setPositiveButton(2131165288, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OPermissionManager.this.showAppSettingInfo();
                dialog.dismiss();
            }
        });
        mRationalDialog.setNegativeButton(17039360, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mRationalDialog.show();
    }

    private void showAppSettingInfo() {
        String appPackage = this.mActivity.getApplicationInfo().packageName;
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + appPackage));
        this.mActivity.startActivity(intent);
    }

    private String permissionReadable(String permission) {
        String[] permissionParts = permission.split("\\.");
        try {
            return this.mActivity.getPackageManager().getPermissionInfo(permission, 128).loadLabel(this.mActivity.getPackageManager()).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return permissionParts[permissionParts.length - 1].replaceAll("_", " ").toLowerCase();
        }
    }
}
