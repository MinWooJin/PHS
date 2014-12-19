/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 	ȸ�� �α׾ƿ� : �α��ε� �Һ����� �α׾ƿ� ����� ���� ǰ���� �Է¹��� �� dbó�� �� ���					 */
/* 	PHS�� �⺻������ ���� �������� ���� ���� ���񽺷μ�, �α׾ƿ��� �ϴ°��� ��ǰ���Ű� �Ϸ�Ǿ��ٴ� ���� ��������	 */
/* 	��ٱ��Ͽ� �߰��Ǿ��ִ� ��� ������ �����Ѵ�.											 */
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

@SuppressLint("HandlerLeak")
public class logout extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/		
	Vector<String> result = new Vector<String>();

	String receive_id = "";

	String ret = "";
	private final String SERVER_ADDRESS = "  ";
	//���� ������ ���� �ּ�
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
		//�α׾ƿ� page���� �� Thread start

		go_main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(logout.this, MainActivity.class);
				startActivity(intent);
			}
		});
		//�α׾ƿ� �Ϸ� �� main page�� �̵�

		// TODO Auto-generated method stub
	}

/*****************************************************************************/
/* 							Thread and  Handler 							 */
/*****************************************************************************/
/* 						������� id�� �ش��ϴ� ��ٱ��������� �����Ѵ� 				 		 */
/*****************************************************************************/		
	Handler xmlHandler = new Handler() { // �����带 ���� �ڵ鷯 ����
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				//�α׾ƿ��� ���и� ���� �ʴ´�.
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

				URL url = new URL(SERVER_ADDRESS + "/logout.php?" + "id="
						+ URLEncoder.encode(receive_id, "UTF-8"));
				url.openStream();
				URL server = new URL(SERVER_ADDRESS + "/logoutresult.xml");
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