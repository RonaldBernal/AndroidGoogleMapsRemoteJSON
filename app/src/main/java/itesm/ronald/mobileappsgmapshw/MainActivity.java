package itesm.ronald.mobileappsgmapshw;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMarkerClickListener{

    private String jsonURL = "https://raw.githubusercontent.com/RonaldBernal/AndroidGoogleMapsRemoteJSON/master/food.json";
    private ArrayList<String> restaurantNames = new ArrayList<>();
    private ArrayList<LatLng> locations = new ArrayList<>();
    private GoogleMap gm;
    private ArrayList<MarkerOptions> markerOpts = new ArrayList<>();
    private LocationManager locationManager;
    private Location currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.appicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        MapFragment mf = (MapFragment)getFragmentManager().findFragmentById(R.id.fragment);
        gm = mf.getMap();

        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.734419, -103.455093), 16));
        gm.getUiSettings().setMyLocationButtonEnabled(true);
        gm.setOnMarkerClickListener(this);
        gm.setMyLocationEnabled(true);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        currentLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        fetchJson();
    }

    public void fetchJson(){
        JsonRequest jreq = new JsonRequest(this, this.locations);
        jreq.execute(jsonURL);
        try {
            this.restaurantNames = jreq.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        createMarkers(locations, restaurantNames);
    }

    public void createMarkers(ArrayList<LatLng> locations, ArrayList<String> markerTitles){
        int tmp = 0;
        float prevDist = 1000000 ;
        float newDist;
        Location tempLocation = new Location("tempLocation");

        for (int i = 0; i < locations.size(); i++) {
            tempLocation.setLatitude(locations.get(i).latitude);
            tempLocation.setLongitude(locations.get(i).longitude);
            newDist = currentLoc.distanceTo(tempLocation) / 1000;
            if(newDist < prevDist){
                tmp = i;
                prevDist = newDist;
            }
            markerOpts.add(new MarkerOptions()
                    .position(locations.get(i))
                    .title(markerTitles.get(i))
            );
        }

        markerOpts.get(tmp).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        for (int i = 0; i < locations.size(); i++) {
            gm.addMarker(markerOpts.get(i));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < locations.size(); i++) {
            if (marker.getTitle().equals(markerOpts.get(i).getTitle())) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("name", marker.getTitle());
                intent.putExtra("location", "The coordinates of this restaurant are: " + marker.getPosition());
                this.startActivity(intent);
                break;
            }
        }
        return false;
    }
}
