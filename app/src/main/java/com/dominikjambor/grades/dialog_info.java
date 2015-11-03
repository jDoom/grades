package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.Map;

/**
 * Created by Dominik on 1/24/2015.
 */
public class dialog_info extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info, null);
        getDialog().setTitle("Információk");
        Button ok = (Button)view.findViewById(R.id.infoOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ok.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Fragment fg = new fragment_easteregg();
                FragmentManager fragmentManager = MainActivity.sfmgr;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fg)
                        .commit();
                dismiss();
                return false;
            }
        });
        TextView backupText = (TextView) view.findViewById(R.id.hiddenBackup);
        TextView restoreText = (TextView) view.findViewById(R.id.hiddenRestore);
        backupText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                saveSharedPreferencesToFile();
                Toast.makeText(getActivity(), "Backup created.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        restoreText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                loadSharedPreferencesFromFile();
                Toast.makeText(getActivity(), "File loaded, restart the app!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return view;
    }
    private boolean saveSharedPreferencesToFile() {
        boolean res = false;
        ObjectOutputStream output = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + "/GRADESBACKUP");
            dir.mkdirs();
            File f = new File(dir, "grds.xml");
            output = new ObjectOutputStream(new FileOutputStream(f));
            SharedPreferences pref =
                    getActivity().getSharedPreferences("com.dominikjambor.grades.data", getActivity().MODE_PRIVATE);
            output.writeObject(pref.getAll());

            res = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    @SuppressWarnings({ "unchecked" })
    private boolean loadSharedPreferencesFromFile() {
        boolean res = false;
        ObjectInputStream input = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + "/GRADESBACKUP");
            dir.mkdirs();
            File f = new File(dir, "grds.xml");
            input = new ObjectInputStream(new FileInputStream(f));
            SharedPreferences.Editor prefEdit = getActivity().getSharedPreferences("com.dominikjambor.grades.data", getActivity().MODE_PRIVATE).edit();
            prefEdit.clear();
            Map<String, ?> entries = (Map<String, ?>) input.readObject();
            for (Map.Entry<String, ?> entry : entries.entrySet()) {
                Object v = entry.getValue();
                String key = entry.getKey();

                if (v instanceof Boolean)
                    prefEdit.putBoolean(key, ((Boolean) v).booleanValue());
                else if (v instanceof Float)
                    prefEdit.putFloat(key, ((Float) v).floatValue());
                else if (v instanceof Integer)
                    prefEdit.putInt(key, ((Integer) v).intValue());
                else if (v instanceof Long)
                    prefEdit.putLong(key, ((Long) v).longValue());
                else if (v instanceof String)
                    prefEdit.putString(key, ((String) v));
            }
            prefEdit.commit();
            res = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }
}
