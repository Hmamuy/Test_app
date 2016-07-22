package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    private ListView list_main_menu;
    private Integer [] icon;
    private String [] detail;
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Bundle extrar = getIntent().getExtras();
        status = extrar.getString("status");

        if(status.equals("0")){
            icon = new Integer[]{R.mipmap.icon1, R.mipmap.icon2, R.mipmap.icon3, R.mipmap.icon4};
            detail = new String[]{"สมุนไพร", "อนุมัติ", "ลสมาชิก", "ผู้เชี่ยวชาญ"};
        } else { // 2
            icon = new Integer[]{R.mipmap.icon1, R.mipmap.icon2};
            detail = new String[]{"สมุนไพร", "อนุมัติ"};
        }

        list_main_menu = (ListView) findViewById(R.id.list_main_menu);
        list_main_menu.setAdapter(new MainMenuAdapter(MainMenu.this, detail, icon));
    }

    public class MainMenuAdapter extends BaseAdapter{

        private Context context;
        private String [] detail;
        private Integer [] icon;
        private LayoutInflater inflater;
        private ViewHolder holder;

        public MainMenuAdapter(Context context, String [] detail, Integer [] icon){
            this.context = context;
            this.detail = detail;
            this.icon = icon;
        }

        @Override
        public int getCount() {
            return detail.length;
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
                rootview = inflater.inflate(R.layout.custom_list_main_menu, null);
                holder = new ViewHolder();
                holder.image_icon = (ImageView) rootview.findViewById(R.id.image_icon);
                holder.text_detail = (TextView) rootview.findViewById(R.id.text_detail);
                rootview.setTag(holder);
            } else {
                holder = (ViewHolder) rootview.getTag();
            }
            holder.image_icon.setImageResource(icon[position]);
            holder.text_detail.setText(detail[position]);
            rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == 0){
                        Intent intent = new Intent(context, ListHerb.class);
                        intent.putExtra("status", status);
                        context.startActivity(intent);
                    } else if(position == 1){

                    } else if (position == 2){
                        Intent intent = new Intent(context, ListMember.class);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ListExpert.class);
                        context.startActivity(intent);
                    }
                }
            });
            return rootview;
        }

        class ViewHolder {
            ImageView image_icon;
            TextView text_detail;
        }
    }
}
