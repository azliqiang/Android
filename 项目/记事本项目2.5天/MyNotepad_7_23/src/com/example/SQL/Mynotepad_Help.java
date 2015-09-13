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
	 * 数据库的帮助类，实现对数据库中信息的增删改查工作， context --上下文菜单
	 */
	public Context context;// 全局变量
	private MyNotepad_SQL DB_SQL;
	private ArrayList<Mynotepad_Model> datelist;

	public Mynotepad_Help(Context context) {
		this.context = context;
		// 创建数据库 存储到内存里
		DB_SQL = new MyNotepad_SQL(context);

	}

	// 添加数据的方法
	public void Date_add(ContentValues values) {
		// 获取对数据库的写入权限； 得到一个SQLiteDatabase
		SQLiteDatabase db = DB_SQL.getWritableDatabase();
		// 插入一条数据
		/**
		 * table 对那张表进行插入
		 */
		db.insert("mynotepad_date", null, values);

	}

	// 删除数据的方法
	public void Date_delete(String time) {
		// 获取对数据库的写入权限； 得到一个SQLiteDatabase
		SQLiteDatabase db = DB_SQL.getWritableDatabase();
		db.delete("mynotepad_date", "_TIME=?", new String[] { time });
	}

	// 修改数据的方法
	public void Date_update() {

	}

	// 查询数据的方法

	public List<Mynotepad_Model> Date_look() {
		datelist = new ArrayList<Mynotepad_Model>();
		// 获取对数据库的读写权限
		SQLiteDatabase db = DB_SQL.getReadableDatabase();
		// 查询全部信息
		Cursor cursor = db.query("mynotepad_date", null, null, null, null,
				null, null);

		while (cursor.moveToNext()) {// 查看是否有下一条 true false;
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

	// 查询数据库信息条数的方法
	public int Date_count() {
		// 获取数据库的读取权限
		SQLiteDatabase db = DB_SQL.getReadableDatabase();
		// 查询全部信息
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
