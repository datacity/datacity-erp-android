package com.datacity.erp.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.datacity.erp.R;
import com.datacity.erp.adapter.FavouritesListAdapter;
import com.datacity.erp.model.Building;
import com.datacity.erp.utils.UserPrefs;

public class FavouritesFragment extends ListFragment {

	// private ArrayList<Building> buildings;

	private OnBuildingSelectedListener mCallback;

	// private Activity mActivity;

	private FavouritesListAdapter mAdapter;

	public interface OnBuildingSelectedListener {
		public void onBuildingSelected(Building building);
	}
	
	public FavouritesFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.setRetainInstance(true);

		UserPrefs userPreferences = new UserPrefs(getActivity());

		// buildings = new
		// UserPrefs(mActivity).retrieveBuildingsFromPreferences();

		mAdapter = new FavouritesListAdapter(getActivity(),
				userPreferences.retrieveBuildingsFromPreferences(),
				userPreferences);

		/** Setting the list adapter for the ListFragment */
		setListAdapter(mAdapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.getView().setBackgroundResource(R.color.gray_background);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden && getActivity() != null) {
			getActivity().getActionBar().setTitle(R.string.favourites);
			mAdapter.updateBuilding();
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnBuildingSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnBuildingSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallback.onBuildingSelected(mAdapter.getItem(position));
	}
}
