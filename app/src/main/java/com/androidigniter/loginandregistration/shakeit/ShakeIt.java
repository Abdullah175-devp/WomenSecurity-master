package com.androidigniter.loginandregistration.shakeit;

import android.content.Context;
import android.content.Intent;

public class ShakeIt {

	static ShakeListener shakeListener;
	static int threshold = 25;
	static int interval = 600;

	public static void initializeShakeService(Context context,
			ShakeListener shakeListener) {
		initializeShakeService(context, threshold, interval, shakeListener);
	}

	public static void initializeShakeService(Context context, int threshold,
			int interval, ShakeListener shakeListener) {
		ShakeIt.threshold = threshold;
		ShakeIt.interval = interval;
		ShakeIt.shakeListener = shakeListener;
		Intent intent = new Intent(context, ShakeService.class);
		context.startService(intent);
	}

	public static void stopShakeService(Context context) {
		ShakeIt.shakeListener = null;
		Intent intent = new Intent(context, ShakeService.class);
		context.stopService(intent);
	}

}
