package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;

public class ProfileActivity extends AppCompatActivity {

    private TextView text_profile_name, text_profile_surname, text_profile_phone, text_profile_address,
            text_profile_user, text_profile_password;
    private Button button_profile_edit;
    private String type;
    private String user;
    private String [] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        Bundle extrar = getIntent().getExtras();
        type = extrar.getString("type");
        user = extrar.getString("user");

        text_profile_name = (TextView) findViewById(R.id.text_profile_name);
        text_profile_surname = (TextView) findViewById(R.id.text_profile_surname);
        text_profile_phone = (TextView) findViewById(R.id.text_profile_phone);
        text_profile_address = (TextView) findViewById(R.id.text_profile_address);
        text_profile_user = (TextView) findViewById(R.id.text_profile_user);
        text_profile_password = (TextView) findViewById(R.id.text_profile_password);
        button_profile_edit = (Button) findViewById(R.id.button_profile_edit);

        button_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditUserActivity.class);
                intent.putExtra("title", getResources().getString(R.string.edit_profile));
                intent.putExtra("user", user);
                intent.putExtra("type", type);
                intent.putExtra("data", data);
                intent.putExtra("page", "3");
                startActivity(intent);
                finish();
            }
        });

        FormBody formBody = new FormBody.Builder().add("user", user).build();
        new LoadProfile(Constants.getProfileUser, formBody).execute();
    }

    public class LoadProfile extends AsyncTask<Void, Void, String>{

        private String url;
        private FormBody formBody;

        public LoadProfile(String url, FormBody formBody){
            this.url = url;
            this.formBody = formBody;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            result = new SendAndLoadURL().postData(url, formBody);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.w("TAG", result);
            try {
                JSONArray json = new JSONArray(result);
                JSONObject obj = json.getJSONObject(0);
                data = new String[8];
                data[0] = obj.getString("id");
                data[1] = obj.getString("User");
                data[2] = obj.getString("Password");
                data[3] = obj.getString("Name");
                data[4] = obj.getString("Surname");
                data[5] = obj.getString("Phone");
                data[6] = obj.getString("Address");
                data[7] = obj.getString("Type");
                text_profile_name.setText(data[3]);
                text_profile_surname.setText(data[4]);
                text_profile_phone.setText(data[5]);
                text_profile_address.setText(data[6]);
                text_profile_user.setText(data[1]);
                text_profile_password.setText(data[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
