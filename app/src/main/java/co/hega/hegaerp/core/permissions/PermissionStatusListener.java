package co.hega.hegaerp.core.permissions;

public interface PermissionStatusListener {
    void denied(String[] strArr);

    void granted(String[] strArr);

    void requestRationale(String str);
}
