import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class ReportGenerator {

	private JasperReportBuilder report;

	public void buildReport(String title, String query, ArrayList<ReportColumnType> columns) {
		report = DynamicReports.report();

		for (ReportColumnType reportColumnType : columns) {
			report.addColumn(Columns.column(reportColumnType.getColumnHeader(),
					reportColumnType.getDatabaseColumnName(), reportColumnType.getDataType()));
		}

		StyleBuilders stl = DynamicReports.stl;

		StyleBuilder boldStyle = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle)
				.setHorizontalAlignment(HorizontalAlignment.CENTER);

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
