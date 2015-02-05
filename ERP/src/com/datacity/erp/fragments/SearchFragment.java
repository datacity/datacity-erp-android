package com.datacity.erp.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.datacity.erp.R;
import com.datacity.erp.adapter.SearchListAdapter;
import com.datacity.erp.fragments.FavouritesFragment.OnBuildingSelectedListener;
import com.datacity.erp.model.Building;

public class SearchFragment extends Fragment {

	private ArrayList<Building> buildingList;

	private SearchListAdapter adapter;

	private EditText inputSearch;

	private OnBuildingSelectedListener mCallback;

	public SearchFragment(ArrayList<Building> buildings) {
		this.buildingList = buildings;
	}

	public SearchFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);

		this.setRetainInstance(true);

		if (buildingList != null) {
			adapter = new SearchListAdapter(inflater.getContext(), buildingList);
			initSearchView(rootView);
		}

		return rootView;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden && getActivity() != null)
			getActivity().getActionBar().setTitle(R.string.search);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
//			activity.getActionBar().setTitle(R.string.search);
			mCallback = (OnBuildingSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnBuildingSelectedListener");
		}
	}

	private void initSearchView(View rootView) {
		inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
		ListView lv = (ListView) rootView.findViewById(R.id.listView_search);

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);
				mCallback.onBuildingSelected(adapter.getItem(position));
			}
		});

		/**
		 * Enabling Search Filter
		 * */
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

	}
}
