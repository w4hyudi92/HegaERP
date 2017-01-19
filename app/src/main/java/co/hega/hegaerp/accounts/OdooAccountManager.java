package co.hega.hegaerp.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OdooAccountManager {
    private AccountManager accountManager;
    private Context mContext;

    public OdooAccountManager(Context context) {
        this.mContext = context;
        this.accountManager = AccountManager.get(context);
    }

    public Account[] getAccounts() {
        return this.accountManager.getAccountsByType("com.odoo.mobile.auth");
    }

    public List<OdooUser> getUserAccounts() {
        List<OdooUser> accounts = new ArrayList();
        for (Account account : getAccounts()) {
            accounts.add(new OdooUser().fromBundle(this.accountManager, account));
        }
        return accounts;
    }

    public boolean hasAccount(String name) {
        for (Account account : getAccounts()) {
            if (account.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Account findAccount(String name) {
        for (Account account : getAccounts()) {
            if (account.name.equals(name)) {
                return account;
            }
        }
        return null;
    }

    public boolean hasAnyAccount() {
        return getAccounts().length > 0;
    }

    public OdooUser getAccount(String username) {
        Account account = findAccount(username);
        if (account != null) {
            return new OdooUser().fromBundle(this.accountManager, account);
        }
        return null;
    }

    public boolean createAccount(OdooUser user) {
        if (hasAccount(user.getAccountName())) {
            return false;
        }
        Account newAccount = new Account(user.getAccountName(), "com.odoo.mobile.auth");
        user.account = newAccount;
        user.notification_unique_id = getNextNotificationId();
        return this.accountManager.addAccountExplicitly(newAccount, "N/A", user.toBundle());
    }

    public void updateDetails(OdooUser user) {
        if (hasAccount(user.getAccountName())) {
            Account account = findAccount(user.getAccountName());
            Bundle userData = user.toBundle();
            for (String key : userData.keySet()) {
                this.accountManager.setUserData(account, key, userData.get(key) + "");
            }
        }
    }

    public int getNextNotificationId() {
        int maxNumber = 0;
        for (OdooUser user : getUserAccounts()) {
            if (maxNumber < user.notification_unique_id) {
                maxNumber = user.notification_unique_id;
            }
        }
        return maxNumber + 1;
    }

    public boolean removeAccount(OdooUser user) {
        Exception e;
        if (hasAccount(user.getAccountName())) {
            if (VERSION.SDK_INT >= 22) {
                return this.accountManager.removeAccountExplicitly(findAccount(user.getAccountName()));
            }
            try {
                return ((Boolean) this.accountManager.removeAccount(findAccount(user.getAccountName()), null, null).getResult()).booleanValue();
            } catch (OperationCanceledException e2) {
                e = e2;
                e.printStackTrace();
                return false;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                return false;
            } catch (AuthenticatorException e4) {
                e = e4;
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public OdooUser getActiveAccount() {
        for (OdooUser user : getUserAccounts()) {
            if (user.active.equals("true")) {
                return user;
            }
        }
        return null;
    }

    public void makeActive(OdooUser user) {
        OdooUser recentActiveUser = getActiveAccount();
        if (recentActiveUser != null) {
            this.accountManager.setUserData(recentActiveUser.account, "active", "false");
        }
        PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putString(user.dbuuid, user.getAccountName()).apply();
        this.accountManager.setUserData(user.account, "active", "true");
    }
}
