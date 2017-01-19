package co.hega.hegaerp.core.coupler.utils;

import java.lang.reflect.Method;

public class PluginMethodMeta {
    public Method method;
    public String methodName;
    public int method_type = 2;

    public String toString() {
        return "PluginMethodMeta{methodName='" + this.methodName + '\'' + ", method=" + this.method + ", method_type=" + this.method_type + '}';
    }
}
