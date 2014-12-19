/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	��ȸ�� �˻���� : �� �α��� �Һ����� �˻� ����� ���� ǰ���� �Է¹��� �� dbó�� �� ���					 */
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
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class nonlogin_search extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/		
	String key = "";
	String l_id[] = new String[9];
	Vector<String> result_id = new Vector<String>();
	Vector<String> result_name = new Vector<String>();
	Vector<String> result_price = new Vector<String>();
	Vector<String> result_quantity = new Vector<String>();
	Vector<String> result_l_id = new Vector<String>();
	
	ImageButton imgbtn[] = new ImageButton[9];
	TextView txtView[][] = new TextView[9][4];
	Button mapbtn[] = new Button[9];
	
	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	//������ ���� �ּ�
	String tagname, content;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nonlogin_search);

		Intent intent = getIntent();
		key = intent.getExtras().getString("key");
		// key�� �޴ºκ�
		
		Button button = (Button) findViewById(R.id.mainbtn);
		Button button2 = (Button) findViewById(R.id.joinbtn);
		Button button3 = (Button) findViewById(R.id.searchbtn);
		final EditText edittext = (EditText) findViewById(R.id.nonsearchText);
		edittext.setText(key);

		imgbtn[0] = (ImageButton) findViewById(R.id.imbtn0);
		txtView[0][1] = (TextView) findViewById(R.id.txtView0_1);
		txtView[0][2] = (TextView) findViewById(R.id.txtView0_2);
		txtView[0][3] = (TextView) findViewById(R.id.txtView0_3);
		mapbtn[0] = (Button) findViewById(R.id.mapbtn0);

		imgbtn[1] = (ImageButton) findViewById(R.id.imbtn1);
		txtView[1][1] = (TextView) findViewById(R.id.txtView1_1);
		txtView[1][2] = (TextView) findViewById(R.id.txtView1_2);
		txtView[1][3] = (TextView) findViewById(R.id.txtView1_3);
		mapbtn[1] = (Button) findViewById(R.id.mapbtn1);

		imgbtn[2] = (ImageButton) findViewById(R.id.imbtn2);
		txtView[2][1] = (TextView) findViewById(R.id.txtView2_1);
		txtView[2][2] = (TextView) findViewById(R.id.txtView2_2);
		txtView[2][3] = (TextView) findViewById(R.id.txtView2_3);
		mapbtn[2] = (Button) findViewById(R.id.mapbtn2);

		imgbtn[3] = (ImageButton) findViewById(R.id.imbtn3);
		txtView[3][1] = (TextView) findViewById(R.id.txtView3_1);
		txtView[3][2] = (TextView) findViewById(R.id.txtView3_2);
		txtView[3][3] = (TextView) findViewById(R.id.txtView3_3);
		mapbtn[3] = (Button) findViewById(R.id.mapbtn3);

		imgbtn[4] = (ImageButton) findViewById(R.id.imbtn4);
		txtView[4][1] = (TextView) findViewById(R.id.txtView4_1);
		txtView[4][2] = (TextView) findViewById(R.id.txtView4_2);
		txtView[4][3] = (TextView) findViewById(R.id.txtView4_3);
		mapbtn[4] = (Button) findViewById(R.id.mapbtn4);

		imgbtn[5] = (ImageButton) findViewById(R.id.imbtn5);
		txtView[5][1] = (TextView) findViewById(R.id.txtView5_1);
		txtView[5][2] = (TextView) findViewById(R.id.txtView5_2);
		txtView[5][3] = (TextView) findViewById(R.id.txtView5_3);
		mapbtn[5] = (Button) findViewById(R.id.mapbtn5);

		imgbtn[6] = (ImageButton) findViewById(R.id.imbtn6);
		txtView[6][1] = (TextView) findViewById(R.id.txtView6_1);
		txtView[6][2] = (TextView) findViewById(R.id.txtView6_2);
		txtView[6][3] = (TextView) findViewById(R.id.txtView6_3);
		mapbtn[6] = (Button) findViewById(R.id.mapbtn6);

		imgbtn[7] = (ImageButton) findViewById(R.id.imbtn7);
		txtView[7][1] = (TextView) findViewById(R.id.txtView7_1);
		txtView[7][2] = (TextView) findViewById(R.id.txtView7_2);
		txtView[7][3] = (TextView) findViewById(R.id.txtView7_3);
		mapbtn[7] = (Button) findViewById(R.id.mapbtn7);

		imgbtn[8] = (ImageButton) findViewById(R.id.imbtn8);
		txtView[8][1] = (TextView) findViewById(R.id.txtView8_1);
		txtView[8][2] = (TextView) findViewById(R.id.txtView8_2);
		txtView[8][3] = (TextView) findViewById(R.id.txtView8_3);
		mapbtn[8] = (Button) findViewById(R.id.mapbtn8);
		//form�� ����ϱ� ���� ���� ���� (id�� ���еǱ� ������ �ٸ������� �� �� ��� �ϳ��� �������־���Ѵ�)
		

		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//page���� �� Thread start

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(nonlogin_search.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
		//����ȭ�� ��ư Ŭ���� ����

		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(nonlogin_search.this, join.class);
				startActivity(intent);
			}
		});
		//ȸ������ ��ư Ŭ���� ����

		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String re_key = edittext.getText().toString();
				Intent intent = new Intent(nonlogin_search.this,
						nonlogin_search.class);
				intent.putExtra("key", re_key);
				startActivity(intent);
			}
		});
		//��˻� ����� ���� ����
		
		// TODO Auto-generated method stub
	}

/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 					����ڰ� �Է��� name�� �˻� �� form�� �°� ������ش� 				 		 */
/*****************************************************************************/	
	Handler xmlHandler = new Handler() { // �����带 ���� �ڵ鷯 ����
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				for(int i=0; i < result_id.size(); i++) {
					txtView[i][1].setText("ǰ��: "+result_name.elementAt(i));
					txtView[i][2].setText("����: "+result_price.elementAt(i));
					txtView[i][3].setText("���: "+result_quantity.elementAt(i));
					mapbtn[i].setText("�൵����");
					mapbtn[i].setTag(i);
					l_id[i] = result_l_id.elementAt(i);
					
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
					//�̹�������� ���� �˻� �� �̹�������
				}
				for(int i=0; i<result_id.size(); i++) {
					mapbtn[i].setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							int tag = (Integer) v.getTag();
							String new_l_id = l_id[tag];
							Intent intent = new Intent(nonlogin_search.this, map.class);
							//�൵��ġ �ٲٱ�
							intent.putExtra("l_id", new_l_id);
							startActivity(intent);
						}
					});
				}
				//�൵���� ��ư Ŭ���� ����
			}
		}
	};
	
	class xmlThread extends Thread {
		public void run() {
			try {
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance(); // xmlPullparser�� ���� �غ����.
				factory.setNamespaceAware(true); // xml�� �������̽� ���
				XmlPullParser xpp = factory.newPullParser();
				
				URL url = new URL(SERVER_ADDRESS + "/search.php?"+"name="
						+ URLEncoder.encode(key, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/searchresult.xml");
				InputStream in = server.openStream();
				xpp.setInput(in, "UTF-8");
				
				int eventType = xpp.getEventType(); // �̺�Ʈ Ÿ�� ������ ������� <node>
													// ���� </node> ���� �����ϱ� ����.
				int i=0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					ret="";
					//�ӽ����庯�� ret�� �ʱ�ȭ
					if (eventType == XmlPullParser.START_TAG) {
						tagname = xpp.getName();
					} else if (eventType == XmlPullParser.TEXT) {
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
						else if(tagname.equals("price")) {
							ret = xpp.getText();
							if (i == 0) {
								result_price.add(ret);
							}
							else if (i % 2 == 0)
								result_price.add(ret);
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
						else if(tagname.equals("l_id")) {
							ret = xpp.getText();
							if (i == 0) {
								result_l_id.add(ret);
							}
							else if (i % 2 == 0)
								result_l_id.add(ret);
							i++;
						}
					}
					eventType = xpp.next();
				}
			} catch (Exception e) {
				e.getMessage(); // ���� üũ

			} finally {
				xmlHandler.sendEmptyMessage(0); // �޽��� ����
			}
		}
	}
}
