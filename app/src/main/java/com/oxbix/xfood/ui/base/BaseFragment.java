package com.oxbix.xfood.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Fragement基类
 * @author AllenZheng 
 * @date 2014年9月12日 上午10:22:34
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
	
	/**
	 * 通过View的id获得这个View
	 * @param view
	 */
	protected abstract void findViewById(View view);
	
	/**
	 * Activity中相关资源的初始化
	 */
	protected abstract void init();
	
	/**
	 * 在这里给View绑定绑定监听器
	 */
	protected abstract void setListener();
}
