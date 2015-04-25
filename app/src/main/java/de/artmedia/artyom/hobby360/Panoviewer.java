package de.artmedia.artyom.hobby360;

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
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import static de.artmedia.artyom.hobby360.R.drawable.pano_rotation_2d_deactive;


/**
 * Created by Artyom on 06.03.2015.
 */
    public class Panoviewer extends PLView
    {
        //int pano;
        String pano;
        //PLSpherical2Panorama panorama = new PLSpherical2Panorama();
        PLILoader panorama = null;
        //private JSONObject jsonObject;
        //private String strJSONValueOf = "\"sensorialRotation\": true,";
        private JSONObject json;
        private JSONObject accelerometer;
        private Context context;

        public void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            try {
                screenRot();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this.getApplicationContext(), "your dev was an idiot",Toast.LENGTH_SHORT).show();
            }

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

        public void JSONLoader (Context context, String url) throws JSONException {
            try
            {
                url = url.trim();
                InputStream is = null;
                if(url.indexOf("res://")==0)
                {
                    int sepPos = url.lastIndexOf("/");
                    int resId = context.getResources().getIdentifier(url.substring(sepPos+1),url.substring(6, sepPos),context.getPackageName());
                    is = context.getResources().openRawResource(resId);
                }
                else
                is = context.openFileInput(url);
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                is.close();
                this.loadJSON(new String(bytes, "utf-8"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }

        }

        protected void loadJSON(String jsonString)
        {
            Toast.makeText(this.getApplicationContext(),jsonString, Toast.LENGTH_LONG).show();
            if(jsonString!=null)
            {
                try {
                    json = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException("JSON parse failed", e);
                }
            }
            else
                throw new RuntimeException("JSON string is empty");
        }

        public int getScreenOrientation()
        {
            int config = getResources().getConfiguration().orientation;
            return config;
        }

        private void screenRot() throws JSONException {
            final Button rot3d = (Button) findViewById(R.id.pano_rotation_3d);
            final Button rot2d = (Button) findViewById(R.id.pano_rotation);


            switch (getScreenOrientation())
            {
                case (0):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d));
                    rot2d.setEnabled(true);
                    rot3d.setBackground(getResources().getDrawable(R.drawable.pano_rotation));
                    rot3d.setEnabled(true);

                    break;
                case (1):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d_deactive));
                    rot2d.setEnabled(false);
                    rot3d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_deactive));
                    rot3d.setEnabled(false);


                    pano = getIntent().getExtras().getString("pano");
                    JSONLoader(this, pano);

                    //The accelerometer has those values, as all if-statements are true (tried with Toast.)
                    accelerometer = json.getJSONObject("accelerometer");
                    try {
                        if (accelerometer.has("enabled")) {
                            accelerometer.put("enabled", true);
                        }
                        if (accelerometer.has("leftRightEnabled")) {
                            accelerometer.put("leftRightEnabled", true);
                        }
                        if (accelerometer.has("upDownEnabled")) {
                            accelerometer.put("upDownEnabled", true);
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    loadPanoramaFromJson();
                    break;
                case (2):
                    rot2d.setBackground(getResources().getDrawable(R.drawable.pano_rotation_2d));
                    rot2d.setEnabled(true);
                    rot3d.setBackground(getResources().getDrawable(R.drawable.pano_rotation));
                    rot3d.setEnabled(true);

                    pano = getIntent().getExtras().getString("pano");
                    JSONLoader(this, pano);

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

        private void loadPanoramaFromJson()
        {
            try
            {
                Context context = this.getApplicationContext();
                pano = getIntent().getExtras().getString("pano");
                panorama = new PLJSONLoader(pano);

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

        public void on3dActive(View view)
        {
            switch (view.getId())
            {
                case(R.id.pano_rotation_3d):

                    break;
            }
        }



    }

