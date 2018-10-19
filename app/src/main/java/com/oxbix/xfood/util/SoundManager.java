package com.oxbix.xfood.util;

import com.oxbix.xfood.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool soundPool;
	private int sm;
	Context context;

	public SoundManager(Context context) {
		this.context = context;
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		sm = soundPool.load(context, R.raw.splash_audio, 1);
	}

	public final void playSound() {
		AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		soundPool.play(sm, volume, volume, 1, 0, 1f);
	}

	public final void cleanUp() {
		context = null;
		soundPool.release();
		soundPool = null;
	}
}
