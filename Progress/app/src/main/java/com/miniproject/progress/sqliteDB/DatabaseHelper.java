package com.miniproject.progress.sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DatabaseName="mainItem.db";
    public static String Table_Name="t_mainItem";

    public String COL_1="task_progress";
    public String COL_2="time_progress";
    public String COL_3="Task_name";
    public String COL_4="subTask_num";
    public String COL_5="date";
    public String COL_6="tot_task";
    public String COL_7="tot_time";
    public String COL_8="tot_sub";


    public DatabaseHelper(Context context) {
        super(context, DatabaseName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Table_Name+"("+
                "id integer primary key autoincrement,"+
                "task_progress integer,"+
                "time_progress integer,"+
                "Task_name text,"+
                "subTask_num integer,"+
                "date text,"+
                "tot_task integer,"+
                "tot_time integer,"+
                "tot_sub integer)"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertData(String task_progress,String time_progress,String Task_name,String subTask_num,String date,String tot_task,String tot_time,String tot_sub){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,task_progress);
        contentValues.put(COL_2,time_progress);
        contentValues.put(COL_3,Task_name);
        contentValues.put(COL_4,subTask_num);
        contentValues.put(COL_5,date);
        contentValues.put(COL_6,tot_task);
        contentValues.put(COL_7,tot_time);
        contentValues.put(COL_8,tot_sub);

        long result=sqLiteDatabase.insert(Table_Name, null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor readID(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+Table_Name+" where id like "+id,null);
        return cursor;
    }
    public String upgradeOne(String id,String value,String name){
        return "UPDATE " +Table_Name+
                " SET "+name+" = '"+value+
                "' WHERE id = "+id;
    }
    public void alterByID(String id,String task_name,String time,String task_num,String complete_num){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int totTask=Integer.parseInt(task_num);
        int taskProgress=Integer.parseInt(complete_num);
        String sql1=upgradeOne(id,task_name,COL_3);
        sqLiteDatabase.execSQL(sql1);
        sql1=upgradeOne(id,time,"date");
        sqLiteDatabase.execSQL(sql1);
        sql1=upgradeOne(id,task_num,"tot_task");
        sqLiteDatabase.execSQL(sql1);
        sql1=upgradeOne(id,complete_num,"task_progress");
        sqLiteDatabase.execSQL(sql1);

    }
    public Cursor readData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Table_Name, null);
        return cursor;
    }
    public void deleteData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from t_mainItem where id in (select id from t_mainItem order by id desc limit 0,1)");
    }
    public Cursor removeData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql="select * from t_mainItem order by id desc limit 0,1";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }
}
