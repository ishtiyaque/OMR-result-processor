
public class OMRProject {
	
	private static final String CONF_TXT = "conf.txt";

	public static void main(String[] args) {
		
		Configuration.loadConfiguration(CONF_TXT);
		//Configuration.printConfiguration();
		
		Processor proc = new Processor(Configuration.datFileName);
		proc.process();
	}
	
}
