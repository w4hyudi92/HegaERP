package co.hega.hegaerp.accounts.manager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import co.hega.hegaerp.Main_User;
import co.hega.hegaerp.ui.User_Login
import com.odoo.mobile.UserLoginActivity;
import com.odoo.mobile.WebViewActivity;
import co.hega.hegaerp.accounts.OdooAccountManager;
import co.hega.hegaerp.accounts.OdooUser;

public class AllAccountsActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener {
    private final String TAG = AllAccountsActivity.class.getSimpleName();
    private ArrayAdapter<OdooUser> adapter;
    private OdooAccountManager odooAccountManager;

    private class AccountDeleteTask extends AsyncTask<OdooUser, Void, Boolean> {
        private ProgressDialog dialog;

        private AccountDeleteTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(AllAccountsActivity.this);
            this.dialog.setCancelable(false);
            this.dialog.setMessage(AllAccountsActivity.this.getString(2131165286));
            this.dialog.show();
        }

        protected Boolean doInBackground(OdooUser... odooUsers) {
            return Boolean.valueOf(AllAccountsActivity.this.odooAccountManager.removeAccount(odooUsers[0]));
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            this.dialog.dismiss();
            if (result.booleanValue()) {
                Toast.makeText(AllAccountsActivity.this, 2131165303, 1).show();
                AllAccountsActivity.this.adapter.clear();
                AllAccountsActivity.this.adapter.addAll(AllAccountsActivity.this.odooAccountManager.getUserAccounts());
                AllAccountsActivity.this.adapter.notifyDataSetChanged();
                if (AllAccountsActivity.this.adapter.getCount() <= 0) {
                    AllAccountsActivity.this.startActivity(new Intent(AllAccountsActivity.this, Login.class));
                    AllAccountsActivity.this.finish();
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2130968606);
        setResult(0);
        this.odooAccountManager = new OdooAccountManager(this);
        findViewById(2131558532).setOnClickListener(this);
        ListView accountListView = (ListView) findViewById(2131558531);
        this.adapter = new ArrayAdapter<OdooUser>(this, 2130968602, this.odooAccountManager.getUserAccounts()) {
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(2130968602, parent, false);
                }
                OdooUser odooUser = (OdooUser) getItem(position);
                convertView.setTag(odooUser);
                OControlUtils.setText(convertView.findViewById(2131558517), odooUser.name);
                OControlUtils.setText(convertView.findViewById(2131558518), odooUser.host);
                OControlUtils.setImage(convertView.findViewById(2131558516), odooUser.avatar);
                convertView.findViewById(2131558519).setOnClickListener(AllAccountsActivity.this);
                return convertView;
            }
        };
        accountListView.setAdapter(this.adapter);
        accountListView.setOnItemClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131558519:
                OdooUser odooUser = (OdooUser) ((View) v.getParent()).getTag();
                if (odooUser != null) {
                    removeAccount(odooUser);
                    setResult(-1);
                    return;
                }
                return;
            case 2131558532:
                Intent newAccount = new Intent(this, User_Login.class);
                newAccount.putExtra("force_new_login", true);
                startActivity(newAccount);
                return;
            default:
                return;
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        OdooUser user = (OdooUser) this.adapter.getItem(position);
        Log.i(this.TAG, "Switching user to : " + user.getAccountName());
        if (user.getSession(this).isValid()) {
            this.odooAccountManager.makeActive(user);
            Intent webActivity = new Intent(this, Main_User.class);
            webActivity.setFlags(268468224);
            startActivity(webActivity);
        } else {
            Intent userLoginIntent = new Intent(this, User_Login.class);
            userLoginIntent.setFlags(268468224);
            userLoginIntent.putExtra("user_name", user.getAccountName());
            startActivity(userLoginIntent);
        }
        finish();
    }

    private void removeAccount(final OdooUser user) {
        Builder builder = new Builder(this);
        builder.setTitle("Remove");
        builder.setMessage("Remove this account ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new AccountDeleteTask().execute(new OdooUser[]{user});
            }
        });
        builder.setNegativeButton("NO", null);
        builder.create().show();
    }
}
