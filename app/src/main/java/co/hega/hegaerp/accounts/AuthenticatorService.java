package co.hega.hegaerp.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {
    private Authenticator mAuthenticator;

    public void onCreate() {
        this.mAuthenticator = new Authenticator(this);
    }

    public IBinder onBind(Intent intent) {
        return this.mAuthenticator.getIBinder();
    }
}
