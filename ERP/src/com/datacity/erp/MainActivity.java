package com.datacity.erp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.datacity.erp.adapter.NavDrawerListAdapter;
import com.datacity.erp.fragments.AboutFragment;
import com.datacity.erp.fragments.BuildingFragment;
import com.datacity.erp.fragments.BuildingFragment.OnBuildingOptionsSelectedListener;
import com.datacity.erp.fragments.CategoriesFragment;
import com.datacity.erp.fragments.CategoriesFragment.OnCategorySelectedListener;
import com.datacity.erp.fragments.CategoriesSecondFragment;
import com.datacity.erp.fragments.FavouritesFragment;
import com.datacity.erp.fragments.FavouritesFragment.OnBuildingSelectedListener;
import com.datacity.erp.fragments.MapFragment;
import com.datacity.erp.fragments.MapFragment.OnBuildingWithIDSelectedListener;
import com.datacity.erp.fragments.SearchFragment;
import com.datacity.erp.model.Building;
import com.datacity.erp.model.NavDrawerItem;
import com.datacity.erp.parser.GSONParser;
import com.datacity.erp.utils.Constants;
import com.datacity.erp.utils.Utils;

public class MainActivity extends Activity implements
		OnBuildingSelectedListener, OnBuildingWithIDSelectedListener,
		OnCategorySelectedListener, OnBuildingOptionsSelectedListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// List of Buildings by categories
	private Map<String, ArrayList<Building>> buildings;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	private FragmentManager fragmentManager;
	private Fragment[] fragments = new Fragment[Constants.FRAGMENT_COUNT];
	private int lastFragmentID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AssetManager assetManager = this.getAssets();
		try {
			buildings = GSONParser.readJsonStream(assetManager
					.open(Constants.JSON_FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (buildings != null) {

			NavDrawerInit();

			lastFragmentID = Constants.MAP;

			if (savedInstanceState != null) {
				fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				for (int i = 0; i < Constants.FRAGMENT_COUNT; i++)
					fragments[i] = fragmentManager.findFragmentByTag(String
							.valueOf(i));
				transaction.remove(fragments[Constants.MAP]);
				fragments[Constants.MAP] = new MapFragment(buildings);
				transaction.commit();
			} else {

				fragments[Constants.MAP] = new MapFragment(buildings);
				fragments[Constants.CATEGORIES] = new CategoriesFragment();
				fragments[Constants.FAVORITES] = new FavouritesFragment();
				fragments[Constants.ABOUT] = new AboutFragment();
				fragments[Constants.BUILDING] = new BuildingFragment();
				fragments[Constants.CATEGORY_SECOND] = new CategoriesSecondFragment();
				fragments[Constants.SEARCH] = new SearchFragment(
						Utils.CustomHashMapToList(buildings));
			}

			displayView(Constants.MAP, false, false);
		} else
			callAlertDialog();
	}

	private void callAlertDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(R.string.error);
		alertDialogBuilder.setMessage(getString(R.string.empty_list))
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						MainActivity.this.finish();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void NavDrawerInit() {
		mTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Map
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[Constants.MAP],
				navMenuIcons.getResourceId(0, -1)));
		// Categories
		navDrawerItems.add(new NavDrawerItem(
				navMenuTitles[Constants.CATEGORIES], navMenuIcons
						.getResourceId(1, -1)));
		// Favorites
		navDrawerItems.add(new NavDrawerItem(
				navMenuTitles[Constants.FAVORITES], navMenuIcons.getResourceId(
						2, -1)));
		// About
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[Constants.ABOUT],
				navMenuIcons.getResourceId(3, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_action_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// changer "mDrawerTitle" par "mTitle" si on veut garder le
				// titre du fragment
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position, false, false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (lastFragmentID != Constants.BUILDING)
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		if (lastFragmentID != Constants.BUILDING) {
			switch (item.getItemId()) {
			case R.id.action_search:
				displayView(Constants.SEARCH, true, false);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
		return false;
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		if (lastFragmentID != Constants.BUILDING)
			menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * 
	 * @param addToBackStack
	 * @param isBackStacked
	 * */
	private void displayView(int position, boolean addToBackStack,
			boolean isBackStacked) {
		switch (position) {
		case Constants.MAP:
			setTitle(getString(R.string.map));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		case Constants.CATEGORIES:
			setTitle(getString(R.string.categories));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		case Constants.FAVORITES:
			setTitle(getString(R.string.favourites));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		case Constants.ABOUT:
			setTitle(getString(R.string.about));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		case Constants.BUILDING:
			setTitle(getString(R.string.building));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		case Constants.CATEGORY_SECOND:
			setTitle(getString(R.string.category_buildings));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		case Constants.SEARCH:
			setTitle(getString(R.string.search));
			showFragment(position, addToBackStack, isBackStacked);
			break;
		default:
			break;
		}
	}

	private void showFragment(int fragmentIndex, boolean addToBackStack,
			boolean isBackStacked) {

		if (fragments[fragmentIndex].isVisible() == false) {

			fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();

			if (fragmentIndex <= Constants.ABOUT) {
				fragmentManager.popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
				mDrawerList.setItemChecked(fragmentIndex, true);
				mDrawerList.setSelection(fragmentIndex);
				mDrawerLayout.closeDrawer(mDrawerList);
			} else if (isBackStacked == false)
				transaction.setCustomAnimations(R.animator.slide_in_left,
						R.animator.slide_out_left, R.animator.slide_in_right,
						R.animator.slide_out_right);

			if (addToBackStack)
				transaction.addToBackStack(fragments[lastFragmentID].getTag());

			for (int i = 0; i < fragments.length; i++) {
				Fragment fragment = fragments[i];
				if (fragment.isAdded() == false)
					transaction.add(R.id.frame_container, fragment,
							String.valueOf(i));
				transaction.hide(fragment);
			}

			lastFragmentID = fragmentIndex;
			invalidateOptionsMenu();
			transaction.show(fragments[fragmentIndex]).commit();
		}
	}

	@Override
	public void onBackPressed() {
		fragmentManager = getFragmentManager();
		int backStackEntryCount = fragmentManager.getBackStackEntryCount();
		if (backStackEntryCount > 0) {
			int tag = Integer.parseInt(fragmentManager.getBackStackEntryAt(
					backStackEntryCount - 1).getName());
			fragmentManager.popBackStack();
			displayView(tag, false, true);
		} else
			super.onBackPressed();
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBuildingWithIDSelected(int categoryID, int buildingID) {
		Building building = this.buildings.get(
				Constants.CATEGORIES_LIST[categoryID]).get(buildingID);
		((BuildingFragment) fragments[Constants.BUILDING])
				.setActualBuilding(building);
		displayView(Constants.BUILDING, true, false);
	}

	@Override
	public void onBuildingSelected(Building building) {
		((BuildingFragment) fragments[Constants.BUILDING])
				.setActualBuilding(building);
		displayView(Constants.BUILDING, true, false);
	}

	@Override
	public void onCategorySelected(int categoryID) {
		((CategoriesSecondFragment) fragments[Constants.CATEGORY_SECOND])
				.setBuildingListAndCategory(Utils
						.BuildingNamesList(this.buildings
								.get(Constants.CATEGORIES_LIST[categoryID])),
						categoryID);
		displayView(Constants.CATEGORY_SECOND, true, false);
	}

	@Override
	public void onMapOptionSelected(String latitude, String longitude) {
		((MapFragment) fragments[Constants.MAP]).setBuildingLatLngToDisplay(
				latitude, longitude);
		displayView(Constants.MAP, false, false);
	}

	@Override
	public void onReturnOptionSelected() {
		onBackPressed();
	}
}
