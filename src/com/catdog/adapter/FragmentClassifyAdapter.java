package com.catdog.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;

public class FragmentClassifyAdapter extends ListAdapter<Recordset> {

	public FragmentClassifyAdapter(Context context, List<Recordset> mList) {
		super(context, mList);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;

		if (null == convertView) {
			holder = new Holder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_fragment_classify, null);
			holder.ivImage = (ImageView) convertView
					.findViewById(R.id.iv_fragment_classify_image);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_fragment_classify_name);
			holder.tvDetail = (TextView) convertView
					.findViewById(R.id.tv_fragment_classify_detail);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Recordset recordset = getList().get(position);
		int imageID;
		switch (Integer.valueOf(recordset.getClassid())) {
		case URLConstant.CLASS_FRUIT_ID:
			imageID = R.drawable.shuiguo;
			break;
		case URLConstant.CLASS_VEGETABLES_ID:
			imageID = R.drawable.shucai;
			break;
		case URLConstant.CLASS_EGG_ID:
			imageID = R.drawable.roudan;
			break;
		case URLConstant.CLASS_FROZEN_ID:
			imageID = R.drawable.dongpin;
			break;
		case URLConstant.CLASS_WATER_ID:
			imageID = R.drawable.shuichan;
			break;
		case URLConstant.CLASS_FRAIN_ID:
			imageID = R.drawable.gantiao;
			break;
		case URLConstant.CLASS_MACHINING_ID:
			imageID = R.drawable.jiagong;
			break;
		case URLConstant.CLASS_DRY_ID:
			imageID = R.drawable.ganguo;
			break;
		case URLConstant.CLASS_MARKET_ID:
			imageID = R.drawable.shangchao;
			break;
		default:
			imageID = R.drawable.shuiguo;
			break;
		}
		holder.ivImage.setImageResource(imageID);
		holder.tvName.setText(recordset.getClassname());
		String detail = "";
		for (int i = 0; i < recordset.getClasslist().size(); ++i) {
			detail += recordset.getClasslist().get(i).getClassname();
			if (2 == i) {
				break;
			} else {
				detail += "/";
			}
		}
		if (3 > recordset.getClasslist().size()) {
			detail = detail.substring(0, detail.length() - 1);
		}
		holder.tvDetail.setText(detail);
		return convertView;
	}

	public class Holder {
		ImageView ivImage;
		TextView tvName;
		TextView tvDetail;
	}

}
