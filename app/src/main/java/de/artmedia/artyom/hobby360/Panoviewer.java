package de.artmedia.artyom.hobby360;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.panoramagl.PLIPanorama;
import com.panoramagl.PLIView;
import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.panoramagl.loaders.PLILoader;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.transitions.PLTransitionBlend;
import com.panoramagl.utils.PLUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import static de.artmedia.artyom.hobby360.R.drawable.pano_rotation;
import static de.artmedia.artyom.hobby360.R.drawable.pano_rotation_2d_deactive;


/**
 * Created by Artyom on 06.03.2015.
 */
    public class Panoviewer extends PLView
    {
        //int pano;
        String pano;
        String lString;
        //PLSpherical2Panorama panorama = new PLSpherical2Panorama();
        PLILoader panorama = null;
        //private JSONObject jsonObject;
        //private String strJSONValueOf = "\"sensorialRotation\": true,";
        //private JSONObject json;
        //private JSONObject accelerometer;
        private Context context;

        public void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            screenRot();
            /*PLSpherical2Panorama panorama = new PLSpherical2Panorama();
            this.load(new PLJSONLoader("res://raw/json_spherical"));

            Intent intentExtras = getIntent();
            int pano = getIntent().getExtras().getInt("pano");


            panorama.setImage(new PLImage(PLUtils.getBitmap(this, pano), false));
            this.setPanorama(panorama);*/
            final Button pano_info = (Button)findViewById(R.id.pano_info);
            pano_info.setOnClickListener(new Button.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View info_popup = layoutInflater.inflate(R.layout.popup_layout, null);
                    final PopupWindow info_window = new PopupWindow(info_popup, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                    Button close_popup = (Button)info_popup.findViewById(R.id.popup_weiter);
                    close_popup.setOnClickListener(new Button.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            info_window.dismiss();
                        }
                    });
                    info_window.showAsDropDown(pano_info, 0, 0);
                }
            });
        }

        /*protected void onPause()
        {
            super.onPause();
            panorama = null;
            System.gc();
        }
        protected void onResume()
        {
            super.onResume();
            loadPanorama();
        }*/

        public int getScreenOrientation()
        {
            int config = getResources().getConfiguration().orientation;
            return config;
        }

        private void screenRot(){

            final Button rot2d = (Button) findViewById(R.id.pano_rotation);

            switch (getScreenOrientation())
            {
                case (0):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d));
                    rot2d.setEnabled(true);

                    pano = getIntent().getExtras().getString("pano");
                    lString = pano+"_3d";
                    loadPanoramaFromJson(lString);

                    break;
                case (1):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d_deactive));
                    rot2d.setEnabled(false);

                    pano = getIntent().getExtras().getString("pano");
                    loadPanoramaFromJson(pano);

                    break;
                case (2):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d));
                    rot2d.setEnabled(true);
                    //rot2d.setOnClickListener(rotActive);
                    pano = getIntent().getExtras().getString("pano");
                    lString = pano+"_3d";
                    loadPanoramaFromJson(lString);

                    break;
            }
        }

        public void onPause()
        {
            super.onPause();
            System.gc();
        }

        @Override
        protected View onContentViewCreated(View contentView)
        {
            ViewGroup mainView = (ViewGroup)this.getLayoutInflater().inflate(R.layout.panoviewer, null);
            mainView.addView(contentView,0);

            //this.loadPanoramaFromJson();

            return super.onContentViewCreated(mainView);
        }

        private void loadPanoramaFromJson(String loadString)
        {
            try
            {
                Context context = this.getApplicationContext();
                //pano = getIntent().getExtras().getString("pano");
                panorama = new PLJSONLoader(loadString);
                if(panorama!=null)
                    this.load(panorama, true, new PLTransitionBlend(2.0f));


            }
            catch (Throwable e)
            {
                Toast.makeText(this.getApplicationContext(), "Fehler: "+e, Toast.LENGTH_SHORT).show();
            }
        }

        public void onBackClick(View view)
        {
            switch(view.getId())
            {
                case(R.id.pano_back):
                    super.onBackPressed();
                    break;
            }

        }

       /* public void onRotClick(View view)
        {
            final Button rot2d = (Button) findViewById(R.id.pano_rotation);
            switch (view.getId())
            {
                case(R.id.pano_rotation):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d_lock));
                    pano = getIntent().getExtras().getString("pano");
                    loadPanoramaFromJson(pano);
                    break;
            }
        }*/

        /*View.OnClickListener rotActive = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                final Button rot2d = (Button) findViewById(R.id.pano_rotation);
                lString = pano+"_3d";
                loadPanoramaFromJson(lString);
                rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d_lock));
                rot2d.setOnClickListener(rotDeactive);
                Toaster(lString);
            }
        };

        View.OnClickListener rotDeactive = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Button rot2d = (Button) findViewById(R.id.pano_rotation);
                loadPanoramaFromJson(pano);
                rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d));
                rot2d.setOnClickListener(rotActive);
                Toaster(pano);
            }
        };

        public void Toaster (String panoValue)
        {
            Toast.makeText(this.getApplicationContext(),panoValue,Toast.LENGTH_LONG).show();
        }*/

    }

