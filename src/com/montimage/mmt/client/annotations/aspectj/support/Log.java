package com.montimage.mmt.client.annotations.aspectj.support;


import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

import com.montimage.mmt.client.annotations.Mask;
import com.montimage.mmt.client.connector.MMTClientConnector;

public class Log {
	static LogBuffer log_buffer;
	static boolean stdout_enabled = false;
	static long log_mask = Mask.ALL;
	static MMTClientConnector connector = null;


	
	// This is how the logger needs to be initialized (by the
	// NotificationModule)
	public static void connect_remote(MMTClientConnector con)  {
		connector = con;
	}

	// This is the second step that might need to be called (for debugging).
	public static void enable_log_to_stdout(boolean enable) {
		stdout_enabled = enable;
	}

	private static void i(String eventName, String msg, JoinPoint jp) {
		i(eventName, msg, jp, Log.getAttributeStore());
	}

	private static void i(String eventName, String msg, JoinPoint jp, AttributeStore as) {
		if (jp != null)
			add_info(as, jp);
		String rest = as.toString();
		if (stdout_enabled) {
			if (!rest.equals(""))
				System.out.println(msg + " -- " + rest);
			else
				System.out.println(msg);
		}
		as.push_front(Mask.LOG_MSG, msg);
		if (log_buffer != null)
			log_buffer.log(as);
		if (connector != null)
			try {
				connector.processEvent(System.currentTimeMillis(), eventName, as.toMMTHeader());
			} catch (Exception e) {
				System.err.println("Remote log error: " + e);
			}
	}

	public static void notif_attribute(String msg, JoinPoint jp, AttributeStore as) {
		as.push_back(Mask.ATTRIBUTE_VALUE, get_attribute_value(jp));
		i("Notif", msg, jp, as);
	}

	public static void notif_ping(JoinPoint jp) {
		i("Notif", "Ping", jp);
	}
	public static void notif_method_enter(JoinPoint jp, AttributeStore as) {
		i("Notif", "Method Enter", jp, as);
	}

	public static void notif_method_leave(JoinPoint jp, AttributeStore as) {
		i("Notif", "Method Leave", jp, as);
	}

	static String get_attribute_value(JoinPoint a) {
		Object[] args = a.getArgs();
		if (args == null || args.length < 1)
			return "";
		return args[0].toString();
	}

	private static void add_info(AttributeStore as, JoinPoint jp) {
		add_source_location(as, jp);
		as.push_back(Mask.THREAD_NAME, Thread.currentThread().getName());
		as.push_back(Mask.THREAD_COUNT, Thread.activeCount());
		as.push_back(Mask.TIME_STAMP, System.currentTimeMillis());
		Counter.log(as);
	}

	static void add_source_location(AttributeStore as, JoinPoint jp) {
		Signature sig = jp.getSignature();
		as.push_back(Mask.NAME, def(sig.getDeclaringTypeName()) + "." + def(sig.getName()));
		SourceLocation so = jp.getSourceLocation();
		as.push_back(Mask.SOURCE_LINE, def(so.getFileName()) + ":" + def(Integer.toString(so.getLine())));

	}

	// return default value on null
	private static String def(String s) {
		return (s == null) ? "" : s;
	}

	static void get_parameters(JoinPoint a, AttributeStore as) {
		Object[] args = a.getArgs();
		if (args == null || args.length == 0)
			return;
		String params = StringUtils.join(args, ", ");
		String buf = String.format("[%s]", params);
		as.push_back(Mask.METHOD_PARAMS, buf);
	}

	static void get_return_value(JoinPoint a, AttributeStore as) {
		Object[] args = a.getArgs();
		if (args == null || args.length == 0)
			return;
		as.push_back(Mask.RETURN_VALUE, args[0]);
	}

	public static LogBuffer log_to_buffer() {
		log_buffer = new LogBuffer();
		return log_buffer;
	}

	public static void setLogMask(long mask) {
		log_mask = mask;
	}

	public static long getLogMask() {
		return log_mask;
	}

	public static AttributeStore getAttributeStore() {
		return getAttributeStore(log_mask);
	}

	public static AttributeStore getAttributeStore(long mask) {
		AttributeStore a = new AttributeStore();
		a.setLogMask(mask);
		return a;
	}
}
