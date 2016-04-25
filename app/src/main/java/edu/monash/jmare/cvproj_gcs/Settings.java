package edu.monash.jmare.cvproj_gcs;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context; import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import java.net.Socket;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText textGrThreshMin;
    EditText textGrThreshMax;
    EditText textGrErodePix;
    EditText textGrDilatePix;
    EditText textGrErodeIter;
    EditText textGrDilateIter;

    GlobalHandler _globalHandler;
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
        bindService(new Intent(Settings.this, SocketService.class), mConnection, Context.BIND_AUTO_CREATE);
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
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //find all the textboxes to populate
        textGrThreshMin = (EditText) findViewById(R.id.edit_greyThreshMin);
        textGrThreshMax = (EditText) findViewById(R.id.edit_greyThreshMax);
        textGrErodePix = (EditText) findViewById(R.id.edit_greyErodePix);
        textGrDilatePix = (EditText) findViewById(R.id.edit_greyDilatePix);
        textGrErodeIter= (EditText) findViewById(R.id.edit_greyErodeIter);
        textGrDilateIter = (EditText) findViewById(R.id.edit_greyDilateIter);
        setSupportActionBar(toolbar);

        _globalHandler = (GlobalHandler) getApplicationContext();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        //we have been sent parameters serialized from the service
                        _globalHandler._paramLocal = (ParamLocal)intent.getSerializableExtra(SocketService.EXTRA_PARAM);
                        textGrThreshMin.setText(String.valueOf(_globalHandler._paramLocal.greyThreshMin));
                        textGrThreshMax.setText(String.valueOf(_globalHandler._paramLocal.greyThreshMax));
                        textGrErodePix.setText(String.valueOf(_globalHandler._paramLocal.greyErodePix));
                        textGrDilatePix.setText(String.valueOf(_globalHandler._paramLocal.greyDilatePix));
                        textGrErodeIter.setText(String.valueOf(_globalHandler._paramLocal.greyErodeIterations));
                        textGrDilateIter.setText(String.valueOf(_globalHandler._paramLocal.greyDilateIterations));
                    }
                }, new IntentFilter(SocketService.ACTION_UPDATE_PARAMS)
        );
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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
        } else if(id == R.id.action_download_params) {
            mBoundService.sendMessage("SHX000PRQEHX");
            mBoundService.new getParams().execute();
            return true;
        } else if(id == R.id.action_upload_params){
            readEditedParams();
            String packedParams = _globalHandler._paramLocal.packString();
            mBoundService.sendMessage("SHX084PSEEHX");
            mBoundService.sendMessage(packedParams);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_control) {
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            //this is me
        } else if (id == R.id.nav_advset) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void readEditedParams(){
     _globalHandler._paramLocal.greyThreshMin = Integer.valueOf( textGrThreshMin.getText().toString());
     _globalHandler._paramLocal.greyThreshMax = Integer.valueOf( textGrThreshMax.getText().toString());
     _globalHandler._paramLocal.greyErodePix = Integer.valueOf(  textGrErodePix.getText().toString());
     _globalHandler._paramLocal.greyDilatePix = Integer.valueOf( textGrDilatePix.getText().toString());
     _globalHandler._paramLocal.greyErodeIterations = Integer.valueOf( textGrErodeIter.getText().toString());
     _globalHandler._paramLocal.greyDilateIterations = Integer.valueOf(textGrDilateIter.getText().toString());
    }
}
