package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class UpdateMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean status = true;
    private MyAlertDialog myAlertDialog;
    private EditText nameEditText, propertyEditText, howtoEditText;
    private String nameString, perpertyString, howtoString;
    private double latADouble, lngADouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_herb);

        nameEditText = (EditText) findViewById(R.id.editText9);
        propertyEditText = (EditText) findViewById(R.id.editText10);
        howtoEditText = (EditText) findViewById(R.id.editText11);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myAlertDialog = new MyAlertDialog();

    }   // onCreate

    public void clickUpdateHerbToServer(View view) {

        nameString = nameEditText.getText().toString().trim();
        perpertyString = propertyEditText.getText().toString().trim();
        howtoString = howtoEditText.getText().toString().trim();

        if (status) {
            //Non Marker
            myAlertDialog.myDialog(this, "ยั่งไม่กำหนดพิกัด", "โปรคลิกที่แผนที่ เพื่อกำหนดพิกัด");

        } else if (nameString.equals("") || perpertyString.equals("") || howtoString.equals("")) {
            myAlertDialog.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่อง คะ");
        } else {
            updateToServer();
        }

    }   // update

    private void updateToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = new FormEncodingBuilde()
//                .add("isAdd", "true")
//                .add("Name", nameString)
//                .add("Image", "http://project-com-tech.ubru.ac.th/~cpt2ys5702/Image/h2.png")
//                .add("Description", perpertyString)
//                .add("HowTo", howtoString)
//                .add("Lat", Double.toString(latADouble))
//                .add("Lng", Double.toString(lngADouble))
//                .add("Status", "1")
//                .build();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url("http://project-com-tech.ubru.ac.th/~cpt2ys5702/add_herb.php").post(requestBody).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                finish();
//            }
//        });

    }   // update

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(15.118437, 104.902535);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                status = false;

                mMap.clear();

                mMap.addMarker(new MarkerOptions()
                        .position(latLng));

                latADouble = latLng.latitude;
                lngADouble = latLng.longitude;

            }   // onMap
        });

    }   // onMap
}   // Main Class
