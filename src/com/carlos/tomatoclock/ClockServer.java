/**
 * 
 */
package com.carlos.tomatoclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

	NotificationManager m_NotificationManager;  

    PendingIntent m_PendingIntent;  

    Intent m_Intent;  

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

        
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification m_Notification = new Notification(/* your notification */);

		m_Notification.icon = R.drawable.ic_launcher;

		m_Notification.tickerText = "hello通知内容........";

		Intent m_Intent = new Intent(this, MainActivity.class);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				m_Intent, 0); /* your intent */
		
        m_Notification.defaults = Notification.DEFAULT_ALL;  

        m_Notification.setLatestEventInfo(this, "hello", "hello"/* your content */,
				pendingIntent);
		notificationManager.notify(1/* id */, m_Notification);
        	
        	
        new Thread( new Runnable() {

            public void run() {
                while ( ! threadDisable) {
                    try {
                        Thread.sleep( SecondTick );
                    } catch (InterruptedException e) {
                    }
                    count ++ ;
                    Log.v( TAGS , " Count is " + count);
                    if(testCount == count)
                    {
//            			Intent intent = new Intent();
//            			intent.setClass(ClockServer.this, MainActivity.class);
//            			startActivity(intent);
//                        threadDisable = true ;
//                    	m_NotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);  
//                        
//                        m_Intent = new Intent(MainActivity.this, MainActivity.class);  
//
//                        m_PendingIntent = PendingIntent.getActivity(MainActivity.this, 0,  
//                                m_Intent, 0); 
//
//                        m_Notification = new Notification();
//                        
//                        m_Notification.icon = R.drawable.ic_launcher;  
//
//                        m_Notification.tickerText = "MainActivity通知内容........";  
////                         通知时既震动又屏幕发亮还有默认的声音 这里用的是ALL  
//                        m_Notification.defaults = Notification.DEFAULT_ALL;  
//                        
//                        m_Notification.setLatestEventInfo(MainActivity.this, "Button4",  
//                                "Button4通知", m_PendingIntent);  
//                        m_NotificationManager.notify(0, m_Notification);  

                    }
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
