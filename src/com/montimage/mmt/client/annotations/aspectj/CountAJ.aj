package com.montimage.mmt.client.annotations.aspectj;

import org.aspectj.lang.JoinPoint;
import com.montimage.mmt.client.annotations.*;
import com.montimage.mmt.client.annotations.aspectj.support.Counter;

public abstract aspect CountAJ extends Base {
	//all initilization with @Count annotation
	pointcut count_class(): 
		initialization( *.new(..) ) 
		&& scope() 
		&& within(@Count *);
	
	before() : count_class() {
		System.out.println("count");
		JoinPoint jp = thisJoinPoint;
		Counter.singleton().add(jp.getTarget(), jp.getTarget().getClass().getName());
	}
}
