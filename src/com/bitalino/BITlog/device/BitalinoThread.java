package com.bitalino.BITlog.device;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bitalino.comm.BITalinoDevice;
import com.bitalino.comm.BITalinoException;
import com.bitalino.comm.BITalinoFrame;

/**
 * 
 * @author bgamecho
 *
 */
public class BitalinoThread extends BTDeviceThread{

	public final static String TAG ="BITalinoThread";

	private int sample_rate;
	private int nFrames;
	private int packNum;
//	private int bitalinoMode; 
	private boolean downsample = false;
	
	private BITalinoDevice _bitalino_dev = null;
	
	/**
	 * BITalinoThreadConstructor
	 * @param myHandler
	 */
	public BitalinoThread(Handler myHandler, String remoteDevice) throws Exception{
		super(myHandler);

		this.setName("BITalinoThread");

		super.setupBT(remoteDevice);

		super.initComm();

		sendMessage("OK", "Connected to BITalino device at: "+_bluetoothDev.getAddress());

	}
	
//	public void setLed(boolean val){
//		int led = (val) ? 1 : 0;
//		int[] digOutputs = {0, 0, led, 0};
//		try {
//			_bitalino_dev.setDigitalOutput(digOutputs);
//		} catch (BITalinoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	int[] channels; 
	public void setChannels(int[] channels){
		this.channels = channels;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		 
		packNum = 0;
		_bitalino_dev = null;

		try {
			_bitalino_dev = new BITalinoDevice(sample_rate, channels);
			_bitalino_dev.open(_inStream, _outStream);		
			
			//String firmVersion = _bitalino_dev.version();
			//Log.v(TAG, "Firm Version: "+firmVersion);
			
			//_bitalino_dev.setBattThreshold(3.8); // Only Works in LIVE MODE
						
//			_bitalino_dev.start(this.bitalinoMode, channels);
			_bitalino_dev.start();
		
			// Led in the Bitalino Boards, blinks twice
//			if(_bitalino_dev.getMode()==BITalinoDevice.LIVE_MODE){
//				setLed(true);
//				Thread.sleep(100);
//				setLed(false);
//				Thread.sleep(50);
//				setLed(true);
//				Thread.sleep(100);
//				setLed(false);
//			}
					
			
			
		} catch (BITalinoException e2) {
			e2.printStackTrace();
		}
//		catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   		
	}

	@Override
	public void loop() {

		try {		
			
			//Blocking task
			BITalinoFrame[] frames = new BITalinoFrame[nFrames];
			
			Log.i(TAG, "Monitor: Read "+(packNum++)+" :"+ System.currentTimeMillis());
			//ApplicationClass.showTop();
			
			frames = _bitalino_dev.read(nFrames);
			
			if(downsample){
				BITalinoFrame[] halfFrames = new BITalinoFrame[nFrames/2];
				for(int i = 0; i<halfFrames.length; i++){
					halfFrames[i]= frames[i*2]; 			
				}
				frames = halfFrames;
				//Log.v(TAG, "Downsample: enable");
			}

			
			//Send the data
			Message msg = new Message();
		
			Bundle myDataBundle = new Bundle();
			myDataBundle.putSerializable("bitalino_data", frames);
			msg.setData(myDataBundle);
			myHandler.sendMessage(msg);       		

		} catch (BITalinoException e1) {
			Log.e(TAG, "Error with Bitalino");
			e1.printStackTrace();
		}

	}

	@Override
	public void close() {
		
		if(_bitalino_dev!=null){
			// Stop the data acquisition
			try {
//				if(_bitalino_dev.getMode()==BITalinoDevice.LIVE_MODE){
//					setLed(true);				
//					Thread.sleep(500);
//					setLed(false); // Only works in LIVE mode
//				}
				_bitalino_dev.stop();
			} catch (BITalinoException e) {
				Log.e(TAG, "Problems closing the BITalino device");
				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		super.close();
	}

	public int getSampleRate() {
		return sample_rate;
	}

	public void setSampleRate(int sample_rate) {
		this.sample_rate = sample_rate;
	}

	public int getNumFrames() {
		return nFrames;
	}

	public void setNumFrames(int nFrames) {
		this.nFrames = nFrames;
	}	

//	public void setMode(int Mode){
//			this.bitalinoMode = Mode;
//	}
	
	public void setDownsamplingOn(boolean downsample){
		this.downsample = downsample;
		
	}
}