/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	ȸ������ : �Һ����� ȸ������ ����� ���� �⺻������ �Է¹��� �� db customer�� �߰�					 */
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
import android.widget.Toast;


@SuppressLint("HandlerLeak")
public class join extends Activity {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/
	EditText idtext;
	EditText pwtext;
	EditText nametext;
	EditText phonetext;
	EditText birthtext;
	
	Vector<String> result = new Vector<String>();
	String ret ="";
	
	BackThread xmlThread;

	private final String SERVER_ADDRESS = "  ";
	//�������� �ּ�
	String tagname, content;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.join);
	    
	    idtext = (EditText) findViewById(R.id.editText1);
		pwtext = (EditText) findViewById(R.id.editText2);
		nametext = (EditText) findViewById(R.id.editText3);
		phonetext = (EditText) findViewById(R.id.editText4);
		birthtext = (EditText) findViewById(R.id.editText5);
	
		Button button2 = (Button) findViewById(R.id.welcomebtn);
		
		xmlThread = new BackThread();
		xmlThread.setDaemon(true);
		
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
					xmlThread.start();
					//��ư Ŭ�� �� Thread start
			}
		});
	    
	    // TODO Auto-generated method stub
	}
	
/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 				customer�� �⺻ ������ �Է¹޾� db�� �߰��� �� ����ó������ �����ش� 				 */
/*****************************************************************************/
	Handler xmlHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (result.size()!=0) {
					Intent intent = new Intent(join.this, welcome_join.class);
					startActivity(intent);
				}
				else if (result.size()==0) {
					Toast.makeText(join.this, "ȸ�����Ծ�� ��Ȯ��!", Toast.LENGTH_LONG).show();
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
				String name = nametext.getText().toString();
				String phone = phonetext.getText().toString();
				String birth = birthtext.getText().toString();

				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				
				factory.setNamespaceAware(true);
				
				XmlPullParser xpp = factory.newPullParser();
				
				
				URL url = new URL(SERVER_ADDRESS + "/join.php?" + "id="
						+ URLEncoder.encode(id, "UTF-8")+"&password="
						+ URLEncoder.encode(pw, "UTF-8")+"&name="
						+ URLEncoder.encode(name, "UTF-8")+"&phone="
						+ URLEncoder.encode(phone, "UTF-8")+"&birthday="
						+ URLEncoder.encode(birth, "UTF-8"));
				//������� �⺻������ �Ķ���ͷ� join.php������ ����
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/joinresult.xml");
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
			//System.out.println(v_name.elementAt(0));
			
		}
		
	}

}
