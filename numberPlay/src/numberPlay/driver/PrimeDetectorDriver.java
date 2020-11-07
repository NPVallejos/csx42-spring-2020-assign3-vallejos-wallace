package numberPlay.driver;

// Command Arg Validator Necessary Imports
import numberPlay.validator.CommandArgHandler;
import numberPlay.exceptions.InvalidArgNameException;
import numberPlay.exceptions.NumberOfArgsException;
import java.lang.IllegalArgumentException;

// Concrete Class Imports
import numberPlay.util.FileProcessor;
import numberPlay.util.Results;
import numberPlay.util.IsPrime;
import numberPlay.util.CreateWorkers;

// Interface imports
import numberPlay.util.ResultsI;
import numberPlay.util.IsPrimeI;

// Other Exceptions
import numberPlay.exceptions.EmptyFileException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

/*
* Purpose - All code related to prime detection is run here
*/
public class PrimeDetectorDriver {
	public static void main(String[] args) {
		// TODO: Get rid of the following print statement
		System.out.println("Working in the PrimeDetectorDriver class...");

		// Command Arg Validation Test
		try {
			boolean checkPrimeDetectorArgs = true;
			int requiredNumArgs = 6;
			CommandArgHandler cmdArgHandler = new CommandArgHandler(args.length, args, requiredNumArgs, checkPrimeDetectorArgs);
		} catch (NumberOfArgsException | InvalidArgNameException | IllegalArgumentException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Store cmd args in easy to read variables
		String inputFile = args[0];
		String numThreads = args[1];
		String capacity = args[2];
		String persisterServiceIp = args[3];
		String persisterServicePort = args[4];
		String debugValue = args[5];

		// TODO: Get rid of the following print statement
		System.out.println("\tCreating instance of FileProcessor, Results, and IsPrime class...");

		// Create instance of FileProcessor, Results, and IsPrime
		try {
			FileProcessor fileProcessor = new FileProcessor(inputFile);
			ResultsI results = new Results(Integer.parseInt(capacity));
			IsPrimeI isPrime = new IsPrime();

			// TODO: Get rid of the following print statement
			System.out.println("\tCreating instance of CreateWorkers class...");
			CreateWorkers workersCreator = new CreateWorkers(fileProcessor, results, isPrime);

			// Initialize connection to persister service
			results.startPersistingResults(persisterServiceIp, persisterServicePort);
			
			// TODO: Get rid of the following print statement
			System.out.println("\tCalling startWorkers() method...");
			workersCreator.startWorkers(Integer.parseInt(numThreads));
		} catch (InvalidPathException | SecurityException | FileNotFoundException | EmptyFileException | NumberFormatException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
