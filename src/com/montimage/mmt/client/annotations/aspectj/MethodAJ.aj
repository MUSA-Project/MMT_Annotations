package com.montimage.mmt.client.annotations.aspectj;

import com.montimage.mmt.client.annotations.Monitor;
import com.montimage.mmt.client.annotations.aspectj.support.MethodDuration;

public aspect MethodAJ extends Base{
	
	pointcut methods(Monitor m): 
		execution(@Monitor * *(..)) 
		&& @annotation(m); // Methods with @Monitor annotation
	
	pointcut classes(Monitor m): 
		execution(* *(..)) 
		&& within(@Monitor *) 
		&& @annotation(m); //All methods within annotated class
	
	pointcut monitor(Monitor m): (methods(m) || classes(m)) && scope();
	
	Object around(Monitor m) : monitor(m) {
		MethodDuration md = new MethodDuration(thisJoinPoint, m.value());
		Object return_value = null;
		boolean success = false;
		try{
			return_value = proceed(m);
			success = true;
			return return_value;
		} catch(Exception e) {		
			md.set_exception(e);
		} finally {
			md.on_end(thisJoinPoint, success, return_value);
		}
		return null;
	}
}
