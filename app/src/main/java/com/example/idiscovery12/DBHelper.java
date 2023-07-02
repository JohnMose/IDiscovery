package com.example.idiscovery12;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TABLE_ACTIVITY = "activities";


    // Activity Table Columns
    private static final String KEY_ACTIVITY_ID = "id";
    private static final String TABLE_REPORT = "reports";
    private static final String KEY_REPORT_ID = "id";
    private static final String KEY_REPORT_ACTIVITY_ID = "activity_id";
    private static final String KEY_REPORT_DESCRIPTION = "description";
    public DBHelper(@Nullable Context context) {

        super(context,"UserDatabase.db",null,3);

    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserRecord1 (NAME Text,LOCATION Text,DATE Text,TIME Text,REPORT Text)");
        DB.execSQL("create Table UserRecord2 (NAME Text,LOCATION Text,DATE Text,TIME Text,REPORT Text)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserRecord1");

    }
    private static final String CREATE_TABLE_REPORT = "CREATE TABLE " + "UserRecord2" +
            "(" +
            KEY_REPORT_ID+ " INTEGER PRIMARY KEY," +
            KEY_REPORT_ACTIVITY_ID + " INTEGER NOT NULL," +
            KEY_REPORT_DESCRIPTION + " TEXT," +
            "FOREIGN KEY(" + KEY_REPORT_ACTIVITY_ID + ") REFERENCES " + TABLE_ACTIVITY + "(" + KEY_ACTIVITY_ID + ")" +
            ")";
    // Adding a report for an activity
    public long addReport(long activityId, String description) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REPORT_ACTIVITY_ID, activityId);
        values.put(KEY_REPORT_DESCRIPTION, description);

        long reportId = db.insert(TABLE_REPORT, null, values);
        db.close();
        return reportId;
    }
    public List<String> getAllReportsForActivity() {
        List<String> reports = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_REPORT_DESCRIPTION + " FROM " + TABLE_REPORT +
                " WHERE " + KEY_REPORT_ACTIVITY_ID ;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(KEY_REPORT_ACTIVITY_ID));
                reports.add(description);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reports;
    }


    public boolean insertUserData(String name, String location,String date, String time, String report){
        SQLiteDatabase DB =this.getWritableDatabase();
        ContentValues contentValue=new ContentValues();
        contentValue.put("NAME",name);
        contentValue.put("LOCATION",location);
        contentValue.put("DATE",date);
        contentValue.put("TIME",time);
        contentValue.put("REPORT",report);
//executing the insert query
        Long result=DB.insert("UserRecord1",null,contentValue);
        return result != -1;
    }
    public boolean updateUserData(String name, String location,String date, String time, String report) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        //assign values to content variable
        contentValue.put("NAME", name);
        contentValue.put("LOCATION", location);
        contentValue.put("DATE", date);
        contentValue.put("TIME", time);
        contentValue.put("REPORT", report);

        Cursor currentRecord = DB.rawQuery("select * from UserRecord where NAME=?", new String[]{name});
        if (currentRecord.getCount() > 0) {
            int result = DB.update("UserRecord1", contentValue, "NAME=?", new String[]{name});
            return result != -1;
        }
        else {
            return false;
        }//end of update
        }
    public boolean deleteUserData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor findRecord= DB.rawQuery("select * from UserRecord1 where NAME=?",new String[]{name});
        if(findRecord.getCount()>0)
        {
            int result=DB.delete("UserRecord1","NAME=?",new String[]{name});
            return result != -1;
        }
        else {
            return false;
        }
    }//end of delete
  public Cursor getdata ()
  {
      SQLiteDatabase DB = this.getWritableDatabase();
      Cursor cursor = DB.rawQuery("Select * from UserRecord1", null);
      return cursor;
  }
    public boolean checkForDuplicate(String name,String location,String date,String time,String report)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " +" UserRecord1" +
                        " WHERE " + name + "=? AND " + location + "=? AND "+date + "=? AND " +time + "=? AND"+report+"=?",
                new String[]{name,location,date,time,report});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }//end of checkifuplicate
    public Cursor searchEvents(String name) {
        SQLiteDatabase DB = this.getReadableDatabase();
        String[] columns = {"_id", "activity_name", "location", "date", "time", "reporter_name"};
        String selection = "activity_name like ?";
        String[] selectionArgs = { "%" + name + "%" };
        Cursor cursor = DB.query("UserRecord1", columns, selection, selectionArgs, null, null, null);
        return cursor;
    }


}


