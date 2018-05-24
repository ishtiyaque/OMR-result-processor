import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Configuration {

	public static String databaseName;
	public static String datFileName;
	public static String postCode;
	public static String examType;
	
	public static float corrrectWeight = 1;
	public static float incorrecWeight=0;
	
	public static HashMap<String, String> codeAnswerMap=new HashMap<>();
	
	public static MySQLAccess dao;
	
	
	
	public static void printConfiguration() {
		
		System.out.println(datFileName);
		System.out.println(postCode);
		System.out.println(examType);
		
		for (String  s : codeAnswerMap.keySet()) {
			System.out.println(s);
			System.out.println(codeAnswerMap.get(s));
		}
		
	}
	
	public static void loadConfiguration(String fileName) {
		
		codeAnswerMap.clear();
		
		try {
			Scanner sc = new Scanner(new File(fileName));
		
			databaseName = sc.nextLine();
			dao = createConnection(databaseName);
			
			
			datFileName = sc.nextLine().trim();
			postCode = sc.nextLine().trim();
			examType = sc.nextLine().trim();
			
			while(sc.hasNext()) {
				String setcode = sc.nextLine().trim();
				String correctAnswer = sc.nextLine().trim();
				codeAnswerMap.put(setcode, correctAnswer);
				dao.insertConfiguration(postCode,setcode,examType,correctAnswer);
				
			}
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static MySQLAccess createConnection(String databaseName) {
		return new MySQLAccess(databaseName);
	}
	
	
	
	
}
