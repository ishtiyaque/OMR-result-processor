import java.io.File;

public class OMRProject {
	
	private static final String CONF_TXT = "conf.txt";

	public static void main(String[] args) {
		
		Configuration.loadConfiguration(CONF_TXT);
		//Configuration.printConfiguration();

		if(!Configuration.getDao().isDataLoaded())loadCandidateInfo();
		
		Processor processor = new Processor(Configuration.getDatFileName());
		processor.process();
		
	}
	
	static void loadCandidateInfo() {
		File directory = new File("data/CandidateInfo/");
		String fileNames[]= directory.list();
		
		for (String string : fileNames) {
			if(!string.startsWith("~$"))
			{
				System.out.println(directory.getAbsolutePath()+File.separator+string);
				CandidateInfoReader reader = new CandidateInfoReader(directory.getAbsolutePath()+File.separator+string);
				reader.readAllRows(0);
			}
		}
	}
	
}
