import java.util.ArrayList;

public class ErrorResolver {

	public static void main(String[] args) {
		Configuration.loadConfiguration();
		
		ErrorResolver errorResolver = new ErrorResolver();
		errorResolver.resolveSetCodeErrors();

	}

	private  void resolveSetCodeErrors() {
		Processor processor = new Processor();
		ArrayList<ResultDetails> resultDetailsList = Configuration.getDao().getResultsWithSetCodeErrors();
		for (ResultDetails resultDetails : resultDetailsList) {
			ResultDetails updatedResult = processor.evaluate(resultDetails);
			updatedResult.setErrorCode(ErrorTypes.NO_ERROR);
			Configuration.getDao().updateResultDetails(updatedResult);
		}
		
	}

}
