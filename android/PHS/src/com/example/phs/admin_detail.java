/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	�����ڿ� ��ǰ�˻��� �����ϱ� ���� db���� �� ���: stuff�� ��������� ������ش�.    	     		 */
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
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class admin_detail extends Activity {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/

	String key = "";
	
	Vector<String> result_id = new Vector<String>();
	Vector<String> result_name = new Vector<String>();
	Vector<String> result_kind = new Vector<String>();
	Vector<String> result_price = new Vector<String>();
	Vector<String> result_cost = new Vector<String>();
	Vector<String> result_quantity = new Vector<String>();
	Vector<String> result_stock = new Vector<String>();
	Vector<String> result_date = new Vector<String>();
	Vector<String> result_expiration = new Vector<String>();
	Vector<String> result_l_id = new Vector<String>();
	
	TextView txtView[][] = new TextView[9][11];
	
	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	String tagname, content;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_detail);
		// TODO Auto-generated method stub
		
		Intent intent = getIntent();
		key = intent.getExtras().getString("key");
		
		final EditText edittext = (EditText) findViewById(R.id.editText1);
		edittext.setText(key);
		//�˻�â�� �Է��� key�� ��� ������ش�

		Button button = (Button) findViewById(R.id.searchbtn);
		Button go_main = (Button) findViewById(R.id.button1);
		Button logout = (Button) findViewById(R.id.button2);
		
		txtView[0][1] = (TextView) findViewById(R.id.txtView0_1);
		txtView[0][2] = (TextView) findViewById(R.id.txtView0_2);
		txtView[0][3] = (TextView) findViewById(R.id.txtView0_3);
		txtView[0][4] = (TextView) findViewById(R.id.txtView0_4);
		txtView[0][5] = (TextView) findViewById(R.id.txtView0_5);
		txtView[0][6] = (TextView) findViewById(R.id.txtView0_6);
		txtView[0][7] = (TextView) findViewById(R.id.txtView0_7);
		txtView[0][8] = (TextView) findViewById(R.id.txtView0_8);
		txtView[0][9] = (TextView) findViewById(R.id.txtView0_9);
		txtView[0][10] = (TextView) findViewById(R.id.txtView0_10);
		
		txtView[1][1] = (TextView) findViewById(R.id.txtView1_1);
		txtView[1][2] = (TextView) findViewById(R.id.txtView1_2);
		txtView[1][3] = (TextView) findViewById(R.id.txtView1_3);
		txtView[1][4] = (TextView) findViewById(R.id.txtView1_4);
		txtView[1][5] = (TextView) findViewById(R.id.txtView1_5);
		txtView[1][6] = (TextView) findViewById(R.id.txtView1_6);
		txtView[1][7] = (TextView) findViewById(R.id.txtView1_7);
		txtView[1][8] = (TextView) findViewById(R.id.txtView1_8);
		txtView[1][9] = (TextView) findViewById(R.id.txtView1_9);
		txtView[1][10] = (TextView) findViewById(R.id.txtView1_10);
		
		txtView[2][1] = (TextView) findViewById(R.id.txtView2_1);
		txtView[2][2] = (TextView) findViewById(R.id.txtView2_2);
		txtView[2][3] = (TextView) findViewById(R.id.txtView2_3);
		txtView[2][4] = (TextView) findViewById(R.id.txtView2_4);
		txtView[2][5] = (TextView) findViewById(R.id.txtView2_5);
		txtView[2][6] = (TextView) findViewById(R.id.txtView2_6);
		txtView[2][7] = (TextView) findViewById(R.id.txtView2_7);
		txtView[2][8] = (TextView) findViewById(R.id.txtView2_8);
		txtView[2][9] = (TextView) findViewById(R.id.txtView2_9);
		txtView[2][10] = (TextView) findViewById(R.id.txtView2_10);
		
		txtView[3][1] = (TextView) findViewById(R.id.txtView3_1);
		txtView[3][2] = (TextView) findViewById(R.id.txtView3_2);
		txtView[3][3] = (TextView) findViewById(R.id.txtView3_3);
		txtView[3][4] = (TextView) findViewById(R.id.txtView3_4);
		txtView[3][5] = (TextView) findViewById(R.id.txtView3_5);
		txtView[3][6] = (TextView) findViewById(R.id.txtView3_6);
		txtView[3][7] = (TextView) findViewById(R.id.txtView3_7);
		txtView[3][8] = (TextView) findViewById(R.id.txtView3_8);
		txtView[3][9] = (TextView) findViewById(R.id.txtView3_9);
		txtView[3][10] = (TextView) findViewById(R.id.txtView3_10);
		
		txtView[4][1] = (TextView) findViewById(R.id.txtView4_1);
		txtView[4][2] = (TextView) findViewById(R.id.txtView4_2);
		txtView[4][3] = (TextView) findViewById(R.id.txtView4_3);
		txtView[4][4] = (TextView) findViewById(R.id.txtView4_4);
		txtView[4][5] = (TextView) findViewById(R.id.txtView4_5);
		txtView[4][6] = (TextView) findViewById(R.id.txtView4_6);
		txtView[4][7] = (TextView) findViewById(R.id.txtView4_7);
		txtView[4][8] = (TextView) findViewById(R.id.txtView4_8);
		txtView[4][9] = (TextView) findViewById(R.id.txtView4_9);
		txtView[4][10] = (TextView) findViewById(R.id.txtView4_10);
	
		txtView[5][1] = (TextView) findViewById(R.id.txtView5_1);
		txtView[5][2] = (TextView) findViewById(R.id.txtView5_2);
		txtView[5][3] = (TextView) findViewById(R.id.txtView5_3);
		txtView[5][4] = (TextView) findViewById(R.id.txtView5_4);
		txtView[5][5] = (TextView) findViewById(R.id.txtView5_5);
		txtView[5][6] = (TextView) findViewById(R.id.txtView5_6);
		txtView[5][7] = (TextView) findViewById(R.id.txtView5_7);
		txtView[5][8] = (TextView) findViewById(R.id.txtView5_8);
		txtView[5][9] = (TextView) findViewById(R.id.txtView5_9);
		txtView[5][10] = (TextView) findViewById(R.id.txtView5_10);
		
		txtView[6][1] = (TextView) findViewById(R.id.txtView6_1);
		txtView[6][2] = (TextView) findViewById(R.id.txtView6_2);
		txtView[6][3] = (TextView) findViewById(R.id.txtView6_3);
		txtView[6][4] = (TextView) findViewById(R.id.txtView6_4);
		txtView[6][5] = (TextView) findViewById(R.id.txtView6_5);
		txtView[6][6] = (TextView) findViewById(R.id.txtView6_6);
		txtView[6][7] = (TextView) findViewById(R.id.txtView6_7);
		txtView[6][8] = (TextView) findViewById(R.id.txtView6_8);
		txtView[6][9] = (TextView) findViewById(R.id.txtView6_9);
		txtView[6][10] = (TextView) findViewById(R.id.txtView6_10);
		
		txtView[7][1] = (TextView) findViewById(R.id.txtView7_1);
		txtView[7][2] = (TextView) findViewById(R.id.txtView7_2);
		txtView[7][3] = (TextView) findViewById(R.id.txtView7_3);
		txtView[7][4] = (TextView) findViewById(R.id.txtView7_4);
		txtView[7][5] = (TextView) findViewById(R.id.txtView7_5);
		txtView[7][6] = (TextView) findViewById(R.id.txtView7_6);
		txtView[7][7] = (TextView) findViewById(R.id.txtView7_7);
		txtView[7][8] = (TextView) findViewById(R.id.txtView7_8);
		txtView[7][9] = (TextView) findViewById(R.id.txtView7_9);
		txtView[7][10] = (TextView) findViewById(R.id.txtView7_10);
		
		txtView[8][1] = (TextView) findViewById(R.id.txtView8_1);
		txtView[8][2] = (TextView) findViewById(R.id.txtView8_2);
		txtView[8][3] = (TextView) findViewById(R.id.txtView8_3);
		txtView[8][4] = (TextView) findViewById(R.id.txtView8_4);
		txtView[8][5] = (TextView) findViewById(R.id.txtView8_5);
		txtView[8][6] = (TextView) findViewById(R.id.txtView8_6);
		txtView[8][7] = (TextView) findViewById(R.id.txtView8_7);
		txtView[8][8] = (TextView) findViewById(R.id.txtView8_8);
		txtView[8][9] = (TextView) findViewById(R.id.txtView8_9);
		txtView[8][10] = (TextView) findViewById(R.id.txtView8_10);

		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//�˻�â�� �����ϸ� �ٷ� db���� �� ����� ���� Thread�� �����Ѵ�.
		
		go_main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin_detail.this, MainActivity.class);
				startActivity(intent);
			}
		});
		//����ȭ������ �������� ��ưŬ�� �� ���۳���
		
		logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin_detail.this, admin_logout.class);
				startActivity(intent);
			}
		});
		//������ logout��ư Ŭ�� �� ���۳���

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String key = edittext.getText().toString();
				Intent intent = new Intent(admin_detail.this, admin_detail.class);
				intent.putExtra("key", key);
				startActivity(intent);
			}
		});
		//������ �˻� page���� ��˻� �� ���� ����
	}

	Handler xmlHandler = new Handler() { // �����带 ���� �ڵ鷯 ����
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				for(int i=0; i < result_id.size(); i++) {
					txtView[i][1].setText(result_id.elementAt(i));
					txtView[i][2].setText(result_name.elementAt(i));
					txtView[i][3].setText(result_kind.elementAt(i));
					txtView[i][4].setText(result_price.elementAt(i));
					txtView[i][5].setText(result_cost.elementAt(i));
					txtView[i][6].setText(result_quantity.elementAt(i));
					txtView[i][7].setText(result_stock.elementAt(i));
					txtView[i][8].setText(result_date.elementAt(i));
					txtView[i][9].setText(result_expiration.elementAt(i));
					txtView[i][10].setText(result_l_id.elementAt(i));
					//stuff�� ��� ������ ������ش�
				}
				
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
				
				URL url = new URL(SERVER_ADDRESS + "/search_admin.php?"+"name="
						+ URLEncoder.encode(key, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/search_adminresult.xml");
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
						else if(tagname.equals("kind")) {
							ret = xpp.getText();
							if (i == 0) {
								result_kind.add(ret);
							}
							else if (i % 2 == 0)
								result_kind.add(ret);
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
						else if(tagname.equals("cost")) {
							ret = xpp.getText();
							if (i == 0) {
								result_cost.add(ret);
							}
							else if (i % 2 == 0)
								result_cost.add(ret);
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
						else if(tagname.equals("stock")) {
							ret = xpp.getText();
							if (i == 0) {
								result_stock.add(ret);
							}
							else if (i % 2 == 0)
								result_stock.add(ret);
							i++;
						}
						else if(tagname.equals("date")) {
							ret = xpp.getText();
							if (i == 0) {
								result_date.add(ret);
							}
							else if (i % 2 == 0)
								result_date.add(ret);
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
