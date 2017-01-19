package co.hega.hegaerp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                    PopUp();
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

        public void PopUp(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(R.layout.information)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Login.this, Main.class);
                            startActivity(intent);
                            finish();
                        }
                });
        AlertDialog alert = builder.create();
        alert.show();
        }

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
