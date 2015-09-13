package com.example.SQL;

import java.util.ArrayList;
import java.util.List;

import com.example.Model.Mynotepad_Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Mynotepad_Help {
	/**
	 * ���ݿ�İ����࣬ʵ�ֶ����ݿ�����Ϣ����ɾ�Ĳ鹤���� context --�����Ĳ˵�
	 */
	public Context context;// ȫ�ֱ���
	private MyNotepad_SQL DB_SQL;
	private ArrayList<Mynotepad_Model> datelist;

	public Mynotepad_Help(Context context) {
		this.context = context;
		// �������ݿ� �洢���ڴ���
		DB_SQL = new MyNotepad_SQL(context);

	}

	// ������ݵķ���
	public void Date_add(ContentValues values) {
		// ��ȡ�����ݿ��д��Ȩ�ޣ� �õ�һ��SQLiteDatabase
		SQLiteDatabase db = DB_SQL.getWritableDatabase();
		// ����һ������
		/**
		 * table �����ű���в���
		 */
		db.insert("mynotepad_date", null, values);

	}

	// ɾ�����ݵķ���
	public void Date_delete(String time) {
		// ��ȡ�����ݿ��д��Ȩ�ޣ� �õ�һ��SQLiteDatabase
		SQLiteDatabase db = DB_SQL.getWritableDatabase();
		db.delete("mynotepad_date", "_TIME=?", new String[] { time });
	}

	// �޸����ݵķ���
	public void Date_update() {

	}

	// ��ѯ���ݵķ���

	public List<Mynotepad_Model> Date_look() {
		datelist = new ArrayList<Mynotepad_Model>();
		// ��ȡ�����ݿ�Ķ�дȨ��
		SQLiteDatabase db = DB_SQL.getReadableDatabase();
		// ��ѯȫ����Ϣ
		Cursor cursor = db.query("mynotepad_date", null, null, null, null,
				null, null);

		while (cursor.moveToNext()) {// �鿴�Ƿ�����һ�� true false;
			Mynotepad_Model note = new Mynotepad_Model(cursor.getInt(cursor
					.getColumnIndex("_ID")), cursor.getString(cursor
					.getColumnIndex("_TITLE")), cursor.getString(cursor
					.getColumnIndex("_WEATHER")), cursor.getString(cursor
					.getColumnIndex("_TIME")), cursor.getString(cursor
					.getColumnIndex("_BODY")));
			datelist.add(note);
		}

		return datelist;
	}

	// ��ѯ���ݿ���Ϣ�����ķ���
	public int Date_count() {
		// ��ȡ���ݿ�Ķ�ȡȨ��
		SQLiteDatabase db = DB_SQL.getReadableDatabase();
		// ��ѯȫ����Ϣ
		Cursor cursor = db.query("mynotepad_date", null, null, null, null,
				null, null);
		// int count = cursor.getCount();
		int count = 0;
		while (cursor.moveToNext()) {
			count++;
		}

		return count;
	}

}
