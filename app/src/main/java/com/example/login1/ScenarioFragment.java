package com.example.login1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zia.toastex.ToastEx;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScenarioFragment extends Fragment implements View.OnClickListener{

    public ScenarioFragment() {
        // Required empty public constructor
    }
    //四个ImageView
    private ImageView iv_movie,iv_eat,iv_sleep,iv_outdoor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scenario, container, false);
        iv_movie = view.findViewById(R.id.iv_movie);
        iv_eat = view.findViewById(R.id.iv_eat);
        iv_sleep = view.findViewById(R.id.iv_sleep);
        iv_outdoor = view.findViewById(R.id.iv_outdoor);
        initEvent();
        return view;
    }

    //初始化响应事件
    private void initEvent() {
        iv_movie.setOnClickListener(this);
        iv_eat.setOnClickListener(this);
        iv_sleep.setOnClickListener(this);
        iv_outdoor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_movie:
                ToastEx.info(Objects.requireNonNull(getActivity()),"影院模式").show();
//                Toast.makeText(getContext(),"影院模式",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_eat:
                ToastEx.info(Objects.requireNonNull(getActivity()),"就餐模式").show();
//                Toast.makeText(getContext(),"就餐模式",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_sleep:
                ToastEx.info(Objects.requireNonNull(getActivity()),"睡眠模式").show();
//                Toast.makeText(getContext(),"睡眠模式",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_outdoor:
                ToastEx.info(Objects.requireNonNull(getActivity()),"离开模式模式").show();
//                Toast.makeText(getContext(),"离开模式",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
}
