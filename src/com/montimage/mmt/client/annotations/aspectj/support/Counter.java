package com.montimage.mmt.client.annotations.aspectj.support;

import java.util.HashMap;
import java.util.WeakHashMap;

import com.montimage.mmt.client.annotations.Mask;


// Counts number of object instances/class. 
// Garbage collected objects are automatically removed.
public class Counter {
	// Each type is tracked by a different HashMap
	static AttributeStore as = new AttributeStore();
	
	@SuppressWarnings("serial")
	static class Map extends HashMap<String, WeakHashMap<Object, Integer>> {
		public void add(Object o, String class_id) {
			WeakHashMap<Object, Integer> key = get(class_id);
			if(key == null) put(class_id, new WeakHashMap<Object, Integer>());
			get(class_id).put(o, 0);
		}
		
		public void dump(AttributeStore as) {
			int sum = 0;
			for(String key: keySet()) {
				int n = get(key).size();
				sum += n;
				as.push_back(Mask.CLASS_ID, key);
				as.push_back(Mask.CLASS_INSTANCES, n);
			}
			if(sum>0) as.push_back(Mask.TRACKED_OBJECTS, sum);
		}
	}
	
	static Counter counter = new Counter();
	static Map map = new Map();
	private Counter() {}
	public static Counter singleton() {	return counter;	}
	public void add(Object ref, String ref_class_id) {	
		map.add(ref, ref_class_id);
		
		as = new AttributeStore();
		map.dump(as);
		Log.notif_counter(as);
	}
	public static void log(AttributeStore as) {	map.dump(as);	}
	public static void clear() {map.clear();}
}
