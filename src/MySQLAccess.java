import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public void insertResult(Result result) {
		try {
    		
			preparedStatement = connect
			        .prepareStatement("insert into  result(roll_no, marks,exam_type) values (?,?,?)");
			preparedStatement.setString(1, result.getRollNo());
	        preparedStatement.setFloat(2, result.getMark());
	        preparedStatement.setString(3, result.getExamType());
	        preparedStatement.executeUpdate();
	        
	        preparedStatement = connect
			        .prepareStatement("insert into  result_details(roll_no, set_code, given_answer, correct, incorrect, unanswered, exam_type) values (?,?,?,?,?,?,?)");
			preparedStatement.setString(1, result.getRollNo());
	        preparedStatement.setString(2, result.getSetCode());
	        preparedStatement.setString(3, result.getGivenAnswer());
	        preparedStatement.setInt(4, result.getCorrect());
	        preparedStatement.setInt(5, result.getIncorrect());
	        preparedStatement.setInt(6, result.getUnanswered());
	        preparedStatement.setString(7, result.getExamType());
	        preparedStatement.executeUpdate();	        
	        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
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

}