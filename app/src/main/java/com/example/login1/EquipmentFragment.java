package com.example.login1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;

import com.zia.toastex.ToastEx;
import com.zia.toastex.anim.ToastImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {

    public EquipmentFragment() {
        // Required empty public constructor
    }

    //设备图片
    private int[] equip_icon = {R.drawable.ic_bx, R.drawable.ic_ds, R.drawable.ic_fs, R.drawable.ic_light, R.drawable.ic_kt, R.drawable.ic_sd};
    //设备名称
    private String[] equip_name = {"冰箱", "电视", "风扇", "灯泡", "空调", "扫地机器人"};

    private Switch aSwitch;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        aSwitch = (Switch) view.findViewById(R.id.sw_switch);
        //1、获取ListView对象
        final ListView listView = (ListView) view.findViewById(R.id.lv_equip);
        //2、获取数据
//        final Boolean[] equip_state = {isCheck, isCheck, isCheck, isCheck, isCheck, isCheck};
        List<Map<String, Object>> list = new ArrayList<>();
        //3、传递数据
        for (int i = 0; i < equip_icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("iv_equipIcon", equip_icon[i]);
            map.put("tv_equipName", equip_name[i]);
//            map.put("sw_switch", equip_state[i]);
            list.add(map);
        }
        //4、准备数据
        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(),//上下文
                list,//数据源
                R.layout.equipment_item,
                new String[]{"iv_equipIcon", "tv_equipName"},
                new int[]{R.id.iv_equipIcon, R.id.tv_equipName}
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                if(equip_name[position].equals("冰箱")){
                    if(equip_state[position].equals(true)){
                        ToastEx.info(Objects.requireNonNull(getContext()),"冰箱已开启").show();
                    }else {
                        ToastEx.info(Objects.requireNonNull(getContext()),"冰箱已关闭").show();
                    }
                }*/

            }
        });
   /*     if (aSwitch.isChecked()) {
            switch (equipName) {
                case "冰箱":
                    ToastEx.success(Objects.requireNonNull(getContext()), "冰箱已打开").show();
                    break;
                case "电视":
                    ToastEx.success(Objects.requireNonNull(getContext()), "电视已打开").show();
                    break;
                case "风扇":
                    ToastEx.success(Objects.requireNonNull(getContext()), "风扇已打开").show();
                    break;
                case "灯泡":
                    ToastEx.success(Objects.requireNonNull(getContext()), "灯泡已打开").show();
                    break;
                case "空调":
                    ToastEx.success(Objects.requireNonNull(getContext()), "空调已打开").show();
                    break;
                case "扫地机器人":
                    ToastEx.success(Objects.requireNonNull(getContext()), "扫地机器人已打开").show();
                    break;
                default:
                    break;
            }
        } else {
            switch (equipName) {
                case "冰箱":
                    ToastEx.success(Objects.requireNonNull(getContext()), "冰箱已关闭").show();
                    break;
                case "电视":
                    ToastEx.success(Objects.requireNonNull(getContext()), "电视已关闭").show();
                    break;
                case "风扇":
                    ToastEx.success(Objects.requireNonNull(getContext()), "风扇已关闭").show();
                    break;
                case "灯泡":
                    ToastEx.success(Objects.requireNonNull(getContext()), "灯泡已关闭").show();
                    break;
                case "空调":
                    ToastEx.success(Objects.requireNonNull(getContext()), "空调已关闭").show();
                    break;
                case "扫地机器人":
                    ToastEx.success(Objects.requireNonNull(getContext()), "扫地机器人已关闭").show();
                    break;
                default:
                    break;
            }
        }
 */
        return view;
    }
}
