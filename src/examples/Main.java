package examples;

import com.montimage.mmt.client.annotations.Mask;
import com.montimage.mmt.client.annotations.connector.Annotation;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Annotation.setMask(Mask.NAME | Mask.SOURCE_LINE | Mask.TIME_STAMP);	
		Annotation.enableStdout(true);

		MainHelper h = new MainHelper();
		h.f();
		h.g();
		
		System.out.println("Done");
	}
}
