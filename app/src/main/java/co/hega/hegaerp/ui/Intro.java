package co.hega.hegaerp.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nuryantowahyudi on 1/10/17.
 */

public class Intro extends AppCompatActivity{

    TextView textIntro3_1, textIntro3_2;
    Button regis_intro3;

    Typeface intro3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);

        textIntro3_1 = (TextView) findViewById(R.id.title_intro3);
        textIntro3_2 = (TextView) findViewById(R.id.content_viewIntro3);
        regis_intro3 = (Button) findViewById(R.id.register_intro3);

        intro3 = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
        textIntro3_1.setTypeface(intro3);
        textIntro3_2.setTypeface(intro3);
        regis_intro3.setTypeface(intro3);
    }

    public void register_intro3(View view)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
}
