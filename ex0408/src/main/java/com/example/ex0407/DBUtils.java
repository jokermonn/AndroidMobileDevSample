package com.example.ex0407;

import android.content.ContextWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by @author joker on 2017/11/10.
 */

public class DBUtils {
    private static final String DB_NAME = "dbUser.db";

    public static SQLiteDatabase OpenCreateDB(ContextWrapper context) {
        SQLiteDatabase db = null;
        try {
            db = context.openOrCreateDatabase(DB_NAME, ContextWrapper.MODE_PRIVATE, null);
        } catch (Throwable e) {
            Log.e("tag", "openDatabase error:" + e.getMessage());
            db = null;
        }
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS tuserinfo (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username VARCHAR, userpwd VARCHAR, usertype INTEGER)");
        } catch (SQLException se) {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag", String.format(msg, se.getClass(), se.getMessage()));
        }
        return db;
    }
}
