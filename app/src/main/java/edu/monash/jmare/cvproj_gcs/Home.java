package edu.monash.jmare.cvproj_gcs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Home extends AppCompatActivity {

    SocketService mBoundService = null;
    boolean mIsBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
            //EDITED PART
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        // TODO Auto-generated method stub
         mBoundService = ((SocketService.LocalBinder)service).getService();

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
       if(mBoundService!=null){
       mBoundService.IsBoundable();
       }
   }


   private void doUnbindService() {
       if (mIsBound) {
           // Detach our existing connection.
           unbindService(mConnection);
           mIsBound = false;
       }
   }

    TextView socktestTextView;
    String ipAddr = "118.138.55.249";
    int port = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
 startService(new Intent(Home.this,SocketService.class));
        doBindService();

    }

    /**
     * Called when the user touches the button
     */
    public void stopTracking(View view) {
        // Do something in response to button click
     //   setContentView(R.layout.content_home);
     //   socktestTextView = (TextView) findViewById(R.id.socktest);
     //   Sockethandler socketobject = new Sockethandler(ipAddr, port, socktestTextView, 0);
      if(mBoundService!=null)
        {
            mBoundService.sendMessage("SHX006DOCEHX");
        }//   socketobject.execute();
    }

    public void startTracking(View view) {
        // Do something in response to button click
     //   setContentView(R.layout.content_home);
     //   socktestTextView = (TextView) findViewById(R.id.socktest);
     //   Sockethandler socketobject = new Sockethandler(ipAddr, port, socktestTextView, 1);
     //   socketobject.execute();
        startService(new Intent(Home.this,SocketService.class));
        doBindService();
    }

}
