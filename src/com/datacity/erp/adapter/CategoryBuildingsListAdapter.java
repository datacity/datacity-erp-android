package com.datacity.erp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryBuildingsListAdapter extends BaseAdapter {

	private ArrayList<String> buildingsName;

	private final Context context;

	public CategoryBuildingsListAdapter(Context context,
			ArrayList<String> arrayList) {
		this.context = context;
		this.buildingsName = arrayList;
	}

	@Override
	public int getCount() {
		return buildingsName.size();
	}

	@Override
	public String getItem(int position) {
		return buildingsName.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void updateBuilding(ArrayList<String> buildingList) {
		this.buildingsName = buildingList;
		notifyDataSetChanged();
	}

	public ArrayList<String> getBuildings() {
		return buildingsName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					android.R.layout.simple_list_item_1, parent, false);
		}

		TextView buildingTextView = (TextView) ViewHolder.get(convertView,
				android.R.id.text1);

		buildingTextView.setText(getItem(position));

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
