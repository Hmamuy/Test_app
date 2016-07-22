package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText, nameEditText,
            surnameEditText, phoneEditText, addressEditText;
    private Button button_sign_up;
    private TextView text_signup_title;
    private String status;
    private String title;
    private String [] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Bundle extrar = getIntent().getExtras();
        status = extrar.getString("status");
        title = extrar.getString("title");

        //Bind Widget
        text_signup_title = (TextView) findViewById(R.id.text_signup_title);
        nameEditText = (EditText) findViewById(R.id.editText3);
        surnameEditText = (EditText) findViewById(R.id.editText4);
        phoneEditText = (EditText) findViewById(R.id.editText5);
        addressEditText = (EditText) findViewById(R.id.editText6);
        userEditText = (EditText) findViewById(R.id.editText7);
        passwordEditText = (EditText) findViewById(R.id.editText8);
        button_sign_up = (Button) findViewById(R.id.button_sign_up);

        //button_sign_up Click
        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set click false
                button_sign_up.setClickable(false);
                //Get Value From Edit Text
                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString();
                String nameString = nameEditText.getText().toString().trim();
                String surnameString = surnameEditText.getText().toString();
                String phoneString = phoneEditText.getText().toString().trim();
                String addressString = addressEditText.getText().toString();

                //Check null from Edittext
                if(!userString.equals("") && !passwordString.equals("") && !nameString.equals("")
                        && !surnameString.equals("") && !phoneString.equals("") && !addressString.equals("")){
                    FormBody formBody = new FormBody.Builder()
                            .add("user", userString)
                            .add("password", passwordString)
                            .add("status", status)
                            .add("name", nameString)
                            .add("surname", surnameString)
                            .add("tel", phoneString)
                            .add("address", addressString)
                            .build();
                    new SendSignup(Constants.Signup, formBody).execute();
                } else {
                    new MyAlertDialog().myDialog(SignUpActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่องคะ");
                }
            }
        });

        text_signup_title.setText(title);
    }

    public class SendSignup extends AsyncTask<Void, Void, String>{

        private String url;
        private FormBody formBody;

        public SendSignup(String url, FormBody formBody){
            this.url = url;
            this.formBody = formBody;
        }


        //doInBackground Sending Data
        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            result = new SendAndLoadURL().postData(url, formBody);
            return result;
        }

        //Sending complete
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.w("TAG", result);
            try {
                JSONArray data = new JSONArray(result);
                JSONObject obj = data.getJSONObject(0);
                if(obj.getString("result").equals("OK")){
                    Toast.makeText(SignUpActivity.this, "บันทึกข้อมูล เรียบร้อย ขอบคุณคะ",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    new MyAlertDialog().myDialog(SignUpActivity.this, "เกิดข้อผิดพลาด",
                            "อาจมีผู้ชื่อผู้ใช้ User อยู่แล้วคะ");
                    Log.w("TAG", "FAIL");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            button_sign_up.setClickable(true);
        }
    }
}