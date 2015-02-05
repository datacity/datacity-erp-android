package com.datacity.erp.utils;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

import com.datacity.erp.model.Building;
import com.google.gson.Gson;

/** stores the user object in SharedPreferences */
public class UserPrefs {

	/** This application's preferences label */
	private static final String PREFS_NAME = "com.datacity.erp.UserPrefs";

	/** This application's preferences */
	private static SharedPreferences settings;

	/** This application's settings editor */
	private static SharedPreferences.Editor editor;

	/** Google Gson instance, allow conversion from object to Json String */
	private static Gson gson;

	/** Constructor takes an android.content.Context argument */
	public UserPrefs(Context ctx) {
		if (settings == null) {
			settings = ctx.getSharedPreferences(PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		editor = settings.edit();
		gson = new Gson();
	}

	public void storeToPreferences(Building building) {
		editor.putString(building.getNom(), gson.toJson(building));
		editor.commit();
	}

	public ArrayList<Building> retrieveBuildingsFromPreferences() {
		ArrayList<Building> buildingList = null;
		Map<String, ?> keys = settings.getAll();

		if (keys != null) {
			buildingList = new ArrayList<Building>();
			for (Map.Entry<String, ?> entry : keys.entrySet())
				buildingList.add(gson.fromJson(entry.getValue().toString(),
						Building.class));
		}
		return buildingList;
	}

	public void deleteFromPreferences(String buildingName) {
		if (isStoredInPreferences(buildingName)) {
			editor.remove(buildingName);
			editor.commit();
		}
	}

	public boolean isStoredInPreferences(String buildingName) {
		return (settings.contains(buildingName));
	}

}
