package com.example.login1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zia.toastex.ToastEx;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_rgsName, et_rgsEmail, et_rgsPhoneNum, et_rgsPsw1, et_rgsPsw2, et_rgsImgCode;
    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private ImageView iv_codeimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setTitle("用户注册");

        initView();

        mDBOpenHelper = new DBOpenHelper(this);
        //将验证码以照片的形式显示出来
        iv_codeimg.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView() {
        et_rgsName = findViewById(R.id.et_rgsName);
        et_rgsEmail = findViewById(R.id.et_rgsEmail);
        et_rgsPhoneNum = findViewById(R.id.et_rgsPhoneNum);
        et_rgsPsw1 = findViewById(R.id.et_rgsPsw1);
        et_rgsPsw2 = findViewById(R.id.et_rgsPsw2);
        et_rgsImgCode = findViewById(R.id.et_rgsImgCode);
        Button btn_register = findViewById(R.id.btn_rgs);
        iv_codeimg = findViewById(R.id.iv_rgsImg);
        ImageView iv_back = findViewById(R.id.iv_back);
        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        iv_back.setOnClickListener(this);
        iv_codeimg.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回登录界面
                Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_rgsImg://改变验证码生成
                iv_codeimg.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.btn_rgs://注册按钮
                //获取用户输入的用户名、密码、验证码
                String username = et_rgsName.getText().toString().trim();
                String password1 = et_rgsPsw1.getText().toString().trim();
                String password2 = et_rgsPsw2.getText().toString().trim();
                String email = et_rgsEmail.getText().toString().trim();
                String phonenum = et_rgsPhoneNum.getText().toString().trim();
                String phoneCode = et_rgsImgCode.getText().toString().toLowerCase();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(phoneCode)) {
                    if (phoneCode.equals(realCode)) {
                        //判断两次密码是否一致
                        if (password1.equals(password2)) {
                            //将用户名和密码加入到数据库中
                            mDBOpenHelper.add(username, password2,email,phonenum);
                            Intent intent1 = new Intent(RegisteredActivity.this, LoginActivity.class);
                            startActivity(intent1);
                            finish();
                            ToastEx.success(getApplicationContext(),"验证通过，注册成功").show();
//                            Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            ToastEx.error(getApplicationContext(),"两次密码不一致,注册失败").show();
//                            Toast.makeText(this, "两次密码不一致,注册失败", Toast.LENGTH_SHORT).show();
                            iv_codeimg.setImageBitmap(Code.getInstance().createBitmap());
                            realCode = Code.getInstance().getCode().toLowerCase();
                        }
                    } else {
                        ToastEx.error(getApplicationContext(),"验证码错误,注册失败").show();
//                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                        iv_codeimg.setImageBitmap(Code.getInstance().createBitmap());
                        realCode = Code.getInstance().getCode().toLowerCase();
                    }
                } else {
                    ToastEx.error(getApplicationContext(),"未完善信息，注册失败").show();
//                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                    iv_codeimg.setImageBitmap(Code.getInstance().createBitmap());
                    realCode = Code.getInstance().getCode().toLowerCase();
                }
                break;
        }
    }
}
