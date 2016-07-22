package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;

public class EditUserActivity extends AppCompatActivity {

    //Explicit
    private EditText passwordEditText, nameEditText, surnameEditText, phoneEditText, addressEditText;
    private Button button_edit_user;
    private TextView text_signup_title, text_edit_user;
    private String title;
    private String [] data;
    private String page;
    private String type;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Bundle extrar = getIntent().getExtras();
        title = extrar.getString("title");
        page = extrar.getString("page");
        data = extrar.getStringArray("data");
        type = extrar.getString("type");
        user = extrar.getString("user");

        //Bind Widget
        text_signup_title = (TextView) findViewById(R.id.text_signup_title);
        text_edit_user = (TextView) findViewById(R.id.text_edit_user);
        nameEditText = (EditText) findViewById(R.id.editText3);
        surnameEditText = (EditText) findViewById(R.id.editText4);
        phoneEditText = (EditText) findViewById(R.id.editText5);
        addressEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText8);
        button_edit_user = (Button) findViewById(R.id.button_edit_user);

        button_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String phone= phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();
                if(!password.equals("") && !name.equals("") && !surname.equals("")
                        && !phone.equals("") && !address.equals("")){
                    FormBody formBody = new FormBody.Builder()
                            .add("id", data[0])
                            .add("user", data[1])
                            .add("password", password)
                            .add("name", name)
                            .add("surname", surname)
                            .add("tel", phone)
                            .add("address", address)
                            .build();
                    new SendEdit(Constants.editUser, formBody).execute();
                } else {
                    new MyAlertDialog().myDialog(EditUserActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่องคะ");
                }
            }
        });
        text_signup_title.setText(title);
        button_edit_user.setText(getResources().getString(R.string.edit));

        text_edit_user.setText(data[1]);
        passwordEditText.setText(data[2]);
        nameEditText.setText(data[3]);
        surnameEditText.setText(data[4]);
        phoneEditText.setText(data[5]);
        addressEditText.setText(data[6]);

    }

    public class SendEdit extends AsyncTask<Void, Void, String>{

        private String url;
        private FormBody formBody;

        public SendEdit(String url, FormBody formBody){
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
                    if(page.equals("1")){
                        Intent intent = new Intent(EditUserActivity.this, ListExpert.class);
                        intent.putExtra("user", user);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    } else if(page.equals("2")) {
                        Intent intent = new Intent(EditUserActivity.this, ListMember.class);
                        intent.putExtra("user", user);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(EditUserActivity.this, ProfileActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    Log.w("TAG", "FAIL");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(page.equals("1")){
            Intent intent = new Intent(EditUserActivity.this, ListExpert.class);
            intent.putExtra("user", user);
            intent.putExtra("type", type);
            startActivity(intent);
        } else if(page.equals("2")){
            Intent intent = new Intent(EditUserActivity.this, ListMember.class);
            intent.putExtra("user", user);
            intent.putExtra("type", type);
            startActivity(intent);
        } else {
            Intent intent = new Intent(EditUserActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("type", type);
            startActivity(intent);
        }
        finish();
    }
}
