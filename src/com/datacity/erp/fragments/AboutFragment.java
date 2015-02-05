package com.datacity.erp.fragments;

import android.app.Fragment;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datacity.erp.R;

public class AboutFragment extends Fragment {

	public AboutFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about, container,
				false);

		this.setRetainInstance(true);

		try {
			((TextView) rootView.findViewById(R.id.txtVersionLabel))
					.setText(getActivity().getString(R.string.version)
							+ " "
							+ getActivity().getPackageManager().getPackageInfo(
									getActivity().getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return rootView;
	}

	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	// activity.getActionBar().setTitle(R.string.about);
	// }

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden && getActivity() != null)
			getActivity().getActionBar().setTitle(R.string.about);
	}

}
