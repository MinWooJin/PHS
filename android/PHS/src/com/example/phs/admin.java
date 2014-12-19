/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	�˸������ �����ϱ� ���� ������ �α��� �� db���� : �������, ������ Ȯ���ؼ� ���ǹ�ǰ ǥ��	     		 */
/* 	url������ ���� Thread���, php���Ͽ� ���� �� db���� ��ȯ								 */
/* 	Thread���� �� Handler���� �˰��� ���� �� ���										 */
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
	//���� ���ڸ� ��� ���� �۾�
	
	Vector<String> result_id = new Vector<String>();
	Vector<String> result_name = new Vector<String>();
	Vector<String> result_quantity = new Vector<String>();
	Vector<String> result_expiration = new Vector<String>();
	//db���� �� ������ ������ ���� Vector
	
	Vector<String> result_alram_id = new Vector<String>();
	Vector<String> result_alram_name = new Vector<String>();
	Vector<String> result_alram_quantity = new Vector<String>();
	Vector<String> result_alram_expiration = new Vector<String>();
	//�˶������ �����ϱ� ���� Vector
	
	TextView txtView[][] = new TextView[6][5];
	//���� ���̾ƿ� view
	
	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	String tagname, content;
	//db�� �����ϱ� ���� ���� �ּ�
	
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
		//txtView�� id�� ���������� ���� �ʱ� ������ �ϳ��� ����
		
		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		//url������ ���� Thread���� �� start
		
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin.this, insert_stuff.class);
				startActivity(intent);
			}
		});
		//��ǰ���� ��ưŬ�� �� �̵�
		
		logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin.this, admin_logout.class);
				startActivity(intent);
			}
		});
		//logout��ư Ŭ�� �� �̵�

		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String key = edittext.getText().toString();
				Intent intent = new Intent(admin.this, admin_detail.class);
				intent.putExtra("key", key);
				startActivity(intent);
			}
		});
		//�˻� ��ư Ŭ�� �� �̵� �� key passing
		// TODO Auto-generated method stub
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/	

	Handler xmlHandler = new Handler() { // �����带 ���� �ڵ鷯 ����
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
					//������� �˰����� ���� ���� ����

					DByear = DBtoday.substring(0, 4);
					Dyear = today.substring(0, 4);
					Dmon = today.substring(4, 6);
					
					DBday = DBtoday.substring(8, 10);
					DBmon = DBtoday.substring(5, 7);
					Dday = today.substring(6, 8);
					//���� db����� ������ѿ� ����� ����
					
					int DBY = Integer.valueOf(DByear);
					int DBM = Integer.valueOf(DBmon);
					int DBD = Integer.valueOf(DBday);
					int DY = Integer.valueOf(Dyear);
					int DM = Integer.valueOf(Dmon);
					int DD = Integer.valueOf(Dday);
					//int�� ����ȯ
				
					int check_quantity = Integer.valueOf(result_quantity.elementAt(i));
					if (check_quantity < 80) {
						result_alram_id.add(result_id.elementAt(i));
						result_alram_name.add(result_name.elementAt(i));
						result_alram_quantity.add(result_quantity.elementAt(i));
						result_alram_expiration.add(result_expiration.elementAt(i));
						//������ 80�� ������ ��쿣 �˶��� ����ϱ� ���� �����Ѵ�
					}
					else if ((DBY < DY) || (DBY == DY && DBM < DM)
							|| (DBY == DY && DBM == DM && DBD < DD)){
						result_alram_id.add(result_id.elementAt(i));
						result_alram_name.add(result_name.elementAt(i));
						result_alram_quantity.add(result_quantity.elementAt(i));
						result_alram_expiration.add(result_expiration.elementAt(i));
						//������� ���������� ���� �����Ѵ�
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
					//�˶������ ������ش�
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
				
				URL url = new URL(SERVER_ADDRESS + "/alram_admin.php?");
				//db������ ���� php���� ���
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/alram_adminresult.xml");
				//��� xml������ ������ ���
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
						//��� xml������ Text�� ����
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
				e.getMessage(); // ���� üũ

			} finally {
				xmlHandler.sendEmptyMessage(0); // �޽��� ����
			}
		}
	}

}
