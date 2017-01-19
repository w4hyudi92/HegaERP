package co.hega.hegaerp.core.service;

import com.odoo.mobile.core.coupler.helper.WebPluginArgs;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OJSONUtils {
    public static <T> ArrayList<T> jsonArrayToList(JSONArray array) {
        ArrayList<T> items = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            try {
                items.add(array.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public static WebPluginArgs toWebPluginArgs(JSONObject params) {
        WebPluginArgs args = new WebPluginArgs();
        Iterator<String> keys = params.keys();
        while (keys.hasNext()) {
            try {
                String key = (String) keys.next();
                Object val = params.get(key);
                if (val instanceof JSONObject) {
                    args.put(key, toWebPluginArgs((JSONObject) val));
                } else if (val instanceof JSONArray) {
                    args.put(key, jsonArrayToList((JSONArray) val));
                } else {
                    args.put(key, val);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return args;
    }
}
