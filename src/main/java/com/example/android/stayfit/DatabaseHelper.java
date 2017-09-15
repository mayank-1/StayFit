package com.example.android.stayfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mayank on 15/09/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stayfit.db";
    private static final String TABLE_NAME = "userinfo";
    private static final String COLOUMN_ID = "id";
    private static final String COLOUMN_NAME = "name";
    private static final String COLOUMN_UNAME = "uname";

    private static final String COLOUMN_EMAIL = "email";
    private static final String COLOUMN_PASS = "pass";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table userinfo(id integer primary key not null," +
            "name text not null, email text not null, uname text not null, pass text not null);";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertContact(Contact c){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String query = "select * from userinfo";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        contentValues.put(COLOUMN_ID, count);
        contentValues.put(COLOUMN_NAME, c.getName());
        contentValues.put(COLOUMN_UNAME, c.getUsername());
        contentValues.put(COLOUMN_EMAIL, c.getEmail());
        contentValues.put(COLOUMN_PASS, c.getPass());

        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public String searchPass(String uname){
        db = this.getReadableDatabase();
        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        String a,b;
        b="not found";
        if(cursor.moveToFirst()){
            do {
                a=cursor.getString(3);

                if(a.equals(uname))
                {
                    b=cursor.getString(4);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
