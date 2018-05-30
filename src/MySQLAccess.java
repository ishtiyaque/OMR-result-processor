import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQLAccess(String databaseName) {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName
					+ "?" + "user=root");
		} catch (Exception e) {
		
		}

    }
    
    public Connection getConnection() {
    	return connect;
    }
    
    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from feedback.comments");
            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
            .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
        }
    }

    // You need to close the resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

	
    public void insertConfiguration(String postCode, String setcode, String examType, String correctAnswer) {
        
    	try {
    		
    		if(!checkConfigurationExist(postCode,setcode,examType))
    		{
				preparedStatement = connect
				        .prepareStatement("insert into  configuration(post_code, set_code, exam_type, correct_answer) values (?,?,?,?)");
				preparedStatement.setString(1, postCode);
		        preparedStatement.setString(2, setcode);
		        preparedStatement.setString(3, examType);
		        preparedStatement.setString(4, correctAnswer);
		        preparedStatement.executeUpdate();
    		}
    		else {
    			System.out.println("Duplicate configuration");
    			return;
    		}
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	private boolean checkConfigurationExist(String postCode, String setcode, String examType) {
		
		try {
			preparedStatement = connect
			        .prepareStatement("SELECT * FROM configuration WHERE POST_CODE=? AND SET_CODE=? AND EXAM_TYPE=?");
			preparedStatement.setString(1, postCode);
	        preparedStatement.setString(2, setcode);
	        preparedStatement.setString(3, examType);
	        resultSet = preparedStatement.executeQuery();
	        resultSet.last();
	        if(resultSet.getRow()>0)return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return false;
	}

	public void insertResultDetails(ResultDetails result) {
		try {
    		
			
	        preparedStatement = connect
			        .prepareStatement("insert into  result_details(omr_header,roll_no, set_code, given_answer, correct, incorrect, multiple_answered,unanswered, exam_type,error_code) values (?,?,?,?,?,?,?,?,?,?)");
	        preparedStatement.setString(1, result.getOmrHeader());
	        preparedStatement.setString(2, result.getRollNo());
	        preparedStatement.setString(3, result.getSetCode());
	        preparedStatement.setString(4, result.getGivenAnswer());
	        preparedStatement.setInt(5, result.getCorrect());
	        preparedStatement.setInt(6, result.getIncorrect());
	        preparedStatement.setInt(7, result.getMultipleAnswered());
	        preparedStatement.setInt(8, result.getUnanswered());
	        preparedStatement.setString(9, result.getExamType());
	        preparedStatement.setInt(10, result.getErrorCode());
	        preparedStatement.executeUpdate();	        
	        
        } 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertCandidateInfo(CandidateInfo candidateInfo) {
		try {
			preparedStatement = connect
			        .prepareStatement("INSERT INTO candidate_info(post_code, roll_no, applicant_name, father_name, mother_name, quota) VALUES (?,?,?,?,?,?)");
			preparedStatement.setString(1, candidateInfo.getPostCode());
	        preparedStatement.setString(2, candidateInfo.getRollNo());
	        preparedStatement.setString(3, candidateInfo.getApplicantName());
	        preparedStatement.setString(4, candidateInfo.getFathersName());
	        preparedStatement.setString(5, candidateInfo.getMothersName());
	        preparedStatement.setString(6, candidateInfo.getQuota());
	        preparedStatement.executeUpdate();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       
	}

	public boolean isDataLoaded() {
		try {
			preparedStatement = connect
			        .prepareStatement("SELECT COUNT(*) FROM CANDIDATE_INFO");
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			
			System.out.println(resultSet.getInt(1));
			if(resultSet.getInt(1)>0)return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public void deleteDuplicates(String rollNo,String examType) {
		try {
			preparedStatement = connect
			        .prepareStatement("DELETE FROM RESULT WHERE ROLL_NO=? AND EXAM_TYPE=?");
			
			preparedStatement.setString(1, rollNo);
			preparedStatement.setString(2, examType);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect
			        .prepareStatement("DELETE FROM RESULT_DETAILS WHERE ROLL_NO=? AND EXAM_TYPE=?");
			
			preparedStatement.setString(1, rollNo);
			preparedStatement.setString(2, examType);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void detectDuplicates() {
		try {
			preparedStatement = connect
			        .prepareStatement("UPDATE result_details set `error_code`=? where roll_no in ( select roll_no from(select * from result_details) as m2 group by roll_no having count(roll_no)>1)");
			preparedStatement.setInt(1, ErrorTypes.DUPLICATE_ROLL);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void insertResult(String rollNo, float marks, String setCode) {
		
		try {
			preparedStatement = connect
			        .prepareStatement("insert into  result(roll_no, marks, set_code) values (?,?,?)");
			preparedStatement.setString(1, rollNo);
	        preparedStatement.setFloat(2,marks);
	        preparedStatement.setString(3, setCode);
	        preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ArrayList<ResultDetails> getResultDetails(ArrayList<String> setCodes) {
		
		ArrayList<ResultDetails> resultDetailsList = new ArrayList<ResultDetails>();
		
		int size = setCodes.size();
		if (size == 0)
		{
			return null;
		}
		String query = "SELECT * FROM result_details WHERE (";
		query += "set_code=? ";
		for(int i=1;i<size;i++) {
			query += "OR set_code=? ";
		}
		
		query += ") AND error_code=0 ";
		
		try {
			preparedStatement = connect
			        .prepareStatement(query);
			for(int i=0;i<size;i++) {
				preparedStatement.setString(i+1, setCodes.get(i));
			
			}
			//System.out.println(preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				ResultDetails resultDetails = new ResultDetails();
	            
			
	            resultDetails.setRollNo(resultSet.getString("roll_no"));
	            resultDetails.setSetCode(resultSet.getString("set_code"));
	            resultDetails.setCorrect(resultSet.getInt("correct"));
	            resultDetails.setIncorrect(resultSet.getInt("incorrect"));
	            resultDetails.setMultipleAnswered(resultSet.getInt("multiple_answered"));
	            resultDetails.setUnanswered(resultSet.getInt("unanswered"));
	            
	            resultDetailsList.add(resultDetails);
	            
	            //System.out.println(resultDetails.getRollNo());
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultDetailsList;
	}
	
   
	
	//SELECT * FROM `result_details` WHERE (set_code="01" OR set_code="02") AND error_code=0 
	

}