/**
 * FileName:MyPagerAdapter.java
 * Copyright(C) 2016 zteict
 */
package com.ipads.miniguider;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author YaoDiWei
 * @version
 * @see com.yao.tab01.MyPagerAdapter.java
 */
public class MyPagerAdapter extends PagerAdapter {

	private List<View> views = new ArrayList<View>();

	public MyPagerAdapter(List<View> views) {
		super();
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = views.get(position);
		container.addView(view);
		return view;
	}

}
