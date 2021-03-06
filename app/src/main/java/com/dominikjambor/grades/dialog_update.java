package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by j-dom on 9/7/2015.
 */
public class dialog_update extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update, null);
        getDialog().setTitle("Frissítés keresése");

        final TextView resultText = (TextView) view.findViewById(R.id.dUpdateResultText);
        final Button okButton = (Button) view.findViewById(R.id.dUpdateOk);
        okButton.setText("OK");

        TextView versionText = (TextView) view.findViewById(R.id.dVersionText);
        final TextView changeText = (TextView) view.findViewById(R.id.updateChangelogTextView);
        versionText.setText(MainActivity.vern);
        final ProgressBar pbar = (ProgressBar) view.findViewById(R.id.dUpdatePBar);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://cleantech.hu/grades/update.php?action=check";
        String url2 ="http://cleantech.hu/grades/update.php?action=log&version=latest";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) throws PackageManager.NameNotFoundException {
                        boolean cani=true;
                        try{
                            Integer.parseInt(response.trim());
                        }catch(NumberFormatException e){
                            cani=false;
                        }
                        Log.w("GOT RESULT",response);
                        if(!cani){
                            resultText.setText("Hiba az ellenőrzés során!");
                        }
                        else{
                            if(Integer.parseInt(response.trim())>MainActivity.verc){
                                resultText.setText("Új frissítés áll rendelkezésre!");
                                okButton.setText("Frissítés");
                                Log.w("NEWVER",String.valueOf(MainActivity.verc));
                            }
                            else{
                                resultText.setText("Ez a legfrissebb verzió.");
                            }
                        }
                        pbar.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultText.setText("Hiba a csatlakozás során!");
                pbar.setVisibility(View.INVISIBLE);

            }
        });
        StringRequest changelogRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) throws PackageManager.NameNotFoundException {
                        changeText.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(okButton.getText()=="OK"){
                    queue.stop();
                    dismiss();
                }
                else if(okButton.getText()=="Frissítés"){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://getgrades.ml/update.php?action=get&version=latest"));
                    startActivity(browserIntent);
                    dismiss();
                    getActivity().finish();
                }
            }
        });
        queue.add(stringRequest);
        queue.add(changelogRequest);
        return view;
    }
}
