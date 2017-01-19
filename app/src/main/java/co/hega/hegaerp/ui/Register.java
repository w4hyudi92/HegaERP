package co.hega.hegaerp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

public class Register extends AppCompatActivity {//implements View.OnClickListener{

    TextView txtview, txtview1, txtview2, txtview3, txtview4;
    Button rgster;

    @NotEmpty(message = "Harus diisi")
    private EditText etxt;


    @NotEmpty(message = "Harus diisi")
    @Email(message = "Format Email Salah")
    private EditText etxt1;

    @NotEmpty(message = "Harus diisi")
    private EditText etxt2;

    @NotEmpty(message = "Harus diisi")
    private EditText etxt3;

    Typeface textfont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtview = (TextView) findViewById(R.id.textView3);
        txtview1 = (TextView) findViewById(R.id.textView4);
        txtview2 = (TextView) findViewById(R.id.textView13);
        txtview3 = (TextView) findViewById(R.id.textView15);
        txtview4 = (TextView) findViewById(R.id.textView18);
        etxt = (EditText) findViewById(R.id.editText4);
        etxt1 = (EditText) findViewById(R.id.editText7);
        etxt2 = (EditText) findViewById(R.id.editText8);
        etxt3 = (EditText) findViewById(R.id.editText9);
        rgster = (Button) findViewById(R.id.button4);
        //rgster.setOnClickListener(this);

        // Change font
        textfont = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");

        txtview.setTypeface(textfont);
        txtview1.setTypeface(textfont);
        txtview2.setTypeface(textfont);
        txtview3.setTypeface(textfont);
        txtview4.setTypeface(textfont);
        etxt.setTypeface(textfont);
        etxt1.setTypeface(textfont);
        etxt2.setTypeface(textfont);
        etxt3.setTypeface(textfont);
        rgster.setTypeface(textfont);
    }

    /*public void onClick(View v){
        if (v == rgster) {
            try{

                String nama, email, password, re_password;
                nama = etxt.getText().toString();
                email = etxt1.getText().toString();
                password = etxt2.getText().toString();
                re_password = etxt3.getText().toString();

                Map daftar = new HashMap();
                daftar.put("redirect", "");
                daftar.put("token", "");
                daftar.put("name", nama);
                daftar.put("login", email);
                daftar.put("password", "abcd");
                daftar.put("email", email);
            }
    }*/

    public void loginText(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Akses.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_from_left,
                R.anim.slide_from_right);
        finish();
        super.onBackPressed();

    }
}
