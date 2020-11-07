package numberPlay.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.DataOutputStream;
import java.lang.IllegalMonitorStateException;
import java.net.Socket;
import java.net.InetAddress;

// Purpose - Single thread that acts as a client; sends data from Results to PersisterService (server)
public class DataSender implements ThreadI, Runnable {
	private ResultsI results;
	private String persisterServiceIp;
	private String persisterServicePort;
	private DataOutputStream output;
	private Socket connectionToServer;

	// Purpose - Constructor
	public DataSender(ResultsI resultsIn, String persisterServiceIpIn, String persisterServicePortIn) {
		results = resultsIn;
		persisterServiceIp = persisterServiceIpIn;
		persisterServicePort = persisterServicePortIn;
		EstablishConnection();
	}

	// Purpose - create socket connection to server
	public void EstablishConnection() {
		try {
			connectionToServer = new Socket(InetAddress.getByName(persisterServiceIp), Integer.parseInt(persisterServicePort));
		} catch(IOException e) {
			System.out.println("DataSender could not open socket on " + persisterServiceIp + ":" + persisterServicePort);
			e.printStackTrace();
			System.exit(0);
		}

		try {
			output = new DataOutputStream(connectionToServer.getOutputStream());
		} catch(IOException e) {
			System.out.println("DataSender could not open output stream on " + persisterServiceIp + ":" + persisterServicePort);
			e.printStackTrace();
			System.exit(0);
		}
	}

	// Purpose - send data to server
	private void write(String data) {
		try {
			output.writeBytes(data + "\n");
			output.flush();
		} catch(IOException e) {
			System.out.println("DataSender could not write " + data + " to output stream on " + persisterServiceIp + ":" + persisterServicePort);
			e.printStackTrace();
			System.exit(0);
		}
	}

	// Purpose - send data from Results instance to PersisterService
	@Override
	public void run() {
		try {
			// Create a synchronized block to ensure only one thread runs at a time
			// Idea - https://www.geeksforgeeks.org/inter-thread-communication-java/
			synchronized(results) {
				while (true) {
					// Check if there are no results
					if (results.IsEmpty()) {
						System.out.println("\t\t\tDataSenderThread.wait()");
						// Make DataSender thread wait
						results.wait();
					}
					// Get data from results
					Integer primeNumber = results.get();

					// Check for EOF (indicated by 0 for now)
					if (primeNumber.equals(0)) {
						System.out.println("\t\t\t" + primeNumber + ": Terminating DataSender thread...");
						// Send "STOP" to server
						write("STOP");
						break;
					}
					else {
						// TODO: remove the following print statement
						System.out.println("\t\t\tSending " + primeNumber + " to persister service");

						// Send data to server
						write(primeNumber.toString());

						// Call notifyAll()
						results.notifyAll();
					}
				}
			}
		} catch (InterruptedException | IllegalMonitorStateException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
