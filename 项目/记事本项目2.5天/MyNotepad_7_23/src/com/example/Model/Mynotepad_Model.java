package com.example.Model;

public class Mynotepad_Model {
	// 信息的model类，信息模板

	private int _id;
	private String _title;
	private String _weather;
	private String _time;
	private String _body;

	// 构造方法
	public Mynotepad_Model(int _id, String _title, String _weather,
			String _time, String _body) {
		this._id = _id;
		this._title = _title;
		this._weather = _weather;
		this._time = _time;
		this._body = _body;

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_weather() {
		return _weather;
	}

	public void set_weather(String _weather) {
		this._weather = _weather;
	}

	public String get_time() {
		return _time;
	}

	public void set_time(String _time) {
		this._time = _time;
	}

	public String get_body() {
		return _body;
	}

	public void set_body(String _body) {
		this._body = _body;
	}

	// 无参数的构造方法
	// public Mynotepad_Model(){
	//
	// }

}
