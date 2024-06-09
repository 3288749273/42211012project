package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 9; // 更新版本号

    // 用户表
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_IMAGE = "image"; // 新增用于存储头像路径的列名
    private static final String COLUMN_IDENTITY = "identity"; // 新增用户身份列

    // FAQ表
    private static final String TABLE_FAQ = "faqs";
    private static final String COLUMN_FAQ_ID = "_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";

    // 管理员用户表
    private static final String TABLE_ADMIN_USER = "admin_users";
    private static final String COLUMN_ADMIN_ID = "id";

    // 教师预约表
    private static final String TABLE_TEACHER_APPOINTMENT = "teacher_appointments";
    private static final String COLUMN_ISSUE = "issue";
    private static final String COLUMN_REPAIR_TIME = "repair_time";
    private static final String COLUMN_OFFICE = "office";

    // 学生预约表
    private static final String TABLE_STUDENT_APPOINTMENT = "student_appointments";
    private static final String COLUMN_STUDENT_ID = "student_id";

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
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_IDENTITY + " TEXT" + ")"; // 添加身份列
        db.execSQL(CREATE_USER_TABLE);

        // 创建FAQ表
        String CREATE_FAQ_TABLE = "CREATE TABLE " + TABLE_FAQ + "("
                + COLUMN_FAQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_QUESTION + " TEXT,"
                + COLUMN_ANSWER + " TEXT" + ")";
        db.execSQL(CREATE_FAQ_TABLE);

        // 创建管理员用户表
        String CREATE_ADMIN_USER_TABLE = "CREATE TABLE " + TABLE_ADMIN_USER + "("
                + COLUMN_ADMIN_ID + " TEXT PRIMARY KEY" + ")";
        db.execSQL(CREATE_ADMIN_USER_TABLE);

        // 创建教师预约表
        String CREATE_TEACHER_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_TEACHER_APPOINTMENT + "("
                + COLUMN_ID + " TEXT,"
                + COLUMN_ISSUE + " TEXT,"
                + COLUMN_REPAIR_TIME + " TEXT,"
                + COLUMN_OFFICE + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_TEACHER_APPOINTMENT_TABLE);

        // 创建学生预约表
        String CREATE_STUDENT_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_STUDENT_APPOINTMENT + "("
                + COLUMN_ID + " TEXT,"
                + COLUMN_ISSUE + " TEXT,"
                + COLUMN_REPAIR_TIME + " TEXT,"
                + COLUMN_STUDENT_ID + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_STUDENT_APPOINTMENT_TABLE);

        // 添加第一个管理员用户
        String firstAdminId = "42211012";
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_ID, firstAdminId);
        db.insert(TABLE_ADMIN_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // 删除旧表
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAQ);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER_APPOINTMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_APPOINTMENT);
            // 重新创建表
            onCreate(db);
        }
    }

    // 添加用户
    public boolean addUser(String name, String id, String password, String phone, String identity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ID, id);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_IDENTITY, identity);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    // 添加教师预约
    public boolean addTeacherAppointment(String id, String issue, String repairTime, String office, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_ISSUE, issue);
        values.put(COLUMN_REPAIR_TIME, repairTime);
        values.put(COLUMN_OFFICE, office);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_TEACHER_APPOINTMENT, null, values);
        db.close();
        return result != -1;
    }

    public boolean addStudentAppointment(String id, String issue, String repairTime, String studentId, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_ISSUE, issue);
        values.put(COLUMN_REPAIR_TIME, repairTime);
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_STUDENT_APPOINTMENT, null, values);
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

    public User getUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_IDENTITY}, COLUMN_ID + "=?",
                new String[]{id}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IDENTITY))
        );
        cursor.close();
        db.close();
        return user;
    }

    // 更新用户头像路径
    public boolean updateUserProfileImage(String id, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, imagePath);

        int rowsAffected = db.update(TABLE_USER, values, COLUMN_ID + "=?", new String[]{id});
        db.close();
        if (rowsAffected > 0) {
            Log.d("DatabaseHelper", "头像路径更新成功: " + imagePath);
            return true;
        } else {
            Log.d("DatabaseHelper", "头像路径更新失败: " + imagePath);
            return false;
        }
    }

    // 添加FAQ
    public boolean addFAQ(String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_ANSWER, answer);

        long result = db.insert(TABLE_FAQ, null, values);
        db.close();
        return result != -1;
    }

    // 获取所有FAQs
    public Cursor getAllFAQs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FAQ, null);
    }

    // 删除FAQ
    public boolean deleteFAQ(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_FAQ, COLUMN_FAQ_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    // 添加管理员用户
    public boolean addAdminUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_ID, id);

        long result = db.insert(TABLE_ADMIN_USER, null, values);
        db.close();
        return result != -1;
    }

    // 删除管理员用户
    public boolean deleteAdminUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ADMIN_USER, COLUMN_ADMIN_ID + "=?", new String[]{id});
        db.close();
        return result > 0;
    }

    // 验证管理员用户
    public boolean checkAdminUser(String id, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // 从用户表中获取密码
        Cursor userCursor = db.query(TABLE_USER,
                new String[]{COLUMN_PASSWORD},
                COLUMN_ID + "=?",
                new String[]{id},
                null, null, null);

        if (userCursor != null && userCursor.moveToFirst()) {
            @SuppressLint("Range") String storedPassword = userCursor.getString(userCursor.getColumnIndex(COLUMN_PASSWORD));
            userCursor.close();

            // 检查管理员表中是否存在该用户
            Cursor adminCursor = db.query(TABLE_ADMIN_USER,
                    new String[]{COLUMN_ADMIN_ID},
                    COLUMN_ADMIN_ID + "=?",
                    new String[]{id},
                    null, null, null);

            boolean isAdmin = adminCursor.getCount() > 0;
            adminCursor.close();
            db.close();

            // 验证密码
            return isAdmin && storedPassword.equals(password);
        }

        db.close();
        return false;
    }

    // 添加现有用户到管理员表
    public boolean addAdminUserById(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_ID},
                COLUMN_ID + "=?",
                new String[]{userId},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return addAdminUser(userId);
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean adminUserExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ADMIN_USER, new String[]{COLUMN_ADMIN_ID}, COLUMN_ADMIN_ID + "=?", new String[]{id}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
}
