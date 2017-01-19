package co.hega.hegaerp.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitmapUtils {
    public static Bitmap getBitmapImage(String base64) {
        byte[] imageAsBytes = Base64.decode(base64.getBytes(), 5);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
