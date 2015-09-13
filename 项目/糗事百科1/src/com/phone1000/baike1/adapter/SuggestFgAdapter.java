package com.phone1000.baike1.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone1000.baike1.R;
import com.phone1000.baike1.bean.ItemBean;
import com.phone1000.baike1.utils.ImageUtils;

public class SuggestFgAdapter extends BaseAdapter{

	private Context context;
	private List<ItemBean> list;
	private ImageUtils imageUtils;
	public SuggestFgAdapter(Context context,List<ItemBean> list){
		this.context=context;
		this.list=list;
		imageUtils=new ImageUtils();
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
			convertView=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
		}
		ImageView imageView=(ImageView) convertView.findViewById(R.id.iv_icon_item);
		TextView tv_title=(TextView) convertView.findViewById(R.id.tv_title_item);
		TextView tv_content=(TextView) convertView.findViewById(R.id.tv_content_item);
		TextView tv_up=(TextView) convertView.findViewById(R.id.tv_up_item);
		TextView tv_comments=(TextView) convertView.findViewById(R.id.tv_comments_item);
		//加载头像资源
		if(TextUtils.isEmpty(list.get(position).getIcon())){
			imageView.setImageResource(R.drawable.ic_launcher);
		}else{
			//获取
			StringBuffer sb=new StringBuffer(context.getResources().getString(R.string.url_pic));
			sb.append(list.get(position).getId().substring(0,list.get(position).getId().length()-4)+"/");
			sb.append(list.get(position).getId()+"/medium/");
			sb.append(list.get(position).getIcon());
			imageUtils.loadBitmap(sb.toString(),imageView);
		}
		tv_title.setText(list.get(position).getLogin());
		tv_content.setText(list.get(position).getContent());
		tv_up.setText("好笑 "+list.get(position).getUp());
		tv_comments.setText(".评论 "+list.get(position).getComments_count());
		
		return convertView;
	}

}
