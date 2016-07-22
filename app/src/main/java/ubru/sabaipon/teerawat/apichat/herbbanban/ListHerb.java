package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.FormBody;

public class ListHerb extends AppCompatActivity {

    private LinkedList<HashMap<String, String>> mListItems;
    private PullAndLoadListView list_main;
    private ProgressBar progressBar;
    private ImageView icon_fab;
    private int widht = 0;
    private int height = 0;
    private String type;
    private String user;
    private ListHerbAdapter herbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                Log.w("TAG", mListItems.get(0).get("id"));
                new PostData(Constants.getListHerb_pulltorefresh, formBody, 0).execute();
            }
        });

        list_main.setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                FormBody formBody = new FormBody.Builder()
                        .add("id", mListItems.get(mListItems.size() - 1).get("id"))
                        .build();
                new PostData(Constants.getListHerb_loadmore, formBody, 1).execute();
            }
        });
        mListItems = new LinkedList<HashMap<String, String>>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.icon1, options);
        widht = options.outWidth;
        height = options.outHeight;

        icon_fab.setVisibility(View.GONE);
        new LoadData(Constants.getListHerb).execute();
    }

    public class ListHerbAdapter extends BaseAdapter{

        private Context context;
        private LinkedList<HashMap<String, String>> mListItems;

        private LayoutInflater inflater;
        private ViewHolder holder;

        private ImageLoaderConfiguration.Builder loaderBuilder;
        private ImageLoader imageLoader = ImageLoader.getInstance();
        private DisplayImageOptions.Builder displayImageOptions;
        private DisplayImageOptions options;


        public ListHerbAdapter(Context context, LinkedList<HashMap<String, String>> mListItems){
            this.context = context;
            this.mListItems = mListItems;
            displayImageOptions = new DisplayImageOptions.Builder();
            displayImageOptions.showImageForEmptyUri(R.mipmap.icon1);
            displayImageOptions.showImageOnFail(R.mipmap.icon1);
            displayImageOptions.cacheInMemory(true);
            displayImageOptions.cacheOnDisk(false);
            displayImageOptions.postProcessor(new BitmapProcessor() {
                @Override
                public Bitmap process(Bitmap bitmap) {
                    return Bitmap.createScaledBitmap(bitmap, widht, height, false);
                }
            });
            options = displayImageOptions.build();
            loaderBuilder = new ImageLoaderConfiguration.Builder(this.context);
            loaderBuilder.diskCacheExtraOptions(widht, height, null);
            ImageLoaderConfiguration config = loaderBuilder.build();
            ImageLoader.getInstance().init(config);
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
                rootview = inflater.inflate(R.layout.custom_list_herb, null);
                holder = new ViewHolder();
                holder.image_herb = (ImageView) rootview.findViewById(R.id.image_herb);
                holder.image_icon_select = (ImageView) rootview.findViewById(R.id.image_icon_select);
                holder.text_herb_name = (TextView) rootview.findViewById(R.id.text_herb_name);
                rootview.setTag(holder);
            } else {
                holder = (ViewHolder) rootview.getTag();
            }

            if(!type.equals("0")){
                if(!mListItems.get(position).get("User").equals(user)){
                    holder.image_icon_select.setVisibility(View.GONE);
                }
            }

            imageLoader.displayImage(mListItems.get(position).get("Image"), holder.image_herb, options);
            holder.text_herb_name.setText(mListItems.get(position).get("Name"));
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
                                    break;
                                case R.id.select_del :
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

            rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String [] data = new String[5];
                    data[0] = mListItems.get(position).get("Image");
                    data[1] = mListItems.get(position).get("Description");
                    data[2] = mListItems.get(position).get("HowTo");
                    data[3] = mListItems.get(position).get("Lat");
                    data[4] = mListItems.get(position).get("Lng");
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("data", data);
                    context.startActivity(intent);
                }
            });
            return rootview;
        }

        class ViewHolder {
            ImageView image_herb;
            ImageView image_icon_select;
            TextView text_herb_name;
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
                    map.put("profile", obj.getString("profile"));
                    map.put("id", obj.getString("id"));
                    map.put("Name", obj.getString("Name"));
                    map.put("Image", obj.getString("Image"));
                    map.put("Description", obj.getString("Description"));
                    map.put("HowTo", obj.getString("HowTo"));
                    map.put("Lat", obj.getString("Lat"));
                    map.put("Lng", obj.getString("Lng"));
                    map.put("Status", obj.getString("Status"));
                    map.put("User", obj.getString("User"));
                    mListItems.add(map);
                }
                herbAdapter = new ListHerbAdapter(ListHerb.this, mListItems);
                list_main.setAdapter(herbAdapter);
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

        public PostData(String url, FormBody formBody, int c){
            this.url = url;
            this.formBody = formBody;
            this.c = c;
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
                Log.w("TAG", "" + data.length());
                HashMap<String, String> map;
                for(int i = 0;i < data.length();i++){
                    JSONObject obj = data.getJSONObject(i);
                    map = new HashMap<String, String>();
                    map.put("profile", obj.getString("profile"));
                    map.put("id", obj.getString("id"));
                    map.put("Name", obj.getString("Name"));
                    map.put("Image", obj.getString("Image"));
                    map.put("Description", obj.getString("Description"));
                    map.put("HowTo", obj.getString("HowTo"));
                    map.put("Lat", obj.getString("Lat"));
                    map.put("Lng", obj.getString("Lng"));
                    map.put("Status", obj.getString("Status"));
                    map.put("User", obj.getString("User"));
                    if(c == 0){
                        mListItems.addFirst(map);
                    } else {
                        mListItems.addLast(map);
                    }
                }
                herbAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(c == 0){
                list_main.onRefreshComplete();
            } else {
                list_main.onLoadMoreComplete();
            }
        }
    }
}
