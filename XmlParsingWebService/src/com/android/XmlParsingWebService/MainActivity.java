package com.android.XmlParsingWebService;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */


	int PROGRESS_DIALOG = 1;
	ProgressDialog dialog;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewlayout);
		ListView lv = (ListView)findViewById(R.id.layoutListView);
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setTitle("Connecting ...");
		dialog.setMessage("Please wait while loading data.");
		dialog.setIcon(R.drawable.icon);
		dialog.show();
		SAXParsingClass spc = new SAXParsingClass(this,lv,dialog);
		spc.execute("http://itunes.apple.com/us/rss/topsongs/limit=50/xml");
		

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub

		if (id == PROGRESS_DIALOG) {
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Please wait while loading ...");
			return dialog;
		}
		return null;
	}
}
