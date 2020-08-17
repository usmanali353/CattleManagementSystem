package fyp.cms;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fyp.cms.Firebase_Operations.firebase_operations;
import fyp.cms.Models.Orders;
import fyp.cms.Models.cartItems;
import fyp.cms.Models.dbhelper;
import fyp.cms.Models.user;
import fyp.cms.Utils.utils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Listener {
    private GoogleMap mMap;
    EasyWayLocation easyWayLocation;
    double lat=0.0,lng=0.0;
    ArrayList<cartItems> cartItems;
    ArrayList<String> sellerIds;
    SharedPreferences prefs;
    Geocoder geocoder;
    List<Address> addresses;
    user u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
         u=new Gson().fromJson(prefs.getString("user_info",null),user.class);
        cartItems=new ArrayList<>();
        sellerIds=new ArrayList<>();
        Cursor productsList=new dbhelper(this).get_products_in_cart(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(productsList.getCount()==0){
            Toast.makeText(this,"No Products in Cart", Toast.LENGTH_LONG).show();
        }else {
            while (productsList.moveToNext()) {
                cartItems.add(new cartItems(productsList.getString(1), productsList.getString(6), productsList.getString(7), productsList.getBlob(3), productsList.getInt(4), productsList.getInt(2), productsList.getString(8), productsList.getString(9), productsList.getString(10)));
            }
        }
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        easyWayLocation = new EasyWayLocation(MapsActivity.this ,request,true,this);
        easyWayLocation.startLocation();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                for(int i=0;i<cartItems.size();i++){
                    sellerIds.add(cartItems.get(i).getSellerId());
                }
                try{
                     geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                     addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
                }catch(Exception e){
                    e.printStackTrace();
                    Log.e("exp",e.getMessage());
                }
                Orders order=new Orders(sellerIds,FirebaseAuth.getInstance().getCurrentUser().getUid(),"UnConformed",addresses.get(0).getAddressLine(0),u.getName(),u.getEmail(),u.getPhone(),latLng.latitude,latLng.longitude,cartItems, utils.getCurrentDate(),new dbhelper(MapsActivity.this).getTotalOfAmount(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                firebase_operations.placeOrders(MapsActivity.this,order);
            }
        });
        // Add a marker in Sydney and move the camera
    }

    @Override
    public void locationOn() {
        easyWayLocation.startLocation();
    }

    @Override
    public void currentLocation(Location location) {
        if(location!=null){
            lat=location.getLatitude();
            lng=location.getLongitude();
            LatLng sydney = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    @Override
    public void locationCancelled() {

    }
}
