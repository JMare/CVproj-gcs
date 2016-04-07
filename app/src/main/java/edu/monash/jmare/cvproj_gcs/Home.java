package edu.monash.jmare.cvproj_gcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView socktestTextView;
    String ipAddr = "192.168.0.72";
    int port = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    /**
     * Called when the user touches the button
     */
    public void stopTracking(View view) {
        // Do something in response to button click
        setContentView(R.layout.content_home);
        socktestTextView = (TextView) findViewById(R.id.socktest);
        Sockethandler socketobject = new Sockethandler(ipAddr, port, socktestTextView, 0);
        socketobject.execute();
    }

    public void startTracking(View view) {
        // Do something in response to button click
        setContentView(R.layout.content_home);
        socktestTextView = (TextView) findViewById(R.id.socktest);
        Sockethandler socketobject = new Sockethandler(ipAddr, port, socktestTextView, 1);
        socketobject.execute();
    }

}
