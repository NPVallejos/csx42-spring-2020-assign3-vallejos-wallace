package numberPlay.util;

public class IsPrime implements IsPrimeI {
	// Purpose - test if a number is prime
	// @param number - Integer to be tested
	// src of idea - https://en.wikipedia.org/wiki/Primality_test#Simple_methods
	public boolean test(Integer number) {
		int numberIntValue = number.intValue();

		if (numberIntValue <= 3) {
			return numberIntValue > 1;
		}
		else if (numberIntValue % 2 == 0 || numberIntValue % 3 == 0) {
			return false;
		}

		int i = 5;
		while (i * i <= numberIntValue) {
			if (numberIntValue % i == 0 || numberIntValue % (i + 2) == 0) {
				return false;
			}
			i = i + 6;
		}

		return true;
	}
}
