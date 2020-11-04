package com.school.loveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LOVEAPP";
    private static final String TABLE_CONTACTS = "userdetails";
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "Uname";
    public static final String KEY_AGE = "Uage";
    public static final String KEY_GENDER = "Ugender";
    public static final String KEY_INTER = "Uinter";
    public static final String KEY_IMAGE = "image";

    private static String sqLiteDatabase_PATH = "";
    private static SQLiteDatabase sqLiteDatabase;


    String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " VARCHAR,"
            + KEY_AGE + " VARCHAR,"
            + KEY_GENDER + " VARCHAR,"
            + KEY_IMAGE + " BLOB ,"
            + KEY_INTER + " VARCHAR"
            + ")";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase_PATH = context.getFilesDir().getParentFile().getPath()
                + "/databases/";
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    
    private static void openDataBase() throws SQLException {
        try {
            String myPath = sqLiteDatabase_PATH + DATABASE_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insertCars(String _Uname,
                           String _Uage,
                           String _Ugender,
                           String _Uinter,
                           byte[] _image) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _Uname);
        values.put(KEY_AGE, _Uage);
        values.put(KEY_GENDER, _Ugender);
        values.put(KEY_INTER, _Uinter);
        values.put(KEY_IMAGE, _image);

        sqLiteDatabase.insert(TABLE_CONTACTS, null, values);
        sqLiteDatabase.close();

        Log.e("INSERTDATA", _Uname + " : " + _Uage + " : " + _Ugender + " : " + _Uinter + " : ");

    }

    public Cursor getCarDetails() {
        openDataBase();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }


    public int CheckcarCount(String age1, String age2, String gender, String inter) {
        int count = 0;
        openDataBase();
        try {
            String query = "SELECT count(*) AS count FROM " + TABLE_CONTACTS
                    + " WHERE " + KEY_GENDER + " LIKE '%" + gender + "%'"
//                    + " AND " + KEY_AGE + " < " + Integer.parseInt(age2)
                    + " AND " + KEY_INTER + " LIKE '%" + inter + "%'";

            Log.e("QUERY", query);

            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(cursor.getColumnIndex("count"));
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqLiteDatabase.close();
        return count;
    }

    public Cursor getCarDetailsSingle(String gender, String inter) {
        openDataBase();
//        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS
//                + " WHERE " + KEY_NAME + " LIKE '%" + car_name + "%'";
        String query = "SELECT count(*) AS count FROM " + TABLE_CONTACTS
                + " WHERE " + KEY_GENDER + " LIKE '%" + gender + "%'"
//                + " AND " + KEY_AGE + " > " + age1
//                + " AND " + KEY_AGE + " < " + age2
                + " AND " + KEY_INTER + " LIKE '%" + inter + "%'"
                + " LIMIT 1";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }
}
