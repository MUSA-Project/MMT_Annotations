package com.montimage.mmt.client.annotations;

import java.util.HashMap;

/**
 * This is used for selecting which information will be monitored, 
 * such as, RETURN_VALUE, SOURCE_LINE, ... 
 *
 * @author Huu Nghia NGUYEN <huunghia.nguyen@montimage.com>
 */
public class Mask {
	// 64 bits ought to be enough for everyone! ;)
	// OK, let 16bits for the moment
	private static HashMap<Long, String> map = init();
	public static final long
	ATTRIBUTE_VALUE = 1 << 0,
	CLASS_ID        = 1 << 1,
	CLASS_INSTANCES = 1 << 2,
	DURATION        = 1 << 3,
	EXCEPTION       = 1 << 4,
	EXIT_STATUS     = 1 << 5,
	LOG_MSG         = 1 << 6,
	NAME            = 1 << 7,
	METHOD_PARAMS   = 1 << 8,
	RETURN_VALUE    = 1 << 9,
	SOURCE_LINE     = 1 << 10,
	THREAD_COUNT    = 1 << 11,
	THREAD_NAME     = 1 << 12,
	TIME_STAMP      = 1 << 13,
	TRACKED_OBJECTS = 1 << 14,
	ALL             = (1 << 16)-1,
	NONE            = 0;

	static private HashMap<Long, String> init() {
		HashMap<Long, String> m = new HashMap<Long, String>();
		m.put(ATTRIBUTE_VALUE, "attribute_value");
		m.put(CLASS_ID, "class_id");
		m.put(CLASS_INSTANCES, "class_instances");
		m.put(DURATION, "duration[microsec]");
		m.put(EXCEPTION, "exception");
		m.put(EXIT_STATUS, "exit_status");
		m.put(LOG_MSG, "msg");
		m.put(NAME, "name");
		m.put(METHOD_PARAMS, "parameters");
		m.put(RETURN_VALUE, "return_value");
		m.put(SOURCE_LINE, "source");
		m.put(THREAD_COUNT, "thread_count");
		m.put(THREAD_NAME, "thread_name");
		m.put(TIME_STAMP, "time_stamp");
		m.put(TRACKED_OBJECTS, "tracked_objects");
		return m;
	}

	public static String getName(long l) {
		return map.get(l);
	}
}
