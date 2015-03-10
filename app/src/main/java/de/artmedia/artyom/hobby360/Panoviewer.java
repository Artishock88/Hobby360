package de.artmedia.artyom.hobby360;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.utils.PLUtils;


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
