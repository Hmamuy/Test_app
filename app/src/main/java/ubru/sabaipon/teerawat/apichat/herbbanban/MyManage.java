package ubru.sabaipon.teerawat.apichat.herbbanban;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 3/27/16 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_User = "User";
    public static final String column_Password = "Password";
    public static final String column_Status = "Status";
    public static final String column_Name = "Name";
    public static final String column_Surname = "Surname";
    public static final String column_Phone = "Phone";
    public static final String column_Address = "Address";

    public static final String herb_table = "herbTABLE";
    public static final String column_Image = "Image";
    public static final String column_Description = "Description";
    public static final String column_HowTo = "HowTo";
    public static final String column_Lat = "Lat";
    public static final String column_Lng = "Lng";

    public MyManage(Context context) {

        //Create & Connected
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   // Constructor

    public long addValueToSQLite(int intTABLE,
                                 String strColumn1,
                                 String strColumn2,
                                 String strColumn3,
                                 String strColumn4,
                                 String strColumn5,
                                 String strColumn6,
                                 String strColumn7) {

        long myLong = 0;
        ContentValues contentValues = new ContentValues();

        switch (intTABLE) {

            case 0:

                contentValues.put(column_User, strColumn1);
                contentValues.put(column_Password, strColumn2);
                contentValues.put(column_Status, strColumn3);
                contentValues.put(column_Name, strColumn4);
                contentValues.put(column_Surname, strColumn5);
                contentValues.put(column_Phone, strColumn6);
                contentValues.put(column_Address, strColumn7);

                myLong = sqLiteDatabase.insert(user_table, null, contentValues);

                break;
            case 1:

                contentValues.put(column_Name, strColumn1);
                contentValues.put(column_Image, strColumn2);
                contentValues.put(column_Description, strColumn3);
                contentValues.put(column_HowTo, strColumn4);
                contentValues.put(column_Lat, strColumn5);
                contentValues.put(column_Lng, strColumn6);
                contentValues.put(column_Status, strColumn7);

                myLong = sqLiteDatabase.insert(herb_table, null, contentValues);

                break;

        }   // switch

        return myLong;
    }
}   // Main Class
