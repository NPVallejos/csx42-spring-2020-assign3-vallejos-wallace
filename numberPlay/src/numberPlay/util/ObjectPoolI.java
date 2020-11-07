package numberPlay.util;

// Purpose - To be implemented by ThreadPool class
public interface ObjectPoolI {
	// Purpose - returns object from internal object pool
	public ThreadI borrow();
}
