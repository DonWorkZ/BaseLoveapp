package com.school.loveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LOVEAPP3";
    private static final String TABLE_CONTACTS = "userdetails2";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "Uname";
    public static final String KEY_AGE = "Uage";
    public static final String KEY_GENDER = "Ugender";
    public static final String KEY_INTER = "Uinter";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_MSG = "Umsg";


    private static String sqLiteDatabase_PATH = "";
    private static SQLiteDatabase sqLiteDatabase;


    String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " VARCHAR,"
            + KEY_AGE + " VARCHAR,"
            + KEY_GENDER + " VARCHAR,"
            + KEY_INTER + " VARCHAR,"
            + KEY_IMAGE + " BLOB,"
            + KEY_MSG + " VARCHAR"
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

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, _Uname);
        contentValues.put(KEY_AGE, _Uage);
        contentValues.put(KEY_GENDER, _Ugender);
        contentValues.put(KEY_INTER, _Uinter);
        contentValues.put(KEY_IMAGE, _image);
        contentValues.put(KEY_MSG,"");
        db.insert(TABLE_CONTACTS, null, contentValues);


    }

    public Cursor getCarDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_CONTACTS , null );
        return res;
    }


    public int CheckcarCount(String age1, String age2, String gender, String inter) {
        int count = 0;
        openDataBase();
        try {
            String query = "SELECT count(*) AS count FROM " + TABLE_CONTACTS
                    + " WHERE " + KEY_GENDER + " LIKE '%" + gender + "%'"
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select " + KEY_ID +", * from " +
                        TABLE_CONTACTS + " WHERE "+
                        KEY_GENDER + " LIKE '" + gender + "'"

                + " LIMIT 1"
                , null );

        Log.e("getCarDetailsSingle", "select " + KEY_ID +", * from " +
                TABLE_CONTACTS + " WHERE "+
                KEY_GENDER + " LIKE '" + gender + "'"

                + " LIMIT 1");
        return res;
    }

    public void updateData(String message, String uname){
        openDataBase();
        SQLiteDatabase database = getWritableDatabase();
//        database.execSQL("UPDATE " + TABLE_CONTACTS + " SET " + KEY_MSG + " = '" + message + "' WHERE " + KEY_ID + " = " +  "" + uid + "");
        database.execSQL("UPDATE "+TABLE_CONTACTS+" SET Umsg = "+"'"+message+"' "+  "WHERE Uname = "+"'"+uname+"'");
    }

}
