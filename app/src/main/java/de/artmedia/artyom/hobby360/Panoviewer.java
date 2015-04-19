package de.artmedia.artyom.hobby360;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.panoramagl.PLIPanorama;
import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.panoramagl.loaders.PLILoader;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.transitions.PLTransitionBlend;
import com.panoramagl.utils.PLUtils;

import java.util.List;


/**
 * Created by Artyom on 06.03.2015.
 */
    public class Panoviewer extends PLView
    {
        //int pano;
        String pano;
        //PLSpherical2Panorama panorama = new PLSpherical2Panorama();
        PLILoader panorama = null;

        public void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);


            /*PLSpherical2Panorama panorama = new PLSpherical2Panorama();
            this.load(new PLJSONLoader("res://raw/json_spherical"));

            Intent intentExtras = getIntent();
            int pano = getIntent().getExtras().getInt("pano");


            panorama.setImage(new PLImage(PLUtils.getBitmap(this, pano), false));
            this.setPanorama(panorama);*/
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

            this.loadPanoramaFromJson();

            return super.onContentViewCreated(mainView);
        }

        //@SuppressWarnings("unused")
        private void loadPanoramaFromJson()
        {
            try
            {
                Context context = this.getApplicationContext();
                pano = getIntent().getExtras().getString("pano");
                //this.load(new PLJSONLoader(pano));

                Toast.makeText(this.getApplicationContext(), pano, Toast.LENGTH_SHORT).show();

                /*panorama = new PLSpherical2Panorama();
                panorama.setImage(new PLImage(PLUtils.getBitmap(context, pano), false));

                if(panorama!=null)
                {
                    panorama.getCamera().lookAt(0.0f,170.0f);
                    this.reset();
                    this.setPanorama(panorama);
                }else {Toast.makeText(this.getApplicationContext(), "no pano", Toast.LENGTH_SHORT).show();}
                this.setLocked(false);*/
                panorama = new PLJSONLoader("res://raw/json_k55");
                if(panorama!=null)
                    this.load(panorama, true, new PLTransitionBlend(2.0f));



            }
            catch (Throwable e)
            {
                Toast.makeText(this.getApplicationContext(), "Fehler: "+e, Toast.LENGTH_SHORT).show();
            }
        }

    }

