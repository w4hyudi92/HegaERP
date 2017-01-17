package co.hega.hegaerp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.hega.hegaerp.R;

public class Staff extends AppCompatActivity {

    private TextView t, t1, t2;
    private Button button1;
    private EditText cmpny, emUser, psUser;

    Typeface textfont;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_staff);
        initToolBar();

        t = (TextView) findViewById(R.id.textView12);
        t1 = (TextView) findViewById(R.id.textView22);
        t2 = (TextView) findViewById(R.id.textView25);
        cmpny = (EditText) findViewById(R.id.company);
        emUser = (EditText) findViewById(R.id.emailUser);
        psUser = (EditText) findViewById(R.id.passUser);
        button1 = (Button) findViewById(R.id.but_login_user);

        textfont = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
        t.setTypeface(textfont);
        t1.setTypeface(textfont);
        t2.setTypeface(textfont);
        cmpny.setTypeface(textfont);
        emUser.setTypeface(textfont);
        psUser.setTypeface(textfont);
        button1.setTypeface(textfont);
    }

    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Staff.this, Akses.class);
                        overridePendingTransition(R.anim.slide_from_left,
                                R.anim.slide_from_right);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    public void go2(View view)
    {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,
                R.anim.slide_from_left);
        finish();
    }

    public void forget(View view)
    {
        Intent intent = new Intent(this, LostPass.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,
                R.anim.slide_from_left);
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
