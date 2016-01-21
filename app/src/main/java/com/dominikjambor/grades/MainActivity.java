package com.dominikjambor.grades;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import tourguide.tourguide.TourGuide;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    static FragmentManager sfmgr;
    static android.app.FragmentManager fmgr;
    static Context cx;
    static FragmentManager fragmentManager;
    private int menuPosition = 0;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout frame;
    static int verc = 0;
    static View ndrFirstId;
    int currentScreen = 0;
    static String vern = "0";
    static TourGuide mTourGuideHandler;
    boolean sureExit = false;
    static Animation fadeIn;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fragmentManager = getSupportFragmentManager();
        try {
            int app_ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            verc = app_ver;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Settings.LoadSavedData(getApplicationContext());
        try {
            String app_vern = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            vern = app_vern;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int pos = getIntent().getIntExtra("pos", -1);
        if (pos > -1) {
            Intent intent = new Intent(this, TantargyNezetActivity.class);
            intent.putExtra("TANTARGY_ID", pos);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        if (!Settings.mainfragment_tutorial || !Settings.tantargyfragment_tutorial || Settings.tinfo_tutorial)
            mTourGuideHandler = TourGuide.init(this).with(TourGuide.Technique.Click);
        if (ViewConfiguration.get(this).hasPermanentMenuKey()) {
            FrameLayout navbarcorrect = (FrameLayout) findViewById(R.id.container);
            navbarcorrect.setPadding(0, 0, 0, 16);
        } else {
            FrameLayout navbarcorrect = (FrameLayout) findViewById(R.id.container);
            navbarcorrect.setPadding(0, 0, 0, 64);
        }

        cx = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        fmgr = getFragmentManager();
        sfmgr = getSupportFragmentManager();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawerlist);

        frame = (LinearLayout) findViewById(R.id.llayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (mDrawerList.getWidth() * slideOffset * 0.25f);
                if (Settings.mainfragment_tutorial && Settings.tantargyfragment_tutorial) {
                    frame.setTranslationX(moveFactor);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment objFragment = null;
        menuPosition = position;
        switch (position) {
            case 0:
                objFragment = new fragment_main();
                currentScreen = 0;
                break;
            case 1:
                objFragment = new fragment_tantargyak();
                currentScreen = 1;
                break;
            case 2:
                objFragment = new fragment_settings();
                currentScreen = 2;
                break;
            case 3:
                objFragment = new fragment_easteregg();
                currentScreen = 3;
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            switch (menuPosition) {
                case 0:
                    getMenuInflater().inflate(R.menu.main, menu);
                    break;
                case 1:
                    getMenuInflater().inflate(R.menu.tantargymenu, menu);
                    break;
                default:
                    menu.clear();
                    break;
            }
            restoreActionBar();
            return true;
        } else {
            menu.clear();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mNavigationDrawerFragment.isDrawerOpen()){
            mDrawerLayout.closeDrawers();
        }
        else if (currentScreen != 0) {
            onNavigationDrawerItemSelected(0);
        } else if (!sureExit) {
            sureExit = true;
            Toast.makeText(this, "Nyomd meg mégegyszer a kilépéshez.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sureExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.tantargyhozzaad:
                ShowTantargyHozzaado();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    static void ShowTantargyHozzaado() {
        dialog_TantargyHozzaado javDiag = new dialog_TantargyHozzaado();
        javDiag.show(MainActivity.fmgr, "asd");
    }
}
