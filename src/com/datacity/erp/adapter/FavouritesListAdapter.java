package com.datacity.erp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.datacity.erp.R;
import com.datacity.erp.model.Building;
import com.datacity.erp.utils.UserPrefs;

public class FavouritesListAdapter extends BaseAdapter {

	private ArrayList<Building> buildings;

	private final Context context;

	private UserPrefs userPreferences;

	public FavouritesListAdapter(Context context,
			ArrayList<Building> buildings, UserPrefs userPrefs) {
		this.context = context;
		this.buildings = buildings;
		userPreferences = userPrefs;
	}

	@Override
	public int getCount() {
		return buildings.size();
	}

	@Override
	public Building getItem(int position) {
		return buildings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void updateBuilding() {
		this.buildings = userPreferences.retrieveBuildingsFromPreferences();
		notifyDataSetChanged();
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.favourite_list_item, parent, false);
		}

		TextView favouriteTextView = (TextView) ViewHolder.get(convertView,
				R.id.favourite_name);
		Button favouriteDeleteButtonView = (Button) ViewHolder.get(convertView,
				R.id.favourite_delete_button);

		Building building = getItem(position);
		favouriteTextView.setText(building.getNom());

		favouriteDeleteButtonView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userPreferences.deleteFromPreferences(((TextView) ViewHolder
						.get((View) v.getParent(), R.id.favourite_name))
						.getText().toString());
				updateBuilding();
			}
		});

		return convertView;
	}

	public static class ViewHolder {
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (viewHolder == null) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
	}
}
