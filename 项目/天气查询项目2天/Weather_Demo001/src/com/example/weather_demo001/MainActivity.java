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
	private String mpath = "101010100";// 首次开机软件默认为北京天气情况
	private String weather_city1;// 城市
	private String weather_date1;
	String weather_week1;
	String weatehr_fengsu1;
	String weatehr_wendu1;
	String weather_tianqi1;
	private weather_model weahter;
	private Map<String, Object> map;// 声明一个Map数组
	private List<Map<String, Object>> listdate = new ArrayList<Map<String, Object>>();// 创建一个list集合
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				weahter = (weather_model) msg.obj;// object
				mpath = weahter.getCode();
				Thread_Weather();// 访问网络，获取数据，解析数据

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
				if (weather_tianqi1.equals("晴")
						|| weather_tianqi1.equals("多云转晴")) {
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
		// 进度对话框，显示加载数据的进度
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle("正在加载，请稍等。。");
		dialog.show();
		Thread_Weather();// 访问网络，获取数据，解析数据

	}

	//
	private void Thread_Weather() {
		// TODO Auto-generated method stub
		/**
		 * 安卓 中只要是耗时的操作都需要放在线程中进行，避免阻碍主线程
		 *真的不知
		 */
		final Weater_Connction connction = new Weater_Connction();
		boolean net_connection = connction.IsNETworkHtttpConnection(this);// true返回手机是否连网
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
						// 连接网络，访问数据，并返回数据
						String return_stg = connction.DouwLoad(sb.toString());
						System.out.println("数据为：" + return_stg);// 将得到的数据进行输出
						// 讲一个String字符串转换成jsonobject
						JSONObject jb = new JSONObject(return_stg);
						JSONObject js = jb.getJSONObject("weatherinfo");// 得到一个json对象

						weather_city1 = js.getString("city");
						weather_date1 = js.getString("date_y");
						weather_week1 = js.getString("week");
						weatehr_fengsu1 = js.getString("fx1");
						weatehr_wendu1 = js.getString("tempF1");
						weather_tianqi1 = js.getString("weather1");

						// 获取后六天的天气情况
						// 获取6天的温度
						String temp1 = js.getString("temp1");// 或得到第一天
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
						// 后六天的风速
						String fl1 = js.getString("fl1");
						String fl2 = js.getString("fl2");
						String fl3 = js.getString("fl3");
						String fl4 = js.getString("fl4");
						String fl5 = js.getString("fl5");
						String fl6 = js.getString("fl6");

						map = new HashMap<String, Object>();
						map.put("temp", temp1);
						map.put("weather", weather1);
						map.put("date", "3月5");
						map.put("fl", fl1);
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp2);// "temp"+i==String
						map.put("weather", weather2);
						map.put("fl", fl2);
						map.put("date", "3月6");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp3);
						map.put("weather", weather3);
						map.put("fl", fl3);
						map.put("date", "3月7");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp4);
						map.put("weather", weather4);
						map.put("fl", fl4);
						map.put("date", "3月8");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp5);
						map.put("weather", weather5);
						map.put("fl", fl5);
						map.put("date", "3月9");
						listdate.add(map);

						map = new HashMap<String, Object>();
						map.put("temp", temp6);
						map.put("weather", weather6);
						map.put("fl", fl6);
						map.put("date", "3月10");
						listdate.add(map);

						// 消息类
						Message msg = new Message();
						msg.obj = listdate;// 将集合信息封装到msg.obj中
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
		if (city_ed.equals("")) {// 判断用户有没有输入
			Toast.makeText(this, "请输入城市：如：北京", 0).show();
		} else {
			dialog.show();
			try {
				XmlPullParser pullparser = Xml.newPullParser();// 获取一个xml解析器
				AssetManager assetmanager = this.getAssets();// 获得当前对象，对assets文件的管理
				InputStream is = assetmanager.open("citylist.xml");
				// inputEncoding 编码格式设置
				pullparser.setInput(is, "utf-8");
				pullparser.next();// 获取下一个标签
				// 获取标签的格式；开始文档 结束文档 开始标签 结束标签 文本
				int type = pullparser.getEventType();
				while (type != pullparser.END_DOCUMENT) {
					String name = pullparser.getName();// 获取标签名称
					if (type == pullparser.START_TAG) {// 是否是一个开始标签
						if ("name".equals(name)) {
							String city_name = pullparser.nextText();// 获取开始标签后面的文本：北京
							if (city_name.equals(city_ed)) {// 查询到得名称跟输入孔中得名称是否对应
								
								weahter = new weather_model();
								weahter.setName(city_name);
								
							}
						} else if ("code".equals(name)) {
							if (weahter != null) {
								if (weahter.getName().equals(city_ed)) {
									String city_code = pullparser.nextText();// 获取code
																				// 后面的文本
									weahter.setCode(city_code);
									Message msg = new Message();
									msg.obj = weahter;
									msg.what = 0;
									handler.sendMessage(msg);
									break;// 结束接环
								}
							}
						}
					}
					pullparser.next();// 开始下一个标签
					type = pullparser.getEventType();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
