package com.datacity.erp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.datacity.erp.model.Building;

public class Utils {

	public static ArrayList<Building> CustomHashMapToList(
			Map<String, ArrayList<Building>> mMap) {
		ArrayList<Building> finalList = new ArrayList<Building>();

		for (ArrayList<Building> buildingList : mMap.values()) {
			finalList.addAll(buildingList);
		}
		return Utils.sortList(finalList);
	}

	public static Map<String, ArrayList<Building>> sortMap(
			Map<String, ArrayList<Building>> mMap) {
		CustomComparator buildingComparator = new CustomComparator();

		for (int i = 0; i < Constants.CATEGORIES_LIST.length; i++) {
			Collections.sort(mMap.get(Constants.CATEGORIES_LIST[i]),
					buildingComparator);
		}

		return mMap;
	}

	public static ArrayList<Building> sortList(ArrayList<Building> mBuildingList) {
		CustomComparator buildingComparator = new CustomComparator();

		Collections.sort(mBuildingList, buildingComparator);

		return mBuildingList;
	}

	public static class CustomComparator implements Comparator<Building> {
		@Override
		public int compare(Building o1, Building o2) {
			return o1.getNom().compareTo(o2.getNom());
		}

	}

	public static String[] BuildingNames(ArrayList<Building> buildings) {

		ArrayList<String> buildingsName = new ArrayList<String>();
		for (Building building : buildings)
			buildingsName.add(building.getNom());

		return buildingsName.toArray(new String[buildingsName.size()]);
	}
	
	public static ArrayList<String> BuildingNamesList(ArrayList<Building> buildings) {

		ArrayList<String> buildingsName = new ArrayList<String>();
		for (Building building : buildings)
			buildingsName.add(building.getNom());

		return buildingsName;
	}
}
