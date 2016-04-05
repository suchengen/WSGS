package com.catdog.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBHelper";
	private static final String DB_NAME = "wsw.db";
	private final static int VERSION = 1;
	public SQLiteDatabase dbConn;
	//表名
	public static final String TB_USER_INFO = "tb_userInfo";
	public static final String TB_RELATIONSHIP = "tb_relationship";
	public static final String TB_USER_SAFETY = "tb_userSafety";
	public static final String TB_TASK_TYPE_LIST = "tb_taskTypeList";//任务类型 列表
	public static final String TB_PUBLISH_TIME_LIST = "tb_publishTimeList";//发布时间列表
	public static final String TB_EXECUTE_TIME_LIST = "tb_executeTimeList";//执行时间限制表

	//	private Object[] colRelationShip = new Object[]{TB_RELATIONSHIP, COLUMN_ID, };

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		dbConn = this.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//		db.execSQL(String.format("create table if not exists %s(%s primary key, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", colRelationShip));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			db.execSQL("drop table if exists　" + TB_USER_INFO);
			onCreate(db);
		}
	}

	//增加列，并赋值为0
	public void addColumnIntoPlaylist(String columnName){
		dbConn.execSQL(String.format("alter table %s add %s",TB_USER_INFO, columnName));
		ContentValues values=new ContentValues();
		values.put(columnName, 0);
		update(TB_USER_INFO, values, null, null);
	}

	//插入
	public void insert(String tableName, ContentValues values) {
		dbConn.insert(tableName, null, values);
	}

	//更新
	public void update(String tableName, ContentValues values, String whereClause, String[] whereArgs){
		dbConn.update(tableName, values, whereClause, whereArgs);
	}

	//删除
	public void delete(String tableName, String whereClause, String[] whereArgs){
		dbConn.delete(tableName, whereClause, whereArgs);
	}

	/**
	 * @作用：执行带占位符的select语句，查询数据，返回Cursor
	 * @param sql
	 * @param selectionArgs
	 * @return Cursor
	 */
	public Cursor selectCursor(String sql, String[] selectionArgs) {
		return dbConn.rawQuery(sql, selectionArgs);
	}

	/**
	 * @作用：执行带占位符的select语句，返回结果集的个数
	 * @param sql 最后不能加 分号
	 * @param selectionArgs
	 * @return int
	 */
	public int selectCount(String sql, String[] selectionArgs) {
		Cursor cursor = dbConn.rawQuery(sql, selectionArgs);
		if (cursor != null) {
			cursor.moveToFirst();
			int count = cursor.getInt(0);
			cursor.close();
			return count;
		} 
		return 0;
	}

	/**
	 * @作用：执行带占位符的select语句，返回多条数据，放进List集合中。
	 * @param sql
	 * @param selectionArgs
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> selectList(String sql, String[] selectionArgs) {
		try {
			Cursor cursor = dbConn.rawQuery(sql, selectionArgs);
			return cursorToList(cursor);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	/**
	 * @作用：将Cursor对象转成List集合
	 * @param Cursor cursor
	 * @return List<Map<String, Object>>集合
	 */
	@SuppressLint("NewApi") public static List<Map<String, Object>> cursorToList(Cursor cursor) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String[] arrColumnName = cursor.getColumnNames();
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < arrColumnName.length; i++) {
				Object cols_value = null;
				switch (cursor.getType(i)) {
				case 1:
					cols_value = cursor.getInt(i);
					break;
				case 2:
					cols_value = cursor.getFloat(i);
					break;
				case 3:
					cols_value = cursor.getString(i);
					break;
				case 4:
					cols_value = cursor.getBlob(i);
					break;
				default:
					break;
				}
				map.put(arrColumnName[i], cols_value);
			}
			list.add(map);
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * @作用：执行带占位符的update、insert、delete语句，更新数据库，返回true或false
	 * @param sql
	 * @param bindArgs
	 * @return boolean
	 */
	public boolean execData(String sql, Object[] bindArgs) {
		try {
			dbConn.execSQL(sql, bindArgs);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clearTables(){
		//		delete(TB_RELATIONSHIP, null, null);
		delete(TB_USER_INFO, null, null);
		delete(TB_USER_SAFETY, null, null);
	}

	/**
	 * 作用：创建一个表
	 * @param tbName
	 * @param columnNames
	 */
	public void creatTable(String tbName, String[] columnNames){
		StringBuffer sb = new StringBuffer("create table if not exists " + tbName + "(");
		for (int i = 0; i < columnNames.length; i++) {
			sb.append(columnNames[i] + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		Log.d(TAG, "---" + sb);
		dbConn.execSQL(sb.toString());
	}

	/**
	 * 作用：存储一些数据到数据库指定表
	 * @param tbName
	 * @param data
	 * @param columnNames
	 */
	public void saveDatas(String tbName, List<Map<String, Object>> datas) {
		if(datas == null || datas.isEmpty())return;//无数据
		Map<String, Object> data = datas.get(0);
		Set<String> set = data.keySet();
		String[] columnNames = new String[set.size()];
		int index = 0;
		for (String string : set) {
			columnNames[index++] = string;
		}
		creatTable(tbName, columnNames);
		for (int i = 0; i < datas.size(); i++) {
			ContentValues values = new ContentValues();
			for (int j = 0; j < columnNames.length; j++) {
				values.put(columnNames[j], datas.get(i).get(columnNames[j]).toString());
			}
			insert(tbName, values);
		}
	}

	/**
	 * 作用：载入指定表的数据
	 * @param tbName
	 * @return
	 */
	public List<Map<String, Object>> loadDatas(String tbName){
		String sql = "select * from " + tbName;
		List<Map<String, Object>> datas = selectList(sql, null);
		return datas;
	}

	public void destroy() {
		if (dbConn != null) {
			dbConn.close();
		}
	}
}
