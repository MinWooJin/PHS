/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	��ٱ��� ���� �� db���� : ��ٱ��Ͽ��� ���� ��ư Ŭ�� �� �����id, ��ǰid�� db bag���� ����     		 */
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
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class delete_bag extends Activity {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/	
	String receive_id = "";
	String receive_name = "";
	String receive_sid = "";

	Vector<String> result = new Vector<String>();
	
	String send_id = "";
	String send_name = "";
	
	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	//������ ���� �ּ�
	String tagname, content;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.delete_bag);
	    
	    Intent intent = getIntent();
		receive_id = intent.getExtras().getString("userid");
		receive_name = intent.getExtras().getString("username");
		receive_sid = intent.getExtras().getString("sid");
		
		send_id = receive_id;
		send_name = receive_name;
		
		Button go_bag = (Button) findViewById(R.id.bagbtn);
		
		Thread xml_Thread = new xmlThread();
		xml_Thread.setDaemon(true);
		xml_Thread.start();
		
		go_bag.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(delete_bag.this, bag.class);
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
/* 							db���� �� ����� �����͸� �����Ѵ�.			 				 */
/*****************************************************************************/
	Handler xmlHandler = new Handler() { // �����带 ���� �ڵ鷯 ����
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				Toast.makeText(delete_bag.this, "���� �Ϸ�!", Toast.LENGTH_LONG).show();
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

				URL url = new URL(SERVER_ADDRESS + "/bagdelete.php?" + "uid="
						+ URLEncoder.encode(receive_id, "UTF-8") + "&sid="
						+ URLEncoder.encode(receive_sid, "UTF-8"));
				//bagdelete.php�� uid,sid�� �Ķ���ͷ� �����ϰ� �Ѵ�

				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/bagdeleteresult.xml");
				//��� xml������ ���
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
				e.getMessage(); // ���� üũ

			} finally {
				xmlHandler.sendEmptyMessage(0); // �޽��� ����
			}
		}
	}

}