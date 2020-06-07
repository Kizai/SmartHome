package com.example.login1;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 音乐播放功能的服务
 */
@SuppressLint("Registered")
public class MusicService extends Service {
    private MediaPlayer player;//播放器组件实例
    private Timer timer;//使用计时器对象

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();//实例化
    }

    //添加计时器，用于设置音乐播放器中的进度条的信息
    public void addTimer(){
        if(timer == null){
            timer = new Timer();//实例化
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(player == null) return;
                    int duration = player.getDuration();//获取歌曲的总长度
                    //获取播放进度
                    int currentDuration = player.getCurrentPosition();
                    //创建消息对象
                    Message msg = MusicplayActivity.handler.obtainMessage();//可能要修改
                    //将音乐的总时长、播放时长封装到消息的对象中去
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentDuration",currentDuration);
                    msg.setData(bundle);
                    //将信息添加到到主线程中去
                    MusicplayActivity.handler.sendMessage(msg);
                }
            };
            //开始计时任务5ms，执行第一次任务，以后每500ms执行一次任务
            timer.schedule(task,5,500);
        }
    }

    //自定义一个Binder类
    class MusicControl extends Binder {
        public void play() {
            try {
                player.reset();//重置音乐播放器
                //加载多媒体文件
                Context context;
                player = MediaPlayer.create(getApplicationContext(), R.raw.music_sign);
                player.start();//播放音乐
                addTimer();//添加计时器
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void pausePlay() {
            player.pause();//暂停
        }

        public void continuePlay() {
            player.start();//继续播放音乐
        }

        public void seekTo(int progress) {
            player.seekTo(progress);//设置音乐的播放位置
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MusicControl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player == null) return;
        if (player.isLooping()) player.stop();//停止播放音乐
        player.release();//释放资源
        player = null;
    }
}
