package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

public class MusicplayActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_conver;//封面图片
    @SuppressLint("StaticFieldLeak")
    private static SeekBar sb;//进度控制条
    @SuppressLint("StaticFieldLeak")
    private static TextView tv_progress, tv_total;//当前播放时间，总时间
    private Button btn_play, btn_pause, btn_continue, btn_exit;//播放，暂停，继续，退出

    private ObjectAnimator animator;//动画组件

    private MusicService.MusicControl musicControl;//音乐服务控制器 Binder实例
    private MyServiceConn myServiceConn;//连接实例

    private Intent intent; //全局的意图对象

    private boolean isUnbind = false;//记录服务是否被解绑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_musicplay);

        initView();
    }

    private void initView() {
        iv_conver = findViewById(R.id.iv_cover);
        animator = ObjectAnimator.ofFloat(iv_conver, "rotation", 0.0f, 360.0f);//旋转动画
        animator.setDuration(10000);//旋转一周用的时长
        animator.setInterpolator(new LinearInterpolator());//匀速转动
        animator.setRepeatCount(-1);//-1表示无限循环

        //进度条点击事件
        sb = findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //滑动变化时的处理
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == seekBar.getMax()) {//滑动最大值时结束动画
                    animator.pause();
                }
            }

            //开始滑动时的处理
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //停止滑动时的处理
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = sb.getProgress();//获取SeeKBar的进度值
                //调用服务的SeekTo方法改进音乐的进度
                musicControl.seekTo(progress);
            }
        });

        tv_progress = findViewById(R.id.tv_progress);
        tv_total = findViewById(R.id.tv_total);

        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_continue = findViewById(R.id.btn_continue);
        btn_exit = findViewById(R.id.btn_exit);

        intent = new Intent(this,MusicService.class);//打开服务的意图
        myServiceConn = new MyServiceConn();//实例化服务连接对象
        bindService(intent,myServiceConn,BIND_AUTO_CREATE);//绑定服务

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    //创建消息处理的对象
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        //处理子线程传来的信息
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData(); //获取信息
            int duration = bundle.getInt("duration");//总时长
            int currentDuration = bundle.getInt("currentDuration");//当前播放时长

            sb.setMax(duration);//设置总长度
            sb.setProgress(currentDuration);//设置当前的进度值

            //显示总时长开始
            int minute = duration / 1000 / 60;//分钟
            int second = duration / 1000 % 60;//秒
            String strMinute = "";
            String strSecond = "";
            if(minute < 10){
                strMinute = "0" + minute;
            }else {
                strMinute = minute + "";
            }
            if(second < 10){
                strSecond = "0" + second;
            }else {
                strSecond = second + "";
            }
            tv_total.setText(strMinute + ":" + strSecond);
            //显示总时长结束

            //显示播放时长开始
            minute = currentDuration / 1000 / 60;//分钟
            second = currentDuration / 1000 % 60;//秒
            if(minute < 10){
                strMinute = "0" + minute;
            }else {
                strMinute = minute + "";
            }
            if(second < 10){
                strSecond = "0" + second;
            }else {
                strSecond = second + "";
            }
            tv_progress.setText(strMinute + ":" + strSecond);
            //显示播放时长结束
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play: //播放
                musicControl.play();
                animator.start();
                break;
            case R.id.btn_pause://暂停
                musicControl.pausePlay();
                animator.pause();
                break;
            case R.id.btn_continue://继续
                musicControl.continuePlay();
                animator.start();
                break;
            case R.id.btn_exit://退出
                myUnbind(isUnbind);
                finish();//关闭界面
/*                Intent intent = new Intent(MusicplayActivity.this, MainActivity.class);
                startActivity(intent);*/
                break;
        }

    }

    //自定义解绑方法
    private void myUnbind(boolean isUnbind) {
        if (!isUnbind) {
            isUnbind = true;
            musicControl.pausePlay();//暂停播放
            unbindService(myServiceConn);//解绑服务
            stopService(intent);//停止服务
        }
    }

    //用于实现连接服务的自定义类
    class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
