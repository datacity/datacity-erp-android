package com.datacity.erp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.datacity.erp.model.Building;

public class SearchListAdapter extends BaseAdapter implements Filterable {

	private List<Building> buildings;
	private List<Building> buildingsFilterList;
	private ValueFilter valueFilter;
	private final Context context;

	public SearchListAdapter(Context context, List<Building> buildings) {
		this.context = context;
		this.buildings = buildings;
		this.buildingsFilterList = buildings;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(
					android.R.layout.simple_list_item_1, parent, false);

		TextView searchTextView = (TextView) ViewHolder.get(convertView,
				android.R.id.text1);

		Building building = getItem(position);
		searchTextView.setText(building.getNom());

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

	@Override
	public Filter getFilter() {
		if (valueFilter == null)
			valueFilter = new ValueFilter();

		return valueFilter;
	}

	private class ValueFilter extends Filter {

		// Invoked in a worker thread to filter the data according to the
		// constraint.
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<Building> filterList = new ArrayList<Building>();
				for (int i = 0; i < buildingsFilterList.size(); i++)
					if (buildingsFilterList.get(i).getNom().toLowerCase()
							.contains(constraint.toString().toLowerCase()))
						filterList.add(buildingsFilterList.get(i));
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = buildingsFilterList.size();
				results.values = buildingsFilterList;
			}
			return results;
		}

		// Invoked in the UI thread to publish the filtering results in the user
		// interface.
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			buildings = (ArrayList<Building>) results.values;
			notifyDataSetChanged();
		}
	}
}
