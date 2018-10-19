package com.oxbix.xfood.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.oxbix.xfood.R;
import com.oxbix.xfood.dto.RoomDTO;

/**
 * 房间列表ListView的Adapter
 * @author AllenZheng 
 * @date 2014年9月20日 上午10:38:38
 */
public class DialogSelectRoomListAdapter extends BaseAdapter {

	private Context context;
	private List<RoomDTO> data;
	private LayoutInflater layoutInflater;

	public DialogSelectRoomListAdapter(Context context, List<RoomDTO> data) {

		this.context = context;
		this.data = data;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public RoomDTO getItem(int position) {

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
