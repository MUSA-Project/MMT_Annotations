package com.montimage.mmt.client.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The ping monitor generates a signal whenever a function is called. 
 * You can regard it as a lower impact alternative to the method monitor, 
 * because duration, exit status and returning from the function are not tracked. 
 * You can use it for example, to monitor application heartbeat by hooking it to a frequently called method.
 *
 * Created Sep 7, 2015</br>
 * @author Huu Nghia NGUYEN <huunghia.nguyen@montimage.com>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ping {
}