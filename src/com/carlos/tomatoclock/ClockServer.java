/**
 * 
 */
package com.carlos.tomatoclock;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * @author carlos
 *
 */
public class ClockServer extends Service {

	
    private boolean threadDisable;
    
    final String TAGS = "ClockServer_Service";
    
    int testCount = 10;

    private int count;
    
    final static int SecondTick = 5000;
    
    Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	Log.v(TAGS, "get one second message");
	        super.handleMessage(msg);
	    }
	};
    
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override  

	public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v( TAGS , " onStartCommand intent is: "+intent);

		Bundle bl;
		// // //获取Intent中的Bundle数据
		bl = intent.getExtras();
		Log.v(TAGS, " onStartCommand count is: " + bl.getInt("count"));
		// Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//	    Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
//	    return super.onStartCommand(intent,flags,startId);
//	}
	
	@Override
    public void onCreate() {
        Log.v( TAGS , " onCreate");

        super .onCreate();
        
//		Intent intent = super.getIntent();
//		Bundle bl;
////		//获取Intent中的Bundle数据
//		bl=intent.getExtras();
//        Log.v( TAGS , " onCreate count is: "+bl.getString("count"));

        new Thread( new Runnable() {

            public void run() {
                while ( ! threadDisable) {
                    try {
                        Thread.sleep( SecondTick );
                    } catch (InterruptedException e) {
                    }
                    count ++ ;
                    Log.v( TAGS , " Count is " + count);
//                    if(testCount == count)
//                    {
////            			Intent intent = new Intent();
////            			intent.setClass(ClockServer.this, MainActivity.class);
////            			startActivity(intent);
//                        threadDisable = true ;
//
//                    }
                }
            }
        }).start();
    }
	@Override
    public void onDestroy() {
        super .onDestroy();
        this .threadDisable = true ;
        Log.v( TAGS , " on destroy " );
    }

    public int getCount() {
        return count;
    }
}
