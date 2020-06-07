package com.example.login1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.superluo.textbannerlibrary.TextBannerView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        TextBannerView tvBanner = (TextBannerView) view.findViewById(R.id.tv_banner);
        //设置数据
        List<String> list = new ArrayList<>();

        list.add("学好Java、Android、C#、C、ios、html+css+js");
        list.add("走遍天下都不怕！！！！！");
        list.add("不是我吹，就怕你做不到，哈哈");
        list.add("superK\uD83D\uDC02");
        list.add("你是最棒的，奔涌吧后浪！");
        //调用setDatas(List<String>)方法后,TextBannerView自动开始轮播
        //注意：此方法目前只接受List<String>类型
        tvBanner.setDatas(list);
        final com.skyfishjy.library.RippleBackground rippleBackground = (com.skyfishjy.library.RippleBackground)view.findViewById(R.id.rb_content);
        rippleBackground.startRippleAnimation();
        ImageView iv_musicPlay = view.findViewById(R.id.iv_musicPlay);
        iv_musicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MusicplayActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<String> list_path = new ArrayList<String>();
        list_path.add("https://www.pexels.com/zh-cn/photo/1665722/");
        list_path.add("https://www.pexels.com/zh-cn/photo/1735658/");
        list_path.add("https://www.pexels.com/zh-cn/photo/4108271/");
        Banner banner = (Banner) view.findViewById(R.id.banner);

        //banner样式
        banner.setBannerGalleryMZ(10);//魅族

        banner.addBannerLifecycleObserver(this)
                .setAdapter(new ImageAdapter(list_path))
                .setIndicator(new CircleIndicator(getActivity()))
                .start();

        return view;
    }
}
