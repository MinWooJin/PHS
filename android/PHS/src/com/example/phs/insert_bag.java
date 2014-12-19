/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	장바구니 추가 시 db접근 : 로그인 검색 에서 담기 버튼 클릭 시 사용자id, 물품id, 입력수량으로 db bag에 삽입	 */
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

@SuppressLint("HandlerLeak")
public class insert_bag extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/
	
	String receive_id = "";
	String receive_name = "";
	String receive_sid = "";
	String receive_quantity = "";
	
	Vector<String> result = new Vector<String>();
	
	String send_id = "";
	String send_name = "";
	
	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	//접속 할 서버주소
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.insert_bag);
	    
	    Intent intent = getIntent();
		receive_id = intent.getExtras().getString("userid");
		receive_name = intent.getExtras().getString("username");
		receive_sid = intent.getExtras().getString("sid");
		receive_quantity = intent.getExtras().getString("quantity");
		
		send_id = receive_id;
		send_name = receive_name;
		
		Button go_bag = (Button) findViewById(R.id.bagbtn);
		
		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		
		go_bag.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(insert_bag.this, bag.class);
				intent.putExtra("userid", send_id);
				intent.putExtra("username", send_name);
				startActivity(intent);
			}
		});
		

	
	    // TODO Auto-generated method stub
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 						입력 수량을 받아 db접근 후 처리한다			 					 */
/*****************************************************************************/
	Handler xmlHandler = new Handler() { // 스레드를 위한 핸들러 생성
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				
			}
		}
	};

	class xmlThread extends Thread {
		public void run() {
			try {
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance(); // xmlPullparser를 위한 준비과정.
				factory.setNamespaceAware(true); // xml의 네임페이스 허용
				XmlPullParser xpp = factory.newPullParser();

				URL url = new URL(SERVER_ADDRESS + "/baginsert.php?" + "uid="
						+ URLEncoder.encode(receive_id, "UTF-8") + "&sid="
						+ URLEncoder.encode(receive_sid, "UTF-8") + "&quantity="
						+ URLEncoder.encode(receive_quantity, "UTF-8"));
				//사용자 id, 물품 id, 담을 수량을 파라미터로 baginsert를 수행한다.
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/baginsertresult.xml");
				InputStream in = server.openStream();
				xpp.setInput(in, "UTF-8");

				int eventType = xpp.getEventType(); // 이벤트 타입 얻어오기 예르들어 <node>
													// 인지 </node> 인지 구분하기 위한.
				int i = 0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					ret = "";
					// 임시저장변수 ret를 초기화
					if (eventType == XmlPullParser.START_TAG) {
						tagname = xpp.getName();

					} else if (eventType == XmlPullParser.TEXT) {
						if (tagname.equals("id")) {
							ret = xpp.getText();
							if (i == 0) {
								result.add(ret);
							} else if (i % 2 == 0)
								result.add(ret);
							i++;
						} 

					}
					eventType = xpp.next();
				}
			} catch (Exception e) {
				e.getMessage(); // 에러 체크

			} finally {
				xmlHandler.sendEmptyMessage(0); // 메시지 전송
			}
		}
	}

}