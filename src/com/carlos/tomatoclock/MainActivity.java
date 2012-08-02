package com.carlos.tomatoclock;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends Activity {
	Vibrator vibrator;
	long[] pattern = { 800, 5000, 400, 30 }; // OFF/ON/OFF/ON...
	Timer timer;
	final String TAGS = "carlos";
	Button btnTomato;
	Button btnRest;

	TextView data;
	
	
	static int status = 0;//tomato stop /1tomato start /2 reststop /3 reststart
	
	static int tomatoCount = 0;

	Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	Log.v(TAGS, "get one second message"+msg.what+" tomatoCount is: "+tomatoCount);
	        super.handleMessage(msg);
	        if(msg.what == 0)
	        {
				switch (status) {
				case 0:

					break;
				case 1:
					tomatoCount -= 1;
					if(tomatoCount < 0)
					{
						status = 2;
						vibrator.vibrate(3000);
					}
					updateData(getString(R.string.tomato).toString(),tomatoCount);
					break;
				case 2:

					break;
				case 3:
					tomatoCount -= 1;
					if(tomatoCount < 0)
					{
						status = 0;
						vibrator.vibrate(3000);
					}
					updateData(getString(R.string.rest).toString(),tomatoCount);
					break;
				default:
					break;
				}
				updateBtn();
	        	
	        }
//	        MainActivity.this.onRestart();
	    }
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//		vibrator.vibrate(pattern, 0);// -1不重复，非-1为从pattern的指定下标开始重复
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 0;
				handler.sendMessage(message);
			}
		}, 0, 1000);
		
		btnTomato = (Button) findViewById(R.id.btnTomato);

		btnTomato.setText(getString(R.string.tomato).toString()+getString(R.string.stop).toString());
		
		btnTomato.setOnClickListener(clickHandler);
		
		data = (TextView)findViewById(R.id.text);
		data.setText(getString(R.string.current_status).toString()+getString(R.string.tomato).toString());
	}
	private OnClickListener clickHandler= new OnClickListener() {
	    public void onClick(View v) {
			Log.v(TAGS, "someone click the button");
			status = status + 1;
			if(status > 3)
				status = 0;
			switch (status) {
			case 0:
				break;
			case 1:
				tomatoCount = 25*60;
				break;
			case 2:
				break;
			case 3:
				tomatoCount = 5*60;
				break;
			default:
				break;
			}
			updateBtn();
	    }
	};	

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
	private void updateData(String tags,int count){
		data.setText(tags+":"+count);
	}
	private void  updateBtn() {
		switch (status) {
		case 0:
			btnTomato.setText(getString(R.string.tomato).toString()+getString(R.string.stop).toString());
			break;
		case 1:
			btnTomato.setText(getString(R.string.tomato).toString()+getString(R.string.start).toString());
			break;
		case 2:
			btnTomato.setText(getString(R.string.rest).toString()+getString(R.string.stop).toString());

			break;
		case 3:
			btnTomato.setText(getString(R.string.rest).toString()+getString(R.string.start).toString());
			break;
		default:
			break;
		}
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
