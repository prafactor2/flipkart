package com.example.khaalijeb.newlistview_module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rajdeep Singh on 22-07-2015.
 */
public class NotificationOperations extends SQLiteOpenHelper {
    public static final int database_version = 3;
    public String create_query = "CREATE TABLE " + NotificationTableData.notiftable_info.table_name + "(" + NotificationTableData.notiftable_info.mSenderMobileNo + " TEXT, " + NotificationTableData.notiftable_info.mSenderName + " TEXT," +  NotificationTableData.notiftable_info.mAmountRequested + " TEXT," + NotificationTableData.notiftable_info.flag + " TEXT);";
    public NotificationOperations(Context context) {
        super(context, UserTableData.table_info.database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + UserTableData.table_info.table_name);
        onCreate(db);
    }
    public  void putinfo(NotificationOperations ob, String mSenderName, String mSenderNumber, String mAmountRequested) {
        SQLiteDatabase sq = ob.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NotificationTableData.notiftable_info.mSenderMobileNo,mSenderNumber);
        cv.put(NotificationTableData.notiftable_info.mSenderName,mSenderName);
        cv.put(NotificationTableData.notiftable_info.mAmountRequested,mAmountRequested);
        cv.put(NotificationTableData.notiftable_info.flag,"0");
        //  cv.put(table_info.val,);
       /* Log.d("dd", "yy");
        Log.d("dd",name);
        Log.d("dd",pass);*/
        sq.insert(NotificationTableData.notiftable_info.table_name,null,cv);
    }
    public Cursor getinfo(Operations ob) {
        SQLiteDatabase sq = ob.getReadableDatabase();
        String[] columns = {NotificationTableData.notiftable_info.mSenderMobileNo, NotificationTableData.notiftable_info.mSenderName, NotificationTableData.notiftable_info.mAmountRequested, NotificationTableData.notiftable_info.flag};
        Cursor cr = sq.query(NotificationTableData.notiftable_info.table_name,columns,null,null,null,null,null);

        return cr;
    }
    public void del(Operations op, String name, String pass) {
        String s = UserTableData.table_info.userMobileNo + " LIKE ? AND " + UserTableData.table_info.userMobilePass + " LIKE ? ";
        String args[] = {name,pass};
        SQLiteDatabase sq = op.getWritableDatabase();
        sq.delete(UserTableData.table_info.table_name, s, args);

    }
    public void set(Operations op, String n,String m) {
        SQLiteDatabase sq = op.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserTableData.table_info.val,n);
        String args[] = {m};

    /*    String s = "Update " + table_info.table_name + " set " + table_info.val + "= " +"'" + n +"'" + " where " + table_info.user_name + "= " + "'" + m + "';";
        sq.execSQL(s);*/
        sq.update(UserTableData.table_info.table_name,cv, UserTableData.table_info.userMobileNo + " = ?", args);
    }

}
