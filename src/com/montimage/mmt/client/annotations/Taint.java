package com.montimage.mmt.client.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * You can use recursive monitoring, to implement taint analysis, 
 * in order to track the control flow of methods that process untrusted input. 
 * Taint monitor produces the same output as method monitor, 
 * but tracks all methods that are reachable by the control flow.
 *
 * Created Sep 7, 2015
 * @author Huu Nghia NGUYEN <huunghia.nguyen@montimage.com>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Taint {
	long value() default Mask.ALL;
}