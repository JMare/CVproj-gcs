package edu.monash.jmare.cvproj_gcs;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by james on 11/04/16.
 */

public class SocketService extends Service {

    GlobalHandler _globalHandler;
    public static final String SERVERIP = "118.138.39.48"; //your computer IP address should be written here
    public static final int SERVERPORT = 13;
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    InetAddress serverAddr;
    private static String LOG_TAG = "BoundService";
    private IBinder mBinder = new MyBinder();

    public static final String
        ACTION_UPDATE_PARAMS = SocketService.class.getName(),
        EXTRA_PARAM = "extra_param";

    private void sendBroadcastMessage(ParamLocal paramLocal){
        Intent intent = new Intent(ACTION_UPDATE_PARAMS);
        intent.putExtra(EXTRA_PARAM, paramLocal);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

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

    private String readUntil(String untilWhat){
        //Cant be done on the UI thread
        String total = "";
        try{
            while (total.length() < 160 && total.endsWith(untilWhat) == false) { // if the string is less then 160 chars long and not ending with !!

                int c = in.read(); // read next char in buffer
                if (c == -1) break; // in.read() return -1 if the end of the buffer was reached
                total += (char) c; // add char to string
            }
        } catch(IOException e) {

        }

        return total;
    }

   public class getParams extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            //Call read
            String paramsHeader = readUntil("EHX");
            String paramsBody = readUntil("EMX");

            if (paramsHeader.equals("SHX087PRPEHX")) {
                //handle the actual parameters in the body
                return paramsBody;
            } else return "bad";
        }

        @Override
        protected void onPostExecute(String result) {
            ParamLocal tmp_Param_Local = new ParamLocal();
            tmp_Param_Local.parseString(result);
            sendBroadcastMessage(tmp_Param_Local);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... values) {}
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        System.out.println("I am in on start");
        _globalHandler = (GlobalHandler) getApplicationContext();
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

                     in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
