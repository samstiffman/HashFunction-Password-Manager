
public class HashCode {
	public int[] bitString;
	public char[] hexCode;

	public HashCode(Partition[] a) {
		bitString = new int[512];
		hexCode = new char[128];
		for (int i = 0; i < 63; i++) 
			bitString[i] = (int) a[0].seedArray[i];
		
		for (int i = 64; i < 127; i++) 
			bitString[i] = a[0].numberArray[i - 64];
		
		for (int i = 128; i < 191; i++) 
			bitString[i] = a[1].numberArray[i - 128];
		
		for (int i = 192; i < 255; i++) 
			bitString[i] = a[2].numberArray[i - 192];
		
		for (int i = 256; i < 319; i++) 
			bitString[i] = a[3].numberArray[i - 256];
		
		for (int i = 320; i < 383; i++) 
			bitString[i] = a[4].numberArray[i - 320];
		
		for (int i = 384; i < 447; i++) 
			bitString[i] = a[5].numberArray[i - 384];
		
		for (int i = 448; i < 511; i++) 
			bitString[i] = a[6].numberArray[i - 448];
		

		// convert to hex
		int[] temp = new int[4];
		int temporary;
		for (int i = 0; i < 512; i += 4) {
			temporary = 0;
			temp[0] = bitString[i];
			temp[1] = bitString[i + 1];
			temp[2] = bitString[i + 2];
			temp[3] = bitString[i + 3];
			temporary = temp[0]*8 + temp[1]*4 + temp[2]*2 + temp[3];

			switch (temporary) {
			case 0:
				hexCode[i/4] = '0';
				break;
			case 1:
				hexCode[i/4] = '1';
				break;
			case 2:
				hexCode[i/4] = '2';
				break;
			case 3:
				hexCode[i/4] = '3';
				break;
			case 4:
				hexCode[i/4] = '4';
				break;
			case 5:
				hexCode[i/4] = '5';
				break;
			case 6:
				hexCode[i/4] = '6';
				break;
			case 7:
				hexCode[i/4] = '7';
				break;
			case 8:
				hexCode[i/4] = '8';
				break;
			case 9:
				hexCode[i/4] = '9';
				break;
			case 10:
				hexCode[i/4] = 'A';
				break;
			case 11:
				hexCode[i/4] = 'B';
				break;
			case 12:
				hexCode[i/4] = 'C';
				break;
			case 13:
				hexCode[i/4] = 'D';
				break;
			case 14:
				hexCode[i/4] = 'E';
				break;
			case 15:
				hexCode[i/4] = 'F';
				break;
			}
		}
	}
}
