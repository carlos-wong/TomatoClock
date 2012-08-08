/**
 * 
 */
package com.carlos.tomatoclock;

import com.carlos.tomatoclock.MainActivity.MyReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
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
    
    final static int SecondTick = 1000;
    
	Vibrator vibrator;
	long[] pattern = { 800, 5000, 400, 30 }; // OFF/ON/OFF/ON...

	MyReceiver receiver;

    
    Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	Bundle b = msg.getData();
	    	Log.v(TAGS, "get one second message is: "+b.getString("color"));
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
		count = bl.getInt("count");
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

        
//		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		Notification m_Notification = new Notification(/* your notification */);
//
//		m_Notification.icon = R.drawable.ic_launcher;
//
//		m_Notification.tickerText = "hello通知内容........";
//
//		Intent m_Intent = new Intent(this, MainActivity.class);
//
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//				m_Intent, 0); /* your intent */
//		
//        m_Notification.defaults = Notification.DEFAULT_ALL;  
//
//        m_Notification.setLatestEventInfo(this, "hello", "hello"/* your content */,
//				pendingIntent);
//		notificationManager.notify(0/* id */, m_Notification);
        	
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//
//		vibrator.vibrate(pattern, 0);// -1不重复，非-1为从pattern的指定下标开始重复

		receiver=new MyReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.carlos.tomatoclock.MainActivity");
		this.registerReceiver(receiver,filter);
        	
        new Thread( new Runnable() {

            public void run() {
                while ( ! threadDisable) {
                    try {
                        Thread.sleep( SecondTick );
                    } catch (InterruptedException e) {
                    }
                    Log.v( TAGS , " Count is " + count);

                    if(count == 1)
                    {
                		vibrator.vibrate(pattern, 0);
                    }
					if (count > 0) {
						count--;
					
                    Intent intent=new Intent();
					intent.putExtra("i", count);
					intent.setAction("com.carlos.tomatoclock.ClockServer");//action与接收器相同
					sendBroadcast(intent);
					
					Message message = new Message();
					message.what = 0;
					Bundle b = new Bundle();// 存放数据
		            b.putString("color", "我的");
		            message.setData(b);
					handler.sendMessage(message);
                    if(0 == count)
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
            }
        }).start();
    }
	@Override
    public void onDestroy() {
        this .threadDisable = true ;
        ClockServer.this.unregisterReceiver(receiver);
        vibrator.cancel();
        Log.v( TAGS , " on destroy " );
        super .onDestroy();
    }

    public int getCount() {
        return count;
    }
    
    public class MyReceiver extends BroadcastReceiver {
		//自定义一个广播接收器
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle=intent.getExtras();
			int a=bundle.getInt("status");
			//处理接收到的内容
	    	Log.v(TAGS, "MyReceiver i is: "+a);

		}
		public MyReceiver(){
			//构造函数，做一些初始化工作，本例中无任何作用
		}
		
 
	}
}
