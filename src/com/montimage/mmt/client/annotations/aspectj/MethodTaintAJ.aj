package com.montimage.mmt.client.annotations.aspectj;

import com.montimage.mmt.client.annotations.Taint;
import com.montimage.mmt.client.annotations.aspectj.support.MethodDuration;

public aspect MethodTaintAJ extends Base {
	pointcut methods(Taint m): 
		execution(@Taint * *(..)) &&
		@annotation(m); // Methods with @Monitor annotation
	
	pointcut monitor(Taint m): 
		cflow(methods(m)) && execution(* *(..)) 
		&& scope();
	
	Object around(Taint m) : monitor(m) {
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
