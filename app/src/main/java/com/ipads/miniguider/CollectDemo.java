package com.ipads.miniguider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.service.HttpGetListener;
import com.service.HttpGetData;
import com.ui.CornerListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.id.list;

public class CollectDemo extends Activity implements OnClickListener, HttpGetListener{

    private static final String url0 = "http://112.74.49.183:8080/Entity/U64afbe41b0739/GpsPro/Route/?Route.username=李四";
    private static final String url1 = "http://112.74.49.183:8080/Entity/U64afbe41b0739/GpsPro/Busline/?Busline.username=李四";
    private static final  int VIEW_CNT = 2;

    private List<List<Map<String, Object>>> mData = new ArrayList<List<Map<String, Object>>>(VIEW_CNT);

    private List<HttpGetData> mhttpgetdata = new ArrayList<HttpGetData>();
    private List<View> views = new ArrayList<View>();
    //List<Map<String, Object>> mData;
    private ViewPager viewPager;
    private LinearLayout llChat, llFriends, llContacts, llSettings;
    private ImageView ivChat, ivFriends, ivContacts, ivSettings, ivCurrent;
    private TextView tvChat, tvFriends, tvContacts, tvSettings, tvCurrent;

    private int tabCurrent;

    private String delim1 = " -> ";
    private String delim2 = " --- ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_colllect);

        initView();
        initData();

        mhttpgetdata.add((HttpGetData) new HttpGetData(url0,this).execute());
        mhttpgetdata.add((HttpGetData) new HttpGetData(url1,this).execute());
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        llChat = (LinearLayout) findViewById(R.id.llChat);
        llFriends = (LinearLayout) findViewById(R.id.llFriends);
		/*llContacts = (LinearLayout) findViewById(R.id.llContacts);
		llSettings = (LinearLayout) findViewById(R.id.llSettings);*/

        llChat.setOnClickListener(this);
        llFriends.setOnClickListener(this);
		/*llContacts.setOnClickListener(this);
		llSettings.setOnClickListener(this);*/

        ivChat = (ImageView) findViewById(R.id.ivChat);
        ivFriends = (ImageView) findViewById(R.id.ivFriends);
		/*ivContacts = (ImageView) findViewById(R.id.ivContacts);
		ivSettings = (ImageView) findViewById(R.id.ivSettings);*/

        tvChat = (TextView) findViewById(R.id.tvChat);
        tvFriends = (TextView) findViewById(R.id.tvFriends);
		/*tvContacts = (TextView) findViewById(R.id.tvContacts);
		tvSettings = (TextView) findViewById(R.id.tvSettings);*/

        ivChat.setSelected(true);
        tvChat.setSelected(true);
        ivCurrent = ivChat;
        tvCurrent = tvChat;
        tabCurrent = 0;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void initData(){
        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.tab01, null);
        View tab02 = mInflater.inflate(R.layout.tab02, null);
		/*View tab03 = mInflater.inflate(R.layout.tab03, null);
		View tab04 = mInflater.inflate(R.layout.tab04, null);*/

        /*getData();

		ListView lst1 = (ListView)tab01.findViewById(R.id.setting_list);
		lst1.setAdapter(new MyAdapter(this, mData.get(0), R.layout.listview_item));
        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                List<Map<String, Object>> curitem = mData.get(0);
                if(arg2 >=0 && arg2 < curitem.size()){
                    Intent intent = new Intent();
                    String city = (String)curitem.get(arg2).get("title");
                    String info = (String)curitem.get(arg2).get("info");
                    int pos1 = info.indexOf(delim1);
                    int pos2 = info.indexOf(delim2);
                    String start = info.substring(0, pos1);
                    String end = info.substring(pos1 + delim1.length(), pos2);
                    String way = info.substring(pos2 + delim2.length());
                    System.out.println("city is " + city + "start is " +
                            start + "end is " + end + "way is " + way);
                    intent.putExtra("city", city);
                    intent.putExtra("start", start);
                    intent.putExtra("end", end);
                    intent.putExtra("way", way);
                    intent.setClass(CollectDemo.this, ShowRoute.class);
                    startActivity(intent);
                }

            }

        });
		ListView lst2 = (ListView)tab02.findViewById(R.id.setting_list);
		lst2.setAdapter(new MyAdapter(this, mData.get(1), R.layout.listview_item));
        lst2.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
            }

        });*/
		/*ListView lst3 = (ListView)tab03.findViewById(R.id.listview);
		lst3.setAdapter(new MyAdapter(this, getData(), R.layout.listview));
		ListView lst4 = (ListView)tab04.findViewById(R.id.listview);
		lst4.setAdapter(new MyAdapter(this, getData(), R.layout.listview));*/

        views.add(tab01);
        views.add(tab02);
		/*views.add(tab03);
		views.add(tab04);*/

        MyPagerAdapter adapter = new MyPagerAdapter(views);
        viewPager.setAdapter(adapter);
    }

    private void getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        //String str="this is first line";
        map = new HashMap<String, Object>();
        map.put("title", "上海");
        map.put("info", "上交 -> 东方明珠 --- 乘车");
        map.put("img", R.drawable.route);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "上海");
        map.put("info", "上交 -> 华师 --- 步行");
        map.put("img", R.drawable.route);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "上海");
        map.put("info", "上交 -> 上财 --- 公交");
        map.put("img", R.drawable.route);
        list.add(map);
        //mData.add(list);

        map = new HashMap<String, Object>();
        map.put("title", "上海");
        map.put("info", "上交 -> 上外 --- 公交");
        map.put("img", R.drawable.line);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "上海");
        map.put("info", "上交 -> 普陀 --- 公交");
        map.put("img", R.drawable.line);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "上海");
        map.put("info", "上交 -> 闵行 --- 公交");
        map.put("img", R.drawable.line);

        list.add(map);

        mData.add(list);
        mData.add(list);
        //return list;
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    private void changeTab(int id) {
        tabCurrent = 1 - tabCurrent;
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {
            case R.id.llChat:
                viewPager.setCurrentItem(0);
            case 0:
                ivChat.setSelected(true);
                ivCurrent = ivChat;
                tvChat.setSelected(true);
                tvCurrent = tvChat;
                break;
            case R.id.llFriends:
                viewPager.setCurrentItem(1);
            case 1:
                ivFriends.setSelected(true);
                ivCurrent = ivFriends;
                tvFriends.setSelected(true);
                tvCurrent = tvFriends;
                break;
		/*case R.id.llContacts:
			viewPager.setCurrentItem(2);
		case 2:
			ivContacts.setSelected(true);
			ivCurrent = ivContacts;
			tvContacts.setSelected(true);
			tvCurrent = tvContacts;
			break;
		case R.id.llSettings:
			viewPager.setCurrentItem(3);
		case 3:
			ivSettings.setSelected(true);
			ivCurrent = ivSettings;
			tvSettings.setSelected(true);
			tvCurrent = tvSettings;
			break;*/
            default:
                break;
        }
    }

    @Override
    public void GetDataUrl(String data){
        //从RMP返回的数据
        System.out.println(data);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int curmode = 1;
        try{
            JSONObject json = new JSONObject(data);
            // 0: 路线规划
            if(!json.optString("Route").equals("")) {
                JSONArray route = json.getJSONArray("Route");
                //System.out.println("the length is " + route.length());
                for(int i = 0; i < route.length(); ++i){
                    JSONObject ele = new JSONObject(route.getString(i));
                    String ele_str = ele.getString("start") + delim1 + ele.getString("end")
                            + delim2 + ele.getString("way");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("img", R.drawable.route);
                    map.put("title", /*ele.getString("city")*/"上海");
                    map.put("info", ele_str);
                    list.add(map);
                }
                //curmode = 0;
                CornerListView listvw = (CornerListView) views.get(0).findViewById(R.id.setting_list);
                listvw.setAdapter(new MyAdapter(this, list, R.layout.listview_item));
                mData.add(0, list);
                listvw.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        List<Map<String, Object>> curitem = mData.get(0);
                        if(arg2 >=0 && arg2 < curitem.size()){
                            Intent intent = new Intent();
                            String city = (String)curitem.get(arg2).get("title");
                            String info = (String)curitem.get(arg2).get("info");
                            int pos1 = info.indexOf(delim1);
                            int pos2 = info.indexOf(delim2);
                            String start = info.substring(0, pos1);
                            String end = info.substring(pos1 + delim1.length(), pos2);
                            String way = info.substring(pos2 + delim2.length());
                            System.out.println("city is " + city + "start is " +
                                    start + "end is " + end + "way is " + way);
                            intent.putExtra("city", city);
                            intent.putExtra("start", start);
                            intent.putExtra("end", end);
                            intent.putExtra("way", way);
                            intent.setClass(CollectDemo.this, ShowRoute.class);
                            startActivity(intent);
                        }

                    }

                });
            }else if(!json.optString("Busline").equals("")) {// 1: 公交线路
                JSONArray route = json.getJSONArray("Busline");
                //System.out.println("the length is " + route.length());
                for (int i = 0; i < route.length(); ++i) {
                    JSONObject ele = new JSONObject(route.getString(i));
                    String ele_str = ele.getString("line");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("img", R.drawable.line);
                    map.put("title", ele.getString("city"));
                    map.put("info", ele_str);
                    list.add(map);
                }
                //curmode = 1;
                CornerListView listvw = (CornerListView) views.get(1).findViewById(R.id.setting_list);
                listvw.setAdapter(new MyAdapter(this, list, R.layout.listview_item));
                mData.add(1, list);
                listvw.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        List<Map<String, Object>> curitem = mData.get(1);
                        if(arg2 >=0 && arg2 < curitem.size()){
                            Intent intent = new Intent();
                            String city = (String)curitem.get(arg2).get("title");
                            String busline = (String)curitem.get(arg2).get("info");
                            System.out.println("city is " + city + "busline is " + busline);
                            intent.putExtra("city", city);
                            intent.putExtra("busline", busline);
                            intent.setClass(CollectDemo.this, ShowBusline.class);
                            startActivity(intent);
                        }

                    }

                });
            }
            /*CornerListView listvw = (CornerListView) views.get(curmode).findViewById(R.id.setting_list);
            listvw.setAdapter(new MyAdapter(this, list, R.layout.listview_item));*/
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
