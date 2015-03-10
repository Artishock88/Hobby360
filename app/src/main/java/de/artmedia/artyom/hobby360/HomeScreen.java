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

    private Toolbar toolbar;

    public void  onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
