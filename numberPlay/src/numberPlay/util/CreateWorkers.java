package numberPlay.util;

import java.util.ArrayList;

import java.io.IOException;
import java.lang.IllegalMonitorStateException;

public class CreateWorkers {
	private FileProcessor fileProcessor;
	private ResultsI results;
	private IsPrimeI isPrime;
	private ObjectPoolI threadPool;

	// Purpose - Constructor
	public CreateWorkers(FileProcessor fileProcessorIn, ResultsI resultsIn, IsPrimeI isPrimeIn) {
		fileProcessor = fileProcessorIn;
		results = resultsIn;
		isPrime = isPrimeIn;
		threadPool = null;
	}

	/* Purpose - Borrows threads from 'threadPool', starts them and joins on them
	*  @param numThreads - total number of threads to borrow
	*/
	public void startWorkers(int numThreadsIn) throws IOException {
		// Initialize the thread pool
		threadPool = new ThreadPool(numThreadsIn, fileProcessor, results, isPrime);

		// TODO: Get rid of the following print statement
		System.out.println("\t\tCreating worker thread pool...");

		// Borrow 'numThreadsIn' threads
		ArrayList<Runnable> borrowedWorkerThreads = new ArrayList<Runnable>();
		for (int i = 0; i < numThreadsIn; i++) {
			Runnable worker = (Runnable)threadPool.borrow();
			borrowedWorkerThreads.add(worker);
		}

		// TODO: Get rid of the following print statement
		System.out.println("\t\tBorrowed "+ borrowedWorkerThreads.size() +" worker threads from thread pool...");

		// Create threads from borrowed workers and start them
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (Runnable thread : borrowedWorkerThreads) {
			Thread t = new Thread(thread);
			threads.add(t);
			t.start();
		}


		// Join on all started threads
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO: handle this exception properly
				e.printStackTrace();
				System.exit(0);
			}
		}

		synchronized(results) {
			try {
				/*
				*  Once the EOF is reached the worker thread stores "STOP"
				*  in Results instace.
				*  For now, we will store a 0 to indicate EOF.
				*/
				results.store(0);
				// Have to notifyAll() to prevent the DataSender thread from hanging
				results.notifyAll();
			} catch (IllegalMonitorStateException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		fileProcessor.close();
	}
}
