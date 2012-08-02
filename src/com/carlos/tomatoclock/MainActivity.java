package com.carlos.tomatoclock;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	Vibrator vibrator;
	long[] pattern = { 800, 5000, 400, 30 }; // OFF/ON/OFF/ON...
	Timer timer;
	final String TAGS = "carlos";
	
	Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {

	    	Log.v(TAGS, "get message");
	        super.handleMessage(msg);
	    }
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(pattern, 0);// -1不重复，非-1为从pattern的指定下标开始重复
		
		

		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()  
        {  
            @Override  
            public void run()  
            {  
                // TODO Auto-generated method stub  
                Message mesasge = new Message();  
                mesasge.what = 0;  
                handler.sendMessage(mesasge);  
            }  
        }, 0, 1000);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
//		vibrator.cancel();
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
//		vibrator.vibrate(pattern, 0);// -1不重复，非-1为从pattern的指定下标开始重复
		super.onResume();
	}

}
