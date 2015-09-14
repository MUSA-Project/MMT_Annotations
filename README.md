#MMTAnnotations#

This project defines a set of Java annotations that allows to monitor status of a Java class, a method, or a field of a class.

For example, add `@Monitor` annotation before a method declaration to report the method status (when it is called, returned values, etc).

The reports can be output to the standard output or sent to MMTServer by using [MMTConnector](https://github.com/Inter-Trust/MMT_Connector).


**Requirements**


- AspectJ
- MMT_Connector (optional, if you want to report information to MMTServer)

##Quick start##

1. Download and install [AspectJ](https://eclipse.org/aspectj/)

2. Create an AspectJ project containing the following code:

To start using the MMTAnnotations, you have to initialize it in the start-up section of your code. The following snippet shows how to do this.

```Java
package foo;
import com.montimage.mmt.client.annotations.connector.Annotation;
import com.montimage.mmt.client.annotations.Mask;
public class Main {
	public static void main(String[] args) throws Exception {
	    //Define which information will be reported, by default: Mask.ALL
		Annotation.setMask(Mask.NAME | Mask.SOURCE_LINE | Mask.TIME_STAMP);
		
		//Print reported information to the standard ouput	
		Annotation.enableStdout(true);
		
		//Use MMTClientConnector to report information to MMTServer
		//MMTClientConnector con = new MMTClientConnector(100);
        //configuration 
        //Annotation.setMMTConnector(con);
        
        //Perform some calculation ....
		A a = new A();
		a.f();
		a.g();
		
		A aa = new A();
        aa.g();
        aa = new A();
	}
}
```

The class `A` is defined as the following:

```Java
package foo;
import com.montimage.mmt.client.annotations.*;

@Count
public class A {
    
    @Monitor
    int x;
    
    public A() {
        x = 0;
    }
    
    @Ping
    public void f() {
        x = x + 1;
    };
    
    @Taint( Mask.NAME | Mask.SOURCE_LINE | Mask.DURATION | Mask.TIME_STAMP)
    public void g() {h();};
    public void h(){i();};
    public void i(){};
    
}
```

3. Download and add [`mmt-annotation-1.0.jar`](blob/master/distribution/mmt-annotation-1.0.jar) in the `Aspect Path` of the project.

4. Build and run project, we will obtain an output as the following:

```
Counter -- class_id:examples.A, class_instances:1, tracked_objects:1
Attribute changed -- attribute_value:0, name:examples.A.x, source:A.java:12, thread_name:main, thread_count:1, time_stamp:1441974368767, class_id:examples.A, class_instances:1, tracked_objects:1
Ping -- name:examples.A.f, source:A.java:15, time_stamp:1441974368768
Attribute changed -- attribute_value:1, name:examples.A.x, source:A.java:16, thread_name:main, thread_count:1, time_stamp:1441974368768, class_id:examples.A, class_instances:1, tracked_objects:1
Method Enter -- name:examples.A.g, source:A.java:20, time_stamp:1441974368774
Method Enter -- name:examples.A.h, source:A.java:23, time_stamp:1441974368774
Method Enter -- name:examples.A.i, source:A.java:25, time_stamp:1441974368774
Method Leave -- duration[microsec]:99.000, name:examples.A.i, source:A.java:25, time_stamp:1441974368784
Method Leave -- duration[microsec]:10148.000, name:examples.A.h, source:A.java:23, time_stamp:1441974368784
Method Leave -- duration[microsec]:10517.000, name:examples.A.g, source:A.java:20, time_stamp:1441974368785
Counter -- class_id:examples.A, class_instances:2, tracked_objects:2
Attribute changed -- attribute_value:0, name:examples.A.x, source:A.java:12, thread_name:main, thread_count:1, time_stamp:1441974368785, class_id:examples.A, class_instances:2, tracked_objects:2
Method Enter -- name:examples.A.g, source:A.java:20, time_stamp:1441974368785
Method Enter -- name:examples.A.h, source:A.java:23, time_stamp:1441974368785
Method Enter -- name:examples.A.i, source:A.java:25, time_stamp:1441974368785
Method Leave -- duration[microsec]:71.000, name:examples.A.i, source:A.java:25, time_stamp:1441974368785
Method Leave -- duration[microsec]:298.000, name:examples.A.h, source:A.java:23, time_stamp:1441974368785
Method Leave -- duration[microsec]:524.000, name:examples.A.g, source:A.java:20, time_stamp:1441974368786
Counter -- class_id:examples.A, class_instances:3, tracked_objects:3
Attribute changed -- attribute_value:0, name:examples.A.x, source:A.java:12, thread_name:main, thread_count:1, time_stamp:1441974368786, class_id:examples.A, class_instances:3, tracked_objects:3
```

##Annotations##

The list of annotations is presented [here](Annotations.md).
