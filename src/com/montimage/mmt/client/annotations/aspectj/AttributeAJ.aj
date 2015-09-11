package com.montimage.mmt.client.annotations.aspectj;

import com.montimage.mmt.client.annotations.Monitor;
import com.montimage.mmt.client.annotations.aspectj.support.AttributeStore;
import com.montimage.mmt.client.annotations.aspectj.support.Log;

public aspect AttributeAJ extends Base {
	
	// All attribute setters within @Monitor annotated class
	/** Usage:
	 * @Monitor(Mask.NAME) 
	 * class A {
	 *  	long a;
	 *  	String b;
	 *  }
	 * Or
	 * class A {
	 * 	@Monitor(Mask.NAME | Mask.SOURCE_LINE ) long a;
	 *  @Monitor(Mask.SOURCE_LINE) String b;
	 * }
	 * 
	 */
	pointcut monitor(Monitor m): 
		((set(* *) && within(@Monitor *))
				|| set(@Monitor * *)) 
		&& @annotation(m) 
		&& scope()
		;
	before(Monitor m) : monitor(m) {
		AttributeStore as = Log.getAttributeStore(m.value());
		Log.notif_attribute("Attribute changed", thisJoinPoint, as);
	}	
	
}
