package numberPlay.validator;

import numberPlay.exceptions.NumberOfArgsException;
import numberPlay.exceptions.InvalidArgNameException;
import java.lang.IllegalArgumentException;

// purpose - store and validate command line arguments
// Reference: Person.java -> https://piazza.com/class/k5k3yuyx97612d?cid=37
public class CommandArgHandler {
	private int numArgs;
	private String[] args;
	private int requiredNumArgs;
	private boolean checkPrimeDetectorArgs;

	private static class ValidatorFetcher {
		// Purpose - Test if there are correct number of args
		public static Validator numArgsValidator(CommandArgHandler c) {
			return new Validator() {
				@Override
				public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
					if (c.numArgs != c.requiredNumArgs) {
						throw new NumberOfArgsException("Incorrect number of arguments [" + c.numArgs + "].");
					}
				}
			};
		}
		// Purpose   - Test if cmd args are missing.
		// Important - The returned 'Validator' is based on the field 'checkPrimeDetectorArgs'
		// Other     - This is a hardcoded
		public static Validator argNamesValidator(CommandArgHandler c) {
			if (c.checkPrimeDetectorArgs) {
				return new Validator() {
					@Override
					public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
						if (c.args[0].equals("${inputFile}")) {
							throw new InvalidArgNameException("Input file path not specified.");
						}
						else if (c.args[1].equals("${numThreads}")) {
							throw new InvalidArgNameException("Total number of threads not specified.");
						}
						else if (c.args[2].equals("${capacity}")) {
							throw new InvalidArgNameException("Capacity of the results data structure not specified.");
						}
						else if (c.args[3].equals("${persisterServiceIp}")) {
							throw new InvalidArgNameException("Persister service ip address not specified.");
						}
						else if (c.args[4].equals("${persisterServicePort}")) {
							throw new InvalidArgNameException("Persister service port number not specified.");
						}
						else if (c.args[5].equals("${debugValue}")) {
							throw new InvalidArgNameException("Debug value not specified.");
						}
					}
				};
			}
			else {
				return new Validator() {
					@Override
					public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
						if (c.args[0].equals("${port}")) {
							throw new InvalidArgNameException("Port number not specified.");
						}
						else if (c.args[1].equals("${outputFile}")) {
							throw new InvalidArgNameException("Output file not specified.");
						}
					}
				};
			}
		}
		// Purpose - Validate the values of the cmd args
		public static Validator argValuesValidator(CommandArgHandler c) {
			if (c.checkPrimeDetectorArgs) {
				return new Validator() {
					@Override
					public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
						if (Integer.parseInt(c.args[1]) < 1 || Integer.parseInt(c.args[1]) > 5) {
							throw new IllegalArgumentException("Number of threads must be between 1 and 5 inclusive. Please try again!");
						}
						else if (Integer.parseInt(c.args[2]) <= 0) {
							throw new IllegalArgumentException("Capacity must be greater than 0!");
						}
						else if (Integer.parseInt(c.args[4]) < 32768 || Integer.parseInt(c.args[4]) > 50000) {
							throw new IllegalArgumentException("Port number must be between 32768 and 50000!");
						}
						else if (Integer.parseInt(c.args[5]) < 0 || Integer.parseInt(c.args[5]) > 4) {
							throw new IllegalArgumentException("Debug value must be between 0 and 4. Please try again!");
						}
					}
				};
			}
			else {
				return new Validator() {
					@Override
					public void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
						// Do nothing
						if (Integer.parseInt(c.args[0]) < 32768 || Integer.parseInt(c.args[0]) > 50000) {
							throw new IllegalArgumentException("Port number must be between 32768 and 50000!");
						}
					}
				};
			}
		}
	}

	// Purpose - Constructor
	public CommandArgHandler(int numArgsIn, String[] argsIn, int requiredNumArgsIn, boolean checkPrimeDetectorArgsIn) throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException {
		this.numArgs = numArgsIn;
		this.args = argsIn;
		this.requiredNumArgs = requiredNumArgsIn;
		this.checkPrimeDetectorArgs = checkPrimeDetectorArgsIn;

		// Validating fields
		ValidatorUtil.validate("Failed: ",
			ValidatorFetcher.numArgsValidator(this),
			ValidatorFetcher.argNamesValidator(this),
			ValidatorFetcher.argValuesValidator(this));
	}
}
