package com.example.contact;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AddAdapter extends BaseAdapter {
	
	private List<Integer> list;
	private Context context;
	
	public AddAdapter(Context context,List<Integer> list){
		this.context=context;
		this.list=list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_add,null);
		}
		ImageView iv_item_add=(ImageView) convertView.findViewById(R.id.iv_item_add);
		iv_item_add.setImageResource(list.get(position));
		return convertView;
	}

}
