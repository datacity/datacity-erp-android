package com.datacity.erp.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.datacity.erp.R;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

	private String url;
	private ImageView imageView;

	public ImageLoadTask(String url, ImageView imageView) {
		this.url = url;
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		try {
			URL urlConnection = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlConnection
					.openConnection();
			connection.setDoInput(true);
			connection.connect();

			int status = connection.getResponseCode();

			InputStream input;
			if (status >= HttpStatus.SC_BAD_REQUEST) {
				// Log.i("TEST", "Bad request " + urlConnection.toString());
				input = connection.getErrorStream();
			} else {
				// Log.i("TEST2", "Pas bad request " +
				// urlConnection.toString());
				input = connection.getInputStream();

			}
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result != null)
			imageView.setImageBitmap(result);
		else
			imageView.setImageResource(R.drawable.nopics);
	}

}