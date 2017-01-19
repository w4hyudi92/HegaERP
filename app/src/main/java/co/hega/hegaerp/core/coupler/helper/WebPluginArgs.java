package co.hega.hegaerp.core.coupler.helper;

import java.util.HashMap;

public class WebPluginArgs extends HashMap<String, Object> {
    public String getString(String key) {
        if (isExists(key)) {
            return get(key).toString();
        }
        return null;
    }

    public Integer getInt(String key) {
        if (isExists(key)) {
            return Integer.valueOf(Integer.parseInt(get(key).toString()));
        }
        return null;
    }

    public WebPluginArgs getMap(String key) {
        if (isExists(key)) {
            return (WebPluginArgs) get(key);
        }
        return null;
    }

    public boolean isExists(String key) {
        return containsKey(key) && get(key) != null;
    }
}
