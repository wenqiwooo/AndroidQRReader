package com.wenqiwu.qrreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final String SCAN = "com.google.zxing.client.android.SCAN";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void scanQR(View v){
		try {
			Intent intent = new Intent(SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			
			startActivityForResult(intent, 0);
			
		} catch (ActivityNotFoundException e){
			showDialog(MainActivity.this, 
					"No scanner found", 
					"Download a scanner code activity?",
					"Yes",
					"No").show();
		}
	}
	
	private static AlertDialog showDialog(final Activity act, 
			CharSequence title, 
			CharSequence message,
			CharSequence buttonYes,
			CharSequence buttonNo){
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException e){
					
				}
			}
		});
		
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// Do nothing if user select no
			}
		});
		
		return downloadDialog.show();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		if (requestCode == 0){
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Use toast to display link to user
				Toast toast = Toast.makeText(this.getApplicationContext(), 
						"Content: " + contents + " Format: " + format, 
						Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}
}
