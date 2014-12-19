/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	회원탈퇴 : 로그인 소비자의 회원탈퇴 기능(id, pw를 재확인 한 후 최종탈퇴한다)						 */
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
public class secession extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/	
	Vector<String> result = new Vector<String>();
	String ret ="";
	BackThread xmlThread;
	EditText idtext;
	EditText pwtext;
	
	private final String SERVER_ADDRESS = "  ";
	//서버접속을 위한 주소
	String tagname, content;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.secession);
	    
		idtext = (EditText) findViewById(R.id.editText1);
		pwtext = (EditText) findViewById(R.id.editText2);
		
		Button secession = (Button) findViewById(R.id.sessionbtn);
		Button cancel = (Button) findViewById(R.id.button1);
		
		xmlThread = new BackThread();
		xmlThread.setDaemon(true);
		
		secession.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					xmlThread.start();
					//버튼 클릭 시 Thread start
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(secession.this, MainActivity.class);
				startActivity(intent);
			}
		});
		//취소 버튼 클릭 시 동작
	
	    // TODO Auto-generated method stub
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 					사용자가 입력한 id, pw를 재확인 후 최종 회원탈퇴를 실행			 		 */
/*****************************************************************************/
	Handler xmlHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (result.elementAt(0).equals("execution!!")) {
					Toast.makeText(secession.this, "회원탈퇴 완료", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(secession.this, MainActivity.class);
					startActivity(intent);
					
				}
				else {
					Toast.makeText(secession.this, "ID,PW 재확인!", Toast.LENGTH_LONG).show();
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
				//사용자가 입력한 id, pw를 받아온다
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				
				factory.setNamespaceAware(true);
				
				XmlPullParser xpp = factory.newPullParser();
				
				
				URL url = new URL(SERVER_ADDRESS + "/deletecustomer.php?" + "id="
						+ URLEncoder.encode(id, "UTF-8")+"&password="
						+ URLEncoder.encode(pw, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/deletecustomerresult.xml");
				InputStream in = server.openStream();
				xpp.setInput(in, "UTF-8");

				int eventType = xpp.getEventType();

				int i = 0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						tagname = xpp.getName();
						System.out.println(tagname);

					} else if (eventType == XmlPullParser.TEXT) {
						if (tagname.equals("execution")) {
							ret = xpp.getText();
							if (i == 0) {
								result.add(ret);
							}
							else if (i % 2 == 0)
								result.add(ret);
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
