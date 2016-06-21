package com.aperto.fatpenguin.aperto;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @Author Rasmus Liebst
 */
public class AboutActivity extends Activity {

    private TextView developers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        developers = (TextView) findViewById(R.id.developers);

        String[] devs = getResources().getStringArray(R.array.developers);
        String simpleDevStr = "";
        for (String s : devs){
            simpleDevStr = simpleDevStr + s + "\n";
        }
        developers.setText(simpleDevStr);
    }
}
