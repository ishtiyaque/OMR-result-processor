import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class CandidateInfoReader {

	private Workbook workbook;
	
	public CandidateInfoReader(String fileName) {
		
		try {
			workbook = WorkbookFactory.create(new File(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ArrayList<CandidateInfo> readAllRows(int sheetNumber) {
		
		ArrayList<CandidateInfo> candidateInfos = new ArrayList<>();
		Sheet sheet = workbook.getSheetAt(sheetNumber);

		DataFormatter dataFormatter = new DataFormatter();
		
		System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
		
		for(int i=1;i<=sheet.getLastRowNum();i++) {
			Row row = sheet.getRow(i);
			CandidateInfo candidateInfo = new CandidateInfo();
			candidateInfo.setPostCode(dataFormatter.formatCellValue(row.getCell(0)));
			// Skipping 1th cell since it is post_name
			candidateInfo.setRollNo(dataFormatter.formatCellValue(row.getCell(2)));
			candidateInfo.setApplicantName(dataFormatter.formatCellValue(row.getCell(3)));
			candidateInfo.setFathersName(dataFormatter.formatCellValue(row.getCell(4)));
			candidateInfo.setMothersName(dataFormatter.formatCellValue(row.getCell(5)));
			candidateInfo.setQuota(dataFormatter.formatCellValue(row.getCell(6)));
			
			System.out.println(i);
			System.out.println(candidateInfo);
			Configuration.getDao().insertCandidateInfo(candidateInfo);
		}
		
		return candidateInfos;
	}
	
	
	public static void main(String[] args) {
		Configuration.loadConfiguration("conf.txt");
		
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
