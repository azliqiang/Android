package com.example.Adapter;

import java.util.List;

import com.example.Model.Mynotepad_Model;
import com.example.mynotepad_7_23.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyNotepad_Adapter extends BaseAdapter {

	/**
	 * MVC���
	 */
	private Context context;
	private List<Mynotepad_Model> datelist;
	private LayoutInflater inflater;// ���ּ�����

	// ���췽��
	public MyNotepad_Adapter(Context context, List<Mynotepad_Model> datelist) {
		this.context = context;
		this.datelist = datelist;
		inflater = LayoutInflater.from(context);// ��ȡȨ�ޣ���ֵ����
	}
	//���һ����Ϣ���õķ���
	public void Add_Item(List<Mynotepad_Model> datelist){
		this.datelist = datelist;
	}

	// �õ����ݵĴ�С
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datelist.size();//
	}

	// �õ�ÿһ�������ʵ��
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// ����ʾ��ͼÿһ��Itemѡ���Ҫչʾ����ͼ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder;
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.mynotepad_item, null);// mynotepad_item.xml
			viewholder.item_image = (ImageView) convertView
					.findViewById(R.id.item_image);
			viewholder.item_layout =convertView
					.findViewById(R.id.item_layout);
			viewholder.item_time = (TextView) convertView
					.findViewById(R.id.item_time);
			viewholder.item_title = (TextView) convertView
					.findViewById(R.id.item_title);
			convertView.setTag(viewholder);// ��viewholder����Ϊ��ͼ��tag����
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}

		
		viewholder.item_time.setText(datelist.get(position).get_time()
				.toString()
				+ "");
		viewholder.item_title.setText(datelist.get(position).get_title()
				.toString()
				+ "");
		if(position%2==0){
			viewholder.item_layout.setBackgroundColor(Color.BLUE);
		//	viewholder.item_layout.setBackgroundResource(R.drawable.d_sunny_cloudy);
		}
		String stg_weather = datelist.get(position).get_weather().toString().trim();
		if(stg_weather.equals("��")){
			viewholder.item_image.setImageResource(R.drawable.d_sunny);
		}else if(stg_weather.equals("С��")){
			viewholder.item_image.setImageResource(R.drawable.d_rain);
		}else if(stg_weather.equals("������")){
			viewholder.item_image.setImageResource(R.drawable.d_thunderstorm);
		}else{
			viewholder.item_image.setImageResource(R.drawable.d_sunny_cloudy);
		}
		
		
		return convertView;
	}

	//
	class ViewHolder {
		ImageView item_image;
		TextView item_time;
		TextView item_title;
		View item_layout;
	}

}
