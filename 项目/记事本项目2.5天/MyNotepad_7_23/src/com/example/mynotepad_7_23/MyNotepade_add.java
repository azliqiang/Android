package com.example.mynotepad_7_23;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.SQL.Mynotepad_Help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MyNotepade_add extends Activity implements OnClickListener {
	private Button btn_nosave;
	private Button btn_save;
	private EditText add_title;
	private Spinner add_spinner;
	private EditText add_body;
	private String stg_weather;

	// �ڶ������ҳ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ���õ�ǰ�������ʵ����
		setContentView(R.layout.mynotepad_add);
		FindView();// ���Ҳ��������õ�id�ؼ�
		InputSpinner();// Ϊѡ����������ӿ�ѡ��Ϣ
		add_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// ��ȡѡ��ѡ���������Ϣ
				stg_weather = add_spinner.getItemAtPosition(position)
						.toString();

				Toast.makeText(MyNotepade_add.this, "������" + stg_weather, 0)
						.show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void InputSpinner() {
		// TODO Auto-generated method stub
		String[] stg_spinner = { "��", "����", "����", "������", "������" };
		// 0,1,2,3,4
		// ������
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stg_spinner);
		add_spinner.setAdapter(adapter);// �����ݼ��ص�add_spinner

	}

	private void FindView() {
		// TODO Auto-generated method stub
		btn_nosave = (Button) findViewById(R.id.btn_nosave);
		btn_save = (Button) findViewById(R.id.btn_save);
		add_title = (EditText) findViewById(R.id.add_title);
		add_spinner = (Spinner) findViewById(R.id.add_spinner);
		add_body = (EditText) findViewById(R.id.add_body);
		btn_nosave.setOnClickListener(this);// ��Ӽ���
		btn_save.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {// v.getId()��ȡ��������ؼ�ID
		case R.id.btn_nosave:
			// ����ť�Ի���
			if (add_title.getText().toString().trim().length() == 0
					&& add_body.getText().toString().trim().length() == 0) {
				MyNotepade_add.this.finish();// ���û�û����������ʱ������ֱ���˳���ǰҳ��
			} else {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						this);
				dialogBuilder.setTitle("������ʾ��");
				dialogBuilder.setMessage("�Ƿ�Ҫɾ����ǰ����");
				//dialogBuilder.setCancelable(false);
				dialogBuilder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						MyNotepade_add.this.finish();//�رյ�ǰҳ��
						Toast.makeText(MyNotepade_add.this, "��ɾ����д����", 0).show();
					}
				});
				dialogBuilder.setPositiveButton("ȡ��", null);
				dialogBuilder.show();
			}
			break;
		case R.id.btn_save:

			if (add_title.getText().toString().trim().length() == 0
					|| add_body.getText().toString().trim().length() == 0) {
				Toast.makeText(this, "��������ݲ���Ϊ��", 1).show();
				return;// ���ص�ǰ����
			}
			String stg_title = add_title.getText().toString().trim();// ��ȡ������
			String stg_body = add_body.getText().toString().trim();// ��ȡ������

			// ��ȡ��ǰϵͳ��ʱ��
			Date d = new Date();// ��ȡ��ǰϵͳ��ʱ�䣬�õ���ʮһ��Long���͵�����
			// ��ʽ�����߽�һ��long���͵�ʱ��ת��Ϊ�����գ�ʱ����ĸ�ʽ
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
			String stg_time = sdf.format(d);// ����һ��String���͵��ַ���
			// ����һ��ContentValuesΪ�˽���Ϣ��ŵ�ContentValues �������ڴ���ֵ�ʹ洢
			// �Լ�ֵ�Ե���ʽ����ֵ
			ContentValues values = new ContentValues();
			values.put("_TITLE", stg_title);
			values.put("_WEATHER", stg_weather);
			values.put("_TIME", stg_time);
			values.put("_BODY", stg_body);
			Mynotepad_Help db = new Mynotepad_Help(this);
			db.Date_add(values);// ���ð������б���ķ���
			Toast.makeText(this, "����ɹ�", 0).show();// ��ʾ����ɹ�
			this.finish();// �رյ�ǰҳ��

			break;

		default:
			break;
		}

	}

}
