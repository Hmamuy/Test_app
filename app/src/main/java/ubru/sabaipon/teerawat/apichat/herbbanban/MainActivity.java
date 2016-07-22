package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private LinearLayout layout_login;
    private Button button_login, button_register, button_guest;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        layout_login = (LinearLayout) findViewById(R.id.layout_login);
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        button_login = (Button) findViewById(R.id.button);
        button_register = (Button) findViewById(R.id.button2);
        button_guest = (Button) findViewById(R.id.button3);

        //button_login Click
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Disable Click
                button_login.setClickable(false);
                //getText String input from Edittext
                String user = userEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                //Check null String
                if (!user.equals("") || !password.equals("")) {
                    FormBody formBody = new FormBody.Builder()
                            .add("user", user)
                            .add("password", password)
                            .build();
                    new Login(Constants.Login, formBody).execute();
                }
            }
        });

        //button_register Click
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra("status", "1");
                intent.putExtra("title", getResources().getString(R.string.signup));
                startActivity(intent);
            }
        });

        //button_guest Click
        button_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListHerb.class);
                intent.putExtra("type", "3");
                intent.putExtra("user", "");
                startActivity(intent);
            }
        });
    }

    public class Login extends AsyncTask<Void, Void, String>{

        private String url;
        private FormBody formBody;

        public Login(String url, FormBody formBody){
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
                JSONArray data = new JSONArray(result);
                JSONObject obj = data.getJSONObject(0);
                if(obj.getString("result").equals("OK")){
                    Log.w("TAG", obj.getString("User"));
                    if(obj.getString("Type").equals("0")){
                        Intent intent = new Intent(MainActivity.this, MenuAdmintActivity.class);
                        intent.putExtra("type", obj.getString("Type"));
                        intent.putExtra("user", obj.getString("User"));
                        startActivity(intent);
                    } else if(obj.getString("Type").equals("1")){
                        Intent intent = new Intent(MainActivity.this, MenuMemberActivity.class);
                        intent.putExtra("type", obj.getString("Type"));
                        intent.putExtra("user", obj.getString("User"));
                        startActivity(intent);
                    } else if(obj.getString("Type").equals("2")){
                        Intent intent = new Intent(MainActivity.this, MenuExpertActivity.class);
                        intent.putExtra("type", obj.getString("Type"));
                        intent.putExtra("user", obj.getString("User"));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        intent.putExtra("status", obj.getString("Type"));
                        startActivity(intent);
                    }
                } else {
                    Log.w("TAG", "FAIL");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            button_login.setClickable(true);
        }
    }
}
