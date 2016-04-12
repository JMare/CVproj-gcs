package edu.monash.jmare.cvproj_gcs;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by james on 11/04/16.
 */

public class SocketService extends Service {
    public static final String SERVERIP = "192.168.0.8"; //your computer IP address should be written here
    public static final int SERVERPORT = 13;
    PrintWriter out;
    Socket socket;
    InetAddress serverAddr;
    private static String LOG_TAG = "BoundService";
    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        SocketService getService() {
            return SocketService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");
        Toast.makeText(this,"Service created", Toast.LENGTH_LONG).show();
    }

    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            System.out.println("in sendMessage "+message);
            out.print(message);
            out.flush();
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        System.out.println("I am in on start");
        Runnable connect = new connectSocket();
        new Thread(connect).start();
        return START_STICKY;
    }


    class connectSocket implements Runnable {

        @Override
        public void run() {


            try {
                 //here you must put your computer's IP address.
                serverAddr = InetAddress.getByName(SERVERIP);
                Log.e("TCP Client", "C: Connecting...");
                //create a socket to make the connection with the server

                socket = new Socket(serverAddr, SERVERPORT);

                 try {


                     //send the message to the server
                     out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);


                     Log.e("TCP Client", "C: Sent.");

                     Log.e("TCP Client", "C: Done.");


                    }
                 catch (Exception e) {

                     Log.e("TCP", "S: Error", e);

                 }
            } catch (Exception e) {

                Log.e("TCP", "C: Error", e);

            }

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
          try {
              socket.close();
          } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          socket = null;
      }


}
