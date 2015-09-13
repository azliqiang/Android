package com.example.mynotepad_7_23;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.MyNotepad_Adapter;
import com.example.Model.Mynotepad_Model;
import com.example.SQL.Mynotepad_Help;

public class MainActivity extends Activity {

	private Button btn_add;
	private ListView listView_01;
	private List<Mynotepad_Model> datelist;
	private Mynotepad_Help db;
	private MyNotepad_Adapter adapter;
	private TextView tv_count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FindView();
		// 实例化帮助类
		db = new Mynotepad_Help(this);
		datelist = db.Date_look();// 查询数据===M
		adapter = new MyNotepad_Adapter(this, datelist);// MC
		listView_01.setAdapter(adapter);// MVC
		// 获取数据库中信息条数
		tv_count.setText("全部\n" + db.Date_count());
		// tv_count.setText("全部\n"+datelist.size());

		// listview每个Item的点击事件
		listView_01.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Mynotepad_Model note = datelist.get(position);
				Intent intent = new Intent(MainActivity.this,
						MyNotepad_look.class);
				intent.putExtra("title", note.get_title());
				intent.putExtra("weather", note.get_weather());
				intent.putExtra("body", note.get_body());

				startActivity(intent);
				Toast.makeText(MainActivity.this, "您点击了：" + note.get_title(), 1)
						.show();

			}
		});
		// listview每一Item长按事件
		listView_01.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int positon, long id) {
				// TODO Auto-generated method stub
				Mynotepad_Model note = datelist.get(positon);
				final String time = note.get_time();
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						MainActivity.this);
				dialogBuilder.setTitle("警告提示框");
				dialogBuilder.setMessage("是否要删除" + time);
				// dialogBuilder.setCancelable(false);
				dialogBuilder.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								db.Date_delete(time);// 删除数据

								// 给用户一个删除成功的提示
								Toast.makeText(MainActivity.this,
										"成功删除：" + time, 1).show();
								Update_Myadapter();

							}
						});
				dialogBuilder.setPositiveButton("取消", null);
				dialogBuilder.show();

				return true;
			}
		});
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						MyNotepade_add.class);
				// startActivity(intent);//启动activity
				// requestCode 请求码 //带返回值的启动方式
				startActivityForResult(intent, 1);

			}
		});

	}

	private void FindView() {
		// TODO Auto-generated method stub
		btn_add = (Button) findViewById(R.id.btn_add);
		listView_01 = (ListView) findViewById(R.id.listview_01);// V
		tv_count = (TextView) findViewById(R.id.tv_count);

	}

	// 接受返回当前页面的信息
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Update_Myadapter();
	}

	// 刷新信息列表的方法
	public void Update_Myadapter() {
		if (datelist.size() > 0) {
			datelist.clear();// 清空集合
		}

		datelist = db.Date_look();// 查询数据===M
		adapter.Add_Item(datelist);
		adapter.notifyDataSetChanged();// 刷新当前页面显示
		tv_count.setText("全部\n" + db.Date_count());
	}

}
