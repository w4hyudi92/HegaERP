package co.hega.hegaerp.core.utils;

import android.content.Intent;

public interface ActivityResultIntentListener {
    void openActivityForResult(Intent intent, int i);

    void setActivityResultListener(ActivityResultListener activityResultListener);
}
