/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	회원 검색기능 : 로그인된 소비자의 검색 기능을 위해 품명을 입력받은 후 db처리 및 출력					 */
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
import android.widget.ImageButton;
import android.widget.TextView;


@SuppressLint("HandlerLeak")
public class login_search extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/	
	String receive_id = "";
	String receive_name = "";
	String send_id = "";
	String send_name = "";
	String key = "";
	
	String[] l_id = new String[9];
	String[] sid = new String[9];

	Vector<String> result_id = new Vector<String>();
	Vector<String> result_name = new Vector<String>();
	Vector<String> result_price = new Vector<String>();
	Vector<String> result_quantity = new Vector<String>();
	Vector<String> result_l_id = new Vector<String>();

	ImageButton imgbtn[] = new ImageButton[9];
	TextView txtView[][] = new TextView[9][4];
	EditText txtEdit[] = new EditText[9];
	Button insert_bag[] = new Button[9];
	Button mapbtn[] = new Button[9];

	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	//서버접속 주소
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_search);

		Intent intent = getIntent();
		receive_id = intent.getExtras().getString("userid");
		receive_name = intent.getExtras().getString("username");
		send_id = receive_id;
		send_name = receive_name;
		key = intent.getExtras().getString("key");
		//key를 받아온다

		Button go_bag = (Button) findViewById(R.id.button1);
		Button re_search = (Button) findViewById(R.id.button4);
		Button go_logout = (Button) findViewById(R.id.button2);
		Button secession = (Button) findViewById(R.id.button3);

		TextView txt_username = (TextView) findViewById(R.id.textView1);
		final EditText txt_search = (EditText) findViewById(R.id.searchtxt);
		// 최상단 textview
		txt_username.setText(receive_name + "님 환영합니다");
		txt_search.setText(key);
		//검색창에 받은 key를 setting한다

		imgbtn[0] = (ImageButton) findViewById(R.id.imbtn0);
		txtView[0][1] = (TextView) findViewById(R.id.txtView0_1);
		txtView[0][2] = (TextView) findViewById(R.id.txtView0_2);
		txtView[0][3] = (TextView) findViewById(R.id.txtView0_3);
		txtEdit[0] = (EditText) findViewById(R.id.editText0);
		insert_bag[0] = (Button) findViewById(R.id.insert_bag0);
		mapbtn[0] = (Button) findViewById(R.id.mapbtn0);

		imgbtn[1] = (ImageButton) findViewById(R.id.imbtn1);
		txtView[1][1] = (TextView) findViewById(R.id.txtView1_1);
		txtView[1][2] = (TextView) findViewById(R.id.txtView1_2);
		txtView[1][3] = (TextView) findViewById(R.id.txtView1_3);
		txtEdit[1] = (EditText) findViewById(R.id.editText1);
		insert_bag[1] = (Button) findViewById(R.id.insert_bag1);
		mapbtn[1] = (Button) findViewById(R.id.mapbtn1);

		imgbtn[2] = (ImageButton) findViewById(R.id.imbtn2);
		txtView[2][1] = (TextView) findViewById(R.id.txtView2_1);
		txtView[2][2] = (TextView) findViewById(R.id.txtView2_2);
		txtView[2][3] = (TextView) findViewById(R.id.txtView2_3);
		txtEdit[2] = (EditText) findViewById(R.id.editText2);
		insert_bag[2] = (Button) findViewById(R.id.insert_bag2);
		mapbtn[2] = (Button) findViewById(R.id.mapbtn2);

		imgbtn[3] = (ImageButton) findViewById(R.id.imbtn3);
		txtView[3][1] = (TextView) findViewById(R.id.txtView3_1);
		txtView[3][2] = (TextView) findViewById(R.id.txtView3_2);
		txtView[3][3] = (TextView) findViewById(R.id.txtView3_3);
		txtEdit[3] = (EditText) findViewById(R.id.editText3);
		insert_bag[3] = (Button) findViewById(R.id.insert_bag3);
		mapbtn[3] = (Button) findViewById(R.id.mapbtn3);

		imgbtn[4] = (ImageButton) findViewById(R.id.imbtn4);
		txtView[4][1] = (TextView) findViewById(R.id.txtView4_1);
		txtView[4][2] = (TextView) findViewById(R.id.txtView4_2);
		txtView[4][3] = (TextView) findViewById(R.id.txtView4_3);
		txtEdit[4] = (EditText) findViewById(R.id.editText4);
		insert_bag[4] = (Button) findViewById(R.id.insert_bag4);
		mapbtn[4] = (Button) findViewById(R.id.mapbtn4);

		imgbtn[5] = (ImageButton) findViewById(R.id.imbtn5);
		txtView[5][1] = (TextView) findViewById(R.id.txtView5_1);
		txtView[5][2] = (TextView) findViewById(R.id.txtView5_2);
		txtView[5][3] = (TextView) findViewById(R.id.txtView5_3);
		txtEdit[5] = (EditText) findViewById(R.id.editText5);
		insert_bag[5] = (Button) findViewById(R.id.insert_bag5);
		mapbtn[5] = (Button) findViewById(R.id.mapbtn5);

		imgbtn[6] = (ImageButton) findViewById(R.id.imbtn6);
		txtView[6][1] = (TextView) findViewById(R.id.txtView6_1);
		txtView[6][2] = (TextView) findViewById(R.id.txtView6_2);
		txtView[6][3] = (TextView) findViewById(R.id.txtView6_3);
		txtEdit[6] = (EditText) findViewById(R.id.editText6);
		insert_bag[6] = (Button) findViewById(R.id.insert_bag6);
		mapbtn[6] = (Button) findViewById(R.id.mapbtn6);

		imgbtn[7] = (ImageButton) findViewById(R.id.imbtn7);
		txtView[7][1] = (TextView) findViewById(R.id.txtView7_1);
		txtView[7][2] = (TextView) findViewById(R.id.txtView7_2);
		txtView[7][3] = (TextView) findViewById(R.id.txtView7_3);
		txtEdit[7] = (EditText) findViewById(R.id.editText7);
		insert_bag[7] = (Button) findViewById(R.id.insert_bag7);
		mapbtn[7] = (Button) findViewById(R.id.mapbtn7);

		imgbtn[8] = (ImageButton) findViewById(R.id.imbtn8);
		txtView[8][1] = (TextView) findViewById(R.id.txtView8_1);
		txtView[8][2] = (TextView) findViewById(R.id.txtView8_2);
		txtView[8][3] = (TextView) findViewById(R.id.txtView8_3);
		txtEdit[8] = (EditText) findViewById(R.id.editText8);
		insert_bag[8] = (Button) findViewById(R.id.insert_bag8);
		mapbtn[8] = (Button) findViewById(R.id.mapbtn8);
		//form에 출력하기 위한 변수 선언 (id로 구분되기 때문에 다른변수를 쓸 수 없어서 하나씩 지정해주어야한다)

		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//page접속 시 Thread start

		go_bag.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(login_search.this, bag.class);
				intent.putExtra("userid", send_id);
				intent.putExtra("username", send_name);
				startActivity(intent);
			}
		});
		//장바구니버튼 클릭 시 동작
		
	    go_logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				send_id = receive_id;
				Intent intent = new Intent(login_search.this, logout.class);
				intent.putExtra("userid", send_id);
				startActivity(intent);
			}
		});
	    //로그아웃버튼 클릭 시 동작

		re_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String re_key = txt_search.getText().toString();
				Intent intent = new Intent(login_search.this,
						login_search.class);
				intent.putExtra("key", re_key);
				startActivity(intent);
			}
		});
		//재검색을 위한 동작
		
	    secession.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(login_search.this, secession.class);
				startActivity(intent);
			}
		});
	    //회원탈퇴 버튼의 동작

		// TODO Auto-generated method stub
	}

/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 					사용자가 입력한 name을 검색 후 form에 맞게 출력해준다 				 		 */
/*****************************************************************************/	
	Handler xmlHandler = new Handler() { // 스레드를 위한 핸들러 생성
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				for (int i = 0; i < result_id.size(); i++) {
					txtView[i][1].setText("품명: " + result_name.elementAt(i));
					txtView[i][2].setText("가격: " + result_price.elementAt(i));
					txtView[i][3].setText("재고: " + result_quantity.elementAt(i));
					insert_bag[i].setText("담기");
					insert_bag[i].setTag(i);
					mapbtn[i].setText("약도보기");
					mapbtn[i].setTag(i);
					l_id[i] = result_l_id.elementAt(i);
					sid[i] = result_id.elementAt(i);
					
					if (result_id.elementAt(i).equals("00001")) {
						imgbtn[i].setImageResource(R.drawable.s00001);
					} else if (result_id.elementAt(i).equals("00002")) {
						imgbtn[i].setImageResource(R.drawable.s00002);
					} else if (result_id.elementAt(i).equals("00003")) {
						imgbtn[i].setImageResource(R.drawable.s00003);
					} else if (result_id.elementAt(i).equals("00004")) {
						imgbtn[i].setImageResource(R.drawable.s00004);
					} else if (result_id.elementAt(i).equals("00005")) {
						imgbtn[i].setImageResource(R.drawable.s00005);
					} else if (result_id.elementAt(i).equals("00006")) {
						imgbtn[i].setImageResource(R.drawable.s00006);
					} else if (result_id.elementAt(i).equals("00007")) {
						imgbtn[i].setImageResource(R.drawable.s00007);
					} else if (result_id.elementAt(i).equals("00008")) {
						imgbtn[i].setImageResource(R.drawable.s00008);
					} else if (result_id.elementAt(i).equals("00009")) {
						imgbtn[i].setImageResource(R.drawable.s00009);
					} else if (result_id.elementAt(i).equals("00010")) {
						imgbtn[i].setImageResource(R.drawable.s00010);
					} else if (result_id.elementAt(i).equals("00011")) {
						imgbtn[i].setImageResource(R.drawable.s00011);
					} else if (result_id.elementAt(i).equals("00012")) {
						imgbtn[i].setImageResource(R.drawable.s00012);
					} else if (result_id.elementAt(i).equals("00013")) {
						imgbtn[i].setImageResource(R.drawable.s00013);
					} else if (result_id.elementAt(i).equals("00014")) {
						imgbtn[i].setImageResource(R.drawable.s00014);
					} else if (result_id.elementAt(i).equals("00015")) {
						imgbtn[i].setImageResource(R.drawable.s00015);
					} else if (result_id.elementAt(i).equals("00016")) {
						imgbtn[i].setImageResource(R.drawable.s00016);
					} else if (result_id.elementAt(i).equals("00017")) {
						imgbtn[i].setImageResource(R.drawable.s00017);
					} else if (result_id.elementAt(i).equals("00018")) {
						imgbtn[i].setImageResource(R.drawable.s00018);
					} else if (result_id.elementAt(i).equals("00019")) {
						imgbtn[i].setImageResource(R.drawable.s00019);
					} else if (result_id.elementAt(i).equals("00020")) {
						imgbtn[i].setImageResource(R.drawable.s00020);
					}  else if (result_id.elementAt(i).equals("00021")) {
						imgbtn[i].setImageResource(R.drawable.s00021);
					}
					//물품id에 따른 이미지표시를 위한 동작내용
				}
				for(int i=0; i<result_id.size(); i++) {
					mapbtn[i].setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							int tag = (Integer) v.getTag();
							String new_l_id = l_id[tag];
							
							Intent intent = new Intent(login_search.this,
									map.class);
							//map화면에서 다시 로그인검색페이지로 넘어간다.
							intent.putExtra("l_id", new_l_id);
							startActivity(intent);
						}
					});
				}
				for(int i=0; i<result_id.size(); i++) {
					
					insert_bag[i].setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							String quantity="";
							String newsid="";
							int tag = (Integer) v.getTag();
							
							quantity = txtEdit[tag].getText().toString();
							
							Intent intent = new Intent(login_search.this, insert_bag.class);
							intent.putExtra("userid", send_id);
							intent.putExtra("username", send_name);
							//장바구니추가 확인버튼 누르면 다시 장바구니 페이지로 넘어오기 위해서
							newsid=sid[tag];
							intent.putExtra("sid", newsid);
							intent.putExtra("quantity", quantity);
							startActivity(intent);
						}
					});
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

				URL url = new URL(SERVER_ADDRESS + "/search.php?" + "name="
						+ URLEncoder.encode(key, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/searchresult.xml");
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
								result_id.add(ret);
							} else if (i % 2 == 0)
								result_id.add(ret);
							i++;
						} else if (tagname.equals("name")) {
							ret = xpp.getText();
							if (i == 0) {
								result_name.add(ret);
							} else if (i % 2 == 0)
								result_name.add(ret);
							i++;
						} else if (tagname.equals("price")) {
							ret = xpp.getText();
							if (i == 0) {
								result_price.add(ret);
							} else if (i % 2 == 0)
								result_price.add(ret);
							i++;
						} else if (tagname.equals("quantity")) {
							ret = xpp.getText();
							if (i == 0) {
								result_quantity.add(ret);
							} else if (i % 2 == 0)
								result_quantity.add(ret);
							i++;
						} else if (tagname.equals("l_id")) {
							ret = xpp.getText();
							if (i == 0) {
								result_l_id.add(ret);
							} else if (i % 2 == 0)
								result_l_id.add(ret);
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