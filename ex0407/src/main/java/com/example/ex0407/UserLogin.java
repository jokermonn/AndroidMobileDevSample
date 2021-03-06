package com.example.ex0407;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {
    private static final String DB_NAME="dbUser.db";
    private SQLiteDatabase db=null;

    private EditText etUserName=null;
    private EditText etPwd=null;
    private Button btnLogin=null;
    private Button btnReg=null;
    private TextView tvShowInfo=null;
    private Intent intentUM=null;
    private Intent intentPC=null;
    private Bundle bundle=null;

    private String strUserName="";
    private String strPwd="";
    private int iUT=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etUserName=(EditText)findViewById(R.id.etULUserName);
        etPwd=(EditText)findViewById(R.id.etULPwd);
        btnLogin=(Button)findViewById(R.id.btnULLogin);
        btnReg=(Button)findViewById(R.id.btnULReg);
        tvShowInfo=(TextView)findViewById(R.id.tvULShowInfo);

        db = DBUtils.OpenCreateDB(this);

        insertAdminInfo();

        Button.OnClickListener listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(v.getId()==R.id.btnULLogin)
                {
                    strUserName=etUserName.getText().toString();
                    strPwd=etPwd.getText().toString();

                    if(isStrEmpty(strUserName)==false)
                    {
                        if(isStrEmpty(strPwd)==false)
                        {
                            if(isValidUser(strUserName,strPwd)==true)
                            {


                                showUserType(strUserName,strPwd);


                            }
                            else
                            {
                                Toast.makeText(UserLogin.this, "用户登录失败！", Toast.LENGTH_SHORT).show();
                                tvShowInfo.setText("用户登录失败");
                            }
                        }
                        else
                        {
                            Toast.makeText(UserLogin.this, "密码不可为空！", Toast.LENGTH_SHORT).show();
                            tvShowInfo.setText("密码不可为空");
                            etPwd.setFocusable(true);
                        }
                    }
                    else
                    {
                        Toast.makeText(UserLogin.this, "用户名不可为空！", Toast.LENGTH_SHORT).show();
                        tvShowInfo.setText("用户名不可为空");
                        etUserName.setFocusable(true);
                    }
                }
                else if(v.getId()==R.id.btnULReg)
                {

                    Intent intent=new Intent();

                    intent.setClass(UserLogin.this, UserReg.class);

                    startActivity(intent);

                }
            }
        };

        btnLogin.setOnClickListener(listener);
        btnReg.setOnClickListener(listener);
    }

    private boolean isStrEmpty(String strInput)
    {

        if(strInput.equals(""))
        {

            return true;
        }
        else
        {

            return false;
        }
    }


    private boolean isValidUser(String strUserName,String strUserPwd)
    {

        Cursor cursor=db.rawQuery("select * from tuserinfo where username='"+strUserName+"' and userpwd='"+strUserPwd+"'",null);


        if(cursor.getCount()== 1)
        {
            cursor.close();
            return true;
        }
        else
        {

            cursor.close();
            return false;
        }
    }

    private int getUserType(String strUserName,String strUserPwd)
    {
        int igut=-1;

        Cursor cursor=db.rawQuery("select * from tuserinfo where username='"+strUserName+"' and userpwd='"+strUserPwd+"'",null);


        if(cursor.getCount()== 1)
        {

            if(cursor.moveToFirst())
            {

                igut =cursor.getInt(cursor.getColumnIndex("usertype"));
                cursor.close();
            }
        }
        else
        {

            cursor.close();
            igut =99;
        }
        return igut;
    }

    private void showUserType(String strUserName,String strUserPwd)
    {
        iUT= getUserType(strUserName,strUserPwd);
        if(iUT==1)
        {
            Toast.makeText(UserLogin.this, "您是系统管理员,正在转入系统管理员页面！", Toast.LENGTH_SHORT).show();
            intentUM=new Intent(UserLogin.this, UserManage.class);

            startActivity(intentUM);

        }
        else if(iUT==0)
        {
            Toast.makeText(UserLogin.this, "您是普通用户，正在转入密码修改页面！", Toast.LENGTH_SHORT).show();
            intentPC=new Intent(UserLogin.this, PwdChange.class);

            bundle = new Bundle();
            bundle.putString("name", strUserName);
            intentPC.putExtras(bundle);


            startActivity(intentPC);
        }
        else
        {
            Toast.makeText(UserLogin.this, "异常情况！", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExistAdmin()
    {
        Cursor cursor=db.rawQuery("select * from tuserinfo where usertype=1",null);

        if(cursor.getCount()> 0)

        {
            cursor.close();
            return true;
        }
        else

        {
            cursor.close();
            return false;
        }
    }

    private void insertAdminInfo()
    {
        String strUserName= "guanghezhang@163.com";

        String strUserPwd="1";

        int iUserType=1;

        if(isExistAdmin()==false)
        {

            ContentValues cvUserInfo = new ContentValues();

            cvUserInfo.put("username", strUserName);

            cvUserInfo.put("userpwd", strUserPwd);

            cvUserInfo.put("usertype", iUserType);
            if(db!=null)
            {
                db.insert("tuserinfo", null, cvUserInfo);
            }
        }
        else
        {
            tvShowInfo.setText("已存在系统管理员\n用户名："+strUserName+"\n"+"密码："+strUserPwd+"\n");
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(db!=null)
        {
            db.close();
        }
    }

}
