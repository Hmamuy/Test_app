package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Resd1 extends AppCompatActivity {
    private String[] myResultStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resd1);

        myResultStrings = getIntent().getStringArrayExtra("Data");
    }
    public void clickReadAll(View view) {

        startActivity(new Intent(this, HerbListView.class));

    }
    public void clickUpdate(View view) {
        startActivity(new Intent(this, UpdateMapsActivity.class));
    }


}
