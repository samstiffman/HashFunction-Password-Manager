import java.security.SecureRandom;

public class Seed {
	public long hashValue;

	/**
	 * Constructor with parameters only used with comparison
	 * @param targetSeed seed retrieved from database to check if the user has the correct credentials
	 */
	public Seed(long targetSeed) {
		hashValue = targetSeed;
	}

	public Seed() {
		
		//Random numbers used to seed other random number generators to make it more random
		SecureRandom random = new SecureRandom();
		SecureRandom random2 = new SecureRandom();
		SecureRandom random3 = new SecureRandom();
		random2.setSeed(random3.nextLong());
		random.setSeed(random2.nextLong());
		random3.setSeed(random.nextLong());
		
		long test = random.nextInt(4) + random2.nextInt(4) + random3.nextInt(4);
		if(test == 0)
			hashValue *= 10;
		else
			hashValue += test;
		//Iterates through powers of 10 in order to build an 18 digit number
		for (int i = 1; i < 18; i++) {
			test = (long) (((Math.pow(10, i)) * random.nextInt(4)) + ((Math.pow(10, i)) * random2.nextInt(4)) + ((Math.pow(10, i)) * random3.nextInt(4)));
			if(test == 0)
				hashValue *= 10;
			else
				hashValue += test;
		}
	}
}
