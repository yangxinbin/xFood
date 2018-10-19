package com.oxbix.xfood.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.oxbix.xfood.R;
import com.oxbix.xfood.util.SoundManager;

/**
 * 应用加载界面
 * @author AllenZheng 
 * @date 2014年9月25日 下午5:25:24
 */
public class SplashActivity extends Activity {

	private static final long LAUNCHACTIVTY_DELAY_DISPLAY_TIME = 4000;
	private ImageView imageview;
	private AnimationDrawable animationDrawable;
	private SoundManager soundManager=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_splash);
		super.onCreate(savedInstanceState);

		
		imageview = (ImageView) findViewById(R.id.splash_imageview);
		animationDrawable = (AnimationDrawable) imageview.getBackground();
		soundManager=new SoundManager(this);
		
		animationDrawable.start();
//		soundManager.playSound();
		// 创建一个线程延迟启动MainActivity
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
//				soundManager.cleanUp();
			}
		}, LAUNCHACTIVTY_DELAY_DISPLAY_TIME);
	}
}
