package me.raspass.abode;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter
{
	private Context context;
	private List<ActivityInfo> apps = new ArrayList<ActivityInfo>();
	private PackageManager pm;
	
	public ImageAdapter( Context context )
	{
		this.context = context;
		this.pm      = context.getPackageManager();

		Intent intent = new Intent( Intent.ACTION_MAIN, null );
		       intent.addCategory(  Intent.CATEGORY_LAUNCHER );
	
		for ( ResolveInfo app : pm.queryIntentActivities( intent, 0 ) )
		{
			int i;

			for ( i = 0; i < apps.size(); i++ )
			{
				if ( apps.get( i ).loadLabel( pm ).toString().compareTo( app.loadLabel( pm ).toString() ) > 0 ){ break; }
			}
			
			this.apps.add( i, app.activityInfo );
		}
	}
	
	public View getView( int position, View convertView, ViewGroup parent )
	{
		ActivityInfo app = apps.get( position );
		View gridView;
		LayoutInflater inflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		if (convertView == null)
		{
			gridView = new View( context );
 
			gridView = inflater.inflate( R.layout.icon, null );
 
			TextView textView = ( TextView ) gridView.findViewById( R.id.icon );
			
			Drawable img = app.loadIcon( pm );
			img.setBounds( 0, 0, 70, 70 );
			textView.setCompoundDrawables( null, img, null, null );

			textView.setText( app.loadLabel( pm ) );
		}
		else
		{
			gridView = ( View ) convertView;
		}
 
		return gridView;
	}
	
	public int getCount()
	{
		return apps.size();
	}
 
	public ActivityInfo getItem( int position )
	{
		return apps.get( position );
	}

	public long getItemId( int position )
	{
		return 0;
	}
}