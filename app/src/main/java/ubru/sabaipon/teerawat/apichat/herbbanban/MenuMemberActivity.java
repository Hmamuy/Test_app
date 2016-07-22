package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MenuMemberActivity extends AppCompatActivity {

    private ImageView icon_menu_member_herb, icon_menu_member_add_herb, icon_menu_member_profile;
    private String type;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_member);
        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        user = extras.getString("user");

        icon_menu_member_herb = (ImageView) findViewById(R.id.icon_menu_member_herb);
        icon_menu_member_add_herb = (ImageView) findViewById(R.id.icon_menu_member_add_herb);
        icon_menu_member_profile = (ImageView) findViewById(R.id.icon_menu_member_profile);

        icon_menu_member_herb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMemberActivity.this, ListHerb.class);
                intent.putExtra("type", type);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        icon_menu_member_add_herb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        icon_menu_member_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMemberActivity.this, ProfileActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}
