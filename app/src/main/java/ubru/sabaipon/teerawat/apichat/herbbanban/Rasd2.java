package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Rasd2 extends AppCompatActivity {

    private String[] myResultStrings;
    private ImageView image_icon1, image_icon2, image_icon3, image_icon4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasd2);

        //Bind Widget
        image_icon1 = (ImageView) findViewById(R.id.image_icon1);
        image_icon2 = (ImageView) findViewById(R.id.image_icon2);
        image_icon3 = (ImageView) findViewById(R.id.image_icon3);
        image_icon4 = (ImageView) findViewById(R.id.image_icon4);

        //Set click image_icon1
        image_icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rasd2.this, HerbListView.class);
                startActivity(intent);
            }
        });

        //Set click image_icon2
        image_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Set click image_icon3
        image_icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Set click image_icon4
        image_icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rasd2.this, UpdateMapsActivity.class);
                startActivity(intent);
            }
        });

    }
}

