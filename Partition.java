
public class Partition {
	public char[] partition;
	// array of boolean bits
	private boolean[] bitArray;
	public int[] numberArray;
	public int[] seedArray;

	/**
	 * sets partition size
	 * 
	 * @param size
	 *            of partitions
	 */
	public Partition(int size) {
		this.partition = new char[size];
	}

	/**
	 * Fills partitions with characters from array
	 * 
	 * @param arrayOfChars
	 *            array of characters to be but into the partition
	 */
	public void fill(char[] a) {
		for (int i = 0; i < a.length; i++) {
			this.partition[i] = a[i];
		}
	}

	/**
	 * Makes an array of boolean bits based on a seed
	 * @param hashValue Random seed generated in the main method
	 */
	public void makeBitArray(long hashValue) {

		this.bitArray = new boolean[64];
		
		long a;
		for (byte q = 0; q < 8; q++) {
			for (int i = 1; i < getDigits(hashValue); i++) {
				a = hashValue % (10 * i);
				this.bitArray[(int) (Math.abs((a + i + q + 6)) % 64)] = !this.bitArray[(int) (Math.abs((a + i + q)) % 64)];
				this.bitArray[(int) (Math.abs((a - i + q + 3)) % 64)] = !this.bitArray[(int) (Math.abs((a - i + q)) % 64)];
				this.bitArray[(int) (Math.abs((a + i + q - 4)) % 64)] = (this.bitArray[i] == this.bitArray[q])? !this.bitArray[i]: !this.bitArray[q];
			}
			this.numberArray = this.toBitArray();
			this.seedArray = this.toBitArray();
		}

		// Here is where we do our final mods
		int max = 0, min = this.partition[0], range;
		for (char e : this.partition) {
			if ((int) e > max) 
				max = (int) e;
			else if ((int) e < min) 
				min = (int) e;
		}
		range = max - min;
		int length = this.partition.length;
		for (int i = 0; i < this.partition.length; i++) {
			for (byte b = 1; b < 50; b++) {
				this.bitArray[((int) this.partition[i % length] * b) % 64] = this.bitArray[((int) this.partition[i % length] + b) % 64];
				this.bitArray[((int) this.partition[i % length] + min * b) % 64] = !this.bitArray[(((int) this.partition[i % length]) >> 2) % 64];
				this.bitArray[((int) this.partition[i % length] / b) % 64] = !this.bitArray[(((int) this.partition[i % length]) << 4) % 64];
				this.bitArray[((int) this.partition[i % length] + min * range / b) % 64] = !this.bitArray[(((int) this.partition[i % length]) >> b) % 64];
			}

		}
		this.numberArray = this.toBitArray();

	}

	/**
	 * 
	 * @param a integer inputed
	 * @return first digit of int
	 */
	public static int getFirst(int a) {
		while (a != 0) {
			a = a/10;
			if(a/10 == 0)
				return a;
		}
		return a;
	}

	/**
	 * 
	 * @param integer inputed
	 * @return amount of digits in integer
	 */
	public static long getDigits(long hashValue) {
		long count = 0;
		while (hashValue != 0) {
			hashValue = hashValue / 10;
			count++;
			if (hashValue / 10 == 0) 
				return count;
		}
		return count;
	}

	public int[] toBitArray() {
		int[] numberArray = new int[64];
		for (int i = 0; i < this.bitArray.length; i++) 
			numberArray[i] = (this.bitArray[i]) ? 1 : 0;
		return numberArray;
	}
}
