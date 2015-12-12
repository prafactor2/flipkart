package com.example.khaalijeb.newlistview_module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.khaalijeb.newlistview_module.UserTableData.table_info;

/**
 * Created by Rajdeep Singh on 08-04-2015.
 */
public class Operations extends SQLiteOpenHelper {

    public static final int database_version = 3;
    public String create_query = "CREATE TABLE " + table_info.table_name + "(" + table_info.userMobileNo + " TEXT, " + table_info.userMobilePass + " TEXT," +  table_info.val + " TEXT, " + table_info.test + " TEXT);";
    public Operations(Context context) {
        super(context,table_info.database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + table_info.table_name);
        onCreate(db);
    }
    public  void putinfo(Operations ob, String name, String pass) {
        SQLiteDatabase sq = ob.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(table_info.userMobileNo,name);
        cv.put(table_info.userMobilePass,pass);
        cv.put(table_info.val,"0");
      //  cv.put(table_info.val,);
        Log.d("dd", "yy");
        Log.d("dd",name);
        Log.d("dd",pass);
        sq.insert(table_info.table_name,null,cv);
    }
    public Cursor getinfo(Operations ob) {
        SQLiteDatabase sq = ob.getReadableDatabase();
        String[] columns = {table_info.userMobileNo,table_info.userMobilePass, table_info.val};
        Cursor cr = sq.query(table_info.table_name,columns,null,null,null,null,null);

        return cr;
    }
    public void del(Operations op, String name, String pass) {
        String s = table_info.userMobileNo + " LIKE ? AND " + table_info.userMobilePass + " LIKE ? ";
        String args[] = {name,pass};
        SQLiteDatabase sq = op.getWritableDatabase();
        sq.delete(table_info.table_name,s,args);

    }
    public void set(Operations op, String n,String m) {
        SQLiteDatabase sq = op.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(table_info.val,n);
        String args[] = {m};

    /*    String s = "Update " + table_info.table_name + " set " + table_info.val + "= " +"'" + n +"'" + " where " + table_info.user_name + "= " + "'" + m + "';";
        sq.execSQL(s);*/
        sq.update(table_info.table_name,cv,table_info.userMobileNo + " = ?", args);
    }

}

