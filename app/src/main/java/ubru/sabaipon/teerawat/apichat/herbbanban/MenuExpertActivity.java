package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MenuExpertActivity extends AppCompatActivity {

    private ImageView icon_menu_expert_herb, icon_menu_expert_add_herb, icon_menu_expert_confirn,
            icon_menu_expert_profile;
    private String type;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_expert);
        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        user = extras.getString("user");

        icon_menu_expert_herb = (ImageView) findViewById(R.id.icon_menu_expert_herb);
        icon_menu_expert_add_herb = (ImageView) findViewById(R.id.icon_menu_expert_add_herb);
        icon_menu_expert_confirn = (ImageView) findViewById(R.id.icon_menu_expert_confirn);
        icon_menu_expert_profile = (ImageView) findViewById(R.id.icon_menu_expert_profile);

        icon_menu_expert_herb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuExpertActivity.this, ListHerb.class);
                intent.putExtra("type", type);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        icon_menu_expert_add_herb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        icon_menu_expert_confirn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        icon_menu_expert_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuExpertActivity.this, ProfileActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}
