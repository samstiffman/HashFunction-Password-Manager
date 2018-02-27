import java.security.SecureRandom;

public class Seed {
	public long hashValue;

	public Seed(long l) {
		this.hashValue = l;
	}

	public Seed() {
		SecureRandom random = new SecureRandom();
		SecureRandom random2 = new SecureRandom();
		SecureRandom random3 = new SecureRandom();
		random2.setSeed(random3.nextLong());
		random.setSeed(random2.nextLong());
		this.hashValue += random.nextInt(4) + random2.nextInt(4) + random3.nextInt(4);
		for (int i = 1; i < 18; i++) {
			this.hashValue += (Math.pow(10, i)) * random.nextInt(4);
			this.hashValue += (Math.pow(10, i)) * random2.nextInt(4);
			this.hashValue += (Math.pow(10, i)) * random3.nextInt(4);
		}
	}
}
