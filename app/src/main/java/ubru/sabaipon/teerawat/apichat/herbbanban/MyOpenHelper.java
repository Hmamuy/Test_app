package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by masterUNG on 3/27/16 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit
    public static final String database_name = "Herb.db";
    private static final int database_version = 1;

    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Status text, " +
            "Name text, " +
            "Surname text, " +
            "Phone text, " +
            "Address text);";

    private static final String create_herb_table = "create table herbTABLE (" +
            "_id integer primary key, " +
            "Name text, " +
            "Image text, " +
            "Description text, " +
            "HowTo text, " +
            "Lat text, " +
            "Lng text, " +
            "Status text);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
        sqLiteDatabase.execSQL(create_herb_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String [] Login(String user, String password){
        try {
            String [] data = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase();
            String strSQL_user = "SELECT * FROM userTABLE WHERE User='" + user + "'";
            Cursor cursor = db.rawQuery(strSQL_user, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    String strSQL_user_password = "SELECT * FROM userTABLE WHERE User='" + user +
                            "' AND Password='" + password + "'";
                    cursor = db.rawQuery(strSQL_user_password, null);
                    if (cursor.moveToFirst()) {
                        data = new String[cursor.getColumnCount()];
                        for(int i = 0;i < data.length;i++){
                            data[i] = cursor.getString(i);
                        }
                    } else {
                        data = new String[1];
                        data[0] = "password";
                    }
                } else {
                    data = new String[1];
                    data[0] = "no";
                }
            } else {
                data = new String[1];
                data[0] = "no";
            }
            cursor.close();
            db.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}   // Main Class
