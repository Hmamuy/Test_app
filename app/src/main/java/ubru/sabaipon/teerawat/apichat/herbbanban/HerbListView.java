package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HerbListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herb_list_view);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.herb_table, null);
        cursor.moveToFirst();
        int intCount = cursor.getCount();

        final String[] nameStrings = new String[intCount];
        final String[] imageStrings = new String[intCount];
        final String[] descripStrings = new String[intCount];
        final String[] howtoStrings = new String[intCount];
        final String[] latStrings = new String[intCount];
        final String[] lngStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameStrings[i] = cursor.getString(1);
            imageStrings[i] = cursor.getString(2);
            descripStrings[i] = cursor.getString(3);
            howtoStrings[i] = cursor.getString(4);
            latStrings[i] = cursor.getString(5);
            lngStrings[i] = cursor.getString(6);

            cursor.moveToNext();

        }   // for
        cursor.close();

        HerbAdapter herbAdapter = new HerbAdapter(this, imageStrings, nameStrings);
        ListView herbListView = (ListView) findViewById(R.id.listView);
        herbListView.setAdapter(herbAdapter);

        herbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(HerbListView.this, DetailActivity.class);

                intent.putExtra("Name", nameStrings[i]);
                intent.putExtra("Image", imageStrings[i]);
                intent.putExtra("Description", descripStrings[i]);
                intent.putExtra("HowTo", howtoStrings[i]);
                intent.putExtra("Lat", latStrings[i]);
                intent.putExtra("Lng", lngStrings[i]);

                startActivity(intent);

            }   // onItem
        });


    }   // Main Method

}   // Main Class
