

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RestoreMissingDisk {

	public static void main(String[] args) throws IOException {

		
		FileInputStream disk0 = null;
		FileInputStream disk1 = null;
		FileOutputStream missingDisk2 = null;
		FileInputStream disk3 = null;
		int byteDisk0;
		int byteDisk1;
		int byteDisk3;
		
		try {
			
			disk0 = new FileInputStream("/Users/quochuy/Downloads/raid4/disk0");
			disk1 = new FileInputStream("/Users/quochuy/Downloads/raid4/disk1");
			disk3 = new FileInputStream("/Users/quochuy/Downloads/raid4/disk3");
			
			// Create a file of the missing disk
			File file = new File("/Users/quochuy/Downloads/raid4/missingDisk2");
			missingDisk2 = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			  }
			
			// Calculate missing Data and write it byte by byte
			while ((byteDisk0 = disk0.read()) != -1) {
				byteDisk1 = disk1.read();
				byteDisk3 = disk3.read();
						
				String bitsInDisk0 = Integer.toBinaryString(byteDisk0);
				String bitsInDisk1 = Integer.toBinaryString(byteDisk1);
				String bitsInDisk3 = Integer.toBinaryString(byteDisk3);
				
				// Calculate a byte of missing disk
				int misingByteFromDisk2 = getByteMissingDisk(bitsInDisk0, bitsInDisk1, bitsInDisk3);
				// Write calculated byte in the file of missing disk
				missingDisk2.write(misingByteFromDisk2);
			}
			
		} finally {
			if(disk0 != null) {
				disk0.close();
				disk1.close();
				disk3.close();
			}
			if(missingDisk2 != null) {
				missingDisk2.close();
				System.out.println("Missing Disk has been calculated and saved");

			}
			
		}
		
	}

	
	//Calculate the byte of the missing disk from 3 bytes from 3 different disks, calculate the byte of the missing disk
	private static int getByteMissingDisk(String bitsInDisk0, String bitsInDisk1, String bitsInDisk3) {
		int[] bits = new int[8];
		
		while (bitsInDisk0.length() <= 7) {
			bitsInDisk0 = addNull(bitsInDisk0);
		}
		
		while (bitsInDisk1.length() <= 7) {
			bitsInDisk1 = addNull(bitsInDisk1);
		}
		
		while (bitsInDisk3.length() <= 7) {
			bitsInDisk3 = addNull(bitsInDisk3);
		}
		
		int bitDisk0InInt;
		int bitDisk1InInt;
		int bitDisk3InInt;
		for(int i = 0; i <= 7; i++) {
			
			bitDisk0InInt = convertCharToInt(bitsInDisk0.charAt(i));
			bitDisk1InInt = convertCharToInt(bitsInDisk1.charAt(i));	
			bitDisk3InInt = convertCharToInt(bitsInDisk3.charAt(i));	
			
			int mod = (bitDisk0InInt + bitDisk1InInt) % 2;
			if(mod == bitDisk3InInt) {
				bits[i] = 0;
			}
			else {
				bits[i] = 1;
			}
		}
		int resultByte = convertBitsToByte(bits);
		return resultByte;
	}
	
	
	//convert char "0" and "1" to 0 and 1 in integer
	private static int convertCharToInt(char charAt) {
		if(charAt == 48) {	
			return 0;
		}else {
			return 1;
		}
	}

	//Convert 8 bits in an Array to a Byte in integer
	// eg: {0,0,1,1,0,0,1,1} to 51
	private static int convertBitsToByte(int[] bits) {
		String bitInString = "";
		for(int i : bits) {
			bitInString = bitInString + i;
		}
		return Integer.parseInt(bitInString, 2);
	}

	// add "0" to Byte, which has fewer than 8 bits
	// eg: "101" to "00000101"
	private static String addNull(String bitDisk) {
		bitDisk = "0" + bitDisk;
		return bitDisk;
	}

}
