package com.aperto.fatpenguin.aperto;
import android.util.Log;
import android.view.View;


public class RecyclerItemClickListener implements View.OnClickListener {

    @Override
    public void onClick(final View v) {
        Log.v("clicked", "something was clicked");
    }

}