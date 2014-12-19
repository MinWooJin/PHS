/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 							�Һ��� �α��� ���� �� �ʱ�ȭ��    	 	 			   		 */
/*****************************************************************************/

/*****************************************************************************/
/*						 Included Header Files 								 */
/*****************************************************************************/

package com.example.phs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class loginsuccess extends Activity {

/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/	
	
	String receive_id = "";
	String receive_name = "";
	String send_id = "";
	String send_name = "";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.loginsuccess);
	    
	    Intent intent = getIntent();
	    receive_id = intent.getExtras().getString("userid");
	    receive_name = intent.getExtras().getString("username");
	    //�α��� �� �Ѱ��� id, name�� �޴´�
	    
	    Button button = (Button) findViewById(R.id.bagbtn);
	    //��ٱ��� ��ư
		Button button2 = (Button) findViewById(R.id.nonsearchbtn);
		//�˻���ư
		Button logout = (Button) findViewById(R.id.button2);
		//�α׾ƿ���ư
		Button secession = (Button) findViewById(R.id.button3);
		TextView txt_username = (TextView) findViewById(R.id.textView1);
		//�ֻ�� textview
		final EditText edittext = (EditText) findViewById(R.id.nonsearchText);
		//�˻����� �Է�â
		txt_username.setText(receive_name+"�� ȯ���մϴ�");
		//�ֻ�� view�� �̸��� ������ش�
	    
	    button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				send_id = receive_id;
				send_name = receive_name;
				Intent intent = new Intent(loginsuccess.this, bag.class);
				intent.putExtra("userid", send_id);
				intent.putExtra("username", send_name);
				startActivity(intent);
			}
		});
	    //��ٱ��Ϸ� ��ư Ŭ�� �� ����
	    
	    logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				send_id = receive_id;
				Intent intent = new Intent(loginsuccess.this, logout.class);
				intent.putExtra("userid", send_id);
				startActivity(intent);
			}
		});
	    //�α׾ƿ� ��ư Ŭ���� ����
	    
	    secession.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(loginsuccess.this, secession.class);
				startActivity(intent);
			}
		});
	    //ȸ��Ż�� ��ưŬ���� ����
	    
	    button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				send_id = receive_id;
				send_name = receive_name;
				String key = edittext.getText().toString();
				Intent intent = new Intent(loginsuccess.this, login_search.class);
				intent.putExtra("userid", send_id);
				intent.putExtra("username", send_name);
				intent.putExtra("key", key);
				startActivity(intent);
			}
		});
	    //ȸ�� �˻���� �̿�� ����
	    // TODO Auto-generated method stub
	}

}
