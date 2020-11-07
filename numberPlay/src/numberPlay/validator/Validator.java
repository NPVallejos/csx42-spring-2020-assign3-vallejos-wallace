package numberPlay.validator;

import numberPlay.exceptions.NumberOfArgsException;
import numberPlay.exceptions.InvalidArgNameException;
import java.lang.IllegalArgumentException;

// src: ValidationTest.java -> https://piazza.com/class/k5k3yuyx97612d?cid=37
public interface Validator {
	void run() throws NumberOfArgsException, InvalidArgNameException, IllegalArgumentException;
}
