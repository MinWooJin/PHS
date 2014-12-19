/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 							소비자 로그인 성공 후 초기화면    	 	 			   		 */
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
	    //로그인 시 넘겨준 id, name을 받는다
	    
	    Button button = (Button) findViewById(R.id.bagbtn);
	    //장바구니 버튼
		Button button2 = (Button) findViewById(R.id.nonsearchbtn);
		//검색버튼
		Button logout = (Button) findViewById(R.id.button2);
		//로그아웃버튼
		Button secession = (Button) findViewById(R.id.button3);
		TextView txt_username = (TextView) findViewById(R.id.textView1);
		//최상단 textview
		final EditText edittext = (EditText) findViewById(R.id.nonsearchText);
		//검색내용 입력창
		txt_username.setText(receive_name+"님 환영합니다");
		//최상단 view에 이름을 출력해준다
	    
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
	    //장바구니로 버튼 클릭 시 동작
	    
	    logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				send_id = receive_id;
				Intent intent = new Intent(loginsuccess.this, logout.class);
				intent.putExtra("userid", send_id);
				startActivity(intent);
			}
		});
	    //로그아웃 버튼 클릭시 동작
	    
	    secession.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(loginsuccess.this, secession.class);
				startActivity(intent);
			}
		});
	    //회원탈퇴 버튼클릭시 동작
	    
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
	    //회원 검색기능 이용시 동작
	    // TODO Auto-generated method stub
	}

}
