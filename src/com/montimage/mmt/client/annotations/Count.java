package com.montimage.mmt.client.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use the Count monitor to track the number of objects on the memory heap. 
 * Count monitor increments a counter when a new object is created, 
 * and decrements the counter an object is finalized. 
 * Finalization occurs right before the garbage collector reclaims the memory allocated to the object. 
 * Each annotated class receives an individual counter.
 *
 * Created Sep 7, 2015
 * @author Huu Nghia NGUYEN <huunghia.nguyen@montimage.com>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Count {
}