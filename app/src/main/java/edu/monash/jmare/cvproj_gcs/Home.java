package edu.monash.jmare.cvproj_gcs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Home extends AppCompatActivity {

    TextView socktestTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setContentView(R.layout.content_home);
        socktestTextView = (TextView)findViewById(R.id.socktest);
        Sockethandler socketobject = new Sockethandler("192.168.0.72",13,socktestTextView);
        socketobject.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
  /*  public class MyClientTask extends AsyncTask<Void, Void, Void> {

  String dstAddress;
  int dstPort;
  String response = "";

  MyClientTask(String addr, int port){
   dstAddress = addr;
   dstPort = port;
  }

  @Override
  protected Void doInBackground(Void... arg0) {

   Socket socket = null;

   try {
    socket = new Socket(dstAddress, dstPort);

    ByteArrayOutputStream byteArrayOutputStream =
                  new ByteArrayOutputStream(1024);
    byte[] buffer = new byte[1024];

    int bytesRead;
    InputStream inputStream = socket.getInputStream();

             while ((bytesRead = inputStream.read(buffer)) != -1){
                 byteArrayOutputStream.write(buffer, 0, bytesRead);
                 response += byteArrayOutputStream.toString("UTF-8");
             }

   } catch (UnknownHostException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    response = "UnknownHostException: " + e.toString();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    response = "IOException: " + e.toString();
   }finally{
    if(socket != null){
     try {
      socket.close();
     } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
     }
    }
   }
   return null;
  }

  @Override
  protected void onPostExecute(Void result) {
   socktestTextView.setText(response);
   super.onPostExecute(result);
  }
}
        */};
