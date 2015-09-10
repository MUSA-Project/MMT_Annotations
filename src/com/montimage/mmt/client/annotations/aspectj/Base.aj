package com.montimage.mmt.client.annotations.aspectj;

import com.montimage.mmt.client.annotations.Exclude;

public abstract aspect Base {
	pointcut exclude_element(): 
		execution(@Exclude * *(..)) || set(@Exclude * *);

	pointcut exclude_class() : 
		(set(* *) || execution(* *(..))) 
			&& within(@Exclude *);

	pointcut exclude(): cflow(exclude_element() || exclude_class());

	protected pointcut scope(): !(	
			within(com.montimage.mmt.client.annotations.*)     ||
			within(com.montimage.mmt.client.annotations.*.*)   || 
			within(com.montimage.mmt.client.annotations.*.*.*) ||
			exclude() ); // Eliminate aspects from operation scope
}
