package co.hega.hegaerp.core.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OControlUtils {
    public static boolean isVisible(View view) {
        return view.getVisibility() == View.GONE;
    }

    public static void setText(View view, String text) {
        ((TextView) view).setText(text);
    }

    public static void setImage(View view, String base64) {
        ImageView imageView = (ImageView) view;
        if (base64 != null && !base64.equals("false")) {
            imageView.setImageBitmap(BitmapUtils.getBitmapImage(base64));
        }
    }

    public static void setImage(View view, int res_id) {
        ((ImageView) view).setImageResource(res_id);
    }
}
