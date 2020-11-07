package numberPlay.util;

import java.io.IOException;
import java.lang.IllegalMonitorStateException;

// Purpose - Thread that parses input file and determine if numbers are prime.
public class WorkerThread implements ThreadI, Runnable {
	private FileProcessor fileProcessor;
	private ResultsI results;
	private IsPrimeI isPrime;
	private String initString = "START OF TASK";

	// Purpose - Constructor
	public WorkerThread(FileProcessor fileProcessorIn, ResultsI resultsIn, IsPrimeI isPrimeIn) {
		fileProcessor = fileProcessorIn;
		results = resultsIn;
		isPrime = isPrimeIn;
	}

	/* Purpose - Process input file, check for prime numbers, and store result in 'results'
	*/
	@Override
	public void run() {
		String input = initString;
		try {
			// Create a synchronized block to ensure only one thread runs at a time
			// Idea - https://www.geeksforgeeks.org/inter-thread-communication-java/
			synchronized(results) {
				// Read line from input file
				input = fileProcessor.poll();
				while (input != null) {
					// Convert 'input' (String) to 'inputValue' (Integer)
					Integer inputValue = Integer.parseInt(input);
					// Test if 'inputValue' is prime
					if (isPrime.test(inputValue)) {
						// TODO: Get rid of the following print statement
						//System.out.println("\t\t\t" + inputValue.toString() + " is prime! ");

							if (results.IsFull()) {
								// TODO: Get rid of the following print statement
								System.out.println("\t\t\tWorkerThread.wait()");

								results.wait();
							}
							// Add prime number to results object
							results.store(inputValue);

							// TODO: Get rid of the following print statement
							System.out.println("\t\t\tWorkerThread.notifyAll()");

							results.notifyAll();
					}
					input = fileProcessor.poll();
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: '" + input + "' cannot be converted to an Integer...skipping to next line in input file.");
		} catch (IOException e) {
			// TODO: Handle this error properly
			e.printStackTrace();
			System.exit(0);
		} catch (InterruptedException | IllegalMonitorStateException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
