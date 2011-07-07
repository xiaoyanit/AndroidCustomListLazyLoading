package com.android.XmlParsingWebService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
public class SAXParsingClass extends AsyncTask<String, Integer, MyData> {
	Activity context;
	ListView listView;
	ProgressDialog pg;
	private static final String kEntry = "entry";
	private static final String kArtist = "artist";
	private static final String kTitle = "title";
	private static final String kImage = "image";
	private static final String kPrice = "price";
	private static final String kDate = "releaseDate";
	ArrayList<MyData> listDatas;
	public SAXParsingClass(Activity c, ListView lv, ProgressDialog p) {
		context = c;
		listView = lv;
		pg = p;		
	}
	@Override
	protected MyData doInBackground(String... params) {
		MyData myData = new MyData();
		try {
			URL url = new URL(params[0]);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			try {
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				MySAXHandler mySAXHandler = new MySAXHandler();
				xr.setContentHandler(mySAXHandler);
				xr.parse(new InputSource(url.openStream()));
				myData = mySAXHandler.getParsedData();
				return myData;

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return myData;

	}

	@Override
	protected void onPostExecute(MyData result) {
		super.onPostExecute(result);
		pg.dismiss();
		for (MyData each : listDatas) {
			Log.d("Each Element", String.format("%s   %s  %s", each.mArtist,
					each.mPrice, each.mReleaseDate));
		}
		ListingAdapter listAdapt = new ListingAdapter(context, listDatas);
		listView.setAdapter(listAdapt);
		pg.dismiss();

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	class MySAXHandler implements org.xml.sax.ContentHandler {
		boolean hasStarted;
		boolean isElement;
		String currentElement;
		StringBuilder builder;
		MyData mydata;

		public MyData getParsedData() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startDocument() throws SAXException {
			listDatas = new ArrayList<MyData>();
			Log.d("Started Document", "Document Parsing has been started");
			mydata = new MyData();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			if (localName.equalsIgnoreCase(kEntry)) {
				hasStarted = true;
			}
			isElement = true;
			if (hasStarted
					&& ((localName.equalsIgnoreCase(kArtist))
							|| (localName.equalsIgnoreCase(kDate)) || ((localName
							.equalsIgnoreCase(kImage)) && (atts
							.getValue("height")).equalsIgnoreCase("170")))
					|| (localName.equalsIgnoreCase(kPrice))
					|| (localName.equalsIgnoreCase(kTitle))) {

				currentElement = localName;
				builder = new StringBuilder();
				isElement = false;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (!isElement) {
				String string = new String(ch, start, length);
				if (currentElement.equalsIgnoreCase(kArtist))
					mydata.mArtist = string;
				else if (currentElement.equalsIgnoreCase(kDate))
					mydata.mReleaseDate = string;
				else if (currentElement.equalsIgnoreCase(kImage))
					mydata.mImage = string;
				else if (currentElement.equalsIgnoreCase(kPrice))
					mydata.mPrice = string;
				else if (currentElement.equalsIgnoreCase(kTitle))
					mydata.mTitle = string;
			}
		}
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (hasStarted
					&& ((localName.equalsIgnoreCase(kArtist))
							|| (localName.equalsIgnoreCase(kDate)) || (localName
							.equalsIgnoreCase(kImage)))
					|| (localName.equalsIgnoreCase(kPrice))
					|| (localName.equalsIgnoreCase(kTitle)) && !isElement) {
				isElement = true;

			} else if (localName.equalsIgnoreCase(kEntry)) {

				listDatas.add(mydata);
				mydata = new MyData();
			}
		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
		
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setDocumentLocator(Locator locator) {
			// TODO Auto-generated method stub

		}

		@Override
		public void skippedEntity(String name) throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			// TODO Auto-generated method stub

		}

	}

}
