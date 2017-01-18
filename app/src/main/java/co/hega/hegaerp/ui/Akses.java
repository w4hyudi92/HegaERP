package co.hega.hegaerp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.hega.hegaerp.R;

public class Akses extends AppCompatActivity {

    TextView aksesText1, aksesText2, aksesText3;
    Button button, buttonStaff;
    Typeface textfont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akses);

        aksesText1 = (TextView) findViewById(R.id.textView);
        aksesText2 = (TextView) findViewById(R.id.textView5);
        aksesText3 = (TextView) findViewById(R.id.textView30);

        button = (Button) findViewById(R.id.button);
        buttonStaff = (Button) findViewById(R.id.button3);


        textfont = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
        aksesText1.setTypeface(textfont);
        aksesText2.setTypeface(textfont);
        aksesText3.setTypeface(textfont);
        button.setTypeface(textfont);
        buttonStaff.setTypeface(textfont);

        buttonStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Akses.this, Staff.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,
                        R.anim.slide_from_right);
                finish();
            }
        });
    }


    public void owner(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void registerText(View view)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }

    /*public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Exit this app ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }*/

}
