import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class ReportGenerator {

	private JasperReportBuilder report;

	public static void main(String args[]) {
		
		Configuration.loadConfiguration();
		// connection needs to be checked

		ArrayList<ReportColumnType> columnTypes = new ArrayList<>();
		columnTypes.add(new ReportColumnType("Roll No.", "roll_no", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Applicant's Name", "applicant_name", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Post Code", "post_code", DataTypes.stringType()));

		ReportGenerator reportGenerator = new ReportGenerator();
		// limit only 100
		/********
		reportGenerator.buildReport("Candidate Info",
				"SELECT roll_no, applicant_name, post_code" + " FROM candidate_info limit 0,100", columnTypes);
		reportGenerator.getPDFReport("report.pdf");
		reportGenerator.getXLSXReport("report.xlsx");
		reportGenerator.getXLSReport("report.xls");
		*************************/
		// Absentee List generator
		columnTypes.clear();
		columnTypes.add(new ReportColumnType("Roll No.", "roll_no", DataTypes.stringType()));
		columnTypes.add(new ReportColumnType("Applicant's Name", "applicant_name", DataTypes.stringType()));
		//columnTypes.add(new ReportColumnType("Post Code", "post_code", DataTypes.stringType()));

		// ReportGenerator reportGenerator = new ReportGenerator();
		//reportGenerator.buildReport("Absentee List", "SELECT roll_no, applicant_name from candidate_info "
		//		+ "where roll_no NOT IN (Select roll_no from result) AND post_code = 150", columnTypes);
		reportGenerator.buildReport("Present List", "SELECT roll_no, applicant_name from candidate_info "
						+ "where roll_no  IN (Select roll_no from result) AND post_code = 150", columnTypes);
		reportGenerator.getPDFReport("Absentee_report.pdf");
		reportGenerator.getXLSXReport("Absentee_report.xlsx");
		reportGenerator.getXLSReport("Absentee_report.xls");
	}

	public void buildReport(String title, String query, ArrayList<ReportColumnType> columns) {
		report = DynamicReports.report();

		for (ReportColumnType reportColumnType : columns) {
			report.addColumn(Columns.column(reportColumnType.getColumnHeader(),
					reportColumnType.getDatabaseColumnName(), reportColumnType.getDataType()));
		}

		StyleBuilders stl = DynamicReports.stl;

		StyleBuilder boldStyle = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);

		report.setColumnTitleStyle(stl.style(boldCenteredStyle).setBorder(stl.penDouble()));

		report.setColumnStyle(stl.style().setBorder(stl.pen1Point())).highlightDetailEvenRows();

		report.title(Components.text(title).setStyle(boldCenteredStyle));

		report.pageFooter(Components.pageXofY());

		report.setDataSource(query, Configuration.getDao().getConnection());
		try {
			report.show();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getPDFReport(String fileName) {

		try {
			report.toPdf(new FileOutputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getXLSXReport(String fileName) {

		try {
			report.toXlsx(new FileOutputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getXLSReport(String fileName) {

		try {
			report.toXls(new FileOutputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
