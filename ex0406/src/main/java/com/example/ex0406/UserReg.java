package com.example.ex0406;

import android.content.ContentValues;
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

public class UserReg extends AppCompatActivity {
    private static final String DB_NAME = "dbUser.db";
    private SQLiteDatabase db;

    private Button btnOk = null;
    private Button btnCancel = null;
    private EditText etUserName = null;
    private EditText etPwd = null;
    private EditText etRePwd = null;
    private TextView tvShowInfo = null;

    private String strUserName = "";
    private String strPwd = "";
    private String strRePwd = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        etUserName = (EditText) findViewById(R.id.etURUserName);
        etPwd = (EditText) findViewById(R.id.etURPwd);
        etRePwd = (EditText) findViewById(R.id.etURRePwd);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        tvShowInfo = (TextView) findViewById(R.id.tvURShowInfo);

        OpenCreateDB();


        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnOk) {
                    strUserName = etUserName.getText().toString();
                    strPwd = etPwd.getText().toString();
                    strRePwd = etRePwd.getText().toString();

                    if (TextUtils.isStrEmpty(strUserName) == false) {
                        if (TextUtils.isStrEmpty(strPwd) == false) {
                            if (TextUtils.isStrEmpty(strRePwd) == false) {
                                if (isPwdSame(strPwd, strRePwd) == true) {
                                    insertUserInfo(strUserName, strPwd);
                                } else {
                                    tvShowInfo.setText("密码和确认密码不一致");
                                }
                            } else {
                                tvShowInfo.setText("确认密码不可为空");
                                etRePwd.setFocusable(true);
                            }
                        } else {
                            tvShowInfo.setText("密码不可为空");
                            etPwd.setFocusable(true);
                        }

                    } else {
                        tvShowInfo.setText("用户名不可为空");
                        etUserName.setFocusable(true);
                    }
                } else if (v.getId() == R.id.btnCancel) {
                    etUserName.setText("");
                    etPwd.setText("");
                    etRePwd.setText("");
                    etUserName.setFocusable(true);
                    tvShowInfo.setText("");
                }
            }
        };
        btnOk.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
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

    private boolean isExistAdmin() {
        Cursor cursor = db.rawQuery("select * from tuserinfo where usertype=1", null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


    private void insertAdminInfo() {
        String strUserName = "guanghezhang@163.com";

        String strUserPwd = "1";

        int iUserType = 1;

        if (isExistAdmin() == false) {

            ContentValues cvUserInfo = new ContentValues();

            cvUserInfo.put("username", strUserName);

            cvUserInfo.put("userpwd", strUserPwd);

            cvUserInfo.put("usertype", iUserType);
            if (db != null) {
                db.insert("tuserinfo", null, cvUserInfo);

                Log.d("msg", "插入结束");
                Toast.makeText(UserReg.this, "注册成功！", Toast.LENGTH_SHORT).show();
                tvShowInfo.setText("用户名：" + strUserName + "\n" + "密码：" + strUserPwd + "\n" + "类别：" +
                        iUserType);
            }
        } else {
            tvShowInfo.setText("已存在系统管理员\n用户名：" + strUserName + "\n" + "密码：" + strUserPwd + "\n");
        }
    }

    private boolean isPwdSame(String strUserPwd, String strUserRePwd) {

        if (strUserPwd.equals(strUserRePwd)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isExistUserName(String strUserName) {

        Cursor cursor = db.rawQuery("select * from tuserinfo where username='" + strUserName + "'", null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }


    private void insertUserInfo(String strUserName, String strUserPwd) {

        int iUserType = 0;

        if (isExistUserName(strUserName) == false) {
            ContentValues cvRUserInfo = new ContentValues();
            cvRUserInfo.put("username", strUserName);
            cvRUserInfo.put("userpwd", strUserPwd);
            cvRUserInfo.put("usertype", iUserType);
            if (db != null) {
                db.insert("tuserinfo", null, cvRUserInfo);
                Toast.makeText(UserReg.this, "注册成功！", Toast.LENGTH_SHORT).show();
                tvShowInfo.setText("已经成功注册普通\n用户名：" + strUserName + "\n" + "密码：" + strUserPwd + "\n");
            }
        } else {
            Toast.makeText(UserReg.this, "您要注册的用户名已经存在！", Toast.LENGTH_SHORT).show();
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
