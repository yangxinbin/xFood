package com.oxbix.xfood.widget;

import com.oxbix.xfood.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义的加载对话框
 * @author AllenZheng 
 * @date 2014年9月18日 下午3:09:16
 */
public class LoadingDialog {

	private Context context;
	private Dialog dialog=null;
	
	public LoadingDialog(Context context){
		
		this.context=context;
		this.dialog= new AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
	}
	
	/**
	 * 显示对话框
	 * @param messageId 对话框中的提示信息
	 */
	public void showDialog(int messageId){
		
		if(dialog!=null){
			
			// 弹出对话框
			dialog.show();
			
			//加载对话框布局文件
			View view=LayoutInflater.from(context).inflate(R.layout.dialog_loading_layout, null);
			TextView progressbarMessageTv=(TextView) view.findViewById(R.id.tv_progressbar_message);
			progressbarMessageTv.setText(messageId);
			dialog.setContentView(view);
		}
	}
	
	/**
	 * 关闭对话框
	 */
	public void dismissDialog(){
		
		if(dialog!=null){
			
			dialog.dismiss();
			dialog=null;
		}
	}
}
