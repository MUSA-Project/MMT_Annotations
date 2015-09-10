#MMT_Connector_Annotations#

This project defines a set of Java annotations that allows to monitor status of a Java class, a method, or a field of a class.

For example, add `@Monitor` annotation before a method declaration to report the method status (when it is called, returned values, etc) to MMT_Server.

The reports can be output to the standard output or sent to MMT_Server via using [MMT_Connector](https://github.com/hn-nguyen/MMT_Connector).


**Requirements**


- AspectJ
- MMT_Connector

##Quick start##

```Java
package foo;
import com.montimage.mmt.client.annotations.connector.Annotation;
import com.montimage.mmt.client.annotations.Mask;
public class Main {
	public static void main(String[] args) throws Exception {
		Annotation.setMask(Mask.NAME | Mask.SOURCE_LINE | Mask.TIME_STAMP);	
		Annotation.enableStdout(true);

		MainHelper h = new MainHelper();
		h.f();
		h.g();
	}
}
```

```Java
package foo;
import com.montimage.mmt.client.annotations.*;

@Monitor
public class MainHelper {
	
	@Count
	public int x;
	
	@Monitor
	public void f() {	x = 10;	};
	
	@Ping
	public void g() {  t();	};
	
	@Monitor( Mask.SOURCE_LINE )
	public void t(){  z();	};
	
	@Exclude
	public void z(){};
}
```


##Annotations##
