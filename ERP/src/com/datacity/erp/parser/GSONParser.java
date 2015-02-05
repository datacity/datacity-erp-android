package com.datacity.erp.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.datacity.erp.model.Building;
import com.datacity.erp.utils.Constants;
import com.datacity.erp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class GSONParser {

	// public GSONParser() {
	// AssetManager assetManager = activity.getAssets();
	// InputStream inputStream;
	// inputStream = assetManager.open(fileName);
	// }

	public static Map<String, ArrayList<Building>> readJsonStream(InputStream in)
			throws IOException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		Map<String, ArrayList<Building>> buildings = new HashMap<String, ArrayList<Building>>();
		ArrayList<Building> buildingsFromCategory;
		String category;
		reader.beginArray();
		while (reader.hasNext()) {
			Building building = gson.fromJson(reader, Building.class);
			category = Constants.mCategoriesMap.get(building.getCategorie());
			buildingsFromCategory = buildings.get(category);
			if (buildingsFromCategory == null) {
				buildingsFromCategory = new ArrayList<Building>();
				buildings.put(category, buildingsFromCategory);
			}
			buildingsFromCategory.add(building);
		}
		reader.endArray();
		reader.close();
		return Utils.sortMap(buildings);
	}

}
