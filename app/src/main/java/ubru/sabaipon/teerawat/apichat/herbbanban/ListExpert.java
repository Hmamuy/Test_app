package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.FormBody;

public class ListExpert extends AppCompatActivity {

    private LinkedList<HashMap<String, String>> mListItems;
    private PullAndLoadListView list_main;
    private ProgressBar progressBar;
    private ImageView icon_fab;
    private String type;
    private String user;

    private ListExpertAdapter expertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        Bundle extrar = getIntent().getExtras();
        type = extrar.getString("type");
        user = extrar.getString("user");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        icon_fab = (ImageView) findViewById(R.id.fab);
        list_main = (PullAndLoadListView) findViewById(R.id.list_main);

        list_main.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FormBody formBody = new FormBody.Builder()
                        .add("id", mListItems.get(0).get("id"))
                        .build();
                new PostData(Constants.getListExpert_pulltorefresh, formBody, 0, 0).execute();
            }
        });

        list_main.setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                FormBody formBody = new FormBody.Builder()
                        .add("id", mListItems.get(mListItems.size() - 1).get("id"))
                        .build();
                new PostData(Constants.getListExpert_loadmore, formBody, 1, 0).execute();
            }
        });

        icon_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListExpert.this, SignUpActivity.class);
                intent.putExtra("status", "2");
                intent.putExtra("title", getResources().getString(R.string.signup_Expert));
                startActivity(intent);
            }
        });
        mListItems = new LinkedList<HashMap<String, String>>();
        new LoadData(Constants.getListExpert).execute();
    }

    public class ListExpertAdapter extends BaseAdapter{

        private Context context;
        private LinkedList<HashMap<String, String>> mListItems;

        private LayoutInflater inflater;
        private ViewHolder holder;

        public ListExpertAdapter(Context context, LinkedList<HashMap<String, String>> mListItems){
            this.context = context;
            this.mListItems = mListItems;
        }

        @Override
        public int getCount() {
            return mListItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rootview = convertView;
            if(rootview == null){
                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                rootview = inflater.inflate(R.layout.custom_list_member, null);
                holder = new ViewHolder();
                holder.image_icon_select = (ImageView) rootview.findViewById(R.id.image_icon_select);
                holder.text_member_user = (TextView) rootview.findViewById(R.id.text_member_user);
                holder.text_member_name = (TextView) rootview.findViewById(R.id.text_member_name);
                holder.text_member_surname = (TextView) rootview.findViewById(R.id.text_member_surname);
                holder.text_member_tel = (TextView) rootview.findViewById(R.id.text_member_tel);
                holder.text_member_address = (TextView) rootview.findViewById(R.id.text_member_address);
                rootview.setTag(holder);
            } else {
                holder = (ViewHolder) rootview.getTag();
            }

            holder.image_icon_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_select, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.select_edit :
                                    String [] data = new String[8];
                                    data[0] = mListItems.get(position).get("id");
                                    data[1] = mListItems.get(position).get("User");
                                    data[2] = mListItems.get(position).get("Password");
                                    data[3] = mListItems.get(position).get("Name");
                                    data[4] = mListItems.get(position).get("Surname");
                                    data[5] = mListItems.get(position).get("Phone");
                                    data[6] = mListItems.get(position).get("Address");
                                    data[7] = mListItems.get(position).get("Type");
                                    Intent intent = new Intent(context, EditUserActivity.class);
                                    intent.putExtra("title", getResources().getString(R.string.edit_expert));
                                    intent.putExtra("user", user);
                                    intent.putExtra("type", type);
                                    intent.putExtra("data", data);
                                    intent.putExtra("page", "1");
                                    context.startActivity(intent);
                                    finish();
                                    break;
                                case R.id.select_del :
                                    FormBody formBody = new FormBody.Builder()
                                            .add("id", mListItems.get(position).get("id"))
                                            .add("user", mListItems.get(position).get("User"))
                                            .build();
                                    new PostData(Constants.delUser, formBody, 3,
                                            position).execute();
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            holder.text_member_user.setText("User : " + mListItems.get(position).get("User"));
            holder.text_member_name.setText(getResources().getString(R.string.member_name)
                    + " " + mListItems.get(position).get("Name"));
            holder.text_member_surname.setText(getResources().getString(R.string.member_surname)
                    + " " + mListItems.get(position).get("Surname"));
            holder.text_member_tel.setText(getResources().getString(R.string.member_tel)
                    + " " + mListItems.get(position).get("Phone"));
            holder.text_member_address.setText(getResources().getString(R.string.member_address)
                    + " " + mListItems.get(position).get("Address"));

            return rootview;
        }

        class ViewHolder {
            ImageView image_icon_select;
            TextView text_member_user;
            TextView text_member_name;
            TextView text_member_surname;
            TextView text_member_tel;
            TextView text_member_address;
        }
    }

    public class LoadData extends AsyncTask<Void, Void, String>{

        private String url;

        public LoadData(String url){
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            result = new SendAndLoadURL().getData(url);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.w("TAG", result);
            try {
                JSONArray data = new JSONArray(result);
                Log.w("TAG", "" + data.length());
                HashMap<String, String> map;
                for(int i = 0;i < data.length();i++){
                    JSONObject obj = data.getJSONObject(i);
                    map = new HashMap<String, String>();
                    map.put("id", obj.getString("id"));
                    map.put("User", obj.getString("User"));
                    map.put("Password", obj.getString("Password"));
                    map.put("Name", obj.getString("Name"));
                    map.put("Surname", obj.getString("Surname"));
                    map.put("Phone", obj.getString("Phone"));
                    map.put("Address", obj.getString("Address"));
                    map.put("Type", obj.getString("Type"));
                    mListItems.add(map);
                }
                expertAdapter = new ListExpertAdapter(ListExpert.this, mListItems);
                list_main.setAdapter(expertAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    public class PostData extends AsyncTask<Void, Void, String>{

        private String url;
        private FormBody formBody;
        private int c;
        private int position;

        public PostData(String url, FormBody formBody, int c, int position){
            this.url = url;
            this.formBody = formBody;
            this.c = c;
            this.position = position;
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
            if(c < 2){
                try {
                    JSONArray data = new JSONArray(result);
                    Log.w("TAG", "" + data.length());
                    HashMap<String, String> map;
                    for(int i = 0;i < data.length();i++){
                        JSONObject obj = data.getJSONObject(i);
                        map = new HashMap<String, String>();
                        map.put("id", obj.getString("id"));
                        map.put("User", obj.getString("User"));
                        map.put("Password", obj.getString("Password"));
                        map.put("Name", obj.getString("Name"));
                        map.put("Surname", obj.getString("Surname"));
                        map.put("Phone", obj.getString("Phone"));
                        map.put("Address", obj.getString("Address"));
                        map.put("Type", obj.getString("Type"));
                        if(c == 0){
                            mListItems.addFirst(map);
                        } else {
                            mListItems.addLast(map);
                        }
                    }
                    expertAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(c == 0){
                    list_main.onRefreshComplete();
                } else {
                    list_main.onLoadMoreComplete();
                }
            } else {
                mListItems.remove(position);
                expertAdapter.notifyDataSetChanged();
                Toast.makeText(ListExpert.this, getResources().getString(R.string.del_list),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
