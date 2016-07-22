package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.squareup.picasso.Picasso;

public class DetailActivity extends FragmentActivity {

    private ImageView image_herb;
    private TextView text_herb_detail_descript, text_herb_detail_howto;
    private String [] data;
    private GoogleMap mMap;

    //Image
    private ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
    private int widht = 0;
    private int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herb_detail);
        Bundle extras = getIntent().getExtras();
        data = extras.getStringArray("data");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.icon1, options);
        widht = options.outWidth;
        height = options.outHeight;

        image_herb = (ImageView) findViewById(R.id.image_herb);
        text_herb_detail_descript = (TextView) findViewById(R.id.text_herb_detail_descript);
        text_herb_detail_howto = (TextView) findViewById(R.id.text_herb_detail_howto);
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        imageLoader.displayImage(data[0], image_herb, new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.icon1)
                .showImageOnFail(R.mipmap.icon1)
                .cacheOnDisk(false)
                .cacheInMemory(true)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return Bitmap.createScaledBitmap(bitmap, widht, height, false);
                    }
                })
                .build()
        );
        text_herb_detail_descript.setText(data[1]);
        text_herb_detail_howto.setText(data[2]);
        addMark(Double.parseDouble(data[3]), Double.parseDouble(data[4]));
    }

    public void addMark(double lat, double lng){
        LatLng coordinate = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(coordinate));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15));
    }
}
