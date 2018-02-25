package waitlisters.travis;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class TranslationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionUtil.PermissionResultCallback {

    private TextView term;
    private Spinner language_selector;
    private TextView translated_term;
    private Button suggested;
    boolean isPermissionGranted;
    private Location mLastLocation;
    double lon;
    double lat;
    private ArrayList<String> permissions = new ArrayList<>();
    private PermissionUtil permissionUtil;
    private GoogleApiClient mGoogleApiClient;



    //TODO: This is how you pass info to this activity:
    /*
    Intent intent = new Intent(getBaseContext(), TranslationActivity.class);
    intent.putExtra("TERM", <The Word>);
    intent.putExtra("LANGUAGE_SELECTED", <The Selected Language Default>);
    startActivity(intent);

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        term = (TextView) findViewById(R.id.term);
        TextView translated_term = (TextView) findViewById(R.id.translated_term);
        language_selector = (Spinner) findViewById(R.id.language_selector);
        this.translated_term = (TextView) findViewById(R.id.translated_term);

        String[] arraySpinner = new String[] {
                "Russia", "Germany", "Spain"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        language_selector.setAdapter(adapter);

        language_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                String lang = "";
                    switch (language_selector.getItemAtPosition(pos).toString()) {
                        case "Germany":
                            lang = "de";
                            break;
                        case "Russia":
                            lang = "ru";
                            break;
                        case "Spain":
                            lang = "es";
                            break;
                    }
                    updateLang(lang);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


//        permissionUtil = new PermissionUtil(this);
//
//        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        permissionUtil.check_permission(permissions, "Need GPS permission for getting your location", 1);
//        getLocation();
//        String country = "";
//        if (mLastLocation != null) {
//            lat = mLastLocation.getLatitude();
//            lon = mLastLocation.getLongitude();
//            country = getAddress();
//            ((TextView)findViewById(R.id.language)).setText(country);
//        }

        updateLang("ru");

//        String country = getIntent().getStringExtra("COUNTRY");
//        TextView tv = (TextView) findViewById(R.id.language);
//        tv.setText(country);
        //language_selector.setSelection((indexOfLanguage(getIntent().getStringExtra("LANGUAGE_SELECTED"))));
        //set translated text to something
        //make button take you to google search
    }

    public void updateLang(String lang)
    {
        term.setText(getIntent().getStringExtra("TERM"));
        googlAutocompleteParser trans = new googlAutocompleteParser();
        trans.translateText(getIntent().getStringExtra("TERM"),translated_term, lang);
        boolean newItem = getIntent().getBooleanExtra("NEW", true);
        if (newItem) {
            HistoryItem item = new HistoryItem(term.getText().toString(), new Date().getTime());
            item.save();
        }

        ListView listview = (ListView) this.findViewById(R.id.association_list);

        try{
            googlAutocompleteParser.urlReader(getIntent().getStringExtra("TERM"), listview, this, lang);
        }
        catch(Exception e){}
    }

    private int indexOfLanguage(String language) {
        //TODO: Fix this
        return 0;
    }

        private void getLocation() {
        if (isPermissionGranted) {
            try {
                if (mGoogleApiClient == null)
                Log.d("LOG", "null mGoogle");
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private String getAddress() {
        Address locationAddress = getAddress(lat, lon);
        if (locationAddress != null) {
            return locationAddress.getCountryName();
        }
        else return null;
    }

    private Address getAddress(double lat, double lon) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            return (geocoder.getFromLocation(lat, lon, 1)).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("TAG", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    // Permission check functions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        permissionUtil.toString();
        // redirects to utils
        permissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }




    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
        isPermissionGranted=true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }



}
