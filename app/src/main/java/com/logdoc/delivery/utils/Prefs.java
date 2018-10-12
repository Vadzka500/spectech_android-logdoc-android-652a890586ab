package com.logdoc.delivery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.ServiceState;

/**
 * Created by  on .
 */

public class Prefs {
	private static final String SHARED_PREFS_NAME = "LOGDOC_DELIVERY_PREFS";

	private static final String KEY_SERVICE_STATE = "KEY_SERVICE_STATE";

	private SharedPreferences sharedPreferences;

	public Prefs(Context context) {
		this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public void setServiceState(int serviceState) {
		sharedPreferences.edit().putInt(KEY_SERVICE_STATE, serviceState).apply();
	}

	public int getServiceState() {
		return sharedPreferences.getInt(KEY_SERVICE_STATE, ServiceState.STATE_IN_SERVICE);
	}


}
