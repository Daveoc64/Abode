package me.raspass.abode;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class Main extends Activity {
	private AlertDialog settings;
	private GridView gridview;
	private ImageAdapter imageAdapter;
	private int columns = 5;
	private int padding = 30;
	private int rows = 7;
	private TextView columnsLabel;
	private TextView paddingLabel;
	private TextView rowsLabel;

	private void redraw() {
		gridview.invalidateViews();
		gridview.setHorizontalSpacing(padding);
		gridview.setNumColumns(columns);
		gridview.setPadding(padding, padding, padding, padding);
		gridview.setVerticalSpacing(padding);

		View content = getWindow().findViewById(Window.ID_ANDROID_CONTENT);

		imageAdapter.height = (int) Math
				.floor((content.getHeight() - (rows + 1) * padding) / rows);
		imageAdapter.width = (int) Math
				.floor((content.getWidth() - (columns + 1) * padding) / columns);

		columnsLabel.setText("" + columns);
		rowsLabel.setText("" + rows);
		paddingLabel.setText("" + padding);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Swap values if landscape
		if (getResources().getConfiguration().orientation == 2) {
			int temp = columns;
			columns = rows;
			rows = temp;
		}

		View view = this.getLayoutInflater().inflate(R.layout.settings, null);

		View.OnClickListener listener = new View.OnClickListener() {
			public void onClick(View view) {
				switch (view.getId()) {
				case R.id.addColumn:
					columns++;
					break;
				case R.id.removeColumn:
					columns--;
					break;
				case R.id.addRow:
					rows++;
					break;
				case R.id.removeRow:
					rows--;
					break;
				case R.id.addPadding:
					padding++;
					break;
				case R.id.removePadding:
					padding--;
					break;
				}

				redraw();
			}
		};

		view.findViewById(R.id.addColumn).setOnClickListener(listener);
		view.findViewById(R.id.removeColumn).setOnClickListener(listener);
		view.findViewById(R.id.addRow).setOnClickListener(listener);
		view.findViewById(R.id.removeRow).setOnClickListener(listener);
		view.findViewById(R.id.addPadding).setOnClickListener(listener);
		view.findViewById(R.id.removePadding).setOnClickListener(listener);

		columnsLabel = ((TextView) view.findViewById(R.id.columns));
		rowsLabel = ((TextView) view.findViewById(R.id.rows));
		paddingLabel = ((TextView) view.findViewById(R.id.padding));

		settings = new AlertDialog.Builder(this).setView(view).create();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		settings.show();

		// Ensure future menu presses call this method
		ActivityCompat.invalidateOptionsMenu(this);

		return true;
	}

	@Override
	public void onWindowFocusChanged(boolean focus) {
		imageAdapter = new ImageAdapter(this);

		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(imageAdapter);
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				((ImageAdapter) parent.getAdapter()).getItem(position);
			}
		});

		redraw();
	}
}