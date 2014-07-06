package com.bitalino.BITlog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author bgamecho
 *
 */
public class LogStoreActivity extends Activity {

	private final String TAG = "LogStoreActivity";
	
	TextView tv;
	Button buttonSendLog;
	Button buttonDeleteLog;
	Spinner spinnerFiles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_store);
		
		tv = (TextView) findViewById(R.id.textView3);
		
		buttonSendLog = (Button) findViewById(R.id.ButtonSendLog);
		buttonSendLog.setOnClickListener(
				new OnClickListener() {
					 
					@Override
					public void onClick(View v) {
						 if(spinnerFiles.getAdapter().getCount()==0){
		            		 toast("There is no file to send");
		            	 }else{
		            		 String myFile = spinnerFiles.getSelectedItem().toString();
		            		 sendEmail(getUsername()+"@gmail.com", new File(spinnerFiles.getSelectedItem().toString()));
		            		 Log.v(TAG, "the file: "+myFile + " has been sent by email");
		            	 }
					}
				});
		
		buttonDeleteLog = (Button) findViewById(R.id.ButtonDeleteLog);
		buttonDeleteLog.setOnClickListener(
			new OnClickListener() {
				 
				@Override
				public void onClick(View v) {
				   	 if(spinnerFiles.getAdapter().getCount()==0){
	            		 toast("There is no file to delete");
	            	 }else{
	            			 
		            	 String myFile = spinnerFiles.getSelectedItem().toString();
		            	 Log.v(TAG, "the file: "+myFile +" has been deleted ");
		           // 	 String path = phf.getActivity().getFilesDir().getPath(); 
	    	   		//     Log.v(TAG, "exportPath: "+path+"/"+myFile);
	    	   		     deleteFile(myFile);
	    	   		     
	    	   		  toast("File "+myFile+ " was deleted");
	    	   		     
	    	   		  	updateSpinner();
	
	            	 }    

				}
			});
		
		
		spinnerFiles = (Spinner) findViewById(R.id.spinnerFiles);
		String[] myFilesList = this.fileList();

		ArrayAdapter<String> spinnerArrayFiles = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, myFilesList);
		spinnerArrayFiles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinnerFiles.setAdapter(spinnerArrayFiles);
		spinnerFiles.setSelection(0);

		
		Log.v(TAG, "MobileBit Activity --OnCreate()--");
	}
	
	/**
	 * 
	 */
	public void updateSpinner(){
		     String[] myFilesList = fileList();
		     ArrayAdapter<String> spinnerArrayFiles = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, myFilesList);
		spinnerArrayFiles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinnerFiles.setAdapter(spinnerArrayFiles);
		spinnerFiles.setSelection(0);
	}
	
	@Override 
	public void onStart(){
		super.onStart();
		Log.v(TAG, "MobileBit Activity --OnStart()--");
	}
	
	public void onResume(){
		super.onResume();
		Log.v(TAG, "MobileBit Activity --OnResume()--");
	}
	
	public void onPause(){
		Log.v(TAG, "MobileBit Activity --OnPause()--");
		super.onPause();
	}

	public void onStop(){
		Log.v(TAG, "MobileBit Activity --OnStop()--");
		super.onStop();
	}
	
	public void onRestart(){
		super.onRestart();
		Log.v(TAG, "MobileBit Activity --OnRestart()--");
	}

	@Override
	public void onDestroy(){
		Log.v(TAG, "MobileBit Activity --OnDestroy()--");
		super.onDestroy();	
	}

	/**
	 * 
	 * @param mEmail
	 * @param filename
	 */
	public void sendEmail(String mEmail, File filename){
	    final Intent emailIntent = new Intent( android.content.Intent.ACTION_SEND);

	    Context ctx = BITlog.getContext();
	    emailIntent.setType("plain/text");
	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { mEmail });
	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT , "Data log");
	    Date cDate = new Date();
	    String fDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(cDate);

	    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, fDate);
      
//	    Log.v(TAG, "File to be sent: "+filename);
	    String path = ctx.getFilesDir().getPath(); 
	    File exportPath = new File(path+"/"+filename);
	    Log.v(TAG, "exportPath: "+path+"/"+filename);
	      
	      
	    //*****************
//	      
//		File f = ctx.getFilesDir();
//		Log.v(TAG, "ctx.getFilesDir: "+f);
//		String[] myList = ctx.fileList();
//		for(String myL : myList)
//			Log.v(TAG, "...filesList: "+myL);
//		
		//*****************
	      
	   
//		try {
//			FileInputStream fin = openFileInput(filename.toString());
//			
//			int c;
//			String temp="";
//			while( (c = fin.read()) != -1){
//			   temp = temp + Character.toString((char)c);
//			}
//			Log.v(TAG, temp);
//			//string temp contains all the data of the file.
//			fin.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	      
		//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filename));
		  
		emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(exportPath));
		  
		//emailIntent.putExtra(Intent.EXTRA_STREAM,"/data/data/com.bitalino.BITlog/files/"+filename);
	      
		ctx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
  	
	}
	
	/**
	 * 
	 * @param message
	 */
	public void toast(String message){
		   Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
		  
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUsername(){
	    AccountManager manager = AccountManager.get(this);
	    Account[] accounts = manager.getAccountsByType("com.google"); 
	    List<String> possibleEmails = new LinkedList<String>();

	    for (Account account : accounts) {
	      // TODO: Check possibleEmail against an email regex or treat
	      // account.name as an email address only for certain account.type values.
	      possibleEmails.add(account.name);
	    }

	    if(!possibleEmails.isEmpty() && possibleEmails.get(0) != null){
	        String email = possibleEmails.get(0);
	        String[] parts = email.split("@");
	        if(parts.length > 0 && parts[0] != null)
	            return parts[0];
	        else
	            return null;
	    }else
	        return null;
	}

	
}
