package com.montimage.mmt.client.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class monitor combines Method monitor and Attribute monitor, 
 * and applies them to all methods and attributes of the class.</br>
 * Using <code>mask</code> to set information to be monitored, 
 * e.g., @Monitor(Mask.ALL) to monitor anything.
 * 
 * @see {@link com.montimage.mmt.client.annotations.Mask}
 * 
 * @author Huu Nghia NGUYEN <huunghia.nguyen@montimage.com>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitor {
	long value() default Mask.ALL;
}