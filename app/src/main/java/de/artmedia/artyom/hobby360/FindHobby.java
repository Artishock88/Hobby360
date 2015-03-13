package de.artmedia.artyom.hobby360;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static de.artmedia.artyom.hobby360.R.color.find_green;

/**
 * Created by Artyom on 10.03.2015.
 */
public class FindHobby extends ActionBarActivity {

    private Toolbar toolbar;
    private String GPSText;
    boolean gpsOn;
    boolean gpsSet;
    private String latitude;
    private String longitude;
    private String chkDataString = "";
    private String chkDataLoSaved ="";
    private GoogleMap googleMap;
    private Double latitudeDSaved;
    private Double longitudeDSaved;
    private Double latitudeCurr;
    private Double longitudeCurr;
    private Boolean restored = false;


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


        //checken, ob die Position bereits gespeichert wurde
        try {
            FileInputStream chkData = openFileInput("latitude.txt");
            InputStreamReader chkDataReader = new InputStreamReader(chkData);

            char[] chkDataBuffer = new char[300];
            int charReadChk;
            while ((charReadChk = chkDataReader.read(chkDataBuffer))>0)
            {
                String readstring = String.copyValueOf(chkDataBuffer,0,charReadChk);
                chkDataString += readstring;

            }
            chkDataReader.close();

            FileInputStream chkDataLo = openFileInput("longitude.txt");
            InputStreamReader chkDataReaderLo = new InputStreamReader(chkDataLo);

            char[] chkDataBufferLo = new char[300];
            int charReadChkLo;
            while ((charReadChkLo = chkDataReaderLo.read(chkDataBufferLo))>0)
            {
                String readstring2 = String.copyValueOf(chkDataBufferLo,0,charReadChkLo);
                chkDataLoSaved += readstring2;

            }
            chkDataReaderLo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(chkDataString.length()>1)
        {
            Toast.makeText(getApplicationContext(),"gespeicherte Position wurde ausgelesen",Toast.LENGTH_SHORT).show();

            //Aussehen der Buttons anpassen
            //Button verändern
            final Button set_find = (Button) findViewById(R.id.pin_find);

            set_find.setBackgroundColor(getResources().getColor(R.color.find_green));
            set_find.setText(getResources().getString(R.string.find));
            gpsSet = true;

            //Funktion des Buttons auf "Suchen" setzen
            set_find.setOnClickListener(onFind);

            //Marker nach dem Schließen wiederherstellen
            //gespeicherte Longitude abrufen

            //test der Doubles
            latitudeDSaved = Double.parseDouble(chkDataString);
            longitudeDSaved = Double.parseDouble(chkDataLoSaved);

            String werte1 = Double.toString(latitudeDSaved);
            String werte2 = Double.toString(longitudeDSaved);
            Log.d("werte",werte1);
            Log.d("werte2",werte2);

            /*final ImageButton openMap = (ImageButton) findViewById(R.id.openMaps);
            openMap.setVisibility(View.VISIBLE);*/

        }else{

            //deaktivieren des Zurücksetzen-Buttons
            final Button clear_find = (Button) findViewById(R.id.clear_pos);
            clear_find.setEnabled(false);
            clear_find.setBackgroundColor(getResources().getColor(R.color.inactive_grey));
            gpsSet = false;

            //Funktion des Buttons auf "Speichern" setzen
            final Button set_find = (Button) findViewById(R.id.pin_find);
            set_find.setOnClickListener(onSet);

            final ImageButton openMap = (ImageButton) findViewById(R.id.openMaps);
            openMap.setVisibility(View.GONE);
        }

        //Google Map initialisieren
        try{
            initializeMap();
            if(latitudeDSaved!=null && longitudeDSaved!=null){
            MarkerOptions markerSaved = new MarkerOptions().position(new LatLng(latitudeDSaved,longitudeDSaved)).title("Gespeicherter Standort");
            markerSaved.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            googleMap.addMarker(markerSaved);

            CameraPosition cameraPositionRestore = new CameraPosition.Builder().target(new LatLng(latitudeDSaved,longitudeDSaved)).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionRestore));}
            else{Log.d("NoPosition","no position");


            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Karte erzeugen
    private void initializeMap() {
        if(googleMap==null){
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_show)).getMap();
            googleMap.setMyLocationEnabled(true);

            if (googleMap==null){
                Toast.makeText(getApplicationContext(),"Karte konnte nicht erzeugt werden",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onResume(){
        super.onResume();
        initializeMap();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Hinzufügen der Elemente zur Actionbar
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


            latitude = Double.toString(loc.getLatitude());
            longitude = Double.toString(loc.getLongitude());

            latitudeCurr = loc.getLatitude();
            longitudeCurr = loc.getLongitude();



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

    //Standort speichern
    View.OnClickListener onSet = new View.OnClickListener()
    {

        @Override
        public void onClick(View v) {
            if (latitude != null) {
                if (gpsOn == true) {

                    //Button verändern
                    final Button set_find = (Button) findViewById(R.id.pin_find);

                    set_find.setBackgroundColor(getResources().getColor(R.color.find_green));
                    set_find.setText(getResources().getString(R.string.find));

                    //Zurücksetzen-Button aktivieren
                    final Button clear_find = (Button) findViewById(R.id.clear_pos);
                    clear_find.setEnabled(true);
                    clear_find.setBackgroundColor(getResources().getColor(R.color.signal_red));
                    gpsSet = true;

                    //Position lokal speichern
                    try {
                        FileOutputStream fosLa = openFileOutput("latitude.txt", Context.MODE_PRIVATE);
                        fosLa.write(latitude.getBytes());
                        fosLa.close();

                        FileOutputStream fosLo = openFileOutput("longitude.txt", Context.MODE_PRIVATE);
                        fosLo.write(longitude.getBytes());
                        fosLo.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Lokale Daten auslesen
                    try {
                        FileInputStream fisLa = openFileInput("latitude.txt");
                        InputStreamReader inputReadLa = new InputStreamReader(fisLa);

                        char[] inputBufferLa = new char[300];
                        String sLa = "";
                        int charReadLa;

                        while ((charReadLa = inputReadLa.read(inputBufferLa)) > 0) {
                            String readstring = String.copyValueOf(inputBufferLa, 0, charReadLa);
                            sLa += readstring;
                        }
                        inputReadLa.close();

                        FileInputStream fisLo = openFileInput("longitude.txt");
                        InputStreamReader inputReadLo = new InputStreamReader(fisLo);

                        char[] inputBufferLo = new char[300];
                        String sLo = "";
                        int charReadLo;

                        while ((charReadLo = inputReadLo.read(inputBufferLo)) > 0) {
                            String readstring = String.copyValueOf(inputBufferLo, 0, charReadLo);
                            sLo += readstring;
                        }
                        inputReadLo.close();


                        Toast.makeText(getApplicationContext(), "Position wurde gespeichert:\n" + sLa + "\n" + sLo, Toast.LENGTH_SHORT).show();

                        //Funktion des Buttons auf "Suchen" setzen
                        set_find.setOnClickListener(onFind);

                        //Google Maps Button aktivieren
                        /*final ImageButton openMap = (ImageButton) findViewById(R.id.openMaps);
                        openMap.setVisibility(View.VISIBLE);*/

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                } else if (gpsOn == false) {
                    Toast.makeText(getApplicationContext(), "Schalten Sie die Positionierung ein", Toast.LENGTH_SHORT).show();
                }

                //Gespeicherten Marker setzen
                Double latitudeD = Double.parseDouble(latitude);
                Double longitudeD = Double.parseDouble(longitude);
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitudeD,longitudeD)).title("Gespeicherter Standort");
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                googleMap.addMarker(marker);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitudeD,longitudeD)).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            } else {
                Toast.makeText(getApplicationContext(), "wartet auf Satelitten", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //Navigation zum gesetzten Zielpunkt
    View.OnClickListener onFind = new View.OnClickListener()
    {
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"Pfadzeichnung wird implementiert",Toast.LENGTH_SHORT).show();

            MarkerOptions markerCurr = new MarkerOptions().position(new LatLng(latitudeCurr,longitudeCurr)).title("Aktueller Standort");
            markerCurr.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            googleMap.addMarker(markerCurr);
        }
    };

    //Google Maps für Navigation öffnen
    public void onOpenMaps (View view)
    {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + latitudeCurr + "," + longitudeCurr + "&daddr=" + latitudeDSaved + "," + longitudeDSaved));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    public void onClearPos (View view)
    {
        if (gpsSet == true) {

            //Button zurücksetzen
            final Button set_find = (Button) findViewById(R.id.pin_find);

            set_find.setBackgroundColor(getResources().getColor(R.color.main_color));
            set_find.setText(getResources().getString(R.string.pin));
            set_find.setOnClickListener(onSet);

            //deaktivieren des Zurücksetzen-Buttons
            final Button clear_find = (Button) findViewById(R.id.clear_pos);
            clear_find.setEnabled(false);
            clear_find.setBackgroundColor(getResources().getColor(R.color.inactive_grey));
            gpsSet = false;

            //Verstecken des GoogleMaps Buttons
            final ImageButton openMap = (ImageButton) findViewById(R.id.openMaps);
            openMap.setVisibility(View.GONE);

            //lokale Daten überschreiben
            String clear = "0";

            try {
                FileOutputStream fosLa = openFileOutput("latitude.txt", Context.MODE_PRIVATE);
                fosLa.write(clear.getBytes());
                fosLa.close();

                FileOutputStream fosLo = openFileOutput("longitude.txt", Context.MODE_PRIVATE);
                fosLo.write(clear.getBytes());
                fosLo.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Kontrollieren, ob die Daten überschrieben wurden
            try {
                FileInputStream fisLa = openFileInput("latitude.txt");
                InputStreamReader inputReadLa = new InputStreamReader(fisLa);

                char[] inputBufferLa = new char[300];
                String sLa = "";
                int charReadLa;

                while ((charReadLa = inputReadLa.read(inputBufferLa)) > 0) {
                    String readstring = String.copyValueOf(inputBufferLa, 0, charReadLa);
                    sLa += readstring;
                }
                inputReadLa.close();

                FileInputStream fisLo = openFileInput("longitude.txt");
                InputStreamReader inputReadLo = new InputStreamReader(fisLo);

                char[] inputBufferLo = new char[300];
                String sLo = "";
                int charReadLo;

                while ((charReadLo = inputReadLo.read(inputBufferLo)) > 0) {
                    String readstring = String.copyValueOf(inputBufferLo, 0, charReadLo);
                    sLo += readstring;
                }
                inputReadLo.close();

                //Marker entfernen
                googleMap.clear();

                Toast.makeText(this,"Position wurde zurückgesetzt:\n" + sLa + "\n" + sLo, Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }




}
