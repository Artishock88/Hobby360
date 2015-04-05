package de.artmedia.artyom.hobby360.r_baureihen;


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
import android.widget.ImageView;
import android.widget.TextView;

import de.artmedia.artyom.hobby360.FindHobby;
import de.artmedia.artyom.hobby360.MyAdapter;
import de.artmedia.artyom.hobby360.MyModelAdapter;
import de.artmedia.artyom.hobby360.R;

/**
 * Created by Artyom on 05.03.2015.
 */
public class s_alkoven extends ActionBarActivity {

    //Menuitems und Icons definieren
    String TITLES[] = {"Home", "Händler", "Hilfe", "Über"};
    int ICONS[] = {R.drawable.ic_home_grey,R.drawable.ic_store_mall_directory_grey,R.drawable.ic_help_grey,R.drawable.ic_info_outline_grey};

    //Titel und Teaser definieren (für programmatische Änderungen)
    String TITLE = "Hobby 360";
    String TEASER = "Ihr Zugang zur Welt von Hobby";
    int IMAGE = R.drawable.h360_icon;

    //Thumbnail für die Liste definieren
    int THUMB[] = {R.drawable.thumb_t70_hge,R.drawable.thumbnail_dummy,R.drawable.thumbnail_dummy,R.drawable.thumbnail_dummy};

    //Entsprechende Panoramen definieren
    int PANO[] = {R.raw.k55_pano,R.raw.spherical_pano_test,R.raw.spherical_pano,R.raw.spherical_pano_test};

    //Modellnamen und Beschreibungen definieren;
    String MODEL[] = {"A70 GM", "Testmodellname", "Testmodell_2", "Testmodell_3"};
    String mINFO[] = {"zukünftiger Text", "Testtext für die einzelnen Modelle","abc","def"};

    private Toolbar toolbar;

    //RecyclerView für das Slide-In-Menu
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    ActionBarDrawerToggle mDrawerToggle;

    //RecyclerView für die Modelliste
    RecyclerView mModelView;
    RecyclerView.Adapter mModelAdapter;
    RecyclerView.LayoutManager mModelLayoutManager;

    public void  onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modellview_recycler);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Siesta Alkoven");

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

        //Setup der Modelliste
        mModelView = (RecyclerView) findViewById(R.id.modell_list);
        mModelView.setHasFixedSize(true);
        mModelAdapter = new MyModelAdapter(THUMB,PANO,MODEL,mINFO,this);
        mModelView.setAdapter(mModelAdapter);
        mModelLayoutManager = new LinearLayoutManager(this);
        mModelView.setLayoutManager(mModelLayoutManager);

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



}

