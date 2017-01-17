/*package co.hega.hegaerp.pref;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import co.hega.hegaerp.pref.UserSession;
import java.util.Locale;

public class HegaUser implements Parcelable {
    public static final Creator<HegaUser> CREATOR = new Creator<HegaUser>() {
        public HegaUser createFromParcel(Parcel in) {
            return new HegaUser(in);
        }

        public HegaUser[] newArray(int size) {
            return new HegaUser[size];
        }
    };
    public Account account;
    public String active = "false";
    public String avatar;
    public String database;
    public String dbuuid = "false";
    public String host;
    public int id;
    public String name;
    public int notification_unique_id = 0;
    public String session_id;
    public String username;

    protected HegaUser(Parcel in) {
        this.id = in.readInt();
        this.host = in.readString();
        this.avatar = in.readString();
        this.name = in.readString();
        this.username = in.readString();
        this.database = in.readString();
        this.session_id = in.readString();
        this.active = in.readString();
        this.dbuuid = in.readString();
        this.notification_unique_id = in.readInt();
    }

    public String getAccountName() {
        return createAccountName(this.username, this.host, this.database);
    }

    public static String createAccountName(String username, String host, String database) {
        Uri uri = Uri.parse(host);
        String port = uri.getPort() != -1 ? uri.getPort() + ":" : "";
        return String.format(Locale.getDefault(), "%s[%s:%s%s]", new Object[]{username, uri.getHost(), port, database});
    }

    public HegaUser fromBundle(AccountManager manager, Account account) {
        HegaUser user = new HegaUser();
        user.id = Integer.parseInt(manager.getUserData(account, "uid"));
        user.avatar = manager.getUserData(account, "avatar");
        user.name = manager.getUserData(account, "name");
        user.host = manager.getUserData(account, "host");
        user.username = manager.getUserData(account, "username");
        user.database = manager.getUserData(account, "database");
        user.active = manager.getUserData(account, "active");
        user.dbuuid = manager.getUserData(account, "dbuuid");
        user.account = account;
        user.notification_unique_id = Integer.parseInt(manager.getUserData(account, "notification_id"));
        return user;
    }

    public Bundle toBundle() {
        Bundle data = new Bundle();
        data.putString("name", this.name);
        data.putString("avatar", this.avatar);
        data.putString("host", this.host);
        data.putString("username", this.username);
        data.putString("database", this.database);
        data.putString("uid", this.id + "");
        data.putString("active", this.active);
        data.putString("dbuuid", this.dbuuid);
        data.putString("notification_id", this.notification_unique_id + "");
        return data;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.host);
        dest.writeString(this.avatar);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.database);
        dest.writeString(this.session_id);
        dest.writeString(this.active);
        dest.writeString(this.dbuuid);
        dest.writeInt(this.notification_unique_id);
    }

    public String toString() {
        return "OdooUser{id=" + this.id + ", host='" + this.host + '\'' + ", avatar='" + (!this.avatar.equals("false")) + '\'' + ", name='" + this.name + '\'' + ", username='" + this.username + '\'' + ", database='" + this.database + '\'' + ", session_id='" + this.session_id + '\'' + ", active='" + this.active + '\'' + ", dbuuid='" + this.dbuuid + '\'' + '}';
    }

    public UserSession getSession(Context context) {
        return new UserSession(context, this);
    }
}*/

