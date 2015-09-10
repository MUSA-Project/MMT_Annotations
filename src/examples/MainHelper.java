package examples;

import java.security.SecureRandom;

import com.montimage.mmt.client.annotations.*;

@Monitor
public class MainHelper {
	
	@Count
	public SecureRandom rndgen;
	
	MainHelper() {
		rndgen = new SecureRandom();
	}
	@Monitor
	public void f() {
		rndgen = new SecureRandom();
	};
	
	@Ping
	public void g() {t();};
	
	@Monitor( Mask.SOURCE_LINE )
	public void t(){z();};
	
	@Exclude
	public void z(){};
	
}
