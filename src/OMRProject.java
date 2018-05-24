
public class OMRProject {
	
	private static final String CONF_TXT = "conf.txt";

	public static void main(String[] args) {
		
		Configuration.loadConfiguration(CONF_TXT);
		//Configuration.printConfiguration();
		
		Processor processor = new Processor(Configuration.datFileName);
		processor.process();
	}
	
}
