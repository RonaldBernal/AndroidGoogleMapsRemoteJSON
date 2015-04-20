package itesm.ronald.mobileappsgmapshw;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");

        TextView titleView = (TextView) findViewById(R.id.titleTxtV);
        TextView locView = (TextView) findViewById(R.id.locTxtV);

        titleView.setText(title);
        locView.setText(location);
    }

}
