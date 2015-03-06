package itesm.ronald.mobileappsgmapshw;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Ronald on 2/27/2015.
 */
public class JsonRequest extends AsyncTask<String, Void, ArrayList<String>> {
    private Context context;
    private ProgressDialog dialog;
    private ArrayList<LatLng> location = new ArrayList<LatLng>();
    private ArrayList<String> markerTitles = new ArrayList<String>();

    public JsonRequest(Context context, ArrayList<LatLng> location){
        this.context = context;
        this.location = location;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        this.dialog = new ProgressDialog(context);
        this.dialog.setTitle("Loading Loactions");
        this.dialog.show();
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        HttpGet get = new HttpGet(params[0]);
        StringBuilder sb = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        JSONObject result = null;

        try {
            HttpResponse response = client.execute(get);
            StatusLine sl = response.getStatusLine();
            int code = sl.getStatusCode();

            if (code == 200) {

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    sb.append(currentLine);
                }

                result = new JSONObject(sb.toString());

                JSONArray locationArray = result.getJSONArray("Restaurants");
                for (int i = 0; i < locationArray.length(); i++) {
                    JSONObject location = locationArray.getJSONObject(i);
                    this.location.add(
                            new LatLng(
                                    location.getDouble("lat"),
                                    location.getDouble("long")
                            )
                    );
                    this.markerTitles.add(location.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.markerTitles;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result){
        this.dialog.dismiss();
    }

}