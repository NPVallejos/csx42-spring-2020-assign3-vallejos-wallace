package numberPlay.util;

import java.util.Queue;
import java.util.LinkedList;

// Purpose - Centralize and manage thread pool consisting of WorkerThread objects
public class ThreadPool implements ObjectPoolI {
	private Queue<ThreadI> workerThreadPool;

	/* Purpose - Create a pool of WorkerThread objects
	*  @param 'numThreadsIn' - number of WorkerThread objects to add to 'workerThreadPool'
	*/
	public ThreadPool(int numThreadsIn, FileProcessor fileProcessorIn, ResultsI resultsIn, IsPrimeI isPrimeIn) {
		workerThreadPool = new LinkedList<>();
		for (int i = 0; i < numThreadsIn; i++) {
			workerThreadPool.add(new WorkerThread(fileProcessorIn, resultsIn, isPrimeIn));
		}
	}

	// Purpose - return a single ThreadI from the 'workerThreadPool' Queue
	@Override
	public ThreadI borrow() {
		return workerThreadPool.poll();
	}
}
