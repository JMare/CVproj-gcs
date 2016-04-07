package edu.monash.jmare.cvproj_gcs;

/**
 * Created by james on 30/03/16.
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.widget.TextView;

public class Sockethandler extends AsyncTask<Void, Void, Void> {

   String dstAddress;
   int dstPort;
   String response = "";
   TextView textResponse;
    int onoroff;
   Sockethandler(String addr, int port, TextView textResponse, int toggle) {
      dstAddress = addr;
      dstPort = port;
      this.textResponse = textResponse;
       onoroff = toggle;
   }

   @Override
   protected Void doInBackground(Void... arg0) {

      Socket socket = null;

      try {
         socket = new Socket(dstAddress, dstPort);

         BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                // Write output
                writer.write("SHX006DOCEHX");
                writer.flush();
          if(onoroff == 1) {
              writer.write("SMXTOG001EMX");
              writer.flush();
          } else {
              writer.write("SMXTOG000EMX");
              writer.flush();
          }

        InputStream stream = socket.getInputStream();
        byte[] data = new byte[12];
        int count = stream.read(data);
        response = new String(data, "UTF-8");


      } catch (UnknownHostException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         response = "UnknownHostException: " + e.toString();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         response = "IOException: " + e.toString();
      } finally {
         if (socket != null) {
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
      textResponse.setText(response);
      super.onPostExecute(result);
   }

}