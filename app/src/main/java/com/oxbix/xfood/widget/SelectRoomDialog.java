package com.oxbix.xfood.widget;

import java.util.List;

import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.DialogSelectRoomListAdapter;
import com.oxbix.xfood.dto.RoomDTO;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择房间对话框（点击即表示选中）
 * @author AllenZheng
 * @date 2014年9月18日 下午3:11:29
 */
public class SelectRoomDialog {

	private Context context;
	private AlertDialog dialog;

	public SelectRoomDialog(Context context) {

		this.context = context;
		this.dialog = new AlertDialog.Builder(context).create();
	}

	/**
	 * 显示对话框并
	 * @param data  对话框中要绑定的数据
	 */
	public void showDialog(List<RoomDTO> data, int dialogTitleId, final Button button) {
		// 显示dialog
		dialog.show();
		// 加载并设置dialog中显示的布局
		View dialogView = View.inflate(context, R.layout.dialog_select_layout, null);
		dialog.setContentView(dialogView);

		// 设置dialog的显示大小
		Window dialogWindow = dialog.getWindow();
		WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		// 获取屏幕宽、高
		Display display = m.getDefaultDisplay();
		// 获取对话框当前的参数值
		WindowManager.LayoutParams params = dialogWindow.getAttributes();
		// dialog高度设置为屏幕的0.8
		params.height = (int) (display.getHeight() * 0.8);
		// 宽度设置为屏幕的0.6
		params.width = (int) (display.getWidth() * 0.4);

		dialogWindow.setAttributes(params);

		// 获取dialog中的取消按钮、对话框标题以及listview
		TextView dialogTitleTv = (TextView) dialogView.findViewById(R.id.tv_dialog_title);
		Button cancelBtn = (Button) dialogView.findViewById(R.id.btn_dialog_cancel);
		ListView dialogDataListview = (ListView) dialogView.findViewById(R.id.lv_data);

		// 设置对话框的标题
		dialogTitleTv.setText(dialogTitleId);
		// 给取消按钮设置点击监听器
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 关闭对话框
				dialog.dismiss();
			}
		});

		// 初始化listview中的数据
		final DialogSelectRoomListAdapter adapter = new DialogSelectRoomListAdapter(context, data);
		dialogDataListview.setAdapter(adapter);

		dialogDataListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				SysApplication.currentRoom = adapter.getItem(position);
				button.setText(adapter.getItem(position).getName());
				// 关闭对话框
				dialog.dismiss();
			}
		});
	}
}
