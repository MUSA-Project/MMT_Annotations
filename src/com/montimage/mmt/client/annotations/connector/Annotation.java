package com.montimage.mmt.client.annotations.connector;

import com.montimage.mmt.client.annotations.aspectj.support.Log;
import com.montimage.mmt.client.connector.MMTClientConnector;

public class Annotation {

	/**
	 * Enable/disable output reports to the standard output.
	 * 
	 * @param enable
	 */
	public static void enableStdout(Boolean enable) {
		Log.enable_log_to_stdout(enable);
	}

	/**
	 * Connect to MMT Server which will receive reports.
	 * 
	 * @param connector
	 */
	public static void setMMTConnector(MMTClientConnector con) {
		Log.connect_remote(con);
		;
	}

	/**
	 * Use values from sl.Const to construct mask.<br/>
	 * <li>To log everything but method parameters and source lines, use xor:
	 * <br/>
	 * mask = ALL ^ METHOD_PARAMS ^ SOURCE_LINES
	 * <li>To log only source_line and object name: <br/>
	 * mask = SOURCE_LINE | NAME
	 * 
	 * @see {@link com.montimage.mmt.client.annotations.Mask}
	 */
	public static void setMask(long mask) {
		Log.setLogMask(mask);
	}
}
