package numberPlay.util;

import java.util.Vector;

// Purpose - Stores the prime numbers that are determined by the WorkerThread objects
public class Results implements ResultsI {
	private int maxCapacity;
	private Vector<Integer> primeNumbers;
	private Runnable dataSender;

	/* Purpose - Constructor
	*  @param maxCapacityIn - specifies the max capacity of the vector
	*/
	public Results(int maxCapacityIn) {
		maxCapacity = maxCapacityIn;
		primeNumbers = new Vector<Integer>(maxCapacity);
	}

	/* Purpose - Adds data to primeNumbers vector
	*  @param data - prime number to be added to vector
	*  @return - returns whether primeNumbers vector is at maxCapacity or not
	*/
	@Override
	public void store(Integer data) {
		primeNumbers.add(data);
	}

	// Purpose - checks if there are any stored prime numbers
	@Override
	public boolean IsEmpty() {
		return primeNumbers.isEmpty();
	}

	// Purpose - check if at max capacity
	@Override
	public boolean IsFull() {
		return primeNumbers.size() >= maxCapacity;
	}

	// Purpose - returns a single prime number
	@Override
	public Integer get() {
		return primeNumbers.remove(0);
	}

	// Purpose - Start data sender thread
	@Override
	public void startPersistingResults(String persisterServiceIp, String port) {
		dataSender = new DataSender(this, persisterServiceIp, port);

		Thread dataSenderThread = new Thread(dataSender);
		dataSenderThread.start();
	}
}
