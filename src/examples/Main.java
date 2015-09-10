package examples;

import com.montimage.mmt.client.annotations.connector.Annotation;

public class Main {
	
	public static void main(String[] args) throws Exception {
			
		Annotation.enableStdout(true);

		MainHelper h = new MainHelper();
		h.f();
		h.g();
		
		System.out.println("Done");
	}
}
