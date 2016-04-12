package edu.monash.jmare.cvproj_gcs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Home extends AppCompatActivity {

    SocketService mBoundService = null;
    boolean mIsBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
                //EDITED PART
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
             mBoundService = ((SocketService.MyBinder)service).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
             mBoundService = null;
        }

    };


   private void doBindService() {
       bindService(new Intent(Home.this, SocketService.class), mConnection, Context.BIND_AUTO_CREATE);
       mIsBound = true;
   }


   private void doUnbindService() {
       if (mIsBound) {
           // Detach our existing connection.
           unbindService(mConnection);
           mIsBound = false;
       }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(mBoundService == null) {
            startService(new Intent(Home.this, SocketService.class));
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mBoundService != null){
            stopService(new Intent(Home.this, SocketService.class));
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!mIsBound){
            doBindService();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mIsBound){
            doUnbindService();
        }
    }
    /**
     * Called when the user touches the button
     */
    public void stopTracking(View view) {
        mBoundService.sendMessage("SHX006DOCEHX");
        mBoundService.sendMessage("SMXTOG000EMX");
    }

    public void startTracking(View view) {
        mBoundService.sendMessage("SHX006DOCEHX");
        mBoundService.sendMessage("SMXTOG001EMX");
    }

}
