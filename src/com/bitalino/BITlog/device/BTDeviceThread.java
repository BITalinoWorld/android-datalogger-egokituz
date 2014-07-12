package com.bitalino.BITlog.device;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 *
 *
 * @author bgamecho
 *
 */
public abstract class BTDeviceThread extends Thread {

	public static String TAG;
	
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	private boolean terminateFlag;
	private boolean beforeStart;
	
	public Handler myHandler;
	
	// Adapter for the local Bluetooth adapter (the device's actual antenna)
	public BluetoothDevice _bluetoothDev;

	public BluetoothSocket _socket;

	public InputStream _inStream;
	public OutputStream _outStream;
	
	
	/**
	 * 
	 * @param myHandler
	 * @throws Exception 
	 */
	public BTDeviceThread(Handler myHandler) throws Exception{
		terminateFlag = false;
		beforeStart = true;
		this.myHandler = myHandler;
	}
	
	/**
	 * Send messages to the class from we received the myHandler
	 * @param code
	 * @param value
	 */
	public void sendMessage(String code, String value){
		Message msg = new Message();
		Bundle myDataBundle = new Bundle();
		myDataBundle.putString(code,value);
		msg.setData(myDataBundle);
		myHandler.sendMessage(msg);  
	}
	
	/**
	 * Looks for the robot in Paired Devices List
	 *  TODO Upgrade this method to discover new devices, etc...
	 */
	public void setupBT(String devAddress) throws Exception{

		final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

		if(btAdapter==null){
			Log.e(TAG, "BT Adapter is not available");
			throw new Exception("mBluetoothAdapter is null");
		}
			
		_bluetoothDev = btAdapter.getRemoteDevice(devAddress);

		if(_bluetoothDev == null){
			Log.e(TAG, "Can't obtain a device with that address");
			throw new Exception("_bluetoothDev is null");
		}
	
	}

	/**
	 * Establish the communication wit the device through sockets
	 */
	public void initComm() throws Exception{

		try {
			_socket = _bluetoothDev.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) {
			Log.e(TAG, "Error connecting in the first attempt");
			throw new Exception(e);
		}
		
		if(_socket==null){
			Log.e(TAG, "Can't create a socket ");
			throw new Exception("_socket is null");
		}
			
		try {
			_socket.connect();
		} catch (IOException e) {
			Log.e(TAG, "Error connecting with the socket");
			throw new Exception(e);
		}

		try {
			_inStream = _socket.getInputStream();
			_outStream = _socket.getOutputStream();
		}catch (Exception e){
			Log.e(TAG, "Error getting the input/output stream");
			throw new Exception(e);
		}
		
		if(_outStream==null || _inStream==null){
			Log.e(TAG, "Unable to obtain an _outStream with the Robot");	
			throw new Exception("_outStream is null");
		}
	}
	
	@Override
	public void run() {
		initialize();
		
		while(!terminateFlag) {
//		while(!Thread.currentThread().isInterrupted()&&!terminateFlag){

			loop();

		}// end the while loop
		
		close();
	}
	
	/**
	 * 
	 */
	public void initialize(){
		beforeStart = false;
	}
	
	/**
	 * 
	 */
	public abstract void loop();
	
	/**
	 * 
	 */
	public void close(){
		
		// After the threads ends close the connection and release the socket connection 
		try {
			_inStream.close();
			_outStream.close();
			_socket.close();
		} catch (IOException e) {
			Log.e(TAG, "Closing the connection with the BT Device");
			e.printStackTrace();
		}
		
		this.sendMessage("OFF", this.getName());
	}
	
	/**
	 * Stops the thread in a safe way
	 */
	public void finalizeThread(){
		
		// if the thread is alive the socket is alredy open and used
		if(this.isAlive()){
			terminateFlag = true;
			Log.d(TAG, this.getName()+" is passing from alive to death state ");
			
		// else the socket is only open but not used
		}else{
			// if the thread hasn't started yet
			if(beforeStart){ //Release the socket connection
				Log.d(TAG, this.getName()+" thread closes without starting the loop");
				close();
			// if the thread is finished the socket is already free
			}else{	
				Log.d(TAG, this.getName()+" thread is finished and in death state");
			}
		}
		
		
	}

}
