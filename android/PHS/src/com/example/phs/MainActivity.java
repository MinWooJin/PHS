/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 								PHS접속 시 초기화면    	 		 			   		 */
/*****************************************************************************/

/*****************************************************************************/
/*						 Included Header Files 								 */
/*****************************************************************************/

package com.example.phs;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements
		android.widget.RadioGroup.OnCheckedChangeListener {
	
/*****************************************************************************/
/* 							Global Variables 								 */
/*****************************************************************************/	
	RadioGroup radio;
	int checklogin = 1;
	String key = "";

/*****************************************************************************/
/* 				라디오버튼의 체크상태를 확인하고 소비자, 관리자 로그인을 결정한다					 */
/*****************************************************************************/		
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (radio.getCheckedRadioButtonId() == R.id.radiobtn0)
			checklogin = 1;
		else if (radio.getCheckedRadioButtonId() == R.id.radiobtn1)
			checklogin = 2;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.loginbtn);
		Button button2 = (Button) findViewById(R.id.joinbtn);
		Button button3 = (Button) findViewById(R.id.nonsearchbtn);
		final EditText edittext = (EditText) findViewById(R.id.nonsearchText);
		
		radio = (RadioGroup) findViewById(R.id.radioGroup1);
		radio.setOnCheckedChangeListener(this);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (checklogin == 1) {
					Intent intent = new Intent(MainActivity.this,loginsession.class);
					startActivity(intent);
				} else if (checklogin == 2) {
					Intent intent = new Intent(MainActivity.this,adminloginsession.class);
					startActivity(intent);
				}
			}
		});
		//소비자 선택일 경우에는 소비자로그인, 관리자 선택일 경우에는 관리자로그인을 실행한다
		
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, join.class);
				startActivity(intent);
			}
		});
		//회원가입버튼 클릭시 joinpage로 이동한다
		
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				key = edittext.getText().toString();
				Intent intent = new Intent(MainActivity.this, nonlogin_search.class);
				intent.putExtra("key", key);
				//key를 넘기는 부분
				startActivity(intent);
			}
		});
		//비회원 물품검색기능
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
