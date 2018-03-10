import java.util.Scanner;

public class SAM512 {

	private final static int[] primeArray = new int[] { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
			61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113 };
	static Database database;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter the location of your database for example C:\\users\\yourName\\Desktop\\database.db");
		String location = in.nextLine();

		database = new Database("jdbc:sqlite:" + location);
		String input, userName, password;
		
		do {
			System.out.println();
			System.out.println("Type INSERT to insert password, or CHECK to check password, or STOP to stop");
			input = in.nextLine();
			
			switch (input.toUpperCase()) {
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
				System.exit(0);
			default:
				System.out.println("You did not enter INSERT, CHECK, or STOP");		
			}
		} while (!in.equals("STOP"));
		in.close();
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
		for (Partition p : testPartitions) {
			p.makeBitArray(seed.hashValue);
		}
		HashCode hashed = new HashCode(testPartitions);
		if (!database.checkUser(user)) {
			database.insert(user, seed.hashValue, String.valueOf(hashed.hexCode));
		}
		return hashed;
	}

	/**
	 * Makes the length of the password divisible by 7
	 * @param theString string to be converted in 7 portions
	 * @return a string divisible by 3
	 */
	private static String partitionable(String theString) {
		while (theString.length() % 7 != 0) {
			theString += (char) (primeArray[theString.length()]);
		}
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

		Partition a = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i);
		}
		a.fill(tempArray);

		Partition b = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i + partitionSize);
		}
		b.fill(tempArray);

		Partition c = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i + (2 * partitionSize));
		}
		c.fill(tempArray);

		Partition d = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i + (3 * partitionSize));
		}
		d.fill(tempArray);

		Partition e = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i + (4 * partitionSize));
		}
		e.fill(tempArray);

		Partition f = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i + (5 * partitionSize));
		}
		f.fill(tempArray);

		Partition g = new Partition(partitionSize);
		for (int i = 0; i < partitionSize; i++) {
			tempArray[i] = theString.charAt(i + (6 * partitionSize));
		}
		g.fill(tempArray);

		return new Partition[] { a, b, c, d, e, f, g };
	}

}
