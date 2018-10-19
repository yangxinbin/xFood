package com.oxbix.xfood.adapter;

import java.util.List;

import com.oxbix.xfood.R;
import com.oxbix.xfood.dto.RoomDTO;
import com.oxbix.xfood.dto.TableDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 桌位列表ListView的Adapter
 * @author AllenZheng 
 * @date 2014年9月20日 上午10:58:17
 */
public class DialogSelectTableListAdapter extends BaseAdapter {

	private Context context;
	private List<TableDTO> data;
	private LayoutInflater layoutInflater;

	public DialogSelectTableListAdapter(Context context, List<TableDTO> data) {

		this.context = context;
		this.data = data;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public TableDTO getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.dialog_select_listview_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textTv.setText(data.get(position).getName());
		return convertView;
	}

	private static class ViewHolder {

		private TextView textTv;

		public ViewHolder(View view) {

			this.textTv = (TextView) view.findViewById(R.id.tv_text);
		}
	}
}
