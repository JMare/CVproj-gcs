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

   Sockethandler(String addr, int port, TextView textResponse) {
      dstAddress = addr;
      dstPort = port;
      this.textResponse = textResponse;
   }

   @Override
   protected Void doInBackground(Void... arg0) {

      Socket socket = null;

      try {
         socket = new Socket(dstAddress, dstPort);

         BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                // Write output
                writer.write("SHX010HRTEHX");
                writer.flush();
                 writer.write("SMX010101101EMX");
                 writer.flush();

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
           // try {
              // socket.close();
           // } catch (IOException e) {
               // TODO Auto-generated catch block
            //   e.printStackTrace();
           // }
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