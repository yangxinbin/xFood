package com.oxbix.xfood.ui.base;

import android.app.Activity;
import android.view.View.OnClickListener;

/**
 * Activity的基类
 * @author AllenZheng 
 * @date 2014年9月12日 上午10:22:12
 */
public abstract class BaseActivity extends Activity implements OnClickListener {

	/**
	 * 通过View的id获得这个View
	 */
	abstract protected void findViewById();

	/**
	 * Activity中相关资源的初始化
	 */
	abstract protected void init();

	/**
	 * 在这里给View绑定绑定监听器
	 */
	abstract protected void setListener();
}
