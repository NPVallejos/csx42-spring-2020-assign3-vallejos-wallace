package numberPlay.util;

// Purpose - to be implemented by Results class
public interface ResultsI {

	/* Purpose - Adds data to primeNumbers vector
	*  @param data - prime number to be added to vector
	*  @return - returns whether primeNumbers vector is at maxCapacity or not
	*/
	public void store(Integer data);

	// Purpose - checks if there are any stored prime numbers
	public boolean IsEmpty();

	// Purpose - check if at max capacity
	public boolean IsFull();

	// Purpose - returns a single prime number
	public Integer get();

	// Purpose - Start data sender thread
	public void startPersistingResults(String persisterServiceIp, String port);
}
