package com.montimage.mmt.client.annotations.aspectj;

import com.montimage.mmt.client.annotations.aspectj.support.Log;
import com.montimage.mmt.client.annotations.*;

public aspect PingAJ extends Base {
	pointcut methods(): 
		execution(@Ping * *(..)); // Methods with @Ping annotation
	
	pointcut all_in_class(): 
		execution(* *(..)) && within(@Ping * );
	
	pointcut monitor(): 
		(methods() || all_in_class()) && scope();
	
	before() : monitor() {
		Log.notif_ping(thisJoinPoint);
	}
}
