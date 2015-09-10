package com.montimage.mmt.client.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The exclusion feature can be used to restrict the impact of global and recursive meters.
 * To improve the performance of the target application, after heavy instrumenting, 
 * the exclude feature can be used to remove code parts from the monitoring scope. 
 * Methods, Attributes and Classes annotated with @Exclude are excluded from monitoring. 
 * The exclusion property is recursively transferred to the reachable code set.
 *
 * Created Sep 7, 2015
 * @author Huu Nghia NGUYEN <huunghia.nguyen@montimage.com>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Exclude {
}