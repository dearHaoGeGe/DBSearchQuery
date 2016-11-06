package com.my.dbsearchquery;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.dbsearchquery.bean.CaseInfo;
import com.my.dbsearchquery.dbmanager.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, TextWatcher {

    private static String TAG = "MainActivity------>";
    private EditText editText;
    private ListView listView;
    private MyAdapter adapter;
    private List<CaseInfo> data;
    private DBUtils db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        //监听编辑框的内容变化
        editText.addTextChangedListener(this);
        editText.clearFocus();
        listView.setOnItemClickListener(this);
    }

    private void initData() {
        db = new DBUtils(this);
        data = new ArrayList<>();
        data.add(new CaseInfo(1L, "盖伦和卡特离婚财产纠纷案件", 20150326L, "2015高执字第102号"));
        data.add(new CaseInfo(2L, "王健林：有万达，上海迪士尼20年之内盈不了利", 20100326L, "2015高执字第122号"));
        data.add(new CaseInfo(3L, "携手同心，誓达幸福——当婚礼遇见马拉松", 20150326L, "2014高执字第302号"));
        data.add(new CaseInfo(4L, "揭秘，马云、李嘉诚、王健林的保镖！", 20051026L, "2001高执字第112号"));
        data.add(new CaseInfo(5L, "辽宁舰舰长换人！背后透出喜讯：中国航母可以打仗了", 20101222L, "2010高执字第005号"));
        data.add(new CaseInfo(6L, "大连马拉松水杯遍地 后勤人员忙清扫", 20160522L, "2016高执字第522号"));
        data.add(new CaseInfo(7L, "小米手机今年首次下滑 富士康代工量将萎缩10%", 20101103L, "2015高执字第102号"));
        data.add(new CaseInfo(8L, "将全部iPhone生产搬到印度？库克说，在很认真地考虑这个事情", 20070326L, "2007高执字第107号"));
        data.add(new CaseInfo(9L, "程序猿薪酬大起底，年薪高，但时薪并不高", 20151109L, "2015高执字第1109号"));
        data.add(new CaseInfo(10L, "用逻辑和常识来揭晓答案：我们应该惧怕人工智能吗？", 20160509L, "2015高执字第509号"));

        db.insertMoreData(data);    //向数据库中插入所有数据
        data = db.orderDesc();   //通过时间倒叙查询
        adapter = new MyAdapter(data, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG + "beforeTextChanged--->", "CharSequence=" + s + ",start=" + start + ",count=" + count + ",after=" + after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG + "onTextChanged--->", "CharSequence=" + s + ",start=" + start + ",before=" + before + ",count=" + count);
        //TODO 在这里添加查询
        List<CaseInfo> tempData = db.fuzzyQueryByName(s.toString());
        adapter.setDatas(tempData);
        if (s.equals("")) {
            adapter.setDatas(data);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG + "afterTextChanged", "Editable=" + s);
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<CaseInfo> datas;
        private Context context;

        public MyAdapter(List<CaseInfo> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }

        public void setDatas(List<CaseInfo> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.item_activity_main, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tv_name_item_MainAct =
                        (TextView) convertView.findViewById(R.id.tv_name_item_MainAct);
                viewHolder.tv_number_item_MainAct =
                        (TextView) convertView.findViewById(R.id.tv_number_item_MainAct);
                viewHolder.tv_time_item_MainAct =
                        (TextView) convertView.findViewById(R.id.tv_time_item_MainAct);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (datas != null) {
                viewHolder.tv_name_item_MainAct.setText(datas.get(position).getName());
                String time = subStr(datas.get(position).getTime() + "");   //截取字符串
                viewHolder.tv_time_item_MainAct.setText(time);
                viewHolder.tv_number_item_MainAct.setText(datas.get(position).getNumber());
            }
            return convertView;
        }

        class ViewHolder {
            private TextView tv_name_item_MainAct, tv_number_item_MainAct,
                    tv_time_item_MainAct;
        }

        /**
         * 截取字符串
         * 如时间为：20150321，截取后为2015-03-21
         *
         * @param time_long
         * @return
         */
        private String subStr(String time_long) {
            String time;
            String t1 = time_long.substring(0, 4);
            String t2 = time_long.substring(4, 6);
            String t3 = time_long.substring(6, 8);
            time = t1 + "-" + t2 + "-" + t3;
            time = "立案时间:  " + time;
            return time;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**删除数据库中的所有数据*/
        db.deleteAllData(CaseInfo.class);
        /**关闭数据库链接*/
        db.closeConnection();
    }
}
