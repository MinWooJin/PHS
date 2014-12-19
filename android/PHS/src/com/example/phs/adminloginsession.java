/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	������ �α����� ���� db���� : �Է��� id, pw�� Ȯ�� �� ������ �㰡���ش�		    	     		 */
/* 	url������ ���� Thread���, php���Ͽ� ���� �� db���� ��ȯ								 */
/* 	Thread���� �� Handler���� �˰��� ���� �� ���										 */
/*****************************************************************************/

/*****************************************************************************/
/*						 Included Header Files 								 */
/*****************************************************************************/

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class adminloginsession extends Activity {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/

	Vector<String> login_result = new Vector<String>();
	String ret ="";
	BackThread xmlThread;
	
	EditText idtext;
	EditText pwtext;
	//id, pw �Է� â

	private final String SERVER_ADDRESS = "  ";
	//���� �� �����ּ�
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminloginsession);
		// TODO Auto-generated method stub
		
		idtext = (EditText) findViewById(R.id.editText1);
		pwtext = (EditText) findViewById(R.id.editText2);
		//�ؽ�Ʈâ ����

		Button button = (Button) findViewById(R.id.mainbtn);
		Button login = (Button) findViewById(R.id.adminloginbtn);
		
		xmlThread = new BackThread();
		xmlThread.setDaemon(true);
		//Thread����� ���� ����

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(adminloginsession.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
		//�������� ��ư�� ����

		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				xmlThread.start();
			}
		});
		//�α��ι�ư�� ���� : Thread�� �����Ѵ�

	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
	
	Handler xmlHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (login_result.size()!=0) {
					Intent intent = new Intent(adminloginsession.this, admin.class);
					startActivity(intent);
					
				}
				else if (login_result.size()==0) {
					Toast.makeText(adminloginsession.this, "id,pw ��Ȯ��", Toast.LENGTH_LONG).show();
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
				//����ڰ� �Է��� id, pw�� �����Ѵ�
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				
				factory.setNamespaceAware(true);
				
				XmlPullParser xpp = factory.newPullParser();
				
				
				URL url = new URL(SERVER_ADDRESS + "/login_admin.php?" + "id="
						+ URLEncoder.encode(id, "UTF-8")+"&password="
						+ URLEncoder.encode(pw, "UTF-8"));
				url.openStream();
				//login_admin.php���Ͽ� id, pw�� �Ѱ��ָ鼭 db������ �Ѵ�
				URL server = new URL(SERVER_ADDRESS + "/login_adminresult.xml");
				//db���� �� ��� xml������ ���
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
