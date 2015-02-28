package itesm.ronald.mobileappsgmapshw;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity {
    GoogleMap gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mf = (MapFragment)getFragmentManager().findFragmentById(R.id.fragment);
        gm = mf.getMap();

        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.734536, -103.456609), 14));

        gm.addMarker(new MarkerOptions()
                .position(new LatLng(20.734536, -103.456609))
                .title("Hey"));
    }

}
