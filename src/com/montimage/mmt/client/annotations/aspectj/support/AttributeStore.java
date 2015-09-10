package com.montimage.mmt.client.annotations.aspectj.support;

import java.util.ArrayList;

import com.montimage.mmt.client.annotations.Mask;
import com.montimage.mmt.client.connector.GenericFieldValueHeader;
import com.montimage.mmt.client.connector.MMTFieldValueHeader;

public class AttributeStore {
	
	ArrayList<Attribute> attributes;
	boolean fail = false;
	long _mask = Mask.ALL;

	public AttributeStore(){
		attributes = new ArrayList<Attribute>();
	}
	public void setLogMask(long log_mask) {
		_mask = log_mask;
	}
	
	public void push_back(long mask, Object value) {
		if((mask & _mask) == 0) return;
		String n = Mask.getName(mask);
		attributes.add(new Attribute(n, value==null?"null":value));
	}
	
	public void push_front(long mask, Object value) {
		if((mask & _mask) == 0) return;
		attributes.add(0, new Attribute(Mask.getName(mask), value));
	}
	
	public String toString() {
		if(attributes.isEmpty()) return "";
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for(Attribute a: attributes) {
			if( ! first )
				buf.append(", ");
			buf.append(a.name + ":"+a.value);
			first = false;
		}
		return buf.toString();
	}
	

	public ArrayList<MMTFieldValueHeader> toMMTHeader() {
		ArrayList<MMTFieldValueHeader> ar = new ArrayList<MMTFieldValueHeader>();
		for(Attribute a: attributes) 
			ar.add(new GenericFieldValueHeader(a.name, a.value));
		return ar; 
	}
}
