package com.datacity.erp.fragments;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datacity.erp.R;
import com.datacity.erp.model.Building;
import com.datacity.erp.utils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMarkerClickListener,
		OnMapLoadedCallback {

	// List of Buildings by categories
	private Map<String, ArrayList<Building>> buildings;

	// private Activity mActivity;

	private ProgressDialog progressBar;
	private MapView googleMap;
	private GoogleMap mMap;
	private OnBuildingWithIDSelectedListener mCallback;
	private CameraPosition moveToPosition;

	public interface OnBuildingWithIDSelectedListener {
		public void onBuildingWithIDSelected(int categoryID, int buildingID);
	}

	public MapFragment(Map<String, ArrayList<Building>> buildings) {
		this.buildings = buildings;
	}
	
	public MapFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);

//		this.setRetainInstance(true);

		if (buildings != null) {
			progressBar = new ProgressDialog(getActivity());
			progressBar.setCancelable(false);
			progressBar.setMessage(getActivity().getString(
					R.string.loading_markers));
			progressBar.setIndeterminate(true);

			googleMap = (MapView) rootView.findViewById(R.id.mapView);
			googleMap.onCreate(savedInstanceState);

			mMap = googleMap.getMap();
			if (mMap != null)
				this.initializeMap();
		}

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activity.getActionBar().setTitle(R.string.map);
			mCallback = (OnBuildingWithIDSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnBuildingWithIDSelectedListener");
		}
	}

	private void initializeMap() {
		mMap.setOnMapLoadedCallback(this);
		MapsInitializer.initialize(getActivity());
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setAllGesturesEnabled(true);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setOnMarkerClickListener(this);

		CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(new LatLng(
				43.610769, 3.876716), 8);
		mMap.moveCamera(upd);
		mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition cameraPosition) {
				checkBounds(cameraPosition);
			}
		});
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		String title = marker.getTitle();
		mCallback.onBuildingWithIDSelected(
				Integer.parseInt(title.substring(0, title.indexOf(","))),
				Integer.parseInt(title.substring(title.indexOf(",") + 1)));
		return true;
	}

	public class DrawMarkers extends AsyncTask<Void, MarkerOptions, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			ArrayList<Building> buildingList;
			for (int i = 0; i < Constants.CATEGORIES_LIST.length; i++) {
				buildingList = buildings.get(Constants.CATEGORIES_LIST[i]);
				for (int y = 0; y < buildingList.size(); y++) {
					Building building = buildingList.get(y);
					if (building.getLatitude().isEmpty()
							|| building.getLongitude().isEmpty())
						continue;
					MarkerOptions marker = new MarkerOptions().position(
							new LatLng(Double.parseDouble(building
									.getLatitude()), Double
									.parseDouble(building.getLongitude())))
							.title(i + "," + y);
					marker.icon(BitmapDescriptorFactory
							.fromResource(Constants.ICONS_ID[i]));
					publishProgress(marker);
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(MarkerOptions... marker) {
			// super.onProgressUpdate(marker);
			mMap.addMarker(marker[0]);
		}

		@Override
		protected void onPostExecute(Void arg0) {
			progressBar.dismiss();
		}
	}

	private void moveCameraToBuilding(String latitude, String longitude) {
		if (latitude != "" && longitude != "")
			moveToPosition = new CameraPosition.Builder()
				.target(new LatLng(Double.parseDouble(latitude), Double
						.parseDouble(longitude))).zoom(18).build();
	}

	private void checkBounds(CameraPosition cameraPosition) {
		LatLngBounds mapCenter = mMap.getProjection().getVisibleRegion().latLngBounds;

		if (!Constants.BOUNDS.contains(mapCenter.getCenter()))
			mMap.moveCamera(CameraUpdateFactory
					.newLatLng(Constants.DEFAULT_POS));
		if (cameraPosition.zoom > Constants.MAX_ZOOM)
			mMap.animateCamera(CameraUpdateFactory.zoomTo(Constants.MAX_ZOOM));
		else if (cameraPosition.zoom < Constants.MIN_ZOOM)
			mMap.animateCamera(CameraUpdateFactory.zoomTo(Constants.MIN_ZOOM));
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			if (getActivity() != null)
				getActivity().getActionBar().setTitle(R.string.map);
			if (moveToPosition != null) {
				mMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(moveToPosition));
				moveToPosition = null;
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (googleMap != null)
		googleMap.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (googleMap != null)
		googleMap.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (googleMap != null)
			googleMap.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (googleMap != null)
		googleMap.onLowMemory();
	}

	@Override
	public void onMapLoaded() {
		 progressBar.show();
		 new DrawMarkers().execute();
	}

	public void setBuildingLatLngToDisplay(String latitude, String longitude) {
		moveCameraToBuilding(latitude, longitude);
	}

}
