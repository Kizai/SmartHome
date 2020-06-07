package com.example.login1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.StatusRecogListener;
import com.baidu.aip.asrwakeup3.core.util.FileUtil;
import com.baidu.speech.EventManager;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class VcFragment extends Fragment {

    public VcFragment() {
        // Required empty public constructor
    }

    private TextView tv_vcResult;
    //    public EventManager asr;
    protected MyRecognizer myRecognizer;
    protected Handler handler;
    protected String resultTxt = null;

    @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = LayoutInflater.from(getContext());
        View myview = inflater.inflate(R.layout.fragment_vc, container, false);
        //建立一个线程来处理信息
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }
        };
        tv_vcResult = myview.findViewById(R.id.tv_vcResult);
        ImageView iv_vc = myview.findViewById(R.id.iv_vc);
        iv_vc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    tv_vcResult.setText("识别中...");
//                    Toast.makeText(getContext(), "正在识别....", Toast.LENGTH_SHORT).show();
                    start();
                } else if (action == MotionEvent.ACTION_UP) {
                    stop();
                }
                return false;
            }
        });
        initSpeechRecog();
        initPermission();
        return myview;
    }

    //初始化语音识别
    private void initSpeechRecog() {
        IRecogListener listener = new MessageStatusRecogListener(handler);
        if (myRecognizer == null) {
            myRecognizer = new MyRecognizer(this.getContext(), listener);
        }
    }

    //开始识别
    private void start() {
        final Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.PID, 1536);//普通话
        myRecognizer.start(params);
    }

    //停止识别
    private void stop() {
        myRecognizer.stop();
    }

    //回调事件处理
    private void handleMsg(Message msg) {
        if (msg.what == MessageStatusRecogListener.STATUS_FINISHED) {
//            String resultTxt = null;
            try {
                JSONObject msgObj = new JSONObject(msg.obj.toString());
                if (msg.arg2 == 1) {
                    String error = msgObj.getString("error");
                    System.out.println("error =>" + error);
                    if ("0".equals(error)) {
                        resultTxt = msgObj.getString("best_result");
                        tv_vcResult.setText(resultTxt);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //释放资源
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myRecognizer != null) {
            myRecognizer.release();//释放资源，不然程序会闪退
            myRecognizer = null;
        }
    }

    //调用权限
    private void initPermission() {
        String[] permissions = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };
        ArrayList<String> toApplyList = new ArrayList<>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), perm)) {
                toApplyList.add(perm);
                //进入这里代表没有权限
            }
        }
        String[] tmplist = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), toApplyList.toArray(tmplist), 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

      /*  private void start() {
        Map<String, Object> params = new LinkedHashMap<>();//传递Map<String,Object>的参数，会将Map自动序列化为json
        String event = null;
        event = SpeechConstant.ASR_START;
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);//回调当前音量
        String json = null;
        json = new JSONObject(params).toString();//demo用json数据来做数据交换的方式
        asr.send(event, json, null, 0, 0);// 初始化EventManager对象,这个实例只能创建一次，就是我们上方创建的asr，此处开始传入
    }

    private void stop() {
//        tv_result.append("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);//此处停止
    }*/

/*    private void initView() {
        Activity view = null;
        tv_vcResult = view.findViewById(R.id.tv_vcResult);
        iv_vc = view.findViewById(R.id.iv_vc);
    }*/

   /* @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String resultTxt = null;
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {//识别结果参数
            if (params.contains("\"final_result\"")) {//语义结果值
                try {
                    JSONObject json = new JSONObject(params);
                    resultTxt = json.getString("best_result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultTxt != null) {
            resultTxt += "\n";
            tv_vcResult.setText(resultTxt);
        }
    }*/

    /*    @Override
        public void onPause() {
            super.onPause();
            asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
            asr.unregisterListener(this);//推出事件管理器
            // 必须与registerListener成对出现，否则可能造成内存泄露
        }*/

}
