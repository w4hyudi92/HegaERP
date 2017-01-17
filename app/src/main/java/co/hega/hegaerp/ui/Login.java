package co.hega.hegaerp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import co.hega.hegaerp.R;

/**
 * Created by nuryantowahyudi on 12/30/16.
 */

public class Login extends ActionBarActivity  {

        TextView text, text1, text2, lwith, fgt;
        Button buttonGo;
        EditText et5, et6;
        Typeface textfont;

        private Toolbar toolbar;

        String username, password;
        private ProgressDialog pDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_login);
            initToolBar();


            text = (TextView) findViewById(R.id.textView2);
            text1 = (TextView) findViewById(R.id.textView9);
            text2 = (TextView) findViewById(R.id.textView11);
            lwith = (TextView) findViewById(R.id.textView7);
            fgt = (TextView) findViewById(R.id.forgetLink);
            et5 = (EditText) findViewById(R.id.emailLogin);
            et6 = (EditText) findViewById(R.id.passLogin);
            buttonGo = (Button) findViewById(R.id.but_login);

            textfont = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
            text.setTypeface(textfont);
            text1.setTypeface(textfont);
            text2.setTypeface(textfont);
            lwith.setTypeface(textfont);
            fgt.setTypeface(textfont);
            et5.setTypeface(textfont);
            et6.setTypeface(textfont);
            buttonGo.setTypeface(textfont);
            buttonGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //new MasukAja().execute();
                    Intent intent = new Intent(Login.this, Main.class);
                    intent.putExtra("usr", et5.getText().toString());
                    intent.putExtra("psw", et6.getText().toString());
                    startActivity(intent);
                    finish();
                }
            });

            text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                    overridePendingTransition(0,
                            R.anim.slide_from_right);
                    finish();
                }
            });

            fgt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, Forget.class);
                    startActivity(intent);
                    overridePendingTransition(0,// TODO Auto-generated method stub
                            R.anim.slide_from_right);
                    finish();
                }
            });
        }

        /*class MasukAja extends AsyncTask<String,Void,String> {

            InputStream stream;
            String result = "";
            String data = "";

            protected void onPreExecute() {
                super.onPreExecute();
                // TODO Auto-generated method stub
                username = et5.getText().toString();
                password = et6.getText().toString();

                pDialog = new ProgressDialog(Login.this);
                pDialog.setMessage("Sedang Login...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("username",username));
                    nameValuePairs.add(new BasicNameValuePair("password",password));
                    Log.v(username, "got");//logcat view
                    Log.v(password,"got" );//logcat view
                    HttpPost httppost = new HttpPost("http://192.168.9.24:8069/web/signin1234?login=admin&password=admin");//?username=" + etusername + "&" + "password=" + etpassword);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    stream = entity.getContent();

                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("log_tag", "Error in http connection "+e.toString());
                } try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    stream.close();
                    result=sb.toString();

                }catch(Exception e){
                    Log.e("log_tag", "Error converting result "+e.toString());
                }
                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                Log.v("result", result);//to get in logcat
                Log.v(result, "result");
                JSONObject j_obj = null ;
                try {
                    j_obj=new JSONObject(result);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String status = null;
                try {
                    status = j_obj.getString("status");

                    Log.v("status", status);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }if(status.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "login Berhasil", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "username atau password salah"
                            , Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected void onProgressUpdate(Void... values) {
                // TODO Auto-generated method stub

                super.onProgressUpdate(values);
            }
            @Override
            protected void onCancelled() {
                // TODO Auto-generated method stub
                super.onCancelled();
            }
        }

        /*public void GetText() throws UnsupportedEncodingException{
            username = et5.getText().toString();
            password = et6.getText().toString();

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

            String text = "";
            BufferedReader reader = null;

            try{
                URL url = new URL("http://192.168.9.24:8069/web/signin1234?login=" + username + "&password=" + password);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                    while((line = reader.readLine()) != null);{
                    sb.append(line + "\n");
                }

            } catch (Exception ex){

            } finally {

            } try {
                reader.close();
            } catch (Exception ex){}
        }*/

        public void initToolBar() {

                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                toolbar.setNavigationIcon(R.drawable.back_button);
                toolbar.setNavigationOnClickListener(

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Login.this, Akses.class);
                                overridePendingTransition(0,
                                        R.anim.slide_from_right);
                                startActivity(intent);
                                finish();
                            }
                        }
                );
        }

        public void onBackPressed() {
            Intent intent = new Intent(Login.this, Akses.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_left,
                    R.anim.slide_from_right);
            finish();
            super.onBackPressed();

        }


}
