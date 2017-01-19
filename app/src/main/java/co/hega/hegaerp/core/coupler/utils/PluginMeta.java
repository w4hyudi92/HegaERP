package co.hega.hegaerp.core.coupler.utils;

import java.util.HashMap;

public class PluginMeta {
    public String alias;
    public Class<?> classObj;
    public HashMap<String, PluginMethodMeta> methods = new HashMap();
}
