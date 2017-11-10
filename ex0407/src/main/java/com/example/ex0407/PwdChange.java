package com.example.ex0407;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PwdChange extends AppCompatActivity {
    private Button btnOk = null;
    private Button btnCancel = null;
    private TextView tvUserName = null;
    private EditText etOldPwd = null;
    private EditText etNewPwd = null;
    private EditText etRePwd = null;
    private TextView tvShowInfo = null;
    private SQLiteDatabase db = null;

    private String strUserName = "";
    private String strOldPwd = "";
    private String strNewPwd = "";
    private String strRePwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);

        tvUserName = (TextView) findViewById(R.id.tvPCCurrentUN);
        etOldPwd = (EditText) findViewById(R.id.etPCOldPwd);
        etNewPwd = (EditText) findViewById(R.id.etPCNewPwd);
        etRePwd = (EditText) findViewById(R.id.etPCRePwd);
        tvShowInfo = (TextView) findViewById(R.id.tvPCShowInfo);
        btnOk = (Button) findViewById(R.id.btnPCOK);
        btnCancel = (Button) findViewById(R.id.btnPCCancel);


        Bundle bundle = this.getIntent().getExtras();
        strUserName = bundle.getString("name");
        tvUserName.setText(strUserName);
        tvUserName.setEnabled(false);

        db = DBUtils.OpenCreateDB(this);

        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnPCOK) {
                    strOldPwd = etOldPwd.getText().toString();
                    strNewPwd = etNewPwd.getText().toString();
                    strRePwd = etRePwd.getText().toString();

                    if (isStrEmpty(strOldPwd) == false) {
                        if (isStrEmpty(strNewPwd) == false) {
                            if (isStrEmpty(strRePwd) == false) {
                                if (isPwdSame(strNewPwd, strRePwd) == true) {
                                    if (updatePwd(strUserName, strNewPwd) == true) {
                                        Toast.makeText(PwdChange.this, "修改密码成功！", Toast.LENGTH_SHORT)
                                                .show();
                                        tvShowInfo.setText("修改密码成功");
                                    } else {
                                        Toast.makeText(PwdChange.this, "修改密码失败！", Toast.LENGTH_SHORT)
                                                .show();
                                        tvShowInfo.setText("修改密码失败");
                                    }
                                } else {
                                    tvShowInfo.setText("新密码和确认密码不一致");
                                    etRePwd.setFocusable(true);
                                }
                            } else {
                                tvShowInfo.setText("确认密码不可为空");
                                etRePwd.setFocusable(true);
                            }
                        } else {
                            tvShowInfo.setText("新密码不可为空");
                            etNewPwd.setFocusable(true);
                        }

                    } else {
                        tvShowInfo.setText("旧密码不可为空");
                        etOldPwd.setFocusable(true);
                    }
                } else if (v.getId() == R.id.btnPCCancel) {
                    etOldPwd.setText("");
                    etRePwd.setText("");
                    etNewPwd.setText("");
                    etOldPwd.setFocusable(true);
                }
            }
        };
        btnOk.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }


    private boolean isStrEmpty(String strInput) {

        if (strInput.equals("")) {

            return true;
        } else {

            return false;
        }
    }

    private boolean isPwdSame(String strUserPwd, String strUserRePwd) {

        if (strUserPwd.equals(strUserRePwd)) {

            return true;
        } else {

            return false;
        }
    }


    private boolean updatePwd(String strUserName, String strNUserPwd) {

        String sql = "update [tuserinfo] set userpwd = '" + strNUserPwd + "' where username='" +
                strUserName + "'";
        try {
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

}