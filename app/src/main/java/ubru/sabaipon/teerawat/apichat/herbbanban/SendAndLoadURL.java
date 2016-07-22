package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendAndLoadURL {

    String result = "";

    public String getData(String url){
        Log.w("TAG", "Load");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .header("Accept", "application/json")
                .addHeader("Accept", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            } else {
                Log.w("TAG", "FAIL");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("TAG", "Exception");
            return "";
        }
    }

    public String postData(String url, FormBody formBody){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .header("Accept", "application/json")
                .addHeader("Accept", "application/x-www-form-urlencoded")
                .post(formBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
