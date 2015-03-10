package de.artmedia.artyom.hobby360;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Artyom on 10.03.2015.
 */
public class FindHobby extends ActionBarActivity {

    private Toolbar toolbar;
    private String GPSText;
    boolean gpsOn;


    public void  onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findhobby);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LocationManager myHobbyLoc = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener myHobbyLocListener = new HobbyLocationListener();
        myHobbyLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,myHobbyLocListener);

        gpsOn = myHobbyLoc.isProviderEnabled(LocationManager.GPS_PROVIDER);

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
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class HobbyLocationListener implements LocationListener
    {

        public void onLocationChanged(Location loc)
        {
            loc.getLatitude();
            loc.getLongitude();
            GPSText = "Latitude = " + loc.getLatitude() + "\n" +
            "Longitude = " + loc.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            gpsOn=true;
        }

        @Override
        public void onProviderDisabled(String provider) {
            gpsOn=false;
        }
    }


    public void onSetPin(View view)
    {


        if(gpsOn == true) {
            Toast.makeText(getApplicationContext(), "Position wurde gespeichert", Toast.LENGTH_SHORT).show();

            TextView GPSloc = (TextView) findViewById(R.id.gps_pos);
            GPSloc.setText("Folgende Position wurde gespeichert: \n" +
                    GPSText);
        }
        else if(gpsOn == false)
        {
            Toast.makeText(getApplicationContext(), "Schalten Sie die Positionierung ein", Toast.LENGTH_LONG).show();
        }

    }




}
