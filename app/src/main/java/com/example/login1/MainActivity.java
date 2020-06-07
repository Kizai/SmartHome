package com.example.login1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zia.toastex.ToastEx;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //底部菜单3个LinearLayout
    private LinearLayout ll_equip;
    private LinearLayout ll_scenario;
    private LinearLayout ll_vc;
    private LinearLayout ll_music;

    //底部菜单3个ImageView
    private ImageView iv_equipIcon;
    private ImageView iv_scenarioIcon;
    private ImageView iv_vcIcon;
    private ImageView iv_musicIcon;

    //底部菜单3个TextView
    private TextView tv_equipName;
    private TextView tv_scenarioName;
    private TextView tv_vcName;
    private TextView tv_musicName;

    //3个Fragment
    private Fragment equipmentFragment;
    private Fragment scenarioFragment;
    private Fragment vcFragment;
    private Fragment musicFragment;

    //顶部title的控件
    private TextView tv_title;
//    private ImageView iv_titleicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_main2);

        //初始化控件
        initView();
        //初始化底部按钮事件
        initEvent();
        //初始化并设置当前Fragment
        initFragment(0);
        //显示用户名
        TextView tv_userName = findViewById(R.id.tv_user);
        Intent intent = this.getIntent();
        String user = intent.getStringExtra("user_name");
        tv_userName.setText(user);

        //退出登录
        ImageView iv_quit = findViewById(R.id.iv_quit);
        iv_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                ToastEx.normal(getApplicationContext(),"退出用户成功").show();
//                Toast.makeText(getApplicationContext(),"退出用户成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //隐藏所有的Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (equipmentFragment == null) {
                    equipmentFragment = new EquipmentFragment();
                    transaction.add(R.id.fl_content, equipmentFragment);
                } else {
                    transaction.show(equipmentFragment);
                }
                break;
            case 1:
                if (scenarioFragment == null) {
                    scenarioFragment = new ScenarioFragment();
                    transaction.add(R.id.fl_content, scenarioFragment);
                } else {
                    transaction.show(scenarioFragment);
                }
                break;
            case 2:
                if (vcFragment == null) {
                    vcFragment = new VcFragment();
                    transaction.add(R.id.fl_content, vcFragment);
                } else {
                    transaction.show(vcFragment);
                }
                break;
            case 3:
                if(musicFragment == null){
                    musicFragment = new MusicFragment();
                    transaction.add(R.id.fl_content,musicFragment);
                }else {
                    transaction.show(musicFragment);
                }
            default:
                break;
        }
        //提交事务
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (equipmentFragment != null) {
            transaction.hide(equipmentFragment);
        }
        if (scenarioFragment != null) {
            transaction.hide(scenarioFragment);
        }
        if (vcFragment != null) {
            transaction.hide(vcFragment);
        }
        if(musicFragment != null){
            transaction.hide(musicFragment);
        }
    }

    private void initEvent() {
        //设置按钮监听
        ll_equip.setOnClickListener(this);
        ll_scenario.setOnClickListener(this);
        ll_vc.setOnClickListener(this);
        ll_music.setOnClickListener(this);
    }

    private void initView() {
        //底部菜单3个LinearLayout
        this.ll_equip = (LinearLayout)findViewById(R.id.ll_equip);
        this.ll_scenario = (LinearLayout)findViewById(R.id.ll_scenario);
        this.ll_vc = (LinearLayout)findViewById(R.id.ll_vc);
        this.ll_music = (LinearLayout)findViewById(R.id.ll_music);
        //底部3个ImageView
        this.iv_equipIcon = (ImageView)findViewById(R.id.iv_equipIcon);
        this.iv_scenarioIcon = (ImageView)findViewById(R.id.iv_scenarioIcon);
        this.iv_vcIcon = (ImageView)findViewById(R.id.iv_vcIcon);
        this.iv_musicIcon = (ImageView)findViewById(R.id.iv_musicIcon);
        //底部3个TextView
        this.tv_equipName = (TextView)findViewById(R.id.tv_equipName);
        this.tv_scenarioName = (TextView)findViewById(R.id.tv_scenarioName);
        this.tv_vcName = (TextView)findViewById(R.id.tv_vcName);
        this.tv_musicName = (TextView)findViewById(R.id.tv_musicName);
        //顶部title
        this.tv_title = (TextView) findViewById(R.id.tv_title);
    }

    //点击换颜色和标题
    @Override
    public void onClick(View v) {
        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restarButton();
        // ImageView和TetxView置为蓝色，页面随之跳转
        switch (v.getId()){
            case R.id.ll_equip:
                iv_equipIcon.setImageResource(R.drawable.sb_p);
                tv_equipName.setTextColor(Color.parseColor("#0099ff"));
                tv_title.setText("设备");
                initFragment(0);
                break;
            case R.id.ll_scenario:
                iv_scenarioIcon.setImageResource(R.drawable.qj_p);
                tv_scenarioName.setTextColor(Color.parseColor("#0099ff"));
                tv_title.setText("情景模式");
                initFragment(1);
                break;
            case R.id.ll_vc:
                iv_vcIcon.setImageResource(R.drawable.sk_p);
                tv_vcName.setTextColor(Color.parseColor("#0099ff"));
                tv_title.setText("声控");
                initFragment(2);
                break;
            case R.id.ll_music:
                iv_musicIcon.setImageResource(R.drawable.music_p);
                tv_musicName.setTextColor(Color.parseColor("#0099ff"));
                tv_title.setText("音乐播放器");
                initFragment(3);
                break;
            default:
                break;
        }

    }

    private void restarButton() {
        //恢复原来的颜色
        iv_equipIcon.setImageResource(R.drawable.sb_n);
        iv_scenarioIcon.setImageResource(R.drawable.qj_n);
        iv_vcIcon.setImageResource(R.drawable.sk_n);
        iv_musicIcon.setImageResource(R.drawable.music_n);


        tv_equipName.setTextColor(Color.BLACK);
        tv_scenarioName.setTextColor(Color.BLACK);
        tv_vcName.setTextColor(Color.BLACK);
        tv_musicName.setTextColor(Color.BLACK);

    }
}
