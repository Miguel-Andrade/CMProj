package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerFC;
import com.kingoftheill.app1.domain2.PlayerItem;
import com.kingoftheill.app1.presentation.ui.util.BagFragment;
import com.kingoftheill.app1.presentation.ui.util.CraftFragment;
import com.kingoftheill.app1.presentation.ui.util.CustomInfoWindowAdapter;
import com.kingoftheill.app1.presentation.ui.util.SectionsPageAdapter;
import com.kingoftheill.app1.presentation.ui.util.SectionsPageAdapter2;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.HashMap;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {

    public final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;

    private static final String TAG = "MapActivity";
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int DEFAULT_ZOOM = 100;
    private final int INTERVAL = 500;

    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private Location mCurrentLocation;
    private CameraPosition mCameraPosition;
    private LatLng mDefaultLocation = new LatLng(38.756618, -9.156069);

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Circle circleLocation;

    private CircularProgressBar pg;
    private ImageButton player_image;

    //USERS POSITIONS MARKERS
    private HashMap<String, Marker> mMarkers = new HashMap<>();

    //ITEMS POSITIONS
    private HashMap<String, Marker> mItemsMarkers = new HashMap<>();

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private static DocumentReference PLAYER;
    private static CollectionReference PLAYER_ITEMS;

    private PlayerFC playerFC;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    private GeoFire geoFire;
    private GeoFire geoFire2;

    private GeoLocation geoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mUsername = mFirebaseUser.getUid();
        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
        PLAYER_ITEMS = mFirebaseFirestore.collection("Users/" + mUsername + "/Items");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/UserPositions/");
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("/ItemsPositions/");
        geoFire = new GeoFire(ref);
        geoFire2 = new GeoFire(ref2);

        player_image = findViewById(R.id.player_image);
        pg = findViewById(R.id.player_xp);
        ProgressBar lifepg = findViewById(R.id.life);
        ProgressBar disxppg = findViewById(R.id.disease_xp);
        TextView name = findViewById(R.id.name);

        //UPDATE THE PLAYER
        PLAYER.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (documentSnapshot.exists()) {
                playerFC = documentSnapshot.toObject(PlayerFC.class);
                    player_image.setBackgroundResource(getApplicationContext().getResources()
                            .getIdentifier(playerFC.getImage(), "drawable", getApplicationContext().getPackageName()));
                    pg.setProgressWithAnimation(playerFC.getCurrXP());
                    lifepg.setProgress(playerFC.getLife());
                    disxppg.setProgress(playerFC.getDisCurrXP());
            }
        });

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        getLocationPermission();

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // PLAYER DETAILS
        player_image.setOnClickListener(v ->
                Toast.makeText(getApplicationContext(),"hhahah", Toast.LENGTH_SHORT).show());

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        geoFire.removeLocation(mUsername);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        geoFire.removeLocation(mUsername);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Map Style
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_sytle));

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            buildGoogleApiClient();
        else
            buildGoogleApiClient();

        // Set a listener for marker click.
        mGoogleMap.setOnMarkerClickListener(this);

        // Set a listener for info window events.
        mGoogleMap.setOnInfoWindowClickListener(this);


        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;

        geoLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
        geoFire.setLocation(mUsername, geoLocation);
        //geoFire2.setLocation("Item1", new GeoLocation(location.getLatitude()-0.0001, location.getLongitude()));
        //geoFire.setLocation( "rafael", new GeoLocation(location.getLatitude()+0.0001, location.getLongitude()));

        if(circleLocation != null){
            circleLocation.remove();
        }
        //Circle around current location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CircleOptions circleOptions = new CircleOptions()
               .center(latLng)
               .radius(7)
               .strokeColor(0x80000000)
               .strokeWidth(5)
               .fillColor(0x26FF0000);
        circleLocation = mGoogleMap.addCircle(circleOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,19));

    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
                mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
                mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude());
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( latLng, DEFAULT_ZOOM));
                        getUsers();
                        getItems();
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mGoogleMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mGoogleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //String[] ya = (String[]) marker.getTag();
        //Log.w("MarkerClick", "Name: " +  marker.getTag());
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        HashMap<String, Object> temp = (HashMap<String, Object>) marker.getTag();
        boolean user = (boolean) temp.get("user");
        DocumentReference ref = (DocumentReference) temp.get("ref");

        if (!user) {
            PLAYER_ITEMS.whereEqualTo("itemId", ref).limit(1).get().addOnSuccessListener(documentSnapshot2 -> {
                if (!documentSnapshot2.isEmpty()) {
                    PlayerItem p = documentSnapshot2.getDocumentChanges().get(0).getDocument().toObject(PlayerItem.class);
                    documentSnapshot2.getDocumentChanges().get(0)
                            .getDocument().getReference().update(PlayerItem.QUANTITY, (p.getQuantity() + 1))
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Item Collected", Toast.LENGTH_SHORT).show());
                }
                else {
                    PLAYER_ITEMS.whereEqualTo("itemId", "").limit(1).get().addOnSuccessListener(documentSnapshots -> {
                        if (!documentSnapshots.isEmpty()) {
                            PlayerItem pl = new PlayerItem(ref, 1, ((String) temp.get("image")));
                            documentSnapshots.getDocumentChanges().get(0).getDocument().getReference().set(pl)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Item Collected", Toast.LENGTH_SHORT).show());
                        }
                        else
                            Toast.makeText(this, "Your Inventory is Full", Toast.LENGTH_SHORT).show();
                    });
                }

            }).addOnFailureListener(e ->
            Log.e(TAG, "Error picking item" + e.getMessage()));
        }

        else {
            Intent intent = new Intent(this, PlayerInfoActivity.class);
            if (Math.hypot(marker.getPosition().latitude - mLastKnownLocation.getLatitude(),
                    marker.getPosition().longitude - mLastKnownLocation.getLongitude()) > 7)
                intent.putExtra("attack", false);
            else
                intent.putExtra("attack", true);

            intent.putExtra("ref", ref.toString());
            startActivity(intent);
        }
        marker.hideInfoWindow();
    }

    public Bitmap myMarker(String type, int id) {
        int height = 100;
        int width = 100;
        int drawableR;
        if (type.equals("item")) {
            drawableR = R.drawable.calendula_flower_icon;
        }
        else
            switch(id) {

                case 0:
                    drawableR = R.drawable.bubonic_plague_doc_icon_3;
                    break;

                case 1:
                    drawableR = R.drawable.bubonic_plague_doc_icon_3;
                    break;

                case 2:
                    drawableR = R.drawable.bubonic_plague_doc_icon_3;
                    break;
                default:
                    drawableR = R.drawable.bubonic_plague_doc_icon_3;
            }

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                drawableR);
        return Bitmap.createScaledBitmap(icon, width, height, false);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter2 adapter = new SectionsPageAdapter2(getSupportFragmentManager());
        adapter.addFragment(new BagFragment(), "Bag");
        adapter.addFragment(new CraftFragment(), "Craft");
        viewPager.setAdapter(adapter);
    }

    private void getUsers() {
        GeoQuery geoQuery = geoFire.queryAtLocation(
                new GeoLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 0.2);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!key.equals(mUsername)) {
                    Marker m = mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.latitude, location.longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(myMarker("player", 1))));

                    mFirebaseFirestore.document("/Users/" + key).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            HashMap<String, Object> o = new HashMap<>();
                                            o.put("ref", documentSnapshot.getReference());
                                            o.put("user", true);
                                            m.setTag(o);
                                            m.setTitle((String) documentSnapshot.get("name"));
                                        }
                                    }
                                )
                            .addOnFailureListener(e ->
                                Log.e("Error on markerTag", e.getMessage()));
                    mMarkers.put(key, m);
                }
            }

            @Override
            public void onKeyExited(String key) {
                if (!key.equals(mUsername))
                    mMarkers.remove(key);
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                if (!key.equals(mUsername))
                    mMarkers.get(key).setPosition(new LatLng(location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                // Hide loading progressbar
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e("Erro GeoFire", error.getMessage());
            }
        });
    }

    private void getItems() {

        GeoQuery geoQuery = geoFire2.queryAtLocation(
                new GeoLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 0.2);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                Marker m = mGoogleMap.addMarker(new MarkerOptions()
                        .title(key)
                        .position(new LatLng(location.latitude, location.longitude))
                        .icon(BitmapDescriptorFactory.fromBitmap(myMarker("item", 1))));

                mFirebaseFirestore.document("/Items/" + key).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            HashMap<String, Object> o = new HashMap<>();
                                            o.put("ref", documentSnapshot.getReference());
                                            o.put("image", documentSnapshot.get("image"));
                                            o.put("user", false);
                                            m.setTag(o);
                                            m.setTitle((String) documentSnapshot.get("name"));
                                            Log.d(TAG, "Item tagName add");
                                        }
                                    }
                            )
                            .addOnFailureListener(e ->
                                    Log.e("Error on markerTag", e.getMessage()));
                mItemsMarkers.put(key, m);
            }

            @Override
            public void onKeyExited(String key) {
                    mItemsMarkers.remove(key);
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                    mItemsMarkers.get(key).setPosition(new LatLng(location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                // Hide loading progressbar
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e("Erro GeoFire2", error.getMessage());
            }
        });

    }
}