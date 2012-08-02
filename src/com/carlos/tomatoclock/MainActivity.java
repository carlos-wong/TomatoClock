package com.carlos.tomatoclock;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);  
        long[] pattern = {800, 5000, 400, 30}; // OFF/ON/OFF/ON...  
        vibrator.vibrate(pattern, 0);//-1不重复，非-1为从pattern的指定下标开始重复  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
}
