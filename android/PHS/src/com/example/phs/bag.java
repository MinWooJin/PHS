/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	��ٱ��� ���� �� db���� : ������� id,name�� �Է¹޾� db bag�� ���� �� ���	   	     		 */
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
public class bag extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/	
	
	String receive_id = "";
	String receive_name = "";
	String send_id = "";
	String send_name = "";

	String[] l_id = new String[9];
	String[] sid = new String[9];

	Vector<String> result_id = new Vector<String>();
	Vector<String> result_name = new Vector<String>();
	Vector<String> result_price = new Vector<String>();
	Vector<String> result_quantity = new Vector<String>();
	Vector<String> result_l_id = new Vector<String>();
	Vector<String> result_total = new Vector<String>();

	ImageButton imgbtn[] = new ImageButton[6];
	TextView txtView[][] = new TextView[6][4];
	EditText txtEdit[] = new EditText[6];
	Button update_bag[] = new Button[6];
	Button mapbtn[] = new Button[6];
	Button delete_bag[] = new Button[6];
	//����� ���� �� ����

	String ret = "";
	int cost = 0;
	//���հ踦 ���ϱ� ���� ����
	private final String SERVER_ADDRESS = "  ";
	//������ �ּ�
	String tagname, content;

	TextView txt_total;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bag);

		Intent intent = getIntent();
		receive_id = intent.getExtras().getString("userid");
		receive_name = intent.getExtras().getString("username");
		send_id = receive_id;
		send_name=receive_name;
		//���� id, name�� ��˻�, �α׾ƿ� �� �ٽ� ���������ش�
		TextView txt_username = (TextView) findViewById(R.id.textView1);
		txt_total = (TextView) findViewById(R.id.textView2);
		txt_username.setText(receive_name + "�� ȯ���մϴ�");
		//������� �̸��� ������ش�

		Button button = (Button) findViewById(R.id.searchbtn);
		Button go_logout = (Button) findViewById(R.id.button2);
		Button secession = (Button) findViewById(R.id.button3);
		final EditText edittext = (EditText) findViewById(R.id.searchTxt);

		imgbtn[0] = (ImageButton) findViewById(R.id.imbtn0);
		txtView[0][1] = (TextView) findViewById(R.id.txtView0_1);
		txtView[0][2] = (TextView) findViewById(R.id.txtView0_2);
		txtView[0][3] = (TextView) findViewById(R.id.txtView0_3);
		txtEdit[0] = (EditText) findViewById(R.id.editText0);
		update_bag[0] = (Button) findViewById(R.id.update_bag0);
		mapbtn[0] = (Button) findViewById(R.id.mapbtn0);
		delete_bag[0] = (Button) findViewById(R.id.delete_bag0);

		imgbtn[1] = (ImageButton) findViewById(R.id.imbtn1);
		txtView[1][1] = (TextView) findViewById(R.id.txtView1_1);
		txtView[1][2] = (TextView) findViewById(R.id.txtView1_2);
		txtView[1][3] = (TextView) findViewById(R.id.txtView1_3);
		txtEdit[1] = (EditText) findViewById(R.id.editText1);
		update_bag[1] = (Button) findViewById(R.id.update_bag1);
		mapbtn[1] = (Button) findViewById(R.id.mapbtn1);
		delete_bag[1] = (Button) findViewById(R.id.delete_bag1);

		imgbtn[2] = (ImageButton) findViewById(R.id.imbtn2);
		txtView[2][1] = (TextView) findViewById(R.id.txtView2_1);
		txtView[2][2] = (TextView) findViewById(R.id.txtView2_2);
		txtView[2][3] = (TextView) findViewById(R.id.txtView2_3);
		txtEdit[2] = (EditText) findViewById(R.id.editText2);
		update_bag[2] = (Button) findViewById(R.id.update_bag2);
		mapbtn[2] = (Button) findViewById(R.id.mapbtn2);
		delete_bag[2] = (Button) findViewById(R.id.delete_bag2);

		imgbtn[3] = (ImageButton) findViewById(R.id.imbtn3);
		txtView[3][1] = (TextView) findViewById(R.id.txtView3_1);
		txtView[3][2] = (TextView) findViewById(R.id.txtView3_2);
		txtView[3][3] = (TextView) findViewById(R.id.txtView3_3);
		txtEdit[3] = (EditText) findViewById(R.id.editText3);
		update_bag[3] = (Button) findViewById(R.id.update_bag3);
		mapbtn[3] = (Button) findViewById(R.id.mapbtn3);
		delete_bag[3] = (Button) findViewById(R.id.delete_bag3);

		imgbtn[4] = (ImageButton) findViewById(R.id.imbtn4);
		txtView[4][1] = (TextView) findViewById(R.id.txtView4_1);
		txtView[4][2] = (TextView) findViewById(R.id.txtView4_2);
		txtView[4][3] = (TextView) findViewById(R.id.txtView4_3);
		txtEdit[4] = (EditText) findViewById(R.id.editText4);
		update_bag[4] = (Button) findViewById(R.id.update_bag4);
		mapbtn[4] = (Button) findViewById(R.id.mapbtn4);
		delete_bag[4] = (Button) findViewById(R.id.delete_bag4);

		imgbtn[5] = (ImageButton) findViewById(R.id.imbtn5);
		txtView[5][1] = (TextView) findViewById(R.id.txtView5_1);
		txtView[5][2] = (TextView) findViewById(R.id.txtView5_2);
		txtView[5][3] = (TextView) findViewById(R.id.txtView5_3);
		txtEdit[5] = (EditText) findViewById(R.id.editText5);
		update_bag[5] = (Button) findViewById(R.id.update_bag5);
		mapbtn[5] = (Button) findViewById(R.id.mapbtn5);
		delete_bag[5] = (Button) findViewById(R.id.delete_bag5);
		//��ٱ��� ����� ���� ���� form�� id���� �� ����

		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//��񱸴� ���� �� Thread ����

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(bag.this, login_search.class);
				String key = edittext.getText().toString();
				intent.putExtra("userid", send_id);
				intent.putExtra("username", send_name);
				intent.putExtra("key", key);
				startActivity(intent);
			}
		});
		//��˻� �� id, name, �˻� key�� ������ �Ѵ�

		go_logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(bag.this, logout.class);
				intent.putExtra("userid", send_id);
				startActivity(intent);
			}
		});
		//�α׾ƿ� �� id�� �����Ѵ�

		secession.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(bag.this, secession.class);
				startActivity(intent);
			}
		});
		//ȸ��Ż�� ��ư Ŭ�� �� ����

		// TODO Auto-generated method stub
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 	db���� �� ����� �������� ����ŭ form�� �������ϰ� ������ ��ư�� ������ ������ �� �ֵ��� �Ѵ�.			 */
/*****************************************************************************/
	Handler xmlHandler = new Handler() { // �����带 ���� �ڵ鷯 ����
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				for (int i = 0; i < result_id.size(); i++) {
					txtView[i][1].setText("ǰ��: " + result_name.elementAt(i));
					txtView[i][2].setText("����: " + result_price.elementAt(i));
					txtView[i][3].setText("����: " + result_quantity.elementAt(i));
					update_bag[i].setText("����");
					update_bag[i].setTag(i);
					mapbtn[i].setText("�൵����");
					mapbtn[i].setTag(i);
					delete_bag[i].setText("����");
					delete_bag[i].setTag(i);
					cost = cost + Integer.valueOf(result_total.elementAt(i));
					l_id[i] = result_l_id.elementAt(i);
					sid[i] = result_id.elementAt(i);
					//db���� �� ����� ������ ����, ������ش�
					
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
					//��ǰ ���̵� ���� �̹����� �����Ѵ�.
					
					
				}
				txt_total.setText("�� �հ� : " + cost);
				//���հ踦 ������ش�
				
				for (int i = 0; i < result_id.size(); i++) {
					update_bag[i].setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							String quantity = "";
							String newsid = "";
							int tag = (Integer) v.getTag();

							quantity = txtEdit[tag].getText().toString();

							Intent intent = new Intent(bag.this,
									update_bag.class);
							intent.putExtra("userid", send_id);
							System.out.println("bag:"+send_id);
							intent.putExtra("username", send_name);
							System.out.println("bag:"+send_name);
							// ��ٱ����߰� Ȯ�ι�ư ������ �ٽ� ��ٱ��� �������� �Ѿ���� ���ؼ�
							newsid = sid[tag];
							intent.putExtra("sid", newsid);
							System.out.println("bag:"+newsid);
							intent.putExtra("quantity", quantity);
							System.out.println("bag:"+quantity);
							startActivity(intent);
						}
					});
				}
				//��ٱ��� ������ư�� ���� �۾� ���� �� ����
				
				for (int i = 0; i < result_id.size(); i++) {
					delete_bag[i].setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							String newsid = "";
							int tag = (Integer) v.getTag();

							Intent intent = new Intent(bag.this,
									delete_bag.class);
							intent.putExtra("userid", send_id);
							intent.putExtra("username", send_name);
							// ��ٱ����߰� Ȯ�ι�ư ������ �ٽ� ��ٱ��� �������� �Ѿ���� ���ؼ�
							newsid = sid[tag];
							intent.putExtra("sid", newsid);
							startActivity(intent);
						}
					});
				}
				//��ٱ��� ������ư�� ���� �۾� ���� �� ����
				
				for(int i=0; i<result_id.size(); i++) {
					mapbtn[i].setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							int tag = (Integer) v.getTag();
							String new_l_id = l_id[tag];
							Intent intent = new Intent(bag.this, map.class);
							//�൵��ġ �ٲٱ�
							intent.putExtra("l_id", new_l_id);
							startActivity(intent);
						}
					});
				}
				//�൵���� ��ư�� ���� �۾� ���� �� ����
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

				URL url = new URL(SERVER_ADDRESS + "/bag.php?" + "id="
						+ URLEncoder.encode(receive_id, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/bagresult.xml");
				InputStream in = server.openStream();
				xpp.setInput(in, "UTF-8");

				int eventType = xpp.getEventType(); // �̺�Ʈ Ÿ�� ������ ������� <node>
													// ���� </node> ���� �����ϱ� ����.
				int i = 0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					ret = "";
					// �ӽ����庯�� ret�� �ʱ�ȭ
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
						} else if (tagname.equals("total")) {
							ret = xpp.getText();
							if (i == 0) {
								result_total.add(ret);
							} else if (i % 2 == 0)
								result_total.add(ret);
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