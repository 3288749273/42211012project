package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 2; // 修改版本号为2

    // 用户表
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone";
    // 新增用于存储头像路径的列名
    private static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表，并添加头像路径的列
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_IMAGE + " TEXT" + ")"; // 将头像路径定义为TEXT类型
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除旧表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // 重新创建表
        onCreate(db);
    }

    // 添加用户
    public boolean addUser(String name, String id, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ID, id);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    // 验证用户
    public boolean checkUser(String id, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_ID},
                COLUMN_ID + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{id, password},
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // 检查用户是否存在
    public boolean isUserExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_ID},
                COLUMN_ID + "=?",
                new String[]{id},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
    // 获取用户信息
    public Cursor getUser(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER,
                null, // 返回所有列
                COLUMN_ID + "=?",
                new String[]{id},
                null, null, null);
    }
    public boolean updateUserProfileImage(String id, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, imagePath);

        int rowsAffected = db.update(TABLE_USER, values, COLUMN_ID + "=?", new String[]{id});
        db.close();
        if (rowsAffected > 0) {
            Log.d("DatabaseHelper", "头像路径更新成功: " + imagePath);
        } else {
            Log.d("DatabaseHelper", "头像路径更新失败: " + imagePath);
        }
        return rowsAffected > 0;
    }
}

