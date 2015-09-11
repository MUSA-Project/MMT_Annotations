package examples;

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
	
	@Monitor
	public void i(){};
	
}
