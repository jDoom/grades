package com.dominikjambor.grades;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Dominik on 1/6/2015.
 */
public class fragment_easteregg extends Fragment {
    View rootView;
    MediaPlayer m = new MediaPlayer();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_menu4,container , false);

        String url ="file:///android_asset/lol.html";

        WebView wv=(WebView) rootView.findViewById(R.id.wv);
        wv.loadUrl(url);
        return rootView;
    }
}
