package com.example.myproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
public class DbContect extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DBNAME = "MyProject.db";
    private Context mContext;

    public DbContect(Context context) {
        super(context, DBNAME, null, VERSION);
        mContext = context;
    }

    //创建数据库
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table register(name varchar(10), " +
                "id varchar(20) PRIMARY KEY UNIQUE,password varchar(30)," +
                "confirm varchar(30),phone varchar(20))");
    }

    //数据库版本更新
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists register");
        onCreate(db);
    }
}

