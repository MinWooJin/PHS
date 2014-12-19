/*****************************************************************************/
/* 																			 */
/* 						System: Purchase Help Systems 						 */
/* 																			 */
/*****************************************************************************/
/* 							약도보기 버튼 클릭 시 동작화면    	 	 			   		 */
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
		//넘겨주는 위치id를 받는다
		ImageView map = (ImageView) findViewById(R.id.imageView1);
		
		if(l_id.equals("1-AD"))
			map.setImageResource(R.drawable.ad_1);
		else if(l_id.equals("1-CH"))
			map.setImageResource(R.drawable.ch_1);
		else if(l_id.equals("1-DF"))
			map.setImageResource(R.drawable.df_1);
		//위치id에 따른 map이미지를 출력한다
		
	    // TODO Auto-generated method stub
	}

}
