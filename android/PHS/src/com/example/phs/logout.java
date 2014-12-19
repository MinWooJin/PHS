/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	회원 로그아웃 : 로그인된 소비자의 로그아웃 기능을 위해 품명을 입력받은 후 db처리 및 출력					 */
/* 	PHS는 기본적으로 매장 내에서의 구매 도움 서비스로서, 로그아웃을 하는것은 물품구매가 완료되었다는 것을 가정으로	 */
/* 	장바구니에 추가되어있는 모든 정보를 삭제한다.											 */
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
public class logout extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/		
	Vector<String> result = new Vector<String>();

	String receive_id = "";

	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	//서버 접속을 위한 주소
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logout);

		Intent intent = getIntent();
		receive_id = intent.getExtras().getString("userid");

		Button go_main = (Button) findViewById(R.id.mainbtn);

		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//로그아웃 page접속 시 Thread start

		go_main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(logout.this, MainActivity.class);
				startActivity(intent);
			}
		});
		//로그아웃 완료 후 main page로 이동

		// TODO Auto-generated method stub
	}

/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 						사용자의 id에 해당하는 장바구니정보를 삭제한다 				 		 */
/*****************************************************************************/		
	Handler xmlHandler = new Handler() { // 스레드를 위한 핸들러 생성
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				//로그아웃은 실패를 하지 않는다.
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

				URL url = new URL(SERVER_ADDRESS + "/logout.php?" + "id="
						+ URLEncoder.encode(receive_id, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/logoutresult.xml");
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
						if (tagname.equals("execution")) {
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