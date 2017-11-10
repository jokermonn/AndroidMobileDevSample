package com.example.ex0406;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {
    private static final String DB_NAME = "dbUser.db";
    private SQLiteDatabase db;

    private EditText etUserName = null;
    private EditText etPwd = null;
    private Button btnLogin = null;
    private Button btnReg = null;
    private TextView tvShowInfo = null;

    private String strUserName = "";
    private String strPwd = "";
    private int iUT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etUserName = (EditText) findViewById(R.id.etULUserName);
        etPwd = (EditText) findViewById(R.id.etULPwd);
        btnLogin = (Button) findViewById(R.id.btnULLogin);
        btnReg = (Button) findViewById(R.id.btnULReg);
        tvShowInfo = (TextView) findViewById(R.id.tvULShowInfo);

        OpenCreateDB();

        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnULLogin) {
                    strUserName = etUserName.getText().toString();
                    strPwd = etPwd.getText().toString();

                    if (TextUtils.isStrEmpty(strUserName) == false) {
                        if (TextUtils.isStrEmpty(strPwd) == false) {
                            if (isValidUser(strUserName, strPwd) == true) {
                                Toast.makeText(UserLogin.this, "用户登录成功！", Toast.LENGTH_SHORT).show();

                                showUserType(strUserName, strPwd);
                            } else {
                                Toast.makeText(UserLogin.this, "用户登录失败！", Toast.LENGTH_SHORT).show();
                                tvShowInfo.setText("用户登录失败");
                            }
                        } else {
                            Toast.makeText(UserLogin.this, "密码不可为空！", Toast.LENGTH_SHORT).show();
                            tvShowInfo.setText("密码不可为空");
                            etPwd.setFocusable(true);
                        }
                    } else {
                        Toast.makeText(UserLogin.this, "用户名不可为空！", Toast.LENGTH_SHORT).show();
                        tvShowInfo.setText("用户名不可为空");
                        etUserName.setFocusable(true);
                    }
                } else if (v.getId() == R.id.btnULReg) {

                    Intent intent = new Intent();

                    intent.setClass(UserLogin.this, UserReg.class);

                    startActivity(intent);

                }
            }
        };

        btnLogin.setOnClickListener(listener);
        btnReg.setOnClickListener(listener);
    }

    private void OpenCreateDB() {
        try {
            db = openOrCreateDatabase(DB_NAME, this.MODE_PRIVATE, null);
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
    }

    private boolean isValidUser(String strUserName, String strUserPwd) {

        Cursor cursor = db.rawQuery("select * from tuserinfo where username='" + strUserName + "' and " +
                "userpwd='" + strUserPwd + "'", null);


        if (cursor.getCount() == 1) {
            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }


    private int getUserType(String strUserName, String strUserPwd) {
        int igut = -1;

        Cursor cursor = db.rawQuery("select * from tuserinfo where username='" + strUserName + "' and " +
                "userpwd='" + strUserPwd + "'", null);


        if (cursor.getCount() == 1) {

            if (cursor.moveToFirst()) {

                igut = cursor.getInt(cursor.getColumnIndex("usertype"));
                cursor.close();
            }
        } else {

            cursor.close();
            igut = 99;
        }
        return igut;
    }

    private void showUserType(String strUserName, String strUserPwd) {
        iUT = getUserType(strUserName, strUserPwd);
        if (iUT == 1) {
            Toast.makeText(UserLogin.this, "您是系统管理员！", Toast.LENGTH_SHORT).show();
        } else if (iUT == 0) {
            Toast.makeText(UserLogin.this, "您是普通用户！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UserLogin.this, "异常情况！", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
