package com.oxbix.xfood.ui;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.RoomDTO;
import com.oxbix.xfood.ui.base.BaseActivity;
import com.oxbix.xfood.widget.SelectRoomDialog;
import com.oxbix.xfood.widget.SelectSkinDialog;
import com.oxbix.xfood.widget.SelectTableDialog;

/**
 * 房间桌位设置界面
 * @author AllenZheng 
 * @date 2014年9月25日 下午5:24:54
 */
public class RestaurantSettingActivity extends BaseActivity {

	private Button selectRoomBtn;
	private Button selectTableBtn;
	private Button saveBtn;
	private Button cancleBtn;
	private List<RoomDTO> rooms = null;
	
	private Button btn_select_skin;
	
	private ArrayList<String> skins;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_restaurant_setting);
		findViewById();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btn_select_room: // 点击选择房间按钮

			SelectRoomDialog selectRoomDialog = new SelectRoomDialog(RestaurantSettingActivity.this);
			rooms = SysApplication.rooms;
			if (rooms != null && rooms.size() > 0) {

				selectRoomDialog.showDialog(rooms, R.string.choose_room, selectRoomBtn);
			}
			break;

		case R.id.btn_select_table: // 点击选择桌位按钮
			

			if (SysApplication.currentRoom != null) {

				SelectTableDialog selectTableDialog = new SelectTableDialog(this);
				selectTableDialog.showDialog(SysApplication.currentRoom.getDiningTables(), R.string.choose_table, selectTableBtn);
			} else {

				Toast toast1=Toast.makeText(RestaurantSettingActivity.this, R.string.choose_room_first, Toast.LENGTH_LONG);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				toast1.show();
			}
			break;

		case R.id.btn_save:
			
			

			if (SysApplication.currentRoom == null) {

				Toast toast2=Toast.makeText(RestaurantSettingActivity.this, R.string.choose_room, Toast.LENGTH_LONG);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				toast2.show();
				return;
			}

			if (SysApplication.currentTable == null) {

				Toast toast3=Toast.makeText(RestaurantSettingActivity.this, R.string.choose_table, Toast.LENGTH_LONG);
				toast3.setGravity(Gravity.CENTER, 0, 0);
				toast3.show();
				return;
			}

			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			this.finish();
			break;

		case R.id.btn_cancel:

			this.finish();
			break;
		
		case R.id.btn_select_skin:
			SelectSkinDialog selectSkinDialog = new SelectSkinDialog(this);
			selectSkinDialog.showDialog(skins, R.string.choose_skin, btn_select_skin);
			break;
			
		default:
			break;
		}
	}

	@Override
	protected void findViewById() {

		selectRoomBtn = (Button) findViewById(R.id.btn_select_room);
		selectTableBtn = (Button) findViewById(R.id.btn_select_table);
		saveBtn = (Button) findViewById(R.id.btn_save);
		cancleBtn = (Button) findViewById(R.id.btn_cancel);
		
		btn_select_skin = (Button) findViewById(R.id.btn_select_skin);
		init();
	}

	@Override
	protected void init() {
		SysApplication.skinColor = getString(R.string.Chocolate);
		
		skins = new ArrayList<String>();
		skins.add(getString(R.string.Chocolate));
		skins.add(getString(R.string.Green));
		setListener();
	}

	@Override
	protected void setListener() {

		selectRoomBtn.setOnClickListener(this);
		selectTableBtn.setOnClickListener(this);
		saveBtn.setOnClickListener(this);
		cancleBtn.setOnClickListener(this);
		

		btn_select_skin.setOnClickListener(this);
	}

}
