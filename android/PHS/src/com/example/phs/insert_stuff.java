/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	관리자 물품 추가 : 관리자의 물품추가 기능을 위해 물품의 모든정보를 파라미터로 db동작					 */
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
public class insert_stuff extends Activity {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/
	EditText idtext;
	EditText nametext;
	EditText kindtext;
	EditText pricetext;
	EditText costtext;
	EditText quantitytext;
	EditText stocktext;
	EditText datetext;
	EditText expirationtext;
	EditText l_idtext;

	Vector<String> result = new Vector<String>();
	String ret ="";
	
	BackThread xmlThread;
	//버튼 클릭 시 동작하기 위한 전역변수 Thread 선언

	private final String SERVER_ADDRESS = "  ";
	//서버접속 주소
	String tagname, content;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.insert_stuff);
	    // TODO Auto-generated method stub
	    
	    idtext = (EditText) findViewById(R.id.editText1);
	    nametext = (EditText) findViewById(R.id.editText2);
	    kindtext = (EditText) findViewById(R.id.editText3);
	    pricetext = (EditText) findViewById(R.id.editText4);
	    costtext = (EditText) findViewById(R.id.editText5);
	    quantitytext = (EditText) findViewById(R.id.editText6);
	    stocktext = (EditText) findViewById(R.id.editText7);
	    datetext = (EditText) findViewById(R.id.editText8);
	    expirationtext = (EditText) findViewById(R.id.editText9);
	    l_idtext = (EditText) findViewById(R.id.editText10);
		
	    Button insert = (Button) findViewById(R.id.inbtn);
	    
		xmlThread = new BackThread();
		xmlThread.setDaemon(true);
	    
	    insert.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					xmlThread.start();
					//버튼 클릭 시 Thread 시작
			}
		});
		
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 				stuff의 모든 정보를 입력받아 db에 추가한 후 정상처리됨을 보여준다 					 */
/*****************************************************************************/
	Handler xmlHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (result.size()!=0) {
					Intent intent = new Intent(insert_stuff.this, admin.class);
					startActivity(intent);
				}
				else if (result.size()==0) {
					Toast.makeText(insert_stuff.this, "양식 재확인!", Toast.LENGTH_LONG).show();
				}

			}
		}
	};
	
	class BackThread extends Thread {
		@Override
		public void run() {
			try {
				String id = idtext.getText().toString();
				String name = nametext.getText().toString();
				String kind = kindtext.getText().toString();
				String price = pricetext.getText().toString();
				String cost = costtext.getText().toString();
				String quantity = quantitytext.getText().toString();
				String stock = stocktext.getText().toString();
				String date = datetext.getText().toString();
				String expiration = expirationtext.getText().toString();
				String l_id = l_idtext.getText().toString();
				
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				
				factory.setNamespaceAware(true);
				
				XmlPullParser xpp = factory.newPullParser();
				
				URL url = new URL(SERVER_ADDRESS + "/stuff_insert.php?" + "id="
						+ URLEncoder.encode(id, "UTF-8")+"&name="
						+ URLEncoder.encode(name, "UTF-8")+"&kind="
						+ URLEncoder.encode(kind, "UTF-8")+"&price="
						+ URLEncoder.encode(price, "UTF-8")+"&cost="
						+ URLEncoder.encode(cost, "UTF-8")+"&quantity="
						+ URLEncoder.encode(quantity, "UTF-8")+"&stock="
						+ URLEncoder.encode(stock, "UTF-8")+"&date="
						+ URLEncoder.encode(date, "UTF-8")+"&expiration="
						+ URLEncoder.encode(expiration, "UTF-8")+"&l_id="
						+ URLEncoder.encode(l_id, "UTF-8"));
				//stuff의 모든 정보를 파라미터로 php파일과 동작한다
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/stuff_insertresult.xml");
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
		}
		
	}

}
