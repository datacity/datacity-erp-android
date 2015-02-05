package com.datacity.erp.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.datacity.erp.R;
import com.datacity.erp.adapter.CategoryBuildingsListAdapter;
import com.datacity.erp.fragments.BuildingFragment.OnBuildingOptionsSelectedListener;
import com.datacity.erp.fragments.MapFragment.OnBuildingWithIDSelectedListener;

public class CategoriesSecondFragment extends ListFragment {

	// List of Buildings
	// private ArrayList<Building> buildingList;
	private int categoryID;
	// private Activity mActivity;

	private CategoryBuildingsListAdapter mAdapter;

	private OnBuildingWithIDSelectedListener mCallback;
	private OnBuildingOptionsSelectedListener mCallbackReturn;

	public CategoriesSecondFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_categories_second,
				container, false);

//		this.setRetainInstance(true);

		// buildingList = new ArrayList<String>();

		mAdapter = new CategoryBuildingsListAdapter(getActivity(),
				new ArrayList<String>());

		setListAdapter(mAdapter);

		((Button) rootView.findViewById(R.id.categories_return_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mCallbackReturn.onReturnOptionSelected();
					}
				});

		return rootView;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden  && getActivity() != null)
			getActivity().getActionBar().setTitle(R.string.category_buildings);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.getView().setBackgroundResource(R.color.gray_background);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// mActivity = activity;
//			activity.getActionBar().setTitle(R.string.category_buildings);
			mCallback = (OnBuildingWithIDSelectedListener) activity;
			mCallbackReturn = (OnBuildingOptionsSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnBuildingWithIDSelectedListener");
		}
	}

	public void setBuildingListAndCategory(ArrayList<String> arrayList,
			int category) {
		// buildingList.clear();
		// buildingList.addAll(arrayList);
		// mAdapter.addAll(Utils.BuildingNames(buildingList));
		mAdapter.updateBuilding(arrayList);
		categoryID = category;

		// mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallback.onBuildingWithIDSelected(categoryID, position);
	}
}
