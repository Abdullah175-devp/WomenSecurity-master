package com.androidigniter.loginandregistration.shakeit;

public interface ShakeListener {

	void onAccelerationChanged(float x, float y, float z);

	void onShake(float force);

}
