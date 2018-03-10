
public class Partition {
	public char[] partition;
	// array of boolean bits
	private boolean[] bitArray;
	public int[] numberArray;
	public int[] seedArray;

	/**
	 * sets partition size
	 * 
	 * @param size of partitions
	 */
	public Partition(int size) {
		partition = new char[size];
	}

	/**
	 * Fills partitions with characters from array
	 * 
	 * @param arrayOfChars array of characters to be but into the partition ae the portion of the String
	 */
	public void fill(char[] a) {
		for (int i = 0; i < a.length; i++) {
			partition[i] = a[i];
		}
	}

	/**
	 * Makes an array of boolean bits based on a seed
	 * @param hashValue Random seed generated in the main method
	 */
	public void makeBitArray(long hashValue) {

		bitArray = new boolean[64];
		
		long a;
		for (int q = 0; q < 8; q++) {
			for (int i = 1; i < 18; i++) {
				a = hashValue % (10 * i);
				bitArray[(int) (Math.abs((a + i + q + 6)) % 64)] = !bitArray[(int) (Math.abs((a + i + q)) % 64)];
				bitArray[(int) (Math.abs((a - i + q + 3)) % 64)] = !bitArray[(int) (Math.abs((a - i + q)) % 64)];
				bitArray[(int) (Math.abs((a + i + q - 4)) % 64)] = (bitArray[i] == bitArray[q])? !bitArray[i]: !bitArray[q];
			}
			numberArray = toBitArray();
			seedArray = toBitArray();
		}

		// Here is where we do our final mods
		int max = 0, min = partition[0], range;
		for (char e : partition) {
			if ((int) e > max) 
				max = (int) e;
			else if ((int) e < min) 
				min = (int) e;
		}
		range = max - min;
		int length = partition.length;
		for (int i = 0; i < partition.length; i++) {
			for (int b = 1; b < 50; b++) {
				bitArray[((int) partition[i % length] * b) % 64] = bitArray[((int) partition[i % length] + b) % 64];
				bitArray[((int) partition[i % length] + min * b) % 64] = !bitArray[(((int) partition[i % length]) >> 2) % 64];
				bitArray[((int) partition[i % length] / b) % 64] = !bitArray[(((int) partition[i % length]) << 4) % 64];
				bitArray[((int) partition[i % length] + min * range / b) % 64] = !bitArray[(((int) partition[i % length]) >> b) % 64];
			}

		}
		numberArray = toBitArray();

	}

	/**
	 * Method for converting the array of boolean values into an array of 0's and 1's
 	 * @return the array of 0's and 1's
 	 */
	public int[] toBitArray() {
		int[] numberArray = new int[64];
		for (int i = 0; i < bitArray.length; i++) 
			numberArray[i] = (bitArray[i]) ? 1 : 0;
		return numberArray;
	}
}
