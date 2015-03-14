package de.artmedia.artyom.hobby360;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;



/**
 * Created by Artyom on 27.02.2015.
 */
public class HomeScreen extends ActionBarActivity{

    //Menuitems und Icons definieren
    String TITLES[] = {"Home", "Händler", "Hilfe", "Über"};
    int ICONS[] = {R.drawable.ic_home_grey,R.drawable.ic_store_mall_directory_grey,R.drawable.ic_help_grey,R.drawable.ic_info_outline_grey};

    //Titel und Teaser definieren (für programmatische Änderungen)
    String TITLE = "Hobby 360";
    String TEASER = "Ihr Zugang zur Welt von Hobby";
    int IMAGE = R.drawable.h360_icon;

    private Toolbar toolbar;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    ActionBarDrawerToggle mDrawerToggle;

    public void  onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup des Slide-In-Menus
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(TITLES,ICONS,TITLE,TEASER,IMAGE);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){
          @Override
        public void onDrawerOpened(View drawerView){
              super.onDrawerOpened(drawerView);
          }
            @Override
        public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }
        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu; Menüelemente zur Actionbar hinzufügen
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    //einzelne Menüelemente aufrufen
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case (R.id.findhobby_actions):
                startActivity(new Intent(this, FindHobby.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


public void onKastenwagenClick (View view)
{
    switch (view.getId())
    {
        case (R.id.kastenwagen):
            startActivity(new Intent(this, Kastenwagen.class));
            break;
    }
}
public void onReisemobileClick(View view)
{
    switch (view.getId())
    {
        case (R.id.reisemobile):
            startActivity(new Intent(this, ReisemobileBaureihe.class));
            break;
    }
}

public void onCaravansClick(View view)
{
    switch (view.getId())
    {
        case (R.id.caravans):
            startActivity(new Intent(this, CaravansBaureihe.class));
            break;
    }
}
}
