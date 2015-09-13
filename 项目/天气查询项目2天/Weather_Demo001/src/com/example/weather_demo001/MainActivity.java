package com.example.weather_demo001;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText weahter_title_ed;
	private Button weahter_title_btn;
	private ImageView weather_image;
	private TextView weahter_city;
	private TextView weather_date;
	private TextView weather_week;
	private TextView weather_fengsu;
	private TextView weather_wendu;
	private ListView weather_listview;
	public ProgressDialog dialog;
	private StringBuffer sb;
	private String city_ed;
	private String mpath = "101010100";// �״ο������Ĭ��Ϊ�����������
	private String weather_city1;// ����
	private String weather_date1;
	String weather_week1;
	String weatehr_fengsu1;
	String weatehr_wendu1;
	String weather_tianqi1;
	private weather_model weahter;
	private Map<String, Object> map;// ����һ��Map����
	private List<Map<String, Object>> listdate = new ArrayList<Map<String, Object>>();// ����һ��list����
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				weahter = (weather_model) msg.obj;// object
				mpath = weahter.getCode();
				Thread_Weather();// �������磬��ȡ���ݣ���������

				break;
			case 1:
				/**
				 * MVC m - v - c
				 */
				dialog.dismiss();

				listdate = (List<Map<String, Object>>) msg.obj;
				Weather_adapter adapter = new Weather_adapter(
						MainActivity.this, listdate);
				weather_listview.setAdapter(adapter);

				weahter_city.setText(weather_city1);
				weather_date.setText(weather_date1);
				weather_week.setText(weather_week1);
				weather_wendu.setText(weatehr_wendu1);
				if (weather_tianqi1.equals("��")
						|| weather_tianqi1.equals("����ת��")) {
					weather_image.setImageResource(R.drawable.d_sunny);
				} else {
					weather_image.setImageResource(R.drawable.d_thunderstorm);
				}

				break;

			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FindView();
		// ���ȶԻ�����ʾ�������ݵĽ���
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle("���ڼ��أ����Եȡ���");
		dialog.show();
		Thread_Weather();// �������磬��ȡ���ݣ���������

	}

	//
	private void Thread_Weather() {
		// TODO Auto-generated method stub
		/**
		 * ��׿ ��ֻҪ�Ǻ�ʱ�Ĳ�������Ҫ�����߳��н��У������谭���߳�
		 *��Ĳ�֪
		 */
		final Weater_Connction connction = new Weater_Connction();
		boolean net_connection = connction.IsNETworkHtttpConnection(this);// true�����ֻ��Ƿ�����
		if (net_connection) {
			// http://m.weather.com.cn/atad/101010100.html
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// http://m.weather.com.cn/atad/101121301.html
					sb = new StringBuffer("http://m.weather.com.cn/atad/");
					sb.append(mpath);
					sb.append(".html");
					try {
						// �������磬�������ݣ�����������
						String return_stg = connction.DouwLoad(sb.toString());
						System.out.println("����Ϊ��" + return_stg);// ���õ������ݽ������
						// ��һ��String�ַ���ת����jsonobject
						JSONObject jb = new JSONObject(return_stg);
						JSONObject js = jb.getJSONObject("weatherinfo");// �õ�һ��json����

						weather_city1 = js.getString("city");
						weather_date1 = js.getString("date_y");
						weather_week1 = js.getString("week");
						weatehr_fengsu1 = js.getString("fx1");
						weatehr_wendu1 = js.getString("tempF1");
						weather_tianqi1 = js.getString("weather1");

						// ��ȡ��������������
						// ��ȡ6����¶�
						String temp1 = js.getString("temp1");// ��õ���һ��
						String temp2 = js.getString("temp2");
						String temp3 = js.getString("temp3");
						String temp4 = js.getString("temp4");
						String temp5 = js.getString("temp5");
						String temp6 = js.getString("temp6");
						//
						String weather1 = js.getString("weather1");
						String weather2 = js.getString("weather2");
						String weather3 = js.getString("weather3");
						String weather4 = js.getString("weather4");
						String weather5 = js.getString("weather5");
						String weather6 = js.getString("weather6");
						// ������ķ���
						String fl1 = js.getString("fl1");
						String fl2 = js.getString("fl2");
						String fl3 = js.getString("fl3");
						String fl4 = js.getString("fl4");
						String fl5 = js.getString("fl5");
						String fl6 = js.getString("fl6");

						map = new HashMap<String, Object>();
						map.put("temp", temp1);
						map.put("weather", weather1);
						map.put("date", "3��5");
						map.put("fl", fl1);
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp2);// "temp"+i==String
						map.put("weather", weather2);
						map.put("fl", fl2);
						map.put("date", "3��6");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp3);
						map.put("weather", weather3);
						map.put("fl", fl3);
						map.put("date", "3��7");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp4);
						map.put("weather", weather4);
						map.put("fl", fl4);
						map.put("date", "3��8");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp5);
						map.put("weather", weather5);
						map.put("fl", fl5);
						map.put("date", "3��9");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp6);
						map.put("weather", weather6);
						map.put("fl", fl6);
						map.put("date", "3��10");
						listdate.add(map);

						// ��Ϣ��
						Message msg = new Message();
						msg.obj = listdate;// ��������Ϣ��װ��msg.obj��
						msg.what = 1;

						handler.sendMessage(msg);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}).start();

		}

	}

	private void FindView() {
		// TODO Auto-generated method stub
		weahter_title_ed = (EditText) findViewById(R.id.weather_title_ed);
		weahter_title_btn = (Button) findViewById(R.id.weather_title_btn);
		weather_image = (ImageView) findViewById(R.id.weather_image);
		weahter_city = (TextView) findViewById(R.id.weather_city);
		weather_date = (TextView) findViewById(R.id.weather_date);
		weather_week = (TextView) findViewById(R.id.weather_week);
		weather_fengsu = (TextView) findViewById(R.id.weather_fengsu);
		weather_wendu = (TextView) findViewById(R.id.weather_wendu);
		weather_listview = (ListView) findViewById(R.id.weather_listview);
	}

	public void city_Onclick(View view) {
		city_ed = weahter_title_ed.getText().toString().trim();
		if (city_ed.equals("")) {// �ж��û���û������
			Toast.makeText(this, "��������У��磺����", 0).show();
		} else {
			dialog.show();
			try {
				XmlPullParser pullparser = Xml.newPullParser();// ��ȡһ��xml������
				AssetManager assetmanager = this.getAssets();// ��õ�ǰ���󣬶�assets�ļ��Ĺ���
				InputStream is = assetmanager.open("citylist.xml");
				// inputEncoding �����ʽ����
				pullparser.setInput(is, "utf-8");
				pullparser.next();// ��ȡ��һ����ǩ
				// ��ȡ��ǩ�ĸ�ʽ����ʼ�ĵ� �����ĵ� ��ʼ��ǩ ������ǩ �ı�
				int type = pullparser.getEventType();
				while (type != pullparser.END_DOCUMENT) {
					String name = pullparser.getName();// ��ȡ��ǩ����
					if (type == pullparser.START_TAG) {// �Ƿ���һ����ʼ��ǩ
						if ("name".equals(name)) {
							String city_name = pullparser.nextText();// ��ȡ��ʼ��ǩ������ı�������
							if (city_name.equals(city_ed)) {// ��ѯ�������Ƹ�������е������Ƿ��Ӧ
								
								weahter = new weather_model();
								weahter.setName(city_name);
								
							}
						} else if ("code".equals(name)) {
							if (weahter != null) {
								if (weahter.getName().equals(city_ed)) {
									String city_code = pullparser.nextText();// ��ȡcode
																				// ������ı�
									weahter.setCode(city_code);
									Message msg = new Message();
									msg.obj = weahter;
									msg.what = 0;
									handler.sendMessage(msg);
									break;// �����ӻ�
								}
							}
						}
					}
					pullparser.next();// ��ʼ��һ����ǩ
					type = pullparser.getEventType();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
