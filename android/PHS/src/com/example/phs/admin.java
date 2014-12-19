/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	알림기능을 수행하기 위해 관리자 로그인 후 db접근 : 유통기한, 수량을 확인해서 주의물품 표시	     		 */
/* 	url접속을 위한 Thread사용, php파일에 접근 후 db정보 교환								 */
/* 	Thread수행 후 Handler에서 알고리즘 적용 및 출력										 */
/*****************************************************************************/

/*****************************************************************************/
/*						 Included Header Files 								 */
/*****************************************************************************/

package com.example.phs;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.util.Calendar;
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
import android.widget.TextView;

@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
public class admin extends Activity {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	Calendar currentDate = Calendar.getInstance();
	String today = dateFormat.format(currentDate.getTime());
	//현재 날자를 얻기 위한 작업
	
	Vector<String> result_id = new Vector<String>();
	Vector<String> result_name = new Vector<String>();
	Vector<String> result_quantity = new Vector<String>();
	Vector<String> result_expiration = new Vector<String>();
	//db접근 후 데이터 저장을 위한 Vector
	
	Vector<String> result_alram_id = new Vector<String>();
	Vector<String> result_alram_name = new Vector<String>();
	Vector<String> result_alram_quantity = new Vector<String>();
	Vector<String> result_alram_expiration = new Vector<String>();
	//알람기능을 수행하기 위한 Vector
	
	TextView txtView[][] = new TextView[6][5];
	//실제 레이아웃 view
	
	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	String tagname, content;
	//db에 접속하기 위한 서버 주소
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);

		Button button = (Button) findViewById(R.id.insertbtn);
		Button button2 = (Button) findViewById(R.id.adminsearchbtn);
		Button logout = (Button) findViewById(R.id.button2);
		final EditText edittext = (EditText) findViewById(R.id.adminsearch);

		txtView[0][1] = (TextView) findViewById(R.id.txtView0_1);
		txtView[0][2] = (TextView) findViewById(R.id.txtView0_2);
		txtView[0][3] = (TextView) findViewById(R.id.txtView0_3);
		txtView[0][4] = (TextView) findViewById(R.id.txtView0_4);
		
		txtView[1][1] = (TextView) findViewById(R.id.txtView1_1);
		txtView[1][2] = (TextView) findViewById(R.id.txtView1_2);
		txtView[1][3] = (TextView) findViewById(R.id.txtView1_3);
		txtView[1][4] = (TextView) findViewById(R.id.txtView1_4);
		
		txtView[2][1] = (TextView) findViewById(R.id.txtView2_1);
		txtView[2][2] = (TextView) findViewById(R.id.txtView2_2);
		txtView[2][3] = (TextView) findViewById(R.id.txtView2_3);
		txtView[2][4] = (TextView) findViewById(R.id.txtView2_4);
		
		txtView[3][1] = (TextView) findViewById(R.id.txtView3_1);
		txtView[3][2] = (TextView) findViewById(R.id.txtView3_2);
		txtView[3][3] = (TextView) findViewById(R.id.txtView3_3);
		txtView[3][4] = (TextView) findViewById(R.id.txtView3_4);
		
		txtView[4][1] = (TextView) findViewById(R.id.txtView4_1);
		txtView[4][2] = (TextView) findViewById(R.id.txtView4_2);
		txtView[4][3] = (TextView) findViewById(R.id.txtView4_3);
		txtView[4][4] = (TextView) findViewById(R.id.txtView4_4);
		//txtView의 id는 변수적용이 되지 않기 때문에 하나씩 적용
		
		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//url접속을 위한 Thread정의 및 start
		
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin.this, insert_stuff.class);
				startActivity(intent);
			}
		});
		//물품삽입 버튼클릭 시 이동
		
		logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin.this, admin_logout.class);
				startActivity(intent);
			}
		});
		//logout버튼 클릭 시 이동

		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String key = edittext.getText().toString();
				Intent intent = new Intent(admin.this, admin_detail.class);
				intent.putExtra("key", key);
				startActivity(intent);
			}
		});
		//검색 버튼 클릭 시 이동 및 key passing
		// TODO Auto-generated method stub
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/	

	Handler xmlHandler = new Handler() { // 스레드를 위한 핸들러 생성
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				for(int i=0; i < result_id.size(); i++) {
					String DBtoday = result_expiration.elementAt(i);
					String DByear = null;
					String Dyear = null;
					String Dmon = null;
					String DBmon = null;
					String Dday = null;
					String DBday = null;
					//유통기한 알고리즘을 위한 변수 선언

					DByear = DBtoday.substring(0, 4);
					Dyear = today.substring(0, 4);
					Dmon = today.substring(4, 6);
					
					DBday = DBtoday.substring(8, 10);
					DBmon = DBtoday.substring(5, 7);
					Dday = today.substring(6, 8);
					//현재 db결과의 유통기한울 나누어서 저장
					
					int DBY = Integer.valueOf(DByear);
					int DBM = Integer.valueOf(DBmon);
					int DBD = Integer.valueOf(DBday);
					int DY = Integer.valueOf(Dyear);
					int DM = Integer.valueOf(Dmon);
					int DD = Integer.valueOf(Dday);
					//int로 형변환
				
					int check_quantity = Integer.valueOf(result_quantity.elementAt(i));
					if (check_quantity < 80) {
						result_alram_id.add(result_id.elementAt(i));
						result_alram_name.add(result_name.elementAt(i));
						result_alram_quantity.add(result_quantity.elementAt(i));
						result_alram_expiration.add(result_expiration.elementAt(i));
						//수량이 80개 이하일 경우엔 알람에 출력하기 위해 저장한다
					}
					else if ((DBY < DY) || (DBY == DY && DBM < DM)
							|| (DBY == DY && DBM == DM && DBD < DD)){
						result_alram_id.add(result_id.elementAt(i));
						result_alram_name.add(result_name.elementAt(i));
						result_alram_quantity.add(result_quantity.elementAt(i));
						result_alram_expiration.add(result_expiration.elementAt(i));
						//유통기한 제한조선에 따라 저장한다
					}
					else if ((DBY == DY && DBM > DM)
								|| (DBY == DY + 1 && (DBM - DM) == 0 && (DBD < DD))
								|| (DBY == DY + 1 && (DBM - DM) < 0)
								|| (DBY == DY && DBM == DM && DBD > DD)){
						result_alram_id.add(result_id.elementAt(i));
						result_alram_name.add(result_name.elementAt(i));
						result_alram_quantity.add(result_quantity.elementAt(i));
						result_alram_expiration.add(result_expiration.elementAt(i));
						
					}
					
				}
				for(int i=0; i < 5; i++) {
					txtView[i][1].setText(result_alram_id.elementAt(i));;
					txtView[i][2].setText(result_alram_name.elementAt(i));;
					txtView[i][3].setText(result_alram_quantity.elementAt(i));
					txtView[i][4].setText(result_alram_expiration.elementAt(i));;
					//알람결과를 출력해준다
				}
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
				
				URL url = new URL(SERVER_ADDRESS + "/alram_admin.php?");
				//db수행을 위한 php접속 경로
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/alram_adminresult.xml");
				//결과 xml파일을 저장한 경로
				InputStream in = server.openStream();
				xpp.setInput(in, "UTF-8");
				
				int eventType = xpp.getEventType(); // 이벤트 타입 얻어오기 예르들어 <node>
													// 인지 </node> 인지 구분하기 위한.
				int i=0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					ret="";
					//임시저장변수 ret를 초기화
					if (eventType == XmlPullParser.START_TAG) {
						tagname = xpp.getName();

					} else if (eventType == XmlPullParser.TEXT) {
						//결과 xml문서의 Text를 저장
						if (tagname.equals("id")) {
							ret = xpp.getText();
							if (i == 0) {
								result_id.add(ret);
							}
							else if (i % 2 == 0)
								result_id.add(ret);
							i++;
						}
						else if(tagname.equals("name")) {
							ret = xpp.getText();
							if (i == 0) {
								result_name.add(ret);
							}
							else if (i % 2 == 0)
								result_name.add(ret);
							i++;
						}
						else if(tagname.equals("quantity")) {
							ret = xpp.getText();
							if (i == 0) {
								result_quantity.add(ret);
							}
							else if (i % 2 == 0)
								result_quantity.add(ret);
							i++;
						}
						else if(tagname.equals("expiration")) {
							ret = xpp.getText();
							if (i == 0) {
								result_expiration.add(ret);
							}
							else if (i % 2 == 0)
								result_expiration.add(ret);
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
