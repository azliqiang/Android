package com.example.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyNotepad_SQL extends SQLiteOpenHelper {
	/**
	 * 
	 * @param context
	 *            --上下文菜单
	 * @param name
	 *            --数据库名
	 * @param factory
	 *            --游标
	 * @param version
	 *            -- 数据库的版本号
	 */
	private static final String DB_NAME = "MyNotepad_SQL.db";// 数据库名称
	private static final int DB_VERSION = 1;// 数据库的版本号
	private static final String TABLE_NAME = "mynotepad_date";// 表名
	// PRIMARY 主键 AUTOINCREMENT自增
	private static final String CREATK_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + "_ID INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ "_TITLE VARCHAR(20) NOT NULL ,"
			+ "_WEATHER VARCHAR(20) NOT NULL ,"
			+ "_TIME VARCHAR(20) NOT NULL ," + "_BODY VARCHAR(200) NOT NULL"
			+ ");";
	//create table 表名（字段名  数据类型(大小) 特殊属性，字段名  数据类型(大小) 特殊属性）;

	public MyNotepad_SQL(Context context) {
		// 为父类的构造方法传递参数
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * onCreate 在数据库第一次创建的时候调用，当数据库存在时，将不会重新调用 只调用一次；
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATK_TABLE);

	}

	/**
	 * onUpgrade 跟新数据库版本的时候调用
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int F, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("");

	}

}
