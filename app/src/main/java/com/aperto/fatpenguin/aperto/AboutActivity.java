package com.aperto.fatpenguin.aperto;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by rasmusliebst on 20/06/16.
 */
public class AboutActivity extends Activity {

    private String[] developers_array;
    private TextView icon_credits;
    private TextView developersTitle;
    private TextView creditsTitle;
    private TextView developers;
    private TextView icon8_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        icon_credits = (TextView) findViewById(R.id.icon8_title);
        creditsTitle = (TextView) findViewById(R.id.credits_title);
        developersTitle = (TextView) findViewById(R.id.developers_title);
        developers = (TextView) findViewById(R.id.developers);
        icon8_link = (TextView) findViewById(R.id.icon8_link);
        //icon8_link.setMovementMethod(LinkMovementMethod.getInstance());

        String[] devs = getResources().getStringArray(R.array.developers);
        String simpleDevStr = "";
        for (String s : devs){
            simpleDevStr = simpleDevStr + s + "\n";
        }

        developers.setText(simpleDevStr);

        //Resources res = getResources();
        //String[] developersArray = res.getStringArray(R.array.developers);

        //developers.setText(Arrays.toString(getResources().getStringArray(R.array.developers)).replaceAll("\\[|\\]", ""));

       //for(int i = 0; i < developers.length; i++){
       //    developers = (TextView)
       //}

    }
}
