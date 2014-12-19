/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 							관리자 로그아웃을 위한 page    	  				   		 */
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

public class admin_logout extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.admin_logout);
	    
	    Button go_main = (Button) findViewById(R.id.mainbtn);
	    
	    go_main.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(admin_logout.this, MainActivity.class);
				startActivity(intent);
			}
		});
	
	    // TODO Auto-generated method stub
	}

}
