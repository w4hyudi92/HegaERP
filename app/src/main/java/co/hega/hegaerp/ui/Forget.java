package co.hega.hegaerp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.hega.hegaerp.R;

public class Forget extends AppCompatActivity {

    TextView Viewtext, Viewtext1, Viewtext2;
    EditText edtMail;
    Button accpt;

    Typeface jnsFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        Viewtext = (TextView) findViewById(R.id.textView66);
        Viewtext1 = (TextView) findViewById(R.id.textView88);
        Viewtext2 = (TextView) findViewById(R.id.bckLogin1);
        edtMail = (EditText) findViewById(R.id.mail);
        accpt = (Button) findViewById(R.id.button55);

        jnsFont = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");

        Viewtext.setTypeface(jnsFont);
        Viewtext1.setTypeface(jnsFont);
        Viewtext2.setTypeface(jnsFont);
        edtMail.setTypeface(jnsFont);
        accpt.setTypeface(jnsFont);
    }

    public void bcLogin1(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_from_left,
                R.anim.slide_from_right);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_from_left,
                R.anim.slide_from_right);
        finish();
        super.onBackPressed();

    }
}
