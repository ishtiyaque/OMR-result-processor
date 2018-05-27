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
	
	private static final String CONF_TXT = "conf.txt";

	public static void main(String[] args) {
		
		Configuration.loadConfiguration(CONF_TXT);
		//Configuration.printConfiguration();

		if(!Configuration.getDao().isDataLoaded()) {
			loadCandidateInfo();
		}
		
		Processor processor = new Processor(Configuration.getDatFileName());
		//processor.process();
		
		//connection needs to be checked
		
		ArrayList<ReportColumnType> columnTypes = new ArrayList<>();
		columnTypes.add(new ReportColumnType("Roll No.", "roll_no", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Applicant's Name", "applicant_name", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Post Code", "post_code", DataTypes.stringType()));
		
		ReportGenerator reportGenerator = new ReportGenerator();
		// limit only 100
		reportGenerator.buildReport("Candidate Info", "SELECT roll_no, applicant_name, post_code"
		  		+ " FROM candidate_info limit 0,100", columnTypes);
		reportGenerator.getPDFReport("report.pdf");
		reportGenerator.getXLSXReport("report.xlsx");
		reportGenerator.getXLSReport("report.xls");
		
		//Absentee List generator
		columnTypes.clear();
		columnTypes.add(new ReportColumnType("Roll No.", "roll_no", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Applicant's Name", "applicant_name", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Post Code", "post_code", DataTypes.stringType()));
		
		//ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.buildReport("Absentee List", "SELECT roll_no, applicant_name, post_code from candidate_info "
				+ "where roll_no NOT IN (Select roll_no from result) limit 0,100", columnTypes);
		reportGenerator.getPDFReport("Absentee_report.pdf");
		reportGenerator.getXLSXReport("Absentee_report.xlsx");
		reportGenerator.getXLSReport("Absentee_report.xls");
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
