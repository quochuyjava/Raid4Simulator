

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class JoinDataFromDisks {

public static void main(String[] args) throws IOException {

		
		FileInputStream disk0 = null;
		FileInputStream disk1 = null;
		FileOutputStream Photo = null;
		FileInputStream disk2 = null;
		int byteDisk0;
		int byteDisk1;
		int byteDisk2;
		
		try {
			disk0 = new FileInputStream("/Users/quochuy/Downloads/raid4/disk0");
			disk1 = new FileInputStream("/Users/quochuy/Downloads/raid4/disk1");
			disk2 = new FileInputStream("/Users/quochuy/Downloads/raid4/missingDisk2");
			
			File file = new File("/Users/quochuy/Downloads/raid4/Photo.JPEG");
			Photo = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			  }
			
			while ((byteDisk0 = disk0.read()) != -1) {
				for(int i=1; i<= 4096; i++) {		//Write each disk for 4096 bytes
					if(byteDisk0 != -1) {
						Photo.write(byteDisk0); 
						if (i != 4096) {
							byteDisk0 = disk0.read();
						}
					}
				}

				for(int i=1; i <= 4096; i++) {
					byteDisk1 = disk1.read();		//Write each disk for 4096 bytes
					if(byteDisk1 != -1) {
						Photo.write(byteDisk1); 
					}

				}

				for(int i=1; i <= 4096; i++) {
					byteDisk2 = disk2.read();		//Write each disk for 4096 bytes
					if(byteDisk2 != -1) {
						Photo.write(byteDisk2); 
					}
				}
			}
			
		} finally {
			if(disk0 != null) {
				disk0.close();
				disk1.close();
				disk2.close();
			}
			if(Photo != null) {
				Photo.close();
				System.out.println("Joining done");

			}
			
		}
		
	}

}
