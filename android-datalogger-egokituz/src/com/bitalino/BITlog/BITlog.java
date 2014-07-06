package com.bitalino.BITlog;

import android.app.Application;
import android.content.Context;

/**
 * 
 * @author bgamecho
 *
 */
public class BITlog extends Application {

	public static String MAC="none";
	public static int SamplingRate=100;
	public static int frameRate= 30;
	public static int[] channels = {0,1,2};

	public static boolean digitalOutputs = false;
	
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        BITlog.mContext = mContext;
    }

}
