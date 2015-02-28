package itesm.ronald.mobileappsgmapshw;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private String jsonURL = "https://raw.githubusercontent.com/RonaldBernal/AndroidGoogleMapsRemoteJSON/master/food.json";
    private ArrayList<LatLng> locations = new ArrayList<LatLng>();
    private GoogleMap gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.appicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        MapFragment mf = (MapFragment)getFragmentManager().findFragmentById(R.id.fragment);
        gm = mf.getMap();

        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.734536, -103.456609), 14));

        JsonRequest jreq = new JsonRequest(this, this.locations);
        jreq.execute(jsonURL);

        gm.addMarker(new MarkerOptions()
                .position(new LatLng(20.734536, -103.456609))
                .title("Hey"));
    }

}
