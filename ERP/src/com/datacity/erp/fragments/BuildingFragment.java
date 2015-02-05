package com.datacity.erp.fragments;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datacity.erp.R;
import com.datacity.erp.model.Building;
import com.datacity.erp.utils.ImageLoadTask;
import com.datacity.erp.utils.UserPrefs;

public class BuildingFragment extends Fragment {

	private Building actualBuilding;

	private ImageView favImage;

	private View rootView;

	// private Activity mActivity;

	private UserPrefs userPreferences;

	private OnBuildingOptionsSelectedListener mCallback;

	public interface OnBuildingOptionsSelectedListener {
		public void onMapOptionSelected(String latitude, String longitude);

		public void onReturnOptionSelected();
	}

	public BuildingFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_building, container,
				false);

		this.setRetainInstance(true);

		userPreferences = new UserPrefs(getActivity());

		favImage = (ImageView) rootView.findViewById(R.id.favouriteImageView);
		addListenerOnButton(rootView);

		// initBuildingView();

		return rootView;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden && getActivity() != null)
			getActivity().getActionBar().setTitle(R.string.building);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// mActivity = activity;
			// activity.getActionBar().setTitle(R.string.building);
			mCallback = (OnBuildingOptionsSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnBuildingOptionsSelectedListener");
		}
	}

	private void initBuildingView() {
		if (actualBuilding != null) {
			ApplicationInfo app;
			Bundle bundle = null;
			String addressURL = null;
			String address = actualBuilding.getNumVoie() + " "
					+ actualBuilding.getTypeVoie() + " "
					+ actualBuilding.getNomVoie() + " "
					+ actualBuilding.getCodePostal() + " "
					+ actualBuilding.getVille();

			try {
				if (getActivity() != null) {
					app = getActivity().getPackageManager().getApplicationInfo(
							this.getActivity().getPackageName(),
							PackageManager.GET_META_DATA);
					bundle = app.metaData;
					addressURL = URLEncoder.encode(address, "UTF-8");
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (bundle != null && addressURL != null)
				new ImageLoadTask(
						"http://maps.googleapis.com/maps/api/streetview?size=480x320&location="
								+ addressURL
								+ "&fov=120&sensor=true&key="
								+ bundle.getString("com.google.android.maps.v2.API_KEY"),
						(ImageView) rootView
								.findViewById(R.id.building_imageView))
						.execute(null, null);

			String emptyString = getActivity().getString(R.string.empty_string);
			TextView buildingTextView = (TextView) rootView
					.findViewById(R.id.building_title_textView);

			if (!actualBuilding.getNom().isEmpty())
				buildingTextView.setText(actualBuilding.getNom());
			else
				buildingTextView.setText(emptyString);

			buildingTextView = (TextView) rootView
					.findViewById(R.id.building_address_textView);
			if (!address.isEmpty())
				buildingTextView.setText(address);
			else
				buildingTextView.setText(emptyString);

			buildingTextView = (TextView) rootView
					.findViewById(R.id.building_phone_textView);

			if (!actualBuilding.getTelephone().isEmpty()) {
				buildingTextView.setText(actualBuilding.getTelephone());
				buildingTextView.setTextColor(getResources().getColor(
						android.R.color.holo_blue_light));
			} else {
				buildingTextView.setText(emptyString);
				buildingTextView.setTextColor(getResources().getColor(
						android.R.color.black));
			}

			buildingTextView = (TextView) rootView
					.findViewById(R.id.building_website_textView);

			if (!actualBuilding.getSiteWeb().isEmpty()) {
				buildingTextView.setText(actualBuilding.getSiteWeb());
				buildingTextView.setTextColor(getResources().getColor(
						android.R.color.holo_blue_light));
			} else {
				buildingTextView.setText(emptyString);
				buildingTextView.setTextColor(getResources().getColor(
						android.R.color.black));
			}

			buildingTextView = (TextView) rootView
					.findViewById(R.id.building_email_textView);
			if (!actualBuilding.getEmail().isEmpty()) {
				buildingTextView.setText(actualBuilding.getEmail());
				buildingTextView.setTextColor(getResources().getColor(
						android.R.color.holo_blue_light));
			} else {
				buildingTextView.setText(emptyString);
				buildingTextView.setTextColor(getResources().getColor(
						android.R.color.black));
			}

			buildingTextView = (TextView) rootView
					.findViewById(R.id.building_subCategory_textView);
			if (!actualBuilding.getCategorie().isEmpty())
				buildingTextView.setText(actualBuilding.getCategorie());
			else
				buildingTextView.setText(emptyString);

			if (userPreferences.isStoredInPreferences(actualBuilding.getNom()))
				favImage.setImageResource(R.drawable.ic_nav_favorites);
			else
				favImage.setImageResource(R.drawable.ic_favorite_empty);
		}
	}

	private void addListenerOnButton(final View rootView) {
		favImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (userPreferences.isStoredInPreferences(actualBuilding
						.getNom())) {
					favImage.setImageResource(R.drawable.ic_favorite_empty);
					userPreferences.deleteFromPreferences(actualBuilding
							.getNom());
				} else {
					favImage.setImageResource(R.drawable.ic_nav_favorites);
					userPreferences.storeToPreferences(actualBuilding);
				}
			}
		});

		((TextView) rootView.findViewById(R.id.building_email_textView))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onClickInformations(v.getId());
					}
				});

		((TextView) rootView.findViewById(R.id.building_phone_textView))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onClickInformations(v.getId());
					}
				});

		((TextView) rootView.findViewById(R.id.building_website_textView))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onClickInformations(v.getId());
					}
				});

		((Button) rootView.findViewById(R.id.building_map_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (!actualBuilding.getLatitude().isEmpty()
								&& !actualBuilding.getLongitude().isEmpty())
							mCallback.onMapOptionSelected(
									actualBuilding.getLatitude(),
									actualBuilding.getLongitude());
						else
							Toast.makeText(rootView.getContext(), R.string.no_coordinates, Toast.LENGTH_LONG).show();
					}
				});

		((Button) rootView.findViewById(R.id.building_return_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mCallback.onReturnOptionSelected();
					}
				});
	}

	public void onClickInformations(final int textViewID) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		// alertDialogBuilder.setTitle(R.string.error);
		// final int textViewID = v.getId();
		final String contact_content = ((TextView) rootView
				.findViewById(textViewID)).getText().toString();
		if (!contact_content.equalsIgnoreCase(getActivity().getString(
				R.string.empty_string))) {
			int dialogMessage;
			if (textViewID == R.id.building_phone_textView)
				dialogMessage = R.string.call_number;
			else if (textViewID == R.id.building_email_textView)
				dialogMessage = R.string.send_email;
			else
				dialogMessage = R.string.go_website;
			TextView myMsg = new TextView(rootView.getContext());
			myMsg.setText(dialogMessage);
			myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
			myMsg.setTextAppearance(rootView.getContext(),
					android.R.style.TextAppearance_DeviceDefault_Medium);
			// alertDialogBuilder.setMessage(dialogMessage);
			// alertDialogBuilder.setGravity(Gravity.CENTER_HORIZONTAL);
			alertDialogBuilder.setView(myMsg);
			alertDialogBuilder.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent contact_intent = null;
							switch (textViewID) {
							case R.id.building_phone_textView:
								contact_intent = new Intent(Intent.ACTION_DIAL);
								contact_intent.setData(Uri.parse("tel:"
										+ contact_content));
								startActivity(contact_intent);
								break;
							case R.id.building_email_textView:
								contact_intent = new Intent(
										Intent.ACTION_SENDTO, Uri
												.fromParts("mailto",
														contact_content, null));
								startActivity(Intent.createChooser(
										contact_intent,
										getActivity().getString(
												R.string.send_email_with)));
								break;
							case R.id.building_website_textView:
								contact_intent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse((contact_content
												.startsWith("http://") ? contact_content
												: "http://" + contact_content)));
								startActivity(contact_intent);
								break;
							default:
								break;
							}
						}
					});
			alertDialogBuilder.setNegativeButton(android.R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
	}

	public void setActualBuilding(Building actualBuilding) {
		this.actualBuilding = actualBuilding;
		if (actualBuilding != null)
			initBuildingView();
	}
}
