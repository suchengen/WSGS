package com.catdog.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.catdog.base.BaseActivity;
import com.catdog.util.HttpUtils;
import com.catdog.util.LocationUtils;
import com.catdog.util.LocationUtils.AddrToLatLngListener;
import com.catdog.util.ResponseListener;
import com.catdog.util.StringUtils;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;

public class AddAddressActivity extends BaseActivity implements OnClickListener{
	private EditText et_name,et_phone,et_add;
	private Spinner sheng_sp,shi_sp,qu_sp;
	private TextView tv_save;
	private TextView tv_title,tv_right;
	private ImageView img_left;
	private int tag;
	private String add_id;
	private LocationUtils locationUtil;
	private String[] 省数组,市数组,区数组;
	private String 省;
	private String 市;
	private String 区;
	private int 省下标;
	private int 市下标;
	private int 区下标;
	private String txt文件;
	private JSONObject 市的json字符串;
	private JSONObject 区的json字符串;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		initView();
		initListener();

		locationUtil = new LocationUtils();

		Intent intent = getIntent();
		tag = intent.getIntExtra("tag", 0);
		if (tag == 1) {
			tv_title.setText("修改地址");
			add_id = intent.getStringExtra("add_id");
			String add_name = intent.getStringExtra("add_name");
			String add_address = intent.getStringExtra("add_address");
			String add_tel = intent.getStringExtra("add_tel");
			et_name.setText(add_name);
			et_phone.setText(add_tel);
			et_add.setText(add_address);
		}else{
			tv_title.setText("添加地址");
		}
		initdata();
		initclick();
	}

	private void initclick() {
		sheng_sp.setOnItemSelectedListener(new OnItemSelectedListener() {//省


			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				省 = 省数组[position];
				省下标 = position;

				try {
					JSONArray shiarray = 市的json字符串.getJSONArray(省数组[省下标]);
					市数组 = new String[shiarray.length()];
					for (int i = 0; i < shiarray.length(); i++) {
						市数组[i] = (String) shiarray.get(i);
					}
					ArrayAdapter<String> _Adaptershi = new ArrayAdapter<String>(AddAddressActivity.this,android.R.layout.simple_list_item_1, 市数组){
						@Override
						public View getView(int position, View convertView,ViewGroup parent) {
							TextView textView = (TextView) super.getView(position, convertView, parent); 
							ColorStateList textColor = getResources().getColorStateList(R.color.black);
							textView.setTextColor(textColor);
							textView.setSingleLine(true);
							textView.setEllipsize(TruncateAt.END);
							textView.setTextSize(14);
							return textView; 
						}
					};
					//绑定 Adapter到控件
					shi_sp.setAdapter(_Adaptershi);

					JSONArray quarray = 区的json字符串.getJSONArray(省数组[省下标] + "-" + 市数组[0]);
					区数组 = new String[quarray.length()];
					for (int i = 0; i < quarray.length(); i++) {
						区数组[i] = (String) quarray.get(i);
					}
					ArrayAdapter<String> _Adapterqu = new ArrayAdapter<String>(AddAddressActivity.this,android.R.layout.simple_list_item_1, 区数组){
						@Override
						public View getView(int position, View convertView,ViewGroup parent) {
							TextView textView = (TextView) super.getView(position, convertView, parent); 
							ColorStateList textColor = getResources().getColorStateList(R.color.black);
							textView.setTextColor(textColor);
							textView.setSingleLine(true);
							textView.setEllipsize(TruncateAt.END);
							textView.setTextSize(14);
							return textView; 
						}
					};
					qu_sp.setAdapter(_Adapterqu);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		shi_sp.setOnItemSelectedListener(new OnItemSelectedListener() {//选择服务的选项


			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				市 = 市数组[position];
				市下标 = position;

				try {
					JSONArray quarray = 区的json字符串.getJSONArray(省数组[省下标] + "-" + 市数组[市下标]);
					区数组 = new String[quarray.length()];
					for (int i = 0; i < quarray.length(); i++) {
						区数组[i] = (String) quarray.get(i);
					}
					ArrayAdapter<String> _Adapterqu = new ArrayAdapter<String>(AddAddressActivity.this,android.R.layout.simple_list_item_1, 区数组){
						@Override
						public View getView(int position, View convertView,ViewGroup parent) {
							TextView textView = (TextView) super.getView(position, convertView, parent); 
							ColorStateList textColor = getResources().getColorStateList(R.color.black);
							textView.setTextColor(textColor);
							textView.setSingleLine(true);
							textView.setEllipsize(TruncateAt.END);
							textView.setTextSize(14);
							return textView; 
						}
					};
					qu_sp.setAdapter(_Adapterqu);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		qu_sp.setOnItemSelectedListener(new OnItemSelectedListener() {//选择服务的选项


			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				区 = 区数组[position];
				区下标 = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void initdata() {
		InputStream inputStream = getResources().openRawResource(R.raw.province_city_district); 
		InputStreamReader inputStreamReader = null;  
		try {  
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");  
		} catch (UnsupportedEncodingException e1) {  
			e1.printStackTrace();  
		}  
		BufferedReader reader = new BufferedReader(inputStreamReader);  
		StringBuffer sb = new StringBuffer("");  
		String line;  
		try {  
			while ((line = reader.readLine()) != null) {  
				sb.append(line);  
				sb.append("\n");  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  

		txt文件 = sb.toString();

		try {
			JSONObject jsonObject = new JSONObject(txt文件);
			JSONArray shengarray = jsonObject.getJSONArray("p");
			省数组 = new String[shengarray.length()];
			for (int i = 0; i < shengarray.length(); i++) {
				省数组[i] = (String) shengarray.get(i);
			}
			ArrayAdapter<String> _Adaptersheng = new ArrayAdapter<String>(AddAddressActivity.this,android.R.layout.simple_list_item_1, 省数组){
				@Override
				public View getView(int position, View convertView,ViewGroup parent) {
					TextView textView = (TextView) super.getView(position, convertView, parent); 
					ColorStateList textColor = getResources().getColorStateList(R.color.black);
					textView.setTextColor(textColor);
					textView.setSingleLine(true);
					textView.setEllipsize(TruncateAt.END);
					textView.setTextSize(14);
					return textView; 
				}
			};
			//绑定 Adapter到控件
			sheng_sp.setAdapter(_Adaptersheng);

			String shijson = jsonObject.getString("c");
			市的json字符串 = new JSONObject(shijson);
			JSONArray shiarray = 市的json字符串.getJSONArray(省数组[0]);
			市数组 = new String[shiarray.length()];
			for (int i = 0; i < shiarray.length(); i++) {
				市数组[i] = (String) shiarray.get(i);
			}
			ArrayAdapter<String> _Adaptershi = new ArrayAdapter<String>(AddAddressActivity.this,android.R.layout.simple_list_item_1, 市数组){
				@Override
				public View getView(int position, View convertView,ViewGroup parent) {
					TextView textView = (TextView) super.getView(position, convertView, parent); 
					ColorStateList textColor = getResources().getColorStateList(R.color.black);
					textView.setTextColor(textColor);
					textView.setSingleLine(true);
					textView.setEllipsize(TruncateAt.END);
					textView.setTextSize(14);
					return textView; 
				}
			};
			//绑定 Adapter到控件
			shi_sp.setAdapter(_Adaptershi);

			String qujson = jsonObject.getString("a");
			区的json字符串 = new JSONObject(qujson);
			JSONArray quarray = 区的json字符串.getJSONArray(省数组[0] + "-" + 市数组[0]);
			区数组 = new String[quarray.length()];
			for (int i = 0; i < quarray.length(); i++) {
				区数组[i] = (String) quarray.get(i);
			}
			ArrayAdapter<String> _Adapterqu = new ArrayAdapter<String>(AddAddressActivity.this,android.R.layout.simple_list_item_1, 区数组){
				@Override
				public View getView(int position, View convertView,ViewGroup parent) {
					TextView textView = (TextView) super.getView(position, convertView, parent); 
					ColorStateList textColor = getResources().getColorStateList(R.color.black);
					textView.setTextColor(textColor);
					textView.setSingleLine(true);
					textView.setEllipsize(TruncateAt.END);
					textView.setTextSize(14);
					return textView; 
				}
			};
			//绑定 Adapter到控件
			qu_sp.setAdapter(_Adapterqu);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initListener() {
		// TODO 自动生成的方法存根
		img_left.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.add_title_text);
		img_left = (ImageView) findViewById(R.id.add_title_img_left);
		et_name = (EditText) findViewById(R.id.dress_et_name);
		et_phone = (EditText) findViewById(R.id.dress_et_phone);
		et_add = (EditText) findViewById(R.id.dress_et_add);
		sheng_sp = (Spinner) findViewById(R.id.sheng);
		shi_sp = (Spinner) findViewById(R.id.shi);
		qu_sp = (Spinner) findViewById(R.id.qu);
		tv_save = (TextView) findViewById(R.id.dress_tv_save);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_address, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.add_title_img_left:
			finish();
			break;
		case R.id.dress_tv_save:
			final String str_name,str_phone,str_add;
			str_name = et_name.getText().toString().trim();
			str_phone = et_phone.getText().toString().trim();
			str_add = et_add.getText().toString().trim();
			if(!isEmpty(str_name) && !isEmpty(str_phone) && !isEmpty(str_add) && !isEmpty(省) && !isEmpty(市) && !isEmpty(区))
			{
				if(StringUtils.isMobileNO(str_phone))
				{
					locationUtil.getLatLng(省 + "省" + 市 + "市" + 区 + "区", str_add, new AddrToLatLngListener() {

						@Override
						public void addrToLatLng(double latitude, double longitude) {
							if (latitude != 0.0D && longitude != 0.0D) {
								if (tag == 1) {
									seekAddressRequest(str_name,str_phone,str_add,省,市,区,longitude+"",latitude+"");
								}else{
									saveAddressRequest(str_name,str_phone,str_add,省,市,区,longitude+"",latitude+"");
								}
							}
						}
					});

				}else
				{
					showToast("输入的电话号码有误！");
				}
			}else
			{
				showToast("输入的信息不能为空，请认真填写！");
			}

			break;
		}
	}

	private void saveAddressRequest(String str_name, String str_phone,
			String str_add,String shengs,String shis,String qus,String lng,String lat) {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dz_meid", getUserId());
		map.put("dz_uname", str_name);
		map.put("dz_tel", str_phone);
		map.put("dz_sheng", shengs);
		map.put("dz_shi", shis);
		map.put("dz_qu", qus);
		map.put("dz_lng", lng);
		map.put("dz_lat", lat);
		map.put("dz_address", str_add);
		map.put("dz_default", "1");
		HttpUtils.getDataFromVolley(mContext, URLConstant.ADD_ADDRESS, null, map, listener);
	}

	private ResponseListener listener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
			try {
				String str = (String) object;
				JSONObject object2 = new JSONObject(str);
				String msg = object2.getString("msg");
				if(!isEmpty(msg))
				{
					if(msg.equals("success"))
					{
						showToast("添加成功!");
						Intent intent = new Intent();
						setResult(100000, intent);

						finish();
					}else
					{
						showToast(msg);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	private void seekAddressRequest(String str_name, String str_phone,
			String str_add,String shengs,String shis,String qus,String lng,String lat) {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dz_meid", getUserId());
		map.put("dz_id", add_id);
		map.put("dz_uname", str_name);
		map.put("dz_tel", str_phone);
		map.put("dz_sheng", shengs);
		map.put("dz_shi", shis);
		map.put("dz_qu", qus);
		map.put("dz_lng", lng);
		map.put("dz_lat", lat);
		map.put("dz_address", str_add);
		map.put("dz_default", "1");
		HttpUtils.getDataFromVolley(mContext, URLConstant.ADD_ADDRESS, null, map, seeklistener);
	}

	private ResponseListener seeklistener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
			try {
				String str = (String) object;
				JSONObject object2 = new JSONObject(str);
				String msg = object2.getString("msg");
				if(!isEmpty(msg))
				{
					if(msg.equals("success"))
					{
						showToast("添加成功!");
						finish();
					}else
					{
						showToast(msg);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
}
