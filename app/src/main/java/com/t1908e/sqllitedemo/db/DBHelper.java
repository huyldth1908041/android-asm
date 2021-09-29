package com.t1908e.sqllitedemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Huy";
    public static final int DB_VERSION = 1;
    public static final String PAYMENTS_TABLE = "TBL_PAYMENT";
    public static final String CATEGORIES_TABLE_NAME = "TBL_CATEGORY";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CATEGORY_ID = "categoryId";
    public static final String DETAIL = "detail";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";
    public static final String PAYMENT_DATE = "paymentDate";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlPayment = "CREATE TABLE " + PAYMENTS_TABLE + "( " +
                ID + " INTEGER PRIMARY KEY, " +
                CATEGORY_ID + " INTEGER," +
                NAME + " TEXT, " +
                PAYMENT_DATE + "INTEGER," +
                DETAIL + " TEXT, " +
                AMOUNT + " REAL, " +
                DESCRIPTION + " TEXT," +
                " FOREIGN KEY( " + CATEGORY_ID + ")" + " REFERENCES " + CATEGORIES_TABLE_NAME + "(" + ID + "));";
        Log.d("TAG", "onCreate: " + sqlPayment);
        sqLiteDatabase.execSQL(sqlPayment);

        String sqlCategory = "CREATE TABLE " + CATEGORIES_TABLE_NAME + "( " +
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT);";
        sqLiteDatabase.execSQL(sqlCategory);

        String sqlSeedCategory = "INSERT INTO " + CATEGORIES_TABLE_NAME + " (" + NAME + " ) VALUES"
                + "('Category 1')" +
                ", ('Category 2')";
        sqLiteDatabase.execSQL(sqlSeedCategory);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + PAYMENTS_TABLE;
        sqLiteDatabase.execSQL(sql);
        String sql2 = "DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME;
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }

    public String addPayment(String name, int categoryId, String detail, double amount, String description) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(CATEGORY_ID, categoryId);
        contentValues.put(PAYMENT_DATE, new Date().getTime());
        contentValues.put(DETAIL, detail);
        contentValues.put(AMOUNT, amount);
        contentValues.put(DESCRIPTION, description);
        long isAdd = db.insert(PAYMENTS_TABLE, null, contentValues);
        if (isAdd == -1) {
            return "Add Fail";
        }
        db.close();
        return "Add Success";
    }

    public Cursor getAllPayments() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PAYMENTS_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + CATEGORIES_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }


}