/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	관리자 로그인을 위한 db접근 : 입력한 id, pw를 확인 후 접속을 허가해준다		    	     		 */
/* 	url접속을 위한 Thread사용, php파일에 접근 후 db정보 교환								 */
/* 	Thread수행 후 Handler에서 알고리즘 적용 및 출력										 */
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
	//id, pw 입력 창

	private final String SERVER_ADDRESS = "  ";
	//접속 할 서버주소
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminloginsession);
		// TODO Auto-generated method stub
		
		idtext = (EditText) findViewById(R.id.editText1);
		pwtext = (EditText) findViewById(R.id.editText2);
		//텍스트창 배정

		Button button = (Button) findViewById(R.id.mainbtn);
		Button login = (Button) findViewById(R.id.adminloginbtn);
		
		xmlThread = new BackThread();
		xmlThread.setDaemon(true);
		//Thread사용을 위한 선언

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(adminloginsession.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
		//메인으로 버튼의 동작

		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				xmlThread.start();
			}
		});
		//로그인버튼의 동작 : Thread를 시작한다

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
					Toast.makeText(adminloginsession.this, "id,pw 재확인", Toast.LENGTH_LONG).show();
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
				//사용자가 입력한 id, pw를 저장한다
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				
				factory.setNamespaceAware(true);
				
				XmlPullParser xpp = factory.newPullParser();
				
				
				URL url = new URL(SERVER_ADDRESS + "/login_admin.php?" + "id="
						+ URLEncoder.encode(id, "UTF-8")+"&password="
						+ URLEncoder.encode(pw, "UTF-8"));
				url.openStream();
				//login_admin.php파일에 id, pw를 넘겨주면서 db접근을 한다
				URL server = new URL(SERVER_ADDRESS + "/login_adminresult.xml");
				//db접근 후 결과 xml파일의 경로
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
