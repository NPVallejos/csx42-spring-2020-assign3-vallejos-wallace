package numberPlay.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PersisterService implements PersisterServiceI{
	private Integer port;
	private ServerSocket serverSocket = null;
	private Socket connectionToClient = null;
	private String outputFilename;
	private BufferedWriter outputWriter;

	public PersisterService(Integer portIn, String outputFilenameIn) {
		port = portIn;
		outputFilename = outputFilenameIn;
		try {
			outputWriter = new BufferedWriter(new FileWriter(outputFilenameIn));
		} catch (IOException e) {
			System.out.println("Error when opening file " + outputFilenameIn);
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void start() {

		try {
			serverSocket = new ServerSocket(port);
		} catch(IOException e) {
			System.out.println("Could not listen on port " + port);
			e.printStackTrace();
			System.exit(0);
		}


		String localHostAddress = null;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			localHostAddress = inetAddress.getHostAddress();
		} catch(UnknownHostException e) {
			System.out.println("Couldn't get host");
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Server Ip4 Address: " + localHostAddress + "\nListening on Port " + port);

		try {
			connectionToClient = serverSocket.accept();
		} catch(IOException e) {
			System.out.println("Could not accept on port " + port);
			e.printStackTrace();
			System.exit(0);
		}

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));

			boolean done = false;

			String outputLine;
			while(!done) {
				outputLine = in.readLine();
				if (outputLine != null) {
					System.out.println(outputLine);
					if(outputLine.contentEquals("STOP")) {
						done = true;
					} else {
						save(Integer.parseInt(outputLine));
					}
				}
			}
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}

		close();
	}

	@Override
	public void close() {
		try {
			connectionToClient.close();
		} catch (IOException e) {
			System.out.println("Failed to close socket properly from PersisterService.");
			e.printStackTrace();
			System.exit(0);
		}
		try {
			outputWriter.close();
		} catch(IOException e) {
			System.out.println("Error when trying to close " + outputFilename);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void save(Integer prime) {
		try {
			outputWriter.write(prime.toString() + "\n");
		} catch (IOException e) {
			close();
			System.out.println("Error when writing to " + outputFilename);
			e.printStackTrace();
			System.exit(0);
		}
	}

}
