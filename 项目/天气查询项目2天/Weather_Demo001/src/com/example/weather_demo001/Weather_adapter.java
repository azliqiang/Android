package com.example.weather_demo001;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Weather_adapter extends BaseAdapter {
	private List<Map<String, Object>> listdate;
	private Context context;
	private LayoutInflater inflater; // ���ּ�����

	// ���췽��
	public Weather_adapter(Context context, List<Map<String, Object>> listdate) {
		this.listdate = listdate;
		this.context = context;
		inflater = inflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdate.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listdate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder;
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.weather_item, null);
			// ImageView item_image = new (ImageView)
			// findViewById(R.id.item_image);

			viewholder.item_image = (ImageView) convertView
					.findViewById(R.id.item_image);// ��ʾ��ͼƬ
			viewholder.item_date = (TextView) convertView
					.findViewById(R.id.item_date);// ��ʾ����
			viewholder.item_wendu = (TextView) convertView
					.findViewById(R.id.item_wendu);// ��ʾ�¶�
			viewholder.item_fengsu = (TextView) convertView
					.findViewById(R.id.item_fengsu);// ��ʾ����
			viewholder.item_tianqi = (TextView) convertView
					.findViewById(R.id.item_tianqi);// ��ʾ�������
			convertView.setTag(viewholder);

		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		// listdate.get(position) ��ȡ��map����---��ֵ�Ե���ʽ����
		viewholder.item_date.setText(listdate.get(position).get("date")
				.toString());
		viewholder.item_wendu.setText(listdate.get(position).get("temp")
				.toString());
		viewholder.item_fengsu.setText(listdate.get(position).get("fl")
				.toString());
		viewholder.item_tianqi.setText(listdate.get(position).get("weather")
				.toString());
		String stg = listdate.get(position).get("weather").toString();
		if (stg.equals("��") || stg.equals("����ת��")) {
			viewholder.item_image.setImageResource(R.drawable.d_sunny);
		} else if (stg.equals("�� ��") || stg.equals("��ת����")) {
			viewholder.item_image.setImageResource(R.drawable.d_sunny_cloudy);
		} else {
			viewholder.item_image.setImageResource(R.drawable.d_rain);
		}

		return convertView;
	}

	// ��ʱ���findviewbyid�ҵ��Ŀؼ�
	class ViewHolder {
		// ��������
		ImageView item_image;
		TextView item_date;
		TextView item_wendu;
		TextView item_fengsu;
		TextView item_tianqi;

	}

}
