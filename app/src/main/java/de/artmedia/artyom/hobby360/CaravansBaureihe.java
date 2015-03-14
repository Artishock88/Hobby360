package de.artmedia.artyom.hobby360;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.artmedia.artyom.hobby360.c_baureihen.c_premium;
import de.artmedia.artyom.hobby360.c_baureihen.deluxe;
import de.artmedia.artyom.hobby360.c_baureihen.excellent;
import de.artmedia.artyom.hobby360.c_baureihen.landhaus;
import de.artmedia.artyom.hobby360.c_baureihen.ontour;
import de.artmedia.artyom.hobby360.c_baureihen.prestige;

/**
 * Created by Artyom on 05.03.2015.
 */
public class CaravansBaureihe extends ActionBarActivity {

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caravans_baureihe);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup des Slide-In-Menus
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(TITLES,ICONS,TITLE,TEASER,IMAGE, this);
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
        //Inflate the menu; this adds Items to the action bar
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

    public void onOntourClick (View view) {
        switch (view.getId()) {
            case (R.id.ontour):
                startActivity(new Intent(this, ontour.class));
                break;
        }
    }

    public void onDeLuxeClick (View view) {
        switch (view.getId()) {
            case (R.id.deluxe):
                startActivity(new Intent(this, deluxe.class));
                break;
        }
    }

    public void onExcellentClick (View view) {
        switch (view.getId()) {
            case (R.id.excellent):
                startActivity(new Intent(this, excellent.class));
                break;
        }
    }

    public void onPrestigeClick (View view) {
        switch (view.getId()) {
            case (R.id.prestige):
                startActivity(new Intent(this, prestige.class));
                break;
        }
    }

    public void onCPremiumClick (View view) {
        switch (view.getId()) {
            case (R.id.c_premium):
                startActivity(new Intent(this, c_premium.class));
                break;
        }
    }

    public void onLandhausClick (View view) {
        switch (view.getId()) {
            case (R.id.landhaus):
                startActivity(new Intent(this, landhaus.class));
                break;
        }
    }
}