package com.example.smd_assignment_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 2;
    // Table names and columns
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_ENTRIES = "Entries";
    private static final String TABLE_RECYCLEBIN = "RecycleBin";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_URL = "url";
    Context context;

    // Create table SQL queries
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    private static final String CREATE_ENTRIES_TABLE = "CREATE TABLE " + TABLE_ENTRIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_URL + " TEXT"
            + ")";

    private static final String CREATE_RECYCLER_TABLE = "CREATE TABLE " + TABLE_RECYCLEBIN + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_URL + " TEXT"
            + ")";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_RECYCLER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECYCLEBIN);
        onCreate(db);
    }

    public long addEntry(String username, String password, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_URL, url);
        long result = db.insert(TABLE_ENTRIES, null, values);
        db.close();
        return result;
    }

    public long addRecycleBinEntry(String username, String password, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_URL, url);
        long result = db.insert(TABLE_RECYCLEBIN, null, values);
        db.close();
        return result;
    }
    public ArrayList<Password> readAllEntries() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Password> records = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_ENTRIES, null);
        int id_Index = cursor.getColumnIndex(COLUMN_ID);
        int username_Index = cursor.getColumnIndex(COLUMN_USERNAME);
        int password_Index = cursor.getColumnIndex(COLUMN_PASSWORD);
        int URL_Index = cursor.getColumnIndex(COLUMN_URL);

        if(cursor.moveToFirst())
        {
            do{
                Password c = new Password();

                c.setId(cursor.getInt(id_Index));
                c.setUsername(cursor.getString(username_Index));
                c.setPassword(cursor.getString(password_Index));
                c.setWebsiteUrl(cursor.getString(URL_Index));

                records.add(c);
            }while(cursor.moveToNext());
        }

        cursor.close();


        db.close();
        return records;
    }

    public ArrayList<Password> readAllReycleBin() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Password> records = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_RECYCLEBIN, null);
        int id_Index = cursor.getColumnIndex(COLUMN_ID);
        int username_Index = cursor.getColumnIndex(COLUMN_USERNAME);
        int password_Index = cursor.getColumnIndex(COLUMN_PASSWORD);
        int URL_Index = cursor.getColumnIndex(COLUMN_URL);

        if(cursor.moveToFirst())
        {
            Toast.makeText(context,"reading recyclerBin",Toast.LENGTH_SHORT);
            do{
                Password c = new Password();

                c.setId(cursor.getInt(id_Index));
                c.setUsername(cursor.getString(username_Index));
                c.setPassword(cursor.getString(password_Index));
                c.setWebsiteUrl(cursor.getString(URL_Index));

                records.add(c);
            }while(cursor.moveToNext());
        }
        else {
            Toast.makeText(context,"nothing in recyclerBin",Toast.LENGTH_SHORT);
        }

        cursor.close();


        db.close();
        return records;
    }


    // Method to add a new user
    // Method to add a new user, only if the user doesn't already exist
    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the user already exists
        if (!isUserExists(db, username)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, username);
            values.put(COLUMN_PASSWORD, password);
            long result = db.insert(TABLE_USERS, null, values);
            db.close();
            return result;
        } else {
            // User already exists, return -1 (indicating failure)
            db.close();
            return -1;
        }
    }

    // Helper method to check if a user exists in the database
    private boolean isUserExists(SQLiteDatabase db, String username) {
        Cursor cursor = db.query(TABLE_USERS,
                null,
                COLUMN_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null);

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    // Method to check if a user exists and validate credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                null,
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password},
                null,
                null,
                null);

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }

  public void updateUser(int id, String name, String password, String url){
      SQLiteDatabase database = this.getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put(COLUMN_USERNAME, name);
      cv.put(COLUMN_PASSWORD, password);
      cv.put(COLUMN_URL, url);

      int records = database.update(TABLE_ENTRIES, cv, COLUMN_ID+"=?", new String[]{id+""});
      if(records>0)
      {
          Toast.makeText(context, "Entry updated", Toast.LENGTH_SHORT).show();
      }
      else
      {
          Toast.makeText(context, "Entry not updated", Toast.LENGTH_SHORT).show();
      }

  }

  public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ENTRIES,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        int deletedRows = 0;
        ContentValues values = new ContentValues();
        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            int urlIndex = cursor.getColumnIndex(COLUMN_URL);

            if (usernameIndex >= 0 && passwordIndex >= 0 && urlIndex >= 0) {
                values.put(COLUMN_USERNAME, cursor.getString(usernameIndex));
                values.put(COLUMN_PASSWORD, cursor.getString(passwordIndex));
                values.put(COLUMN_URL, cursor.getString(urlIndex));

                cursor.close();

                // Insert into recycle bin
                long result = db.insert(TABLE_RECYCLEBIN, null, values);

                // Delete from main table
                deletedRows = db.delete(TABLE_ENTRIES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            } else {
                // Handle the case where one or more columns are missing

            }
        } else {
            // Handle the case where the cursor is null or empty

        }

        db.close();
        return deletedRows;
    }

    public int  restoreBinItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RECYCLEBIN,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        int deletedRows = 0;
        ContentValues values = new ContentValues();
        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            int urlIndex = cursor.getColumnIndex(COLUMN_URL);

            if (usernameIndex >= 0 && passwordIndex >= 0 && urlIndex >= 0) {
                values.put(COLUMN_USERNAME, cursor.getString(usernameIndex));
                values.put(COLUMN_PASSWORD, cursor.getString(passwordIndex));
                values.put(COLUMN_URL, cursor.getString(urlIndex));

                cursor.close();

                // Insert into recycle bin
                long result = db.insert(TABLE_ENTRIES, null, values);

                // Delete from main table
                deletedRows = db.delete(TABLE_RECYCLEBIN, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            } else {
                // Handle the case where one or more columns are missing

            }
        } else {
            // Handle the case where the cursor is null or empty

        }

        db.close();
        return deletedRows;


    }

    public int  deleteBinItem(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            int deletedRows = 0;
            ContentValues values = new ContentValues();
                    // Delete from main table
        deletedRows = db.delete(TABLE_RECYCLEBIN, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

            db.close();
            return deletedRows;
        }
}