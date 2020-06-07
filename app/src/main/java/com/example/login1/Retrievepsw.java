package com.example.login1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zia.toastex.ToastEx;

import java.util.ArrayList;

public class Retrievepsw extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_withdraw, iv_retImg;
    private EditText et_retName, et_retEnail, et_retPhoneNum, et_retImgCode;
    private Button btn_ret;
    private DBOpenHelper mDBOpenHelper;
    private String realCode;
    private TextView tv_showPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_retrievepsw);
        setTitle("找回密码");

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码以照片的形式显示出来
        iv_retImg.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView() {
        //初始化控件
        iv_retImg = findViewById(R.id.iv_retImg);
        iv_withdraw = findViewById(R.id.iv_withdraw);
        et_retName = findViewById(R.id.et_retName);
        et_retEnail = findViewById(R.id.et_retEmail);
        et_retPhoneNum = findViewById(R.id.et_retPhoneNum);
        et_retImgCode = findViewById(R.id.et_retImgCode);
        btn_ret = findViewById(R.id.btn_ret);
        tv_showPsw = findViewById(R.id.tv_showPsw);
        //初始化点击事件
        btn_ret.setOnClickListener(this);
        iv_withdraw.setOnClickListener(this);
        iv_retImg.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_withdraw:
                Intent intent = new Intent(Retrievepsw.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_retImg:
                iv_retImg.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.btn_ret:
                String userName = et_retName.getText().toString().trim();
                String email = et_retEnail.getText().toString().trim();
                String phoneNum = et_retPhoneNum.getText().toString().trim();
                String imgCode = et_retImgCode.getText().toString().toLowerCase();
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(imgCode)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (imgCode.equals(realCode)) {
                            if (userName.equals(user.getName()) && email.equals(user.getEmail()) && phoneNum.equals(user.getPhonenum())) {
                                tv_showPsw.setText(user.getName() + "的密码是:" + user.getPassword());
                                ToastEx.success(getApplicationContext(), "密码找回成功！").show();
                            } else {
                                iv_retImg.setImageBitmap(Code.getInstance().createBitmap());
                                realCode = Code.getInstance().getCode().toLowerCase();
                            }
                        }else {
                            ToastEx.error(getApplicationContext(), "验证码有误请重新输入！").show();
                            iv_retImg.setImageBitmap(Code.getInstance().createBitmap());
                            realCode = Code.getInstance().getCode().toLowerCase();
                        }
                    }
                }else {
                    ToastEx.error(getApplicationContext(), "找回信息不完善，找回密码失败！").show();
                    iv_retImg.setImageBitmap(Code.getInstance().createBitmap());
                    realCode = Code.getInstance().getCode().toLowerCase();
                }
                break;
        }
    }
}
