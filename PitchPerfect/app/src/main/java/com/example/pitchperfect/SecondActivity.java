package com.example.pitchperfect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

    public class SecondActivity extends Activity {
        private static final String TAG = "BlueTest5-Controlling";
        private int mMaxChars = 50000;//Default//change this to string..........
        private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        private BluetoothSocket mBTSocket;
        private ReadInput mReadThread = null;

        private boolean mIsUserInitiatedDisconnect = false;
        private boolean mIsBluetoothConnected = false;



        private BluetoothDevice mDevice;

        final static String on="Start";//on
        final static String off="Stop";//off


        private ProgressDialog progressDialog;
        Button btnOn,btnOff,veloc_str,spin_str,time_str;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);

            ActivityHelper.initialize(this);
            // mBtnDisconnect = (Button) findViewById(R.id.btnDisconnect);
            btnOn=(Button)findViewById(R.id.collectThrow);
            btnOff=(Button)findViewById(R.id.stopCollect);

            veloc_str=(Button)findViewById(R.id.veloc);
            spin_str=(Button)findViewById(R.id.spin);
            time_str=(Button)findViewById(R.id.time);

            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            mDevice = b.getParcelable(ThirdActivity.DEVICE_EXTRA);
            mDeviceUUID = UUID.fromString(b.getString(ThirdActivity.DEVICE_UUID));
            mMaxChars = b.getInt(ThirdActivity.BUFFER_SIZE);

            Log.d(TAG, "Ready");

            btnOn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
                    Button button = (Button)findViewById(R.id.collectThrow);
                    button.setText("Starting...");
                    try {
                        mBTSocket.getOutputStream().write(on.getBytes());
                        mBTSocket.getOutputStream().write(on.getBytes());
                        mBTSocket.getOutputStream().write(on.getBytes());
                        button.setText("Started");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }});

            btnOff.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
                    Button button = (Button)findViewById(R.id.stopCollect);
                    button.setText("Stopping...");
                    try {
                        mBTSocket.getOutputStream().write(off.getBytes());
                        //mBTSocket.getOutputStream().write(off.getBytes());
                        //mBTSocket.getOutputStream().write(off.getBytes());
                        //mBTSocket.getOutputStream().write(off.getBytes());
                        button.setText("Stop Sent");
                        final int BUFFER_SIZE = 32768;
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytes = 0;
                        int b = BUFFER_SIZE;
                        boolean gotData = false;
                        int count = 0;

                        while (!gotData) {
                            InputStream inStream = null;
                            try {
                                inStream = mBTSocket.getInputStream();
                                button.setText("Input Stream Found");
                            } catch (IOException e) {
                                e.printStackTrace();
                                button.setText("No Input Stream " + count++);
                            }
                            try {
                                if (inStream != null){
                                    gotData = true;
                                    bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
                                    String data = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                        data = new String(buffer, StandardCharsets.UTF_8);
                                    }
                                    button.setText(data);
                                }
                                else{
                                    button.setText("No Data " + count);
                                    count++;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        button.setText("Error");
                    }
                }
            });
        }

        private class ReadInput implements Runnable {

            private boolean bStop = false;
            private Thread t;

            public ReadInput() {
                t = new Thread(this, "Input Thread");
                t.start();
            }

            public boolean isRunning() {
                return t.isAlive();
            }

            @Override
            public void run() {

            }

            public void stop() {
                bStop = true;
            }

        }

        private class DisConnectBT extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Void doInBackground(Void... params) {//cant inderstand these dotss

                if (mReadThread != null) {
                    mReadThread.stop();
                    while (mReadThread.isRunning())
                        ; // Wait until it stops
                    mReadThread = null;

                }

                try {
                    mBTSocket.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                mIsBluetoothConnected = false;
                if (mIsUserInitiatedDisconnect) {
                    finish();
                }
            }

        }

        private void msg(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPause() {
            if (mBTSocket != null && mIsBluetoothConnected) {
                new DisConnectBT().execute();
            }
            Log.d(TAG, "Paused");
            super.onPause();
        }

        @Override
        protected void onResume() {
            if (mBTSocket == null || !mIsBluetoothConnected) {
                new ConnectBT().execute();
            }
            Log.d(TAG, "Resumed");
            super.onResume();
        }

        @Override
        protected void onStop() {
            Log.d(TAG, "Stopped");
            super.onStop();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
// TODO Auto-generated method stub
            super.onSaveInstanceState(outState);
        }

        private class ConnectBT extends AsyncTask<Void, Void, Void> {
            private boolean mConnectSuccessful = true;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(SecondActivity.this, "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554
            }

            @Override
            protected Void doInBackground(Void... devices) {

                try {
                    if (mBTSocket == null || !mIsBluetoothConnected) {
                        mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        mBTSocket.connect();
                    }
                } catch (IOException e) {
// Unable to connect to device`
                    // e.printStackTrace();
                    mConnectSuccessful = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                if (!mConnectSuccessful) {
                    Toast.makeText(getApplicationContext(), "Could not connect to device.Please turn on your Hardware", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    msg("Connected to device");
                    mIsBluetoothConnected = true;
                    mReadThread = new ReadInput(); // Kick off input reader
                }

                progressDialog.dismiss();
            }

        }
        @Override
        protected void onDestroy() {
            // TODO Auto-generated method stub
            super.onDestroy();
        }

        public void openMainActivity(View view){
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
        }
    }

