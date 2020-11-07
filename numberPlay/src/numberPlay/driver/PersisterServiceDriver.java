package numberPlay.driver;

// Command Arg Validator Necessary Imports
import numberPlay.validator.CommandArgHandler;
import numberPlay.exceptions.InvalidArgNameException;
import numberPlay.exceptions.NumberOfArgsException;
import numberPlay.util.PersisterService;
import numberPlay.util.PersisterServiceI;
import numberPlay.exceptions.EmptyFileException;
import java.lang.IllegalArgumentException;

/*
* Purpose - All code related to PersisterService is run here
*/
public class PersisterServiceDriver {
	public static void main(String[] args) {
		// TODO: Get rid of the following print statment
		System.out.println("Working in the PersisterServiceDriver class...");

		// Command Arg Validation Test
		try {
			boolean checkPrimeDetectorArgs = false;
			int requiredNumArgs = 2;
			CommandArgHandler cmdArgHandler = new CommandArgHandler(args.length, args, requiredNumArgs, checkPrimeDetectorArgs);
		} catch (NumberOfArgsException | InvalidArgNameException | IllegalArgumentException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Integer port = Integer.parseInt(args[0]);
		String outputFilename = args[1];
		
		PersisterServiceI persister = new PersisterService(port, outputFilename);
		persister.start();
	}
}
