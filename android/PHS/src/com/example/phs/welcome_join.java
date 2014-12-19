/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 								회원가입 환영 page    	  				   		 */
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

public class welcome_join extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.welcome_join);
	    
	    Button button = (Button) findViewById(R.id.mainbtn);
	    
	    button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(welcome_join.this, MainActivity.class);
				startActivity(intent);
			}
		});
	    //회원가입 완료 됨을 알려주고, mainpage 이동
	
	    // TODO Auto-generated method stub
	}

}
