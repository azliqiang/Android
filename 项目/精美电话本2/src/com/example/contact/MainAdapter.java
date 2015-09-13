package com.example.contact;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
	private List<Bean> list = null;
	private Context context = null;

	public MainAdapter(List<Bean> list, Context context) {
		this.list = list;
		this.context = context;

		for (Bean bean : list) {
			Log.i("Add", "adapterbean=" + bean.toString());
		}
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
		Log.i("Add", "position=" + position);
		
		
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					parent,false);
		}
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.iv_item);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_item);
		imageView.setImageResource(Integer.parseInt(list.get(position)
				.getImgId()));
		Log.i("Add", "adaptermyId=" + list.get(position).getImgId());
		textView.setText(list.get(position).getName());
		
		return convertView;
	}

}
