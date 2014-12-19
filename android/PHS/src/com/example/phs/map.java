/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 							�൵���� ��ư Ŭ�� �� ����ȭ��    	 	 			   		 */
/*****************************************************************************/

/*****************************************************************************/
/*						 Included Header Files 								 */
/*****************************************************************************/
package com.example.phs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class map extends Activity{

	String l_id = "";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    
		Intent intent = getIntent();
		l_id = intent.getExtras().getString("l_id");
		//�Ѱ��ִ� ��ġid�� �޴´�
		ImageView map = (ImageView) findViewById(R.id.imageView1);
		
		if(l_id.equals("1-AD"))
			map.setImageResource(R.drawable.ad_1);
		else if(l_id.equals("1-CH"))
			map.setImageResource(R.drawable.ch_1);
		else if(l_id.equals("1-DF"))
			map.setImageResource(R.drawable.df_1);
		//��ġid�� ���� map�̹����� ����Ѵ�
		
	    // TODO Auto-generated method stub
	}

}
