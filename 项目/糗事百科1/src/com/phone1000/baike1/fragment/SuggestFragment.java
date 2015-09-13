package com.phone1000.baike1.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phone1000.baike1.MainActivity;
import com.phone1000.baike1.R;
import com.phone1000.baike1.adapter.SuggestFgAdapter;
import com.phone1000.baike1.bean.ItemBean;


public class SuggestFragment extends Fragment{
	private MainActivity activity=null;
	private ListView listView=null;
	private int page=1;
	private int count=5;
	private Handler handler;
	public SuggestFragment(){
		
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=(MainActivity) activity;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				List<ItemBean> list=(List<ItemBean>) msg.obj;
				Log.i("NetWork","size="+list.size());
				SuggestFgAdapter adapter=new SuggestFgAdapter(activity,list);
				listView.setAdapter(adapter);
			}
		};
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fg_suggest, container,false);
		listView = (ListView) view.findViewById(R.id.lv_item);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//http://m2.qiushibaike.com/article/list/suggest?page=1&type=refresh&count=30
		final String url=getResources().getString(R.string.url)+"suggest?page="+page;
		Log.i("NetWork","url="+url);
		//∑√Œ Õ¯¬Á  
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String result=activity.netUtils.httpGet(url);
//				Log.i("NetWork","result="+result);
				//Ω‚Œˆ
				Message msg=handler.obtainMessage();
				msg.obj=parser(result);
				handler.sendMessage(msg);
			}
		}).start();
	}
	protected List<ItemBean> parser(String result) {
		List<ItemBean> list=new ArrayList<ItemBean>();
		try {
			JSONObject jobj=new JSONObject(result);
			JSONArray jsonArray=jobj.getJSONArray("items");
			for(int i=0;i<jsonArray.length();i++){
				ItemBean bean=new ItemBean();
				bean.setContent(jsonArray.getJSONObject(i).getString("content"));
				bean.setComments_count(jsonArray.getJSONObject(i).getString("comments_count"));
				bean.setUp(jsonArray.getJSONObject(i).getJSONObject("votes").getString("up"));
				bean.setDown(jsonArray.getJSONObject(i).getJSONObject("votes").getString("down"));
				Object jtemp=jsonArray.getJSONObject(i).get("user");
				if(jtemp!=JSONObject.NULL){
					bean.setIcon(jsonArray.getJSONObject(i).getJSONObject("user").getString("icon"));
					bean.setLogin(jsonArray.getJSONObject(i).getJSONObject("user").getString("login"));
					bean.setId(jsonArray.getJSONObject(i).getJSONObject("user").getString("id"));
				}
				Log.i("NetWork","bean="+bean.toString());
				list.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
