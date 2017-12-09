package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerFC;
import com.kingoftheill.app1.domain2.PlayerItem;
import com.kingoftheill.app1.domain2.Positions;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //PLAYER POSITIONS MARKERS
    private Map<DocumentReference, MarkerCustom> UserPositions;
    //PLAYER ITEMS
    private List<PlayerItem> PlayerItems;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private static DocumentReference PLAYER;
    private static DocumentReference PLAYER_POSITION;
    private static CollectionReference PLAYER_ITEMS;
    
    private static DocumentReference ATTACKER;
    
    private static CollectionReference POSITIONS;
    private PlayerFC playerFC;

    //RECYCLER VIEW
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getEmail();
            PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
            PLAYER_POSITION = mFirebaseFirestore.document("Positions/" + mUsername);
            PLAYER_ITEMS = mFirebaseFirestore.collection("Users/" + mUsername + "/Items");
            ATTACKER = mFirebaseFirestore.document("Positions/rafael.r.t.96@gmail.com");
            POSITIONS = mFirebaseFirestore.collection("Positions");
            UserPositions = new HashMap<>();
            PlayerItems = new ArrayList<>();
            for (int i =0; i<=29; i++) {
                PlayerItems.add(new PlayerItem(null, 10, ""));
            }
        }

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

        //UPDATE THE HASHMAP WITH THE MOST RECENT POSITIONS
        POSITIONS.addSnapshotListener(this, ((documentSnapshots, e) -> {
            if (!documentSnapshots.isEmpty()) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges ()) {
                    DocumentSnapshot tempDoc = doc.getDocument();
                    Positions temp = tempDoc.toObject(Positions.class);
                    UserPositions.put(tempDoc.getReference(), new MarkerCustom( new MarkerOptions()
                            .position(temp.getPos())
                            .snippet(tempDoc.getId())
                            .title(tempDoc.getId())
                            .icon(BitmapDescriptorFactory.fromBitmap(myMarker(temp.getType())))
                    ));
                }
            }
        }));

        //UPDATE PLAYER ITEMS
        PLAYER_ITEMS.addSnapshotListener(this, ((documentSnapshots, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen:error", e);
                return;
            }
            if (!documentSnapshots.isEmpty()) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    PlayerItem pI = doc.getDocument().toObject(PlayerItem.class);
                    int pos = Integer.parseInt(doc.getDocument().getId())-1;
                    PlayerItems.set(pos, pI);
                    mAdapter.notifyItemChanged(pos);
                    Log.w(TAG, "Item"+ pos +"was updated.");
                }
            }
        }));

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 5);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyAdapter.RecyclerViewClickListener listener = (view, position) -> {
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();
        };
        mAdapter = new MyAdapter(this, PlayerItems, listener);
        //mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);



        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        getLocationPermission();

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


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
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            buildGoogleApiClient();
        else
            buildGoogleApiClient();

        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = null;
                try {
                    // Getting view from the layout file info_window_layout
                    v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);

                    ImageView itemImage = findViewById(R.id.item);
                    itemImage.setBackgroundResource(getResources().getIdentifier( "logo_pic_bubonic_bird",
                            "drawable", getPackageName()));
                } catch (Exception ev) {
                    System.out.print(ev.getMessage());
                }
                return v;
            }
        });

        // Set a listener for marker click.
        mGoogleMap.setOnMarkerClickListener(this);

        // Set a listener for info window events.
        mGoogleMap.setOnInfoWindowClickListener(this);
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
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
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
        PLAYER_POSITION.update("pos", new GeoPoint(mLastKnownLocation.getLatitude(),
                mLastKnownLocation.getLongitude()))
                .addOnSuccessListener( aa -> Log.d(TAG, "My Position saved."))
                .addOnFailureListener( aa -> {Log.d(TAG, "My Position saved failed.");
                    Log.e(TAG, "Exception");});

        ATTACKER.update("pos", new GeoPoint(mLastKnownLocation.getLatitude()+0.0001,
                mLastKnownLocation.getLongitude()))
                .addOnSuccessListener( aa -> Log.d(TAG, "Attacker Position saved."))
                .addOnFailureListener( aa -> {Log.d(TAG, "Attacker Position saved failed.");
                    Log.e(TAG, "Exception");});

        if(circleLocation != null){
            circleLocation.remove();
        }
        //Circle around current location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CircleOptions circleOptions = new CircleOptions()
               .center(latLng)
               .radius(playerFC.getTotalRange())
               .strokeColor(0x80000000)
               .strokeWidth(5)
               .fillColor(0x26FF0000);
        circleLocation = mGoogleMap.addCircle(circleOptions);

        //USERPOSITIONS ON MAP
        for (Map.Entry<DocumentReference, MarkerCustom> entry : UserPositions.entrySet()) {
            if (!PLAYER_POSITION.equals(entry.getKey())) {
                MarkerCustom temp = entry.getValue();
                if (temp.getM() == null) {
                    Marker m = mGoogleMap.addMarker(temp.getMk());
                    m.setTag(entry.getKey());
                    temp.setM(m);
                }
                else {
                    temp.getM().remove();
                    Marker m = mGoogleMap.addMarker(temp.getMk());
                    m.setTag(entry.getKey());
                    temp.setM(m);
                }
            }
        }

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
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, PlayerInfoActivity.class);
        intent.putExtra("name", (String) marker.getTag());
        startActivity(intent);
    }

    public Bitmap myMarker(int type) {
        int height = 100;
        int width = 100;
        int drawableR = 0;
        switch(type) {

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
                R.drawable.bubonic_plague_doc_icon_3);
        Bitmap smallMarker = Bitmap.createScaledBitmap(icon, width, height, false);
        return smallMarker;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MarkerCustom {
        public MarkerOptions Mk;
        public Marker M;

        public MarkerCustom(MarkerOptions Mk) {
            this.Mk = Mk;
        }

        public void setM(Marker m) {
            M = m;
        }

        public MarkerOptions getMk() {
            return Mk;
        }

        public void setMk(MarkerOptions mk) {
            Mk = mk;
        }

        public Marker getM() {
            return M;
        }
    }
}