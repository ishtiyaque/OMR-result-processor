import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class OMRProject {
	
	

	public static void main(String[] args) {
		
		Configuration.loadConfiguration();
		//Configuration.printConfiguration();

		if(!Configuration.getDao().isDataLoaded()) {
			loadCandidateInfo();
		}
		
		Processor processor = new Processor();
		processor.processFile(Configuration.getDatFileName());
		
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
