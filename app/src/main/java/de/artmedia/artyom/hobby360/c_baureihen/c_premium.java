package de.artmedia.artyom.hobby360.c_baureihen;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import de.artmedia.artyom.hobby360.FindHobby;
import de.artmedia.artyom.hobby360.R;

/**
 * Created by Artyom on 05.03.2015.
 */
public class c_premium extends ActionBarActivity {

        private Toolbar toolbar;

        public void  onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.modellview);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

//Fill the top Infobox
            TextView titel = (TextView) findViewById(R.id.modell_title);
            titel.setText("660 WFU");

            TextView beschreibung = (TextView) findViewById(R.id.modell_text);
            beschreibung.setText("Informationstext kommt hier");

            ImageView thumbnail = (ImageView) findViewById(R.id.modell_image);
            thumbnail.setImageResource(R.drawable.thumbnail_dummy);
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

