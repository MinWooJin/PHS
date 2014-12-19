package com.example.phs;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("HandlerLeak")
public class loginsession extends Activity {
	
	Vector<String> login_result = new Vector<String>();
	Vector<String> login_name = new Vector<String>();
	String ret ="";
	BackThread xmlThread;
	EditText idtext;
	EditText pwtext;
	String send_id = "";
	String send_name = "";
	
	private final String SERVER_ADDRESS = "  ";
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginsession);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		final Button login = (Button) findViewById(R.id.button2);
		idtext = (EditText) findViewById(R.id.editText2);
		pwtext = (EditText) findViewById(R.id.editText3);

		Button button = (Button) findViewById(R.id.joinbtn);
		Button button2 = (Button) findViewById(R.id.nonsearchbtn);
		final EditText edittext = (EditText) findViewById(R.id.nonsearchText);
		
		xmlThread = new BackThread();
		xmlThread.setDaemon(true);
		
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.button2:
					if (idtext.getText().toString().equals("")) {
						login.setText("fail");
						return;
					}
					
					xmlThread.start();
					
					break;
				}
				
			}
		});
		
		
		
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(loginsession.this, join.class);
				startActivity(intent);
			}
		});
		

		
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String key = edittext.getText().toString();

				Intent intent = new Intent(loginsession.this, nonlogin_search.class);
				intent.putExtra("key", key);
				startActivity(intent);
			}
		});	
		// TODO Auto-generated method stub
		
	}
	
	
	Handler xmlHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (login_result.size()!=0) {
					send_id = login_result.elementAt(0);
					send_name = login_name.elementAt(0);
					Intent intent = new Intent(loginsession.this, loginsuccess.class);
					intent.putExtra("userid", send_id);
					intent.putExtra("username",send_name);
					startActivity(intent);
					
				}
				else if (login_result.size()==0) {
					Intent intent = new Intent(loginsession.this, MainActivity.class);
					startActivity(intent);
				}

			}
		}
	};
	
	class BackThread extends Thread {
		@Override
		public void run() {
			try {

				String id = idtext.getText().toString();
				String pw = pwtext.getText().toString();
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				
				factory.setNamespaceAware(true);
				
				XmlPullParser xpp = factory.newPullParser();
				
				
				URL url = new URL(SERVER_ADDRESS + "/login.php?" + "id="
						+ URLEncoder.encode(id, "UTF-8")+"&password="
						+ URLEncoder.encode(pw, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/loginresult.xml");
				InputStream in = server.openStream();
				xpp.setInput(in, "UTF-8");

				int eventType = xpp.getEventType();

				int i = 0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						tagname = xpp.getName();

					} else if (eventType == XmlPullParser.TEXT) {
						if (tagname.equals("id")) {
							ret = xpp.getText();
							if (i == 0) {
								login_result.add(ret);
							}
							else if (i % 2 == 0)
								login_result.add(ret);
							i++;
							
						}
						else if (tagname.equals("name")) {
							ret = xpp.getText();
							if (i == 0) {
								login_name.add(ret);
							}
							else if (i % 2 == 0)
								login_name.add(ret);
							i++;
							
						}

					}
					eventType = xpp.next();
				}
			} catch (Exception e) {
				e.getMessage();
			}
			xmlHandler.sendEmptyMessage(0);
			//System.out.println(v_name.elementAt(0));
			
		}
		
	}

}