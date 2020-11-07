package numberPlay.util;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.InvalidPathException;

import java.util.List;

import numberPlay.exceptions.EmptyFileException;

public final class FileProcessor {
	private BufferedReader reader;
	private String line;

	public FileProcessor(String inputFilePath)
		throws InvalidPathException, SecurityException, FileNotFoundException, IOException, EmptyFileException {

		// Check if file exists
		if (!Files.exists(Paths.get(inputFilePath))) {
			throw new FileNotFoundException("invalid input file or input file in incorrect location");
		}

		/* TODO: Check if the file is empty
		** - Cannot simply check if first line is empty
		*/
		{
			File file = new File(inputFilePath);
			if (file.length() == 0) {
			    throw new EmptyFileException("file is empty!");
			}
		}

		reader = new BufferedReader(new FileReader(new File(inputFilePath)));
		line = reader.readLine();
	}

	public String poll() throws IOException {
		if (null == line) return null;

		String newValue = line.trim();
		line = reader.readLine();
		return newValue;
	}

	public void close() throws IOException {
		try {
			reader.close();
			line = null;
		} catch (IOException e) {
			throw new IOException("failed to close file", e);
		}
	}
}
