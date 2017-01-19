package co.hega.hegaerp.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.odoo.mobile.accounts.OdooUser;
import com.odoo.mobile.core.service.exceptions.OdooServiceException;
import java.io.ByteArrayOutputStream;
import java.security.cert.CertPathValidatorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import javax.net.ssl.SSLHandshakeException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OdooIntentService extends IntentService {
    public static final Integer REQUEST_TIMEOUT_MS = Integer.valueOf(5000);
    public static final String TAG = OdooIntentService.class.getCanonicalName();
    private LocalBroadcastManager broadcastManager;
    private DefaultRetryPolicy defaultRetryPolicy;
    private RequestQueue requestQueue;
    private String session_id;

    public OdooIntentService() {
        super(TAG);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onHandleIntent(Intent r15) {
        /*
        r14 = this;
        r10 = 0;
        r11 = android.support.v4.content.LocalBroadcastManager.getInstance(r14);
        r14.broadcastManager = r11;
        r11 = com.odoo.mobile.core.service.RequestQueueSingleton.getRequestQueue(r14);
        r14.requestQueue = r11;
        r11 = new com.android.volley.DefaultRetryPolicy;
        r12 = REQUEST_TIMEOUT_MS;
        r12 = r12.intValue();
        r13 = 0;
        r11.<init>(r12, r10, r13);
        r14.defaultRetryPolicy = r11;
        r2 = r15.getExtras();
        r11 = "service_action";
        r0 = r2.getString(r11);
        r11 = TAG;
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "Performing action : ";
        r12 = r12.append(r13);
        r12 = r12.append(r0);
        r12 = r12.toString();
        android.util.Log.d(r11, r12);
        r11 = r14.getApplicationContext();
        r11 = com.odoo.mobile.core.utils.NetworkUtils.isConnected(r11);
        if (r11 != 0) goto L_0x0059;
    L_0x0047:
        r5 = new android.os.Bundle;
        r5.<init>();
        r0 = "error_response";
        r10 = "error_type";
        r11 = "no_network_connection";
        r5.putString(r10, r11);
        r14.sendResponse(r0, r5);
    L_0x0058:
        return;
    L_0x0059:
        r11 = -1;
        r12 = r0.hashCode();
        switch(r12) {
            case -1875150091: goto L_0x0097;
            case -664008627: goto L_0x00ab;
            case 644143781: goto L_0x00a1;
            case 1449490702: goto L_0x0084;
            case 1452768191: goto L_0x008d;
            default: goto L_0x0061;
        };
    L_0x0061:
        r10 = r11;
    L_0x0062:
        switch(r10) {
            case 0: goto L_0x0066;
            case 1: goto L_0x00b5;
            case 2: goto L_0x00cf;
            case 3: goto L_0x0102;
            case 4: goto L_0x0129;
            default: goto L_0x0065;
        };
    L_0x0065:
        goto L_0x0058;
    L_0x0066:
        r8 = new android.os.Bundle;
        r8.<init>();
        r10 = "host";
        r10 = r2.getString(r10);
        r6 = r14.isValidOdooVersion(r10);
        if (r6 == 0) goto L_0x0058;
    L_0x0077:
        r10 = "service_response";
        r11 = r6.booleanValue();
        r8.putBoolean(r10, r11);
        r14.sendResponse(r0, r8);
        goto L_0x0058;
    L_0x0084:
        r12 = "odoo_version";
        r12 = r0.equals(r12);
        if (r12 == 0) goto L_0x0061;
    L_0x008c:
        goto L_0x0062;
    L_0x008d:
        r10 = "db_list";
        r10 = r0.equals(r10);
        if (r10 == 0) goto L_0x0061;
    L_0x0095:
        r10 = 1;
        goto L_0x0062;
    L_0x0097:
        r10 = "authenticate_user";
        r10 = r0.equals(r10);
        if (r10 == 0) goto L_0x0061;
    L_0x009f:
        r10 = 2;
        goto L_0x0062;
    L_0x00a1:
        r10 = "user_from_session_id";
        r10 = r0.equals(r10);
        if (r10 == 0) goto L_0x0061;
    L_0x00a9:
        r10 = 3;
        goto L_0x0062;
    L_0x00ab:
        r10 = "user_avatar";
        r10 = r0.equals(r10);
        if (r10 == 0) goto L_0x0061;
    L_0x00b3:
        r10 = 4;
        goto L_0x0062;
    L_0x00b5:
        r10 = "host";
        r10 = r2.getString(r10);
        r3 = r14.getDatabaseList(r10);
        if (r3 == 0) goto L_0x0058;
    L_0x00c1:
        r8 = new android.os.Bundle;
        r8.<init>();
        r10 = "service_response";
        r8.putStringArrayList(r10, r3);
        r14.sendResponse(r0, r8);
        goto L_0x0058;
    L_0x00cf:
        r8 = new android.os.Bundle;	 Catch:{ OdooServiceException -> 0x00fc }
        r8.<init>();	 Catch:{ OdooServiceException -> 0x00fc }
        r10 = "host";
        r10 = r2.getString(r10);	 Catch:{ OdooServiceException -> 0x00fc }
        r11 = "username";
        r11 = r2.getString(r11);	 Catch:{ OdooServiceException -> 0x00fc }
        r12 = "password";
        r12 = r2.getString(r12);	 Catch:{ OdooServiceException -> 0x00fc }
        r13 = "database";
        r13 = r2.getString(r13);	 Catch:{ OdooServiceException -> 0x00fc }
        r7 = r14.authenticate(r10, r11, r12, r13);	 Catch:{ OdooServiceException -> 0x00fc }
        if (r7 == 0) goto L_0x0058;
    L_0x00f2:
        r10 = "service_response";
        r8.putParcelable(r10, r7);	 Catch:{ OdooServiceException -> 0x00fc }
        r14.sendResponse(r0, r8);	 Catch:{ OdooServiceException -> 0x00fc }
        goto L_0x0058;
    L_0x00fc:
        r4 = move-exception;
        r14.sendErrorResponse(r4);
        goto L_0x0058;
    L_0x0102:
        r8 = new android.os.Bundle;
        r8.<init>();
        r10 = "host";
        r10 = r2.getString(r10);	 Catch:{ OdooServiceException -> 0x0123 }
        r11 = "session_id";
        r11 = r2.getString(r11);	 Catch:{ OdooServiceException -> 0x0123 }
        r9 = r14.getUserUsingSession(r10, r11);	 Catch:{ OdooServiceException -> 0x0123 }
        if (r9 == 0) goto L_0x0058;
    L_0x0119:
        r10 = "service_response";
        r8.putParcelable(r10, r9);	 Catch:{ OdooServiceException -> 0x0123 }
        r14.sendResponse(r0, r8);	 Catch:{ OdooServiceException -> 0x0123 }
        goto L_0x0058;
    L_0x0123:
        r4 = move-exception;
        r14.sendErrorResponse(r4);
        goto L_0x0058;
    L_0x0129:
        r8 = new android.os.Bundle;
        r8.<init>();
        r10 = "session_id";
        r10 = r2.containsKey(r10);
        if (r10 == 0) goto L_0x013e;
    L_0x0136:
        r10 = "session_id";
        r10 = r2.getString(r10);
        r14.session_id = r10;
    L_0x013e:
        r10 = "host";
        r10 = r2.getString(r10);
        r11 = "user_id";
        r11 = r2.getInt(r11);
        r1 = r14.getUserAvatar(r10, r11);
        if (r1 == 0) goto L_0x0058;
    L_0x0150:
        r10 = "service_response";
        r8.putString(r10, r1);
        r14.sendResponse(r0, r8);
        goto L_0x0058;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.odoo.mobile.core.service.OdooIntentService.onHandleIntent(android.content.Intent):void");
    }

    private ArrayList<String> getDatabaseList(String host) {
        try {
            JSONObject result = newRequest(host + "/web/database/list", new JSONObject());
            if (result.has("result")) {
                return OJSONUtils.jsonArrayToList(result.getJSONArray("result"));
            }
            return new ArrayList();
        } catch (Exception e) {
            sendErrorResponse(e);
            return null;
        }
    }

    private Boolean isValidOdooVersion(String host) {
        boolean z = false;
        try {
            JSONObject result = newRequest(host + "/web/webclient/version_info", new JSONObject());
            if (result.has("result")) {
                JSONArray version_info = result.getJSONObject("result").getJSONArray("server_version_info");
                if (version_info.length() >= 6) {
                    int version = version_info.getInt(0);
                    String ent = version_info.getString(5);
                    if (version >= 9 && ent.equals("e")) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            }
            return Boolean.valueOf(false);
        } catch (Exception e) {
            sendErrorResponse(e);
            return null;
        }
    }

    private OdooUser getUserUsingSession(String host, String session_id) throws OdooServiceException {
        String url = host + "/web/session/get_session_info";
        this.session_id = session_id;
        try {
            JSONObject response = newRequest(url, new JSONObject());
            if (response.has("result")) {
                JSONObject result = response.getJSONObject("result");
                return createUserObject(host, result.getString("username"), result);
            }
            if (response.has("error")) {
                throw new OdooServiceException("unknown_error", "Unknown error.");
            }
            return null;
        } catch (Exception e) {
            sendErrorResponse(e);
        }
    }

    private OdooUser authenticate(String host, String username, String password, String database) throws OdooServiceException {
        String url = host + "/web/session/authenticate";
        try {
            JSONObject params = new JSONObject();
            params.put("db", database);
            params.put("login", username);
            params.put("password", password);
            params.put("context", new JSONObject());
            JSONObject response = newRequest(url, params);
            if (response.has("result")) {
                JSONObject result = response.getJSONObject("result");
                if (!(result.get("uid") instanceof Boolean)) {
                    return createUserObject(host, username, result);
                }
                throw new OdooServiceException("authentication_failed", "Authentication failed");
            }
            if (response.has("error")) {
                String errorMessage = response.getJSONObject("error").getJSONObject("data").getString("message");
                if (Pattern.compile("database .* does not exist").matcher(errorMessage).find()) {
                    throw new OdooServiceException("database_not_found", String.format("Database \"%s\" does not exist", new Object[]{database}));
                }
                throw new OdooServiceException("authentication_failed", errorMessage);
            }
            return null;
        } catch (Exception e) {
            sendErrorResponse(e);
        }
    }

    private OdooUser createUserObject(String host, String username, JSONObject result) {
        try {
            this.session_id = result.getString("session_id");
            OdooUser odooUser = new OdooUser();
            odooUser.host = host;
            odooUser.username = username;
            odooUser.database = result.getString("db");
            odooUser.active = "true";
            odooUser.id = result.getInt("uid");
            odooUser.session_id = this.session_id;
            if (result.has("display_name")) {
                odooUser.name = result.getString("display_name");
            } else if (result.has("name")) {
                odooUser.name = result.getString("name");
            } else {
                odooUser.name = username;
            }
            odooUser.dbuuid = getDBUUID(host);
            if (odooUser.dbuuid != null) {
                odooUser.avatar = getUserAvatar(odooUser.host, odooUser.id);
                return odooUser;
            }
        } catch (Exception e) {
            sendErrorResponse(e);
        }
        return null;
    }

    private String getDBUUID(String host) {
        String model = "ir.config_parameter";
        String url = host + "/web/dataset/call_kw/" + model + "/get_param";
        try {
            JSONObject params = new JSONObject();
            params.put("model", model);
            params.put("method", "get_param");
            params.put("args", new JSONArray().put("database.uuid"));
            params.put("kwargs", new JSONObject());
            JSONObject result = newRequest(url, params);
            if (result.has("result")) {
                return result.getString("result");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(e);
        }
        return null;
    }

    private String getUserAvatar(String host, int user_id) {
        Exception e;
        String url = String.format("%s/web/image?model=res.users&field=image_medium&id=%s", new Object[]{host, Integer.valueOf(user_id)});
        RequestFuture<Bitmap> future = RequestFuture.newFuture();
        ImageRequest request = new ImageRequest(url, future, 0, 0, null, Config.RGB_565, future) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return OdooIntentService.this.createRequestHeader(super.getHeaders());
            }
        };
        request.setTag(TAG);
        request.setRetryPolicy(this.defaultRetryPolicy);
        this.requestQueue.add(request);
        try {
            Bitmap bitmap = (Bitmap) future.get((long) REQUEST_TIMEOUT_MS.intValue(), TimeUnit.MILLISECONDS);
            if (bitmap == null) {
                return "false";
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 100, stream);
            return Base64.encodeToString(stream.toByteArray(), 0);
        } catch (InterruptedException e2) {
            e = e2;
            sendErrorResponse(e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            sendErrorResponse(e);
            return null;
        } catch (TimeoutException e4) {
            e = e4;
            sendErrorResponse(e);
            return null;
        }
    }

    private JSONObject newRequest(String url, JSONObject params) throws InterruptedException, ExecutionException, TimeoutException, CertPathValidatorException {
        JSONObject payload = createPayload(params);
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, payload, requestFuture, requestFuture) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return OdooIntentService.this.createRequestHeader(super.getHeaders());
            }
        };
        request.setTag(TAG);
        request.setRetryPolicy(this.defaultRetryPolicy);
        this.requestQueue.add(request);
        return (JSONObject) requestFuture.get((long) REQUEST_TIMEOUT_MS.intValue(), TimeUnit.MILLISECONDS);
    }

    private JSONObject createPayload(JSONObject params) {
        JSONObject payload = new JSONObject();
        if (params == null) {
            try {
                params = new JSONObject();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        payload.put("jsonrpc", "2.0");
        payload.put("method", "call");
        payload.put("params", params);
        payload.put("id", UUID.randomUUID());
        return payload;
    }

    private Map<String, String> createRequestHeader(Map<String, String> header) {
        if (header == null || header.equals(Collections.emptyMap())) {
            header = new HashMap();
        }
        if (this.session_id != null) {
            header.put("Cookie", "session_id=" + this.session_id);
        }
        return header;
    }

    private void sendErrorResponse(Exception e) {
        Bundle error = new Bundle();
        if (e instanceof TimeoutException) {
            error.putString("error_type", "time_out");
        }
        if (e instanceof OdooServiceException) {
            error.putString("error_type", ((OdooServiceException) e).error_type);
            error.putString("error_message", e.getMessage());
        }
        if ((e instanceof NoConnectionError) || (e instanceof ExecutionException)) {
            error.putString("error_type", "server_not_reachable");
        }
        if (!(e.getCause() == null || e.getCause().getCause() == null || (!(e.getCause().getCause() instanceof SSLHandshakeException) && !(e.getCause().getCause() instanceof CertPathValidatorException)))) {
            error.putString("error_type", "certificate_not_trusted");
            error.putString("error_message", getString(2131165280));
        }
        sendResponse("error_response", error);
    }

    private void sendResponse(String request_action, Bundle result) {
        Intent resultIntent = new Intent(TAG);
        resultIntent.setAction(TAG);
        resultIntent.putExtra("service_action", request_action);
        resultIntent.putExtras(result);
        this.broadcastManager.sendBroadcast(resultIntent);
    }
}
