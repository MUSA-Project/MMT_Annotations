package examples;

import com.montimage.mmt.client.annotations.Mask;
import com.montimage.mmt.client.annotations.connector.Annotation;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Annotation.setMask(Mask.NAME | Mask.TIME_STAMP);	
		
		A a = new A();
		a.f();
		a.g();
		
		A aa = new A();
		aa.g();
	
		aa = new A();
		
	}
}