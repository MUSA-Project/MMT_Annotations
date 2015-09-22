#List of Annotations#

The table lists each monitor, along with its annotation mask, application scope, and targeted source code element. Global variants exist for several monitors that can be enabled by weaving in their corresponding aspect.

Annotation             | Target                   | Scope     | Using mask 
---------------------- | ------------------------ | --------- | -------------
[`@Monitor`](#monitor) | Class, method, attribute | local     | Mask
[`@Ping`](#ping)       | Method                   | local     | 
[`@Count`](#count)     | Class                    | local     |
[`@Exclude`](#exclude) | Class, method, attribute | local     |
[`@Tail`](#taint)       | Method                   | recursive | Mask

##Preliminaries##

###Scope###

Measurement points, or meters measure different aspects of the code during runtime, like execution duration, thread count or function name.
Monitoring meters work on two different scope levels:

- Local, and
- Recursive.

You can define local meters using Java annotations. Annotations are a part of the Java language, and provide a mechanism for annotating code with metadata. Annotations do not modify program execution by themselves, but they can be read during compile time or execution time. 

Meters operating at the local scope level are always marked by an annotation. The annotation may be bound to a class, method or attribute declaration. Only annotated elements are impacted by local scope meters. 

In the next scope level, recursive monitoring, annotations are used as well. Beside the annotated code, all code reachable through control flow is monitored, up to the available call depth. Call depth is limited by the available source code, because static aspects operate by modifying accessible source code. Our instrumentation therefore does not penetrate pre-compiled classes, like .class files or system libraries.

###Reported Information###

The Notification Aspect appends status information to each logged event. The status information string contains the following data:

- Monitored object name, which can be a class name, method name or attribute name;
- Source file name and source line number;
- Thread id of current thread;
- Total number of threads;
- Total number of tracked objects;
- Timestamp.

Sample output:

```
Method Leave -- exit_status:success, duration[microsec]:3034.000, return_value:null, name:examples.MainHelper.f, source:MainHelper.java:16, thread_name:main, thread_count:1, time_stamp:1441958247662
```

Output is made up of name-value pairs.


###Mask###

Applications that are sensitive to disclosing information to the remote logger can configure their logging output. Applications can set a mask that controls the logging output.  Valid options are specified in `Mask` class.

ID              | Description
--------------- | ----------------------------------------------------
ATTRIBUTE_VALUE | Value of the monitored attribute
CLASS_ID        | Id of classes monitored by `@Count`
CLASS_INSTANCES | Number of class instances monitored by `@Count`
DURATION        | Method duration monitored by `@Monitor`
EXCEPTION       | String representation of exception, thrown during execution of a monitored function.
EXIT_STATUS     | Exit status of method
LOG_MSG         | Description of log message 
NAME            | Name of join point, such as function, variable, etc.
METHOD_PARAMS   | Method parameters, disable for sensitive methods
RETURN_VALUE    | Method return value
SOURCE_LINE     | Source line of join point
THREAD_COUNT    | Number of threads in current process
THREAD_NAME     | Name of current thread
TIME_STAMP      | Time stamp
TRACKED_OBJECTS | Number of tracked objects
ALL             | Union of all of the above options. Use it to subtract options with XOR

By default `ALL` mask is used. You can use `|` to combine the masks as the following example:

```Java
@Monitor(Mask.NAME | Mask.SOURCE_LINE)
public class A {
    int a;
    public void func() {...}
    ...
}
```

Mask is used by the annotations `@Monitor` and `@Tail`.

##Annotations##

###@Monitor###

Using this meter you can measure

- the execution time of a method and monitor its exit status,
- changes made to attributes, and to log the new value,

#####Location#####

Before method or attribute declarations.

You can also put this annotation before a class declaration to monitor all methods and attributes of the class.

#####Reported Information#####

**Application for Method monitoring**

- Method start event
- Method end event
- Method duration
- Exit status
    - success –  on normal return
    - exception_thrown – if an exception was raised
    - exception text –  if an exception was raised
- Status  information

```Java
public class A{
    @Monitor
    void func() { ... }
}
```

In the usage sample, we annotated monitor_nested() with the @Monitor annotation. First, method monitor logs the function start event. It produces a second log event on function return, which includes the run-time duration and exit status. Because of local scope, method monitor does not track the call to nested() in the method body.

```
Method Enter -- name:examples.MainHelper.f, source:MainHelper.java:16, thread_name:main, thread_count:1, time_stamp:1441958247650
Method Leave -- exit_status:success, duration[microsec]:3034.000, return_value:null, name:examples.MainHelper.f, source:MainHelper.java:16, thread_name:main, thread_count:1, time_stamp:1441958247662
```

**Application for Attribute monitoring**

Attribute value, as returned by toString(), on each assignment operation

```Java
public class A {
    @Monitor
    public int x = -1;
    @Monitor
    private int y;
}
```

In the usage sample, we have annotated variable x and y with the @Monitor annotation. Attribute monitor generates a log entry for the variable x, on object initialization. Later in the code, we assign y the value 3 (not shown in the usage sample), which is logged by attribute monitor. A log about y does not appear in the log either, since no value is assigned to it.

```
Attribute changed -- attribute_value:0, name:examples.A.x, source:A.java:12, thread_name:main, thread_count:1, time_stamp:1441974143556, class_id:examples.A, class_instances:2, tracked_objects:2
Attribute changed -- attribute_value:1, name:examples.A.x, source:A.java:16, thread_name:main, thread_count:1, time_stamp:1441974143541, class_id:examples.A, class_instances:1, tracked_objects:1
```

###@Count###
Use the `@Count` monitor to track the number of objects on the memory heap. Count monitor increments a counter when a new object is created, and decrements the counter an object is finalized. Finalization occurs right before the garbage collector reclaims the memory allocated to the object. Each annotated class receives an individual counter.

#####Location#####
Before class declaration

#####Reported Information#####
The global object counter is increased and decreased by this meter. The object count total is part of the common status string, and is appended to every log output.

#####Example#####

```Java
@Count
public class A {...}

...
A a = new A();
a = new A();
```

We have added the `@Count` annotation to class A. On each call of the new operator the object, its counter is incremented. Eventually, when the object is no longer referenced, the garbage collector de-allocates it.

We will be reported information as the following:

```
Counter -- class_id:examples.A, class_instances:1, tracked_objects:1
Counter -- class_id:examples.A, class_instances:2, tracked_objects:2
```

###@Ping###
The ping monitor generates a signal whenever a function is called. You can regard it as a lower impact alternative to the method monitor, because duration, exit status and returning from the function are not tracked. You can use it for example, to monitor application heartbeat by hooking it to a frequently called method.

#####Location#####
Before method declaration

#####Reported Information#####
On each invocation of the method a ‘Ping’ message is added to the log.

#####Example#####

```Java
public class A{
    @Ping
    public void f(){}
}
```

We have annotated the `f()` function with the `@Ping` annotation. Whenever this function is executed, ping monitor produces a log event as the following:

```
Ping -- name:examples.A.f, source:A.java:15, time_stamp:1441968811999
```

###@Exclude###
The exclusion feature can be used to restrict the impact of global and recursive meters.

To improve the performance of the target application, after heavy instrumenting, the exclude feature can be used to remove code parts from the monitoring scope. Methods, Attributes and Classes annotated with @Exclude are excluded from monitoring. The exclusion property is recursively transferred to the reachable code set.


#####Location#####
Before class or method declaration

#####Example#####

```Java
@Monitor
public class A {
    int a;
    @Exclude
    public void func() {...}
    ...
}
```

We have added the `@Monitor` annotation to class A. This would generate log entries for attribute `a` and method `func()`. 
To omit `func()` from log generation, we have added the `@Exclude` annotation. Now, class monitor will only track attribute `a`, and no logs will be generated for `func()`.

###@Taint###

Local monitors allow us to focus on a single class, method or attribute. Recursive and global monitors work on a larger scope and provide a higher coverage of the source code base. Recursive monitors factor in the execution control flow, while global monitors target methods and attributes within the entire source code base.

You can use recursive monitoring, to implement taint analysis, in order to track the control flow of methods that process untrusted input. Taint monitor produces the same output as method monitor, but tracks all methods that are reachable by the control flow.

#####Location#####
Before method declaration

#####Reported Information#####

Outputs the same information as method monitor, for each method encountered by the control flow. Notably:

- duration, and
- exit status.

#####Example#####

```Java
public class A{
    @Taint( Mask.NAME | Mask.SOURCE_LINE | Mask.DURATION | Mask.TIME_STAMP)
    public void g() {h();};
    public void h(){i();};
    public void i(){};
}
```

We have annotated `taint_test()` with the `@Taint` annotation. Taint monitor tracks `taint_test()`, `nested()` and `inner()`, because they  are reachable by control flow from `taint_test()`. Note that they are not annotated with `@Taint`.

The output is as below:

```
Method Enter -- name:examples.A.g, source:A.java:20, time_stamp:1441974368774
Method Enter -- name:examples.A.h, source:A.java:23, time_stamp:1441974368774
Method Enter -- name:examples.A.i, source:A.java:25, time_stamp:1441974368774
Method Leave -- duration[microsec]:99.000, name:examples.A.i, source:A.java:25, time_stamp:1441974368784
Method Leave -- duration[microsec]:10148.000, name:examples.A.h, source:A.java:23, time_stamp:1441974368784
Method Leave -- duration[microsec]:10517.000, name:examples.A.g, source:A.java:20, time_stamp:1441974368785
```
