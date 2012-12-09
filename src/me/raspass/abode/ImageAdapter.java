package me.raspass.abode;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ResolveInfo> apps = new ArrayList<ResolveInfo>();
	private PackageManager pm;

	public int height;
	public int width;

	public ImageAdapter(Context context) {
		this.context = context;
		this.pm = context.getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		for (ResolveInfo app : pm.queryIntentActivities(intent, 0)) {
			int i;

			for (i = 0; i < apps.size(); i++) {
				if (apps.get(i).loadLabel(pm).toString()
						.compareTo(app.loadLabel(pm).toString()) > 0) {
					break;
				}
			}

			this.apps.add(i, app);
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ResolveInfo app = apps.get(position);

		ImageView view = convertView == null ? new ImageView(context)
				: (ImageView) convertView;

		// TODO text = app.loadLabel(pm));

		view.setImageDrawable(app.loadIcon(pm));
		view.setScaleType(ImageView.ScaleType.FIT_CENTER);
		view.setLayoutParams(new GridView.LayoutParams(width, height));

		return view;
	}

	public int getCount() {
		return apps.size();
	}

	public Object getItem(int position) {
		context.startActivity(pm.getLaunchIntentForPackage(apps.get(position).activityInfo.packageName));

		return null;
	}

	public long getItemId(int position) {
		return 0;
	}
}