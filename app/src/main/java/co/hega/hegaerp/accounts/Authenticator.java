package co.hega.hegaerp.accounts;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import co.hega.hegaerp.ui.User_Login;

public class Authenticator extends AbstractAccountAuthenticator {
    private Context mContext;

    public Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse response, Account account) throws NetworkErrorException {
        Bundle result = super.getAccountRemovalAllowed(response, account);
        if (result != null && result.containsKey("booleanResult") && !result.containsKey("intent") && result.getBoolean("booleanResult")) {
            new OdooAccountManager(this.mContext).getAccount(account.name).getSession(this.mContext).removeSession();
        }
        return result;
    }

    public Bundle editProperties(AccountAuthenticatorResponse r, String s) {
        throw new UnsupportedOperationException();
    }

    public Bundle addAccount(AccountAuthenticatorResponse r, String s, String s2, String[] strings, Bundle bundle) throws NetworkErrorException {
        Intent newAccount = new Intent(this.mContext, User_Login.class);
        newAccount.putExtra("force_new_login", true);
        Bundle intentData = new Bundle();
        intentData.putParcelable("intent", newAccount);
        return intentData;
    }

    public Bundle confirmCredentials(AccountAuthenticatorResponse r, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    public Bundle getAuthToken(AccountAuthenticatorResponse r, Account account, String s, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    public String getAuthTokenLabel(String s) {
        throw new UnsupportedOperationException();
    }

    public Bundle updateCredentials(AccountAuthenticatorResponse r, Account account, String s, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    public Bundle hasFeatures(AccountAuthenticatorResponse r, Account account, String[] strings) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
}
