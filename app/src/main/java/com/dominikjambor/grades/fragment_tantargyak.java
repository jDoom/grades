package com.dominikjambor.grades;

import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.melnykov.fab.FloatingActionButton;
import com.terlici.dragndroplist.DragNDropCursorAdapter;
import com.terlici.dragndroplist.DragNDropListView;

import java.util.ArrayList;
import java.util.List;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;

/**
 * Created by Dominik on 1/6/2015.
 */
public class fragment_tantargyak extends Fragment {
    static View rootView;
    static LayoutInflater inflaterr;
    static DragNDropListView list;
    static int szerkesztid = 0;
    static FloatingActionButton fab;
    private static List<String> tantargyList = new ArrayList<>();

    static void createList() {
        tantargyList.clear();
        for (int i = 0; i < Settings.tantargyakSzama; i++) {
            tantargyList.add(Settings.tantargyak[i].nev);
        }
        populateListView(rootView.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_menu2, container, false);
        inflaterr = inflater;
        list = (DragNDropListView) rootView.findViewById(R.id.listView2);
        createList();
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Settings.tantargyfragment_tutorial) {
                    MainActivity.mTourGuideHandler.cleanUp();
                    Settings.tantargyfragment_tutorial = true;
                    Settings.SaveAll(getActivity().getApplicationContext());
                }
                MainActivity.ShowTantargyHozzaado();
            }
        });
        if (!Settings.tantargyfragment_tutorial) {
            MainActivity.mTourGuideHandler.setPointer(new Pointer())
                    .setToolTip(new ToolTip()
                                    .setTitle("Itt is egy tantárgy!")
                                    .setDescription("Itt tudod a tantárgyakat szerkeszteni és újat hozzáadni.\nA sorrendjüket a jobb oldaluk megfogásával tudod változtatni.\nNe felejtsd el beállítani az alapértelmezett félévet!\nKezdj is hozzá!")
                                    .setGravity(Gravity.TOP | Gravity.LEFT)
                                    .setEnterAnimation(MainActivity.fadeIn)
                    )
                    .setOverlay(new Overlay().disableClick(true));
            MainActivity.mTourGuideHandler.playOn(fab);
        }
        return rootView;

    }

    private static void populateListView(Context cx) {
        //ArrayAdapter<String> adapter = new MyListAdapter(rootView.getContext());

        String[] columns = new String[]{"_id", "item"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        //startManagingCursor(matrixCursor);


        for (int i = 0; i < tantargyList.size(); i++) {
            matrixCursor.addRow(new Object[]{1, tantargyList.get(i)});
        }

        DragNDropCursorAdapter adapter = new DragNDropCursorAdapter(cx,
                R.layout.itemlayout_tantargy,
                matrixCursor,
                new String[]{"item"},
                new int[]{R.id.tNevTextView},
                R.id.handler) {
            @Override
            public void onItemDrop(DragNDropListView parent, View view, int startPosition, int endPosition, long id) {
                super.onItemDrop(parent, view, startPosition, endPosition, id);
                if (startPosition < endPosition) {
                    Tantargy t = Settings.tantargyak[startPosition];
                    for (int i = startPosition; i < endPosition; i++) {
                        Settings.tantargyak[i] = Settings.tantargyak[i + 1];
                    }
                    Settings.tantargyak[endPosition] = t;
                } else if (startPosition > endPosition) {
                    Tantargy t = Settings.tantargyak[startPosition];
                    for (int i = startPosition; i > endPosition; i--) {
                        Settings.tantargyak[i] = Settings.tantargyak[i - 1];
                    }
                    Settings.tantargyak[endPosition] = t;
                }
                Settings.SaveAll(view.getContext());
            }
        };
        list.setDragNDropAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                szerkesztid = i;
                ShowTantargySzerkeszto();
            }
        });
    }

    /*private static class MyListAdapter extends ArrayAdapter<String>{

        public MyListAdapter(Context context) {
            super(context,R.layout.itemlayout_main,tantargyList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflaterr.inflate(R.layout.itemlayout_tantargy, parent, false);
            }
            TextView tantargyTextView = (TextView) itemView.findViewById(R.id.tNevTextView);
            //ImageView deleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    Toast.makeText(rootView.getContext(), Settings.tantargyak[position].nev+" törölve.",
                                            Toast.LENGTH_SHORT).show();
                                    for(int i=position;i<Settings.tantargyakSzama-1;i++)
                                    {
                                        Settings.tantargyak[i]=Settings.tantargyak[i+1];
                                    }
                                    Settings.tantargyakSzama--;
                                    Settings.tantargyList.remove(position);
                                    createList();
                                    Settings.SaveAll(getContext());
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                    builder.setMessage("'"+Settings.tantargyak[position].nev+"' törlése?").setPositiveButton("Igen", dialogClickListener)
                            .setNegativeButton("Nem", dialogClickListener).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    szerkesztid=position;
                    ShowTantargySzerkeszto();
                }
            });
            tantargyTextView.setText(Settings.tantargyak[position].nev);
            return itemView;
        }
    }*/
    static void ShowTantargySzerkeszto() {
        dialog_TantargySzerkeszto javDiag = new dialog_TantargySzerkeszto();
        javDiag.show(MainActivity.fmgr, "asd");
    }
}
