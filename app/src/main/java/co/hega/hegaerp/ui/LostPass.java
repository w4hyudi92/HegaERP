package co.hega.hegaerp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LostPass extends AppCompatActivity {

    TextView tView1, tView2, tView3;
    EditText edtxt1, edtxt2;
    Button btn_submit;

    Typeface fontText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pass);

        tView1 = (TextView) findViewById(R.id.textView6);
        tView2 = (TextView) findViewById(R.id.textView8);
        tView3 = (TextView) findViewById(R.id.bLogin);
        edtxt1 = (EditText) findViewById(R.id.comp);
        edtxt2 = (EditText) findViewById(R.id.email);
        btn_submit = (Button) findViewById(R.id.button5);

        fontText = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");

        tView1.setTypeface(fontText);
        tView2.setTypeface(fontText);
        tView3.setTypeface(fontText);
        edtxt1.setTypeface(fontText);
        edtxt2.setTypeface(fontText);
        btn_submit.setTypeface(fontText);


    }

    public void bckLogin(View view)
    {
        Intent intent = new Intent(this, Staff.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_from_left,
                R.anim.slide_from_right);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Staff.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_from_left,
                R.anim.slide_from_right);
        finish();
        super.onBackPressed();

    }
}
