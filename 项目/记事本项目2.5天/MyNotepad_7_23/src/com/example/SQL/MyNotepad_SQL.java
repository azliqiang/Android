package com.example.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyNotepad_SQL extends SQLiteOpenHelper {
	/**
	 * 
	 * @param context
	 *            --�����Ĳ˵�
	 * @param name
	 *            --���ݿ���
	 * @param factory
	 *            --�α�
	 * @param version
	 *            -- ���ݿ�İ汾��
	 */
	private static final String DB_NAME = "MyNotepad_SQL.db";// ���ݿ�����
	private static final int DB_VERSION = 1;// ���ݿ�İ汾��
	private static final String TABLE_NAME = "mynotepad_date";// ����
	// PRIMARY ���� AUTOINCREMENT����
	private static final String CREATK_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + "_ID INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ "_TITLE VARCHAR(20) NOT NULL ,"
			+ "_WEATHER VARCHAR(20) NOT NULL ,"
			+ "_TIME VARCHAR(20) NOT NULL ," + "_BODY VARCHAR(200) NOT NULL"
			+ ");";
	//create table �������ֶ���  ��������(��С) �������ԣ��ֶ���  ��������(��С) �������ԣ�;

	public MyNotepad_SQL(Context context) {
		// Ϊ����Ĺ��췽�����ݲ���
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * onCreate �����ݿ��һ�δ�����ʱ����ã������ݿ����ʱ�����������µ��� ֻ����һ�Σ�
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATK_TABLE);

	}

	/**
	 * onUpgrade �������ݿ�汾��ʱ�����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int F, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("");

	}

}
