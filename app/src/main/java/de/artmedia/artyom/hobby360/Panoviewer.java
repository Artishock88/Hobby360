package de.artmedia.artyom.hobby360;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.utils.PLUtils;

import java.util.List;


/**
 * Created by Artyom on 06.03.2015.
 */
public class Panoviewer extends PLView {



public void onCreate (Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    this.load(new PLJSONLoader("res://raw/json_spherical"));
    PLSpherical2Panorama panorama = new PLSpherical2Panorama();
    Intent intentExtras = getIntent();
    int pano = getIntent().getExtras().getInt("pano");


    panorama.setImage(new PLImage(PLUtils.getBitmap(this, pano), false));
    this.setPanorama(panorama);

}


}
