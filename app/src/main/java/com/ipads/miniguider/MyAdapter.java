package com.ipads.miniguider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by zxlmillie on 16-12-15.
 */

public class MyAdapter extends BaseAdapter {

    public final class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
    }

    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;
    int mlayout;

    public MyAdapter(Context context, List<Map<String, Object>> data, int layout){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mlayout = layout;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            holder=new ViewHolder();
            convertView = mInflater.inflate(this.mlayout, null);
            holder.img = (ImageView)convertView.findViewById(R.id.image);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.info = (TextView)convertView.findViewById(R.id.info);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }
        holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
        //holder.title.setText((String)mData.get(position).get("title"));
        holder.info.setText((String)mData.get(position).get("info"));
        return convertView;
    }

}