package gcu.ac.uk.gamenews;
import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity{

    // Declare variables
    List<MapData> mapDataLst;
    private Marker[] mapDataMarkerList = new Marker[9];
    private GoogleMap mapStuidios;
    //set the cour of the map markers
    private float markerColours[] = {201.0f,120.0f,300.0f,330.0f,270.0f,120.0f,300.0f,330.0f,270.0f};
    //starting location on the map
    private LatLng latlangStart = new LatLng(55.866008,-4.251422);


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedStateInstance){
        //create the activity
        super.onCreate(savedStateInstance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.map_view);
        //create list to hold map marker data
        mapDataLst = new ArrayList<MapData>();
        //get data from database
        MapDataDBMgr mapDB = new MapDataDBMgr(this,"GameStudios.s3db",null,1);
        try{
            mapDB.dbCreate();
        } catch (IOException e) {//catch any errors
            e.printStackTrace();
        }
        //put database data into list
        mapDataLst = mapDB.allMapData();
        SetUpMap();
        AddMarkers();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void SetUpMap(){
        //create the map with default values
        mapStuidios = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        if(mapStuidios != null){
            mapStuidios.moveCamera(CameraUpdateFactory.newLatLngZoom(latlangStart, 12));
            mapStuidios.setMyLocationEnabled(true);
            mapStuidios.getUiSettings().setCompassEnabled(true);
            mapStuidios.getUiSettings().setMyLocationButtonEnabled(true);
            mapStuidios.getUiSettings().setRotateGesturesEnabled(true);
        }
    }

    //add the markers to the map
    public void  AddMarkers(){
        MarkerOptions marker;
        MapData mapData;
        String mrkTitle;
        String mrkText;

        for(int i = 0; i < mapDataLst.size(); i++)
        {
            //set the data for the markers
            mapData = mapDataLst.get(i);
            mrkTitle = mapData.getName();
            mrkText = "Website: " + mapData.getWebsite();
            marker = SetMarker(mrkTitle, mrkText, new LatLng(mapData.getLatitude(),mapData.getLongitude()),markerColours[i],true);
            //then add the marker to the map
            mapDataMarkerList[i] = mapStuidios.addMarker(marker);
        }
    }

    //create the marker
    public MarkerOptions SetMarker(String title, String snippet, LatLng position, float markerColour, boolean centreAnchor){
        float anchorX;
        float anchorY;
        //set marker position
        if(centreAnchor){
            anchorX = 0.5f;
            anchorY = 0.5f;
        }
        else{
            anchorX = 0.5f;
            anchorY = 1f;
        }
        //set the markers icon and position
        MarkerOptions marker = new MarkerOptions().title(title).snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(markerColour))
                .anchor(anchorX, anchorY).position(position);
        //return the marker
        return marker;
    }
}