package edu.monash.jmare.cvproj_gcs;

import android.app.Application;
import android.content.Intent;

/**
 * Created by james on 4/20/16.
 */
public class GlobalHandler extends Application {


   public ParamLocal _paramLocal;


    @Override
    public void onCreate() {
        super.onCreate();
            //Gets called once only
            //need to handle if socket stops
            startService(new Intent(GlobalHandler.this, SocketService.class));
    }
}