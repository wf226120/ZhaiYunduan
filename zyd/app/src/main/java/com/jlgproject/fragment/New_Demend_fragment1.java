package com.jlgproject.fragment;

import android.view.View;
import android.widget.ListView;

import com.jlgproject.R;
import com.jlgproject.adapter.New_Demend_Adapter;
import com.jlgproject.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by sunbeibei on 2017/5/8.
 * 全部资产
 */

public class New_Demend_fragment1 extends BaseFragment {

    private ListView listView;
    private ArrayList<String>list= new ArrayList<>();

    @Override
    public int getLoadViewId() {
        return R.layout.debt_manger_listview;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        for (int i = 0; i <8 ; i++) {
            list.add("500万");
        }
    }

    @Override
    public View initView(View view) {
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new New_Demend_Adapter(getActivity(),list));
        return view ;
    }
}
