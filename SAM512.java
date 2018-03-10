import java.util.Scanner;

public class SAM512 {

	private final static int[] primeArray = { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
			61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113 };
	static Database database;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter the location of your database for example C:\\users\\yourName\\Desktop\\database.db");
		String location = in.nextLine();

		database = new Database("jdbc:sqlite:" + location);
		String input, userName, password;
		
		while(true) {
			System.out.println();
			System.out.println("Type INSERT to insert password, or CHECK to check password, or STOP to stop");
			input = in.nextLine().toUpperCase();
			
			switch (input) {
			case "INSERT":
				System.out.println("Please enter your username");
				userName = in.nextLine();
				if (database.checkUser(userName)) {
					System.out.println("Sorry but that username already exists");
					continue;
				}
				
				System.out.println("Please enter your password");
				password = in.nextLine();
				hash(userName, password, new Seed());
				break;
			case "CHECK":
				System.out.println("Please enter your username");
				userName = in.nextLine();
				if (!database.checkUser(userName)) {
					System.out.println("Sorry but that username does not exist");
					continue;
				}
				System.out.println("Please enter your password");
				password = in.nextLine();
				if (testHash(userName, password)) { 
					System.out.println("You used the correct password");
					continue;
				}
				System.out.println("Sorry that was the incorrect password");
				break;
			case "STOP":
				in.close();
				System.exit(0);
			default:
				System.out.println("You did not enter INSERT, CHECK, or STOP");		
			}
		}
	}

	private static boolean testHash(String user, String s) {
		return String.valueOf(hash(user, s, new Seed(database.getSeed(user))).hexCode).equals(database.getHash(user));
	}

	/**
	 * Method that hashes the password and inserts it into the database
	 * @param user userName to be stored in database
	 * @param password to be hashed
	 * @param seed used to avoid collisions
	 * @return The final hashed value of the string
	 */
	private static HashCode hash(String user, String password, Seed seed) {

		Partition[] testPartitions = partition(password);
		for (Partition p : testPartitions) 
			p.makeBitArray(seed.hashValue);
		
		HashCode hashed = new HashCode(testPartitions);
		if (!database.checkUser(user)) 
			database.insert(user, seed.hashValue, String.valueOf(hashed.hexCode));
		return hashed;
	}

	/**
	 * Makes the length of the password divisible by 7
	 * @param theString string to be converted in 7 portions
	 * @return a string divisible by 3
	 */
	private static String partitionable(String theString) {
		while (theString.length() % 7 != 0) 
			theString += (char) (primeArray[theString.length()]);
		return theString;
	}

	/**
	 * Separates the String into 7 equally sized pieces which will each be turned
	 * into 64bit bit-strings
	 * 
	 * @param theString String to be separated into pieces
	 * @return The string turned into 7 equally sized pieces
	 */
		private static Partition[] partition(String theString) {

		theString = partitionable(theString);
		int length = theString.length();
		int partitionSize = length / 7;
		char[] tempArray = new char[partitionSize];
		int upperBound = partitionSize;
		int lowerBound = 0;

				Partition a = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		a.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		Partition b = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		b.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		Partition c = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		c.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		Partition d = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		d.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		Partition e = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		e.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		Partition f = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		f.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		Partition g = new Partition(partitionSize);
		for (int i = lowerBound; i < upperBound; i++) 
			tempArray[i-lowerBound] = theString.charAt(i);
		g.fill(tempArray);
		upperBound += partitionSize;
		lowerBound += partitionSize;
		
		return new Partition[] { a, b, c, d, e, f, g };
	}

}
