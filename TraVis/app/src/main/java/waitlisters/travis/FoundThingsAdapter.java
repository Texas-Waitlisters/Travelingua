package waitlisters.travis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by ChiaHuaBladeWX on 2/25/2018.
 */

public class FoundThingsAdapter extends ArrayAdapter<String[]> /* implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,OnRequestPermissionsResultCallback,
        PermissionUtil.PermissionResultCallback*/ {
    Context context;
    String[][] foundThings;

    private Location mLastLocation;
    double lon;
    double lat;
    private ArrayList<String> permissions = new ArrayList<>();
    private PermissionUtil permissionUtil;
    private GoogleApiClient mGoogleApiClient;

    boolean isPermissionGranted;

    public FoundThingsAdapter(Context c, String[][] foundThings) {
        super(c, 0, foundThings);
        this.foundThings = foundThings;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.found_things_row, parent, false);
        }
        TextView rating = (TextView) convertView.findViewById(R.id.ratingTextview);
        TextView term = (TextView) convertView.findViewById(R.id.termTextview);
        rating.setText((Math.floor(10000*Double.parseDouble(foundThings[position][0].substring(0,foundThings[position][0].length()-1))))/100+"%:");
        term.setText(foundThings[position][1]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PermissionUtil permissionUtil = new PermissionUtil(this);

//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//
//                permissionUtil.check_permission(permissions, "Need GPS permission for getting your location", 1);
//                getLocation();
//                String country = "";
//                if (mLastLocation != null) {
//                    lat = mLastLocation.getLatitude();
//                    lon = mLastLocation.getLongitude();
//                    country = getAddress();
//                }
                Intent intent = new Intent(getContext(), TranslationActivity.class);
                intent.putExtra("TERM", foundThings[position][1]);
//                intent.putExtra("COUNTRY", country);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private String getAddress() {
        Address locationAddress = getAddress(lat, lon);
        if (locationAddress != null) {
            return locationAddress.getCountryName();
        }
        else return null;
    }

    private Address getAddress(double lat, double lon) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            return (geocoder.getFromLocation(lat, lon, 1)).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private void getLocation() {
//        if (isPermissionGranted) {
//            try {
//                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            } catch (SecurityException e) {
//                e.printStackTrace();
//            }
//        }
//    }

        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

    /**
     * Google api callback methods
     */
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
//                + result.getErrorCode());
//    }
//
//    @Override
//    public void onConnected(Bundle arg0) {
//
//        // Once connected with google api, get the location
//        getLocation();
//    }
//
//
//    @Override
//    public void onConnectionSuspended(int arg0) {
//        mGoogleApiClient.connect();
//    }
//
//
//    // Permission check functions
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        // redirects to utils
//        permissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
//
//    }
//
//
//
//
//    @Override
//    public void PermissionGranted(int request_code) {
//        Log.i("PERMISSION","GRANTED");
//        isPermissionGranted=true;
//    }
//
//    @Override
//    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
//        Log.i("PERMISSION PARTIALLY","GRANTED");
//    }
//
//    @Override
//    public void PermissionDenied(int request_code) {
//        Log.i("PERMISSION","DENIED");
//    }
//
//    @Override
//    public void NeverAskAgain(int request_code) {
//        Log.i("PERMISSION","NEVER ASK AGAIN");
//    }

}
