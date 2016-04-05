package com.catdog.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catdog.activity.login.LoginActivity;
import com.catdog.base.BaseActivity;
import com.catdog.helper.LoadImageHelper;
import com.catdog.util.Bimp;
import com.catdog.util.FileUtils;
import com.catdog.util.HttpUtils;
import com.catdog.util.LogUtil;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.URLConstant;
import com.catdog.view.PopupwindowView;
import com.catdog.view.RoundImageView;
import com.catdog.wsgs.R;

public class InfoActivity extends BaseActivity implements OnClickListener{

	private PopupwindowView mPopupwindowView;
	private LinearLayout ll_head,ll_eamil,ll_phone,ll_pwd,ll_name;
	private RoundImageView img_head;
	private ImageView img_back;
	private TextView info_textview_exit;
	private float dp;
	private LoadImageHelper imageHelper;
	private SharePreferencesManager preferencesManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		imageHelper = new LoadImageHelper(mContext);
		if(!isEmpty(getIntent().getStringExtra("photo")))
		{
			imageHelper.loadImage(getIntent().getStringExtra("photo"), img_head);
		}
		initView();
		initListener();
		requestInfo();
	}
	@Override
	protected void onResume() {
		super.onResume();

	}
	private void requestInfo() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_id", getUserId());
		HttpUtils.getDataFromVolley(InfoActivity.this, URLConstant.USER_HEAD, null, map, listeners);
	}
	String me_pic;
	private ResponseListener listeners = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			me_pic = object2.getString("me_pic");
			imageHelper.loadImage(me_pic, img_head);
		}
	};
	private void initListener() {
		// TODO 自动生成的方法存根
		ll_head.setOnClickListener(this);
		ll_name.setOnClickListener(this);
		ll_pwd.setOnClickListener(this);
		img_back.setOnClickListener(this);
		ll_phone.setOnClickListener(this);
		info_textview_exit.setOnClickListener(this);
	}

	private void initView() {
		// TODO 自动生成的方法存根
		img_back = (ImageView) findViewById(R.id.info_img_left);
		ll_head = (LinearLayout) findViewById(R.id.info_ll_head);
		img_head = (RoundImageView) findViewById(R.id.info_rimg_head);
		ll_pwd = (LinearLayout) findViewById(R.id.info_ll_pwd);
		ll_name = (LinearLayout) findViewById(R.id.info_ll_name);
		ll_eamil = (LinearLayout) findViewById(R.id.info_ll_email);
		ll_phone = (LinearLayout) findViewById(R.id.info_ll_phone);
		info_textview_exit = (TextView) findViewById(R.id.info_textview_exit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch (v.getId()) {
		case R.id.info_ll_head:
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				mPopupwindowView = new PopupwindowView(getApplicationContext(), img_head, this);
			} else {
				showToast("sdcard已拔出，不能选择照片");
			}
			break;


		case R.id.info_img_left:
			finish();
			break;
		case R.id.info_ll_name:
			intent = new Intent(mContext,ChangeNameActivity.class);
			startActivity(intent);
			break;
		case R.id.item_popupwindows_camera:

			photo();
			//			startActivityForResult(PhotoUtil.photo(), Constant.TAKE_PICTURE);
			mPopupwindowView.dismiss();
			break;

		case R.id.item_popupwindows_Photo:

			Intent i = new Intent(
					// 相册
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, Constant.RESULT_LOAD_IMAGE);
			mPopupwindowView.dismiss();
			break;
		case R.id.info_ll_pwd:
			intent = new Intent(mContext,ChangePwdActivity.class);
			startActivity(intent);
			break;

		case R.id.info_ll_email:
			intent = new Intent(mContext,ChangeEmailActivity.class);
			startActivity(intent);
			break;

		case R.id.info_ll_phone:
			intent = new Intent(mContext,ChangePhoneActivity.class);
			startActivity(intent);
			break;
		case R.id.info_textview_exit:
			if(mPopupwindowView != null)
				mPopupwindowView.dismiss();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					mContext);
			builder.setTitle("提示");
			builder.setMessage("确定要退出么");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog,int which) {
					preferencesManager = new SharePreferencesManager(mContext, Constant.REMEMBER_PREFERENCE);
					preferencesManager.clearAll();
					Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					InfoActivity.this.finish();
				}
			});
			builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
			break;
		}
	}

	private String path = "", drr = "", address;
	private Uri photoUri;
	private Bitmap mBitmap;

	public void photo() {
		try {
			Intent openCameraIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			String sdcardState = Environment.getExternalStorageState();
			String sdcardPathDir = android.os.Environment
					.getExternalStorageDirectory().getPath() + "/tempImage/";
			File file = null;
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				// 有sd卡，是否有myImage文件夹
				File fileDir = new File(sdcardPathDir);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				// 是否有headImg文件
				file = new File(sdcardPathDir + System.currentTimeMillis()
						+ ".JPEG");

			}
			if (file != null) {
				path = file.getPath();
				photoUri = Uri.fromFile(file);
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void startPhotoZoom(Uri uri) {
		try {
			// 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			address = sDateFormat.format(new java.util.Date());
			if (!FileUtils.isFileExist("")) {
				FileUtils.createSDDir("");
			}
			drr = FileUtils.SDPATH + address + ".JPEG";
			LogUtil.e("asd", "照片的路径" + drr);
			Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
					+ ".JPEG");

			final Intent intent = new Intent("com.android.camera.action.CROP");

			// 照片URL地址
			intent.setDataAndType(uri, "image/*");

			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 480);
			intent.putExtra("outputY", 480);
			// 输出路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			// 输出格式
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			// 不启用人脸识别
			intent.putExtra("noFaceDetection", false);
			intent.putExtra("return-data", false);
			startActivityForResult(intent, Constant.CUT_PHOTO_REQUEST_CODE);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Constant.TAKE_PICTURE:
			if (resultCode == Activity.RESULT_OK) {// 拍照
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
					MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");
					startPhotoZoom(photoUri);
				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
			break;
		case Constant.RESULT_LOAD_IMAGE:
			if (resultCode == Activity.RESULT_OK && null != data) {// 相册返回
				Uri uri = data.getData();
				if (uri != null) {
					startPhotoZoom(uri);
				}
			}
			break;
		case Constant.CUT_PHOTO_REQUEST_CODE:
			if (resultCode == Activity.RESULT_OK && null != data) {// 裁剪返回
				mBitmap = Bimp.getLoacalBitmap(drr);
				mBitmap = Bimp.createFramedPhoto(480, 480, mBitmap,
						(int) (dp * 1.6f));
				img_head.setImageBitmap(mBitmap);

				/**
				 * 上传图片至服务器
				 */
				upLoadFile();

			}

		}


	}

	private MyProgressDialog pDialog;
	private void upLoadFile() {
		// TODO Auto-generated method stub
		pDialog = new MyProgressDialog(mContext);
		pDialog.setMessage("头像正在上传");
		pDialog.show();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] buffer = baos.toByteArray();
		String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_id", getUserId());
		map.put("pic", photo);
		HttpUtils.getDataFromVolley(mContext, URLConstant.USER_CHANGE_HEAD, null, map, listener);
	}

	private ResponseListener listener = new ResponseListener(){

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			if(null != pDialog && pDialog.isShowing())
			{
				pDialog.dismiss();
			}
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			String str_pic = object2.getString("me_pic");
			if(!isEmpty(str_pic))
			{
				showToast("修改成功！");
				Intent intent = new Intent();
				intent.putExtra("photo", str_pic);
				setResult(10000, intent);
			}
		}

	};

	protected void onDestroy() {
		super.onDestroy();
		if(mPopupwindowView != null)
			mPopupwindowView.dismiss();
	};

}
