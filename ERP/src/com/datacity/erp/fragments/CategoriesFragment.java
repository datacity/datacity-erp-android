package com.datacity.erp.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.datacity.erp.R;
import com.datacity.erp.utils.Constants;

public class CategoriesFragment extends ListFragment {

	private OnCategorySelectedListener mCallback;

	public interface OnCategorySelectedListener {
		public void onCategorySelected(int categoryID);
	}
	
	public CategoriesFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		this.setRetainInstance(true);

		/** Setting the list adapter for the ListFragment */
		setListAdapter(new ArrayAdapter<String>(inflater.getContext(),
				android.R.layout.simple_list_item_1, Constants.CATEGORIES_LIST));

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
		if (!hidden && getActivity() != null)
			getActivity().getActionBar().setTitle(R.string.categories);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
//			activity.getActionBar().setTitle(R.string.categories);
			mCallback = (OnCategorySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnCategorySelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallback.onCategorySelected(position);
	}
}
