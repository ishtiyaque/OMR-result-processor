import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Configuration {

	private static String databaseName;
	private static String datFileName;
	private static String postCode;
	private static String examType;
	
	private static final int desiredLength=151;

	private static float corrrectWeight = 1;
	private static float incorrecWeight = 0;

	private static HashMap<String, String> codeAnswerMap;

	private static MySQLAccess dao;

	public static void loadConfiguration(String fileName) {

		codeAnswerMap = new HashMap<String, String>();

		try {
			Scanner sc = new Scanner(new File(fileName));

			databaseName = sc.nextLine();
			dao = createConnection(databaseName);

			datFileName = sc.nextLine().trim();
			postCode = sc.nextLine().trim();
			examType = sc.nextLine().trim();

			while (sc.hasNext()) {
				String setcode = sc.nextLine().trim();
				String correctAnswer = sc.nextLine().trim();
				codeAnswerMap.put(setcode, correctAnswer);
				dao.insertConfiguration(postCode, setcode, examType, correctAnswer);

			}
			sc.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getDatabaseName() {
		return databaseName;
	}

	public static String getDatFileName() {
		return datFileName;
	}

	public static String getPostCode() {
		return postCode;
	}

	public static String getExamType() {
		return examType;
	}

	public static float getCorrrectWeight() {
		return corrrectWeight;
	}

	public static float getIncorrecWeight() {
		return incorrecWeight;
	}

	public static int getDesiredlength() {
		return desiredLength;
	}

	public static HashMap<String, String> getCodeAnswerMap() {
		return codeAnswerMap;
	}

	public static MySQLAccess getDao() {
		return dao;
	}

	private static MySQLAccess createConnection(String databaseName) {
		return new MySQLAccess(databaseName);
	}

	public static void printConfiguration() {

		System.out.println(datFileName);
		System.out.println(postCode);
		System.out.println(examType);

		for (String s : codeAnswerMap.keySet()) {
			System.out.println(s);
			System.out.println(codeAnswerMap.get(s));
		}

	}

}
