package com.example.ex0409;

import android.database.Cursor;
import android.os.Bundle;

/**
 * Created by @author joker on 2017/11/10.
 */
public class UserRegImpl extends UserReg {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
    }

    @Override
    public boolean isExistAdmin() {
        Cursor cursor = db.rawQuery("select * from tuserinfo where usertype=1", null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    @Override
    public boolean isExistUserName(String strUserName) {

        Cursor cursor = db.rawQuery("select * from tuserinfo where username='" + strUserName + "'", null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }
}
