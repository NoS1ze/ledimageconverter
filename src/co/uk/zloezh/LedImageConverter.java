package co.uk.zloezh;

import java.io.FileWriter;

public class LedImageConverter {
	
	
	private long[] hexArray;
	private FileWriter writer;
	private int width;
	private int hight;
	
	public LedImageConverter(FileWriter fileWriter, long[] array, int w, int h){
		hexArray = array;
		writer = fileWriter;
		width = w;
		hight = h;
    }


	public void writeLedString() {
        try {
        	writer.append("Reverced for Matric Layout:"  + System.lineSeparator());
        	
        	boolean reverse = false;  
        	long[] tmpArray = new long[width*hight];
        	int q = (hight*width)+hight -1;
        	for (int i = 0; i < 25; i++) {
        		reverse = !reverse;
        		if(reverse) {
        			q -= (hight-1);
        		}else {
        			q -= (hight+1);
        		}

        		for (int j = 0; j < hight; j++) {
        			if(reverse) {
        				q--;
        			}else {
        				q++;
        			}
        			System.out.println("i: " + i + ",j: "+ j + ",i+j: " + (i+j*width) + " " + String.format("0x%08X", hexArray[i+j*width]) + " Goes to: " + q);
        			tmpArray[q] = hexArray[i+j*width];

        		}

        	}
        	

        	for (int i = 0; i < tmpArray.length; i++) {
        		writer.append(tmpArray[i]+",");
        	}
        	
        	/*
        	
        	long[] newFrameShadow = new long[900];
        	int k =0;
        	for (int i = 0; i < newFrame.length; i++) {
        		//System.out.print(String.format("0x%08X", newFrame[i])+ ",");
        		z ++;
        		if (newFrame[i] != 0) {
        			//System.out.println("");
        			newFrameShadow[k] = i;
        			System.out.println(i);
        			k++;
        		}
        	}
        	
        	for (int i = 0; i < newFrameShadow.length; i++) {
        		//System.out.print(String.format("0x%08X", newFrame[i])+ ",");
        		
        			newFrameShadow[k] = i;
        			System.out.print(newFrameShadow[i] + ",");
        			//k++;

        	} */
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
    
	public static void main(String[] args) {
        try {
        	
        /**	long[] frame = {0xe028d4,0x1bdedb,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0xc4b721,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x44,0x44,0x44,0x44,0x44,0x20,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x44,0x44,0x44,0x44,0x44,0x20,0x00,0x00
        			,0x00,0x68,0xf2,0xf1,0xf1,0xf1,0xf2,0xad,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x44,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xad,0x00,0x00,0x00,0x00,0x00,0x00,0xad,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xa9,0x00,0x00,0x00,0x00,0x44,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xa9,0x00,0x00,0x00,0xad,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0x89,0x00,0x44,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xcd,0xad,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0x00,0x00
        			,0x00,0x68,0xf2,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0x00,0x00
        			,0x00,0x44,0xf1,0xd1,0xd1,0xd1,0xd1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xd1,0xd1,0xd1,0xd1,0xf1,0xcd,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x64,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0xf2,0xcd,0x24,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x44,0xf1,0xf1,0xf1,0xf1,0xf1,0xf1,0x89,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x44,0xf1,0xf1,0xf1,0xf1,0xcd,0x24,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x44,0xf1,0xf1,0xf1,0x89,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x44,0xf6,0xcd,0x24,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x20,0x89,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        			,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
}; **/


//cat1
	//long[] frame = {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x3b4455, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x382f26, 0x866031, 0x3b3937, 0x333d4a, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x32353b, 0xc38843, 0xffb45e, 0xd1914a, 0x846032, 0x313c49, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x614c30, 0xefa963, 0xf4bcbf, 0xf9bcaf, 0xf5ab5e, 0x815e34, 0x2c3440, 0x2c3641, 0x2c3139, 0x39414b, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x2a333e, 0xae7735, 0xfcb36f, 0xf1bbc4, 0xf1bbc3, 0xf4b7a6, 0xf1a755, 0xaf7835, 0xab7b41, 0xb4b2b1, 0x6b6d70, 0x36383c, 0x393f46, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x2b343f, 0xa8763f, 0xfabfb8, 0xeeb7af, 0xf1b08a, 0xf1a752, 0xf2a74d, 0xfbab48, 0xfbaf53, 0xffffff, 0xfaf9f9, 0xeeedec, 0xa6a5a6, 0x515154, 0x2d333b, 0x2c333b, 0x2d333c, 0x2d343d, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x293341, 0xa4733a, 0xfbb57c, 0xf0a656, 0xf0a13c, 0xf1a241, 0xf1a242, 0xf0a241, 0xf4cc98, 0xfefdfa, 0xffffff, 0xffffff, 0xffffff, 0xf5f5f5, 0x999898, 0x9d9a9a, 0x9e9b9b, 0x9a9a9a, 0x3c3f43, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x383636, 0xb8792f, 0xf8a438, 0xf1af5b, 0xf5bd76, 0xfbc57d, 0xfac176, 0xf7d2a1, 0xfffffe, 0xffffff, 0xfefefe, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xffe9e9, 0xffcdce, 0xffeff0, 0xb3b2b1, 0x2d3239, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x695746, 0xf8cf9e, 0xf5ce9c, 0xfaeddb, 0xf1eff1, 0xb7a1a3, 0xbfadae, 0xf9fbfd, 0xfdfeff, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xfdf8f8, 0xeeb6b8, 0xf9d3d5, 0xcccaca, 0x2e333a, 0x000000, 0x000000, 0x000000, 0x000000, 0x45474a, 0xd8d9db, 0xffffff, 0xffffff, 0xfdfeff, 0xae9596, 0x947173, 0x642e2f, 0xa48f90, 0xf6f8f9, 0xffffff, 0xffffff, 0xffffff, 0xf2efef, 0xe1d8d8, 0xf4f2f2, 0xffffff, 0xebd7d7, 0xe1d5d5, 0x5b5d5f, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x3e3f42, 0xf9f8f8, 0xffffff, 0xffffff, 0xffffff, 0x825959, 0x6d3b3c, 0x653031, 0xa18283, 0xfeffff, 0xffffff, 0xffffff, 0xefeaea, 0xb6a1a1, 0x754748, 0x906b6b, 0xf2eeee, 0xffffff, 0xbbbbba, 0x34383f, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x404145, 0xf3f4f3, 0xfffbfb, 0xefb3b3, 0xf1b0b0, 0xc4a9a9, 0x7a4f4f, 0x865e5e, 0xe8e0e0, 0xfaf9f9, 0xaa9091, 0xded5d5, 0xdcd1d1, 0x7e5354, 0x7d5051, 0x632e2f, 0xdacece, 0xf8f9f8, 0x5d5e60, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x404144, 0xf7f8f8, 0xfffbfb, 0xeb8b8b, 0xe98080, 0xfcecec, 0xf6f4f4, 0xefebeb, 0xe8e1e1, 0xffffff, 0xcebfbf, 0xffffff, 0xebe4e5, 0x7f5455, 0x683738, 0x7c5454, 0xefecec, 0xb8b8b8, 0x2e333a, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x3e4043, 0xe2e2e2, 0xf0eeee, 0xe4c2c2, 0xecc8c9, 0xfef9f9, 0xffffff, 0xf7f4f4, 0xb8a3a3, 0xab9091, 0xb9a4a4, 0xaa8e8f, 0xeeeaeb, 0xdedada, 0xc4a8a8, 0xce8e8e, 0xf7c8c8, 0xb2aead, 0x292e36, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x363e48, 0x6b6a6b, 0xdadbda, 0xd3d8d8, 0xdadfdf, 0xf8f9f9, 0xffffff, 0xffffff, 0xfaf8f8, 0xeae2e2, 0xfcfbfb, 0xe6dede, 0xeeeded, 0xfdfefd, 0xe8c3c2, 0xd8c7c7, 0xdacbcb, 0x878688, 0x323a43, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x393e45, 0xb8b8b8, 0xffffff, 0xffffff, 0xeeeeee, 0xe0e0e0, 0xffffff, 0xfefefe, 0xfcfcfc, 0xfeffff, 0xffffff, 0xffffff, 0xffffff, 0xe9e9e9, 0xe0e0e0, 0xffffff, 0xf6f8f8, 0x717174, 0x353d47, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x525253, 0xdfdddd, 0xf0f0f0, 0xf3f3f3, 0xd9d8d8, 0xafaead, 0xc4c2c2, 0xc3c2c1, 0xc2c1c0, 0xc2c0c0, 0xc3c2c1, 0xc7c6c6, 0xc8c7c6, 0xb0aeae, 0xebebeb, 0xf0f0f0, 0xf2f2f2, 0xa7a6a5, 0x35383c, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x41454b, 0x4a4c4f, 0x8a8889, 0x878686, 0x56575a, 0x2b2e34, 0x303338, 0x2f3238, 0x34373c, 0x32353a, 0x303238, 0x2f3238, 0x2f3237, 0x2f3237, 0x7f7f7f, 0x898787, 0x8c8b8b, 0x7b7b7c, 0x515559, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000};
    // cat 2 (bad)
     //   	long[] frame = {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xcfcbca, 0xd9d7d6, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xcbc9c8, 0xe7e5e4, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xcac6c5, 0x382520, 0x5a4c48, 0xe7e6e5, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xb5afad, 0x2d1f1a, 0x877c78, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x4b392f, 0x979695, 0x78706b, 0x6c5d57, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xc3beba, 0x3a261f, 0xa4a7a7, 0x50433f, 0x827673, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xb6afad, 0x60544d, 0x9c9c9c, 0x888480, 0x6e635c, 0xb7aeac, 0xb0a8a5, 0xb1a8a6, 0xb4aba9, 0xb4aba8, 0xafa6a4, 0xb6adab, 0x968c88, 0x5d524d, 0xa4a6a6, 0x6d6764, 0x746965, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x4f3a34, 0x91908e, 0x9fa0a0, 0xa4a6a6, 0x7d7a77, 0x231610, 0x453832, 0x423630, 0x281c16, 0x2d201b, 0x4f433d, 0x2d201a, 0x4a413c, 0xa7a8a8, 0x9d9c9c, 0xa8aaaa, 0x62564f, 0xa59b98, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x533f38, 0x8d8b89, 0x9fa0a0, 0xa2a2a2, 0x7c7c7d, 0x2a2c2c, 0x747575, 0x6d6e6f, 0x2e3030, 0x383a3b, 0x8d8e8f, 0x424344, 0x4e4f50, 0xa1a1a1, 0x9d9d9d, 0xa5a6a6, 0x60534c, 0xada4a1, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x948a87, 0x736a65, 0x9d9d9d, 0x9e9f9f, 0xa0a0a1, 0x969898, 0x777878, 0x8d8d8d, 0x8b8b8b, 0x767676, 0x797979, 0x959595, 0x7d7d7d, 0x868787, 0xa2a3a3, 0x9d9d9d, 0xa2a4a4, 0x8d8a88, 0x746964, 0xd8d5d3, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x55443d, 0x7e7975, 0x605651, 0x9b9c9a, 0x8a8683, 0x43332c, 0x736a66, 0xa4a5a5, 0xa0a1a1, 0xa4a4a4, 0xa3a4a4, 0xa0a1a1, 0x9a9998, 0x4f3f38, 0x584c48, 0xa3a5a5, 0x6c6360, 0x736c6a, 0x544840, 0xbfb8b5, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x5e4e48, 0x837f7b, 0x7a746f, 0x60534b, 0x887b78, 0x2a0c05, 0x5e504b, 0xa4a6a6, 0x9d9d9d, 0x5c4e48, 0x7d7874, 0xa2a3a4, 0x93918f, 0x321911, 0x4e3633, 0x817875, 0x5d504a, 0x939190, 0x5a4d46, 0xbcb5b2, 0x000000, 0x000000, 0x000000, 0x000000, 0x7d6e6a, 0x7b7470, 0x989795, 0x574d43, 0x9a8a8a, 0xe89cb4, 0xc3909f, 0x939693, 0xa3a4a4, 0x6d635d, 0x372116, 0x443128, 0x8f8c8a, 0x9ca09e, 0xa29094, 0xe395ae, 0xcb9dac, 0x5f5751, 0x6d635d, 0x9d9d9c, 0x52413c, 0xcdc9c7, 0x000000, 0x000000, 0x000000, 0x6b5854, 0x807a76, 0xa5a6a6, 0x989795, 0x9f9f9e, 0xa69d9f, 0xa5a0a1, 0x9e9f9f, 0x9d9d9d, 0x999897, 0x989794, 0x989694, 0x9c9c9b, 0x9d9e9e, 0xa1a0a0, 0xa79ea1, 0xa39e9f, 0x9a9a98, 0x9a9a98, 0xa5a6a6, 0x3e2823, 0xbeb7b3, 0x000000, 0x000000, 0x000000, 0x6e5c58, 0x6d6763, 0x8f9091, 0x9c9d9d, 0x9d9d9d, 0x9c9d9d, 0x9c9d9d, 0x9d9d9d, 0x9d9d9d, 0x9e9e9e, 0x9e9f9f, 0x9e9f9f, 0x9d9d9d, 0x9d9d9d, 0x9d9d9d, 0x9c9d9d, 0x9c9d9d, 0x9f9f9f, 0x959596, 0x898a8a, 0x3a251f, 0xc0b9b5, 0x000000, 0x000000, 0x000000, 0x6d5b57, 0x26201d, 0x3a3b3b, 0x939393, 0x9e9e9e, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0x9c9c9c, 0xa4a3a3, 0x747474, 0x3a3b3a, 0x35231c, 0xc1bab7, 0x000000, 0x000000, 0x000000, 0x766661, 0x4b4543, 0x636363, 0x9c9c9c, 0xa2a2a2, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa1a1a1, 0xa5a5a5, 0x888888, 0x666666, 0x493b35, 0xc0bab8, 0x000000};
	//cat 3
        	
        	long[] frame = {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x493738, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x605652, 0xcf99ab, 0x5c4344, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x837978, 0xe0a5ba, 0xd89daf, 0x584243, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x312e2e, 0x8a7f82, 0xdc9fb3, 0xe2a2b8, 0xd297aa, 0x493736, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x5e605d, 0x989595, 0xb995a1, 0xb898a2, 0xbd99a4, 0xa8848e, 0x31322e, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x463944, 0x525251, 0x8a8e8c, 0x8a908e, 0x888f8c, 0x868e8b, 0x8a8f8e, 0x6e716f, 0x2e2c29, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x564e54, 0x4f4f4d, 0x5a5a5a, 0x6b6e6c, 0x8f9190, 0x939594, 0x949594, 0x979998, 0x9a9d9d, 0x585858, 0x3c373c, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x524e52, 0x7d7f7c, 0x878888, 0x5e6160, 0x575a58, 0x6a6c6b, 0x949595, 0x747474, 0x6b6c6c, 0x7c7d7c, 0x909090, 0x808280, 0x393735, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x433742, 0x4a4d47, 0x808282, 0x989998, 0x7c7f7d, 0x6f7170, 0x787b79, 0x939494, 0x848484, 0x2d292a, 0x413d3d, 0x989897, 0x8e8f8f, 0xadadaf, 0x55514e, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x353131, 0x5a5b58, 0x505050, 0x565656, 0x5d6160, 0x7d807f, 0x949795, 0x909391, 0x919493, 0x909593, 0x7f7f7f, 0x2f2929, 0x413e3c, 0x909190, 0xb8b8b8, 0xf1f2f2, 0xb0aead, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x3f3639, 0x625452, 0x807474, 0x8c8083, 0x9e9a9a, 0x909593, 0x6a6e6c, 0x575a58, 0x6f7170, 0x939594, 0x8e908f, 0x8f9190, 0x8c8f8e, 0x808082, 0x868283, 0x8c8b8a, 0xc0c0be, 0xeaeaea, 0xf1f2f2, 0xa7a4a3, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x4c3839, 0xd19cae, 0xdea3b7, 0xdfa2b7, 0xbe98a3, 0x8f9593, 0x8c8f8e, 0x6b6e6c, 0x7c7f7d, 0x939594, 0x8e908f, 0x959997, 0xa8a9a9, 0xdadada, 0xededed, 0xeaeaea, 0xebebeb, 0xeaeaea, 0xf2f2f2, 0xb0aead, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x513f3e, 0xd393a5, 0xe5a1b9, 0xbb98a2, 0x8b938f, 0x8c8f8e, 0x909090, 0x959897, 0x939794, 0x888c8a, 0xa4a5a4, 0xf1f1f1, 0xc4c3c2, 0xdddcdc, 0xebebeb, 0xeaeaea, 0xeaeaea, 0xdfe0e0, 0x676561, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x453131, 0xcf93a7, 0xc19ca8, 0x8b9390, 0x8f908f, 0x828282, 0x454545, 0x3d3d3a, 0x878684, 0xe0e0e0, 0xb9b8b8, 0x333330, 0xd2d2d1, 0xececec, 0xeaeaea, 0xebebeb, 0xbab8b8, 0x3f333d, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x523d3d, 0xb28c97, 0x8e9190, 0x8c8e8c, 0x9d9e9e, 0x7b7878, 0x1e1919, 0x878383, 0xeaebeb, 0xdcdcdc, 0xcacac9, 0xe4e4e4, 0xe8e9e8, 0xe9eaea, 0xeaeaeb, 0x5a5655, 0x2c2f2e, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x34322f, 0x747575, 0x989898, 0x868686, 0x484747, 0x3d3b39, 0x868483, 0xe6e6e7, 0xececec, 0xececec, 0xe7e7e7, 0xe8e8e8, 0xf0f1f1, 0xcfc9c8, 0x417470, 0x2e7d7b, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x2e2928, 0x515051, 0x8c8c8c, 0x959795, 0x8f8f8f, 0xbdbdbd, 0xe8e8e8, 0xeaeaea, 0xe7e7e7, 0xe6e8e8, 0xf0f0f0, 0xc3bcbb, 0x3d6f6f, 0x3ba7af, 0x294c49, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x3d373c, 0x808280, 0x939494, 0xc3c3c3, 0xebebeb, 0xebebeb, 0xececec, 0xeaeaea, 0xeaeaea, 0xc8c3c4, 0x3c6366, 0x31abb2, 0x41666a, 0x746c6c, 0x433c3d, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x4a444a, 0x423c3f, 0x564a57, 0x000000, 0x000000, 0x000000, 0x343330, 0xaeaeb0, 0xf2f3f3, 0xefeff0, 0xf1f1f2, 0xe7e7e8, 0xcac9c8, 0x4a4242, 0x324747, 0x396e6e, 0x384645, 0x6a6e6a, 0x9a9d9d, 0x504e4d, 0x000000, 0x000000, 0x000000, 0x000000, 0x433f3e, 0x888c8c, 0x5e6262, 0x515454, 0x322f2e, 0x000000, 0x000000, 0x000000, 0x54504e, 0xaea9aa, 0x9f9c9c, 0xa9a4a3, 0x605d5b, 0x4c4a48, 0x716b6b, 0x656763, 0xb6b6b3, 0xc7c5c5, 0xa5a8a7, 0x8f9191, 0x4d4c4a, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x454441, 0x3a3e3c, 0x636867, 0x6f7370, 0x37342e, 0x000000, 0x000000, 0x000000, 0x3c3c39, 0x4f504e, 0x5a5858, 0x8a8a88, 0xd1d1d1, 0xf1f2f2, 0xf2f1f1, 0xf0efee, 0xededed, 0xe6e5e6, 0xb7b8b8, 0x4c4947, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x443b44, 0x454342, 0x9d9c9d, 0x6b6b6e, 0x2e2a26, 0x000000, 0x443c45, 0x6e6f6b, 0xa4a8a4, 0xdedfdf, 0xf4f4f4, 0xe9e9e9, 0xe8e8e8, 0xeaeae9, 0xe8e8e8, 0xeaeaea, 0xebebeb, 0xefefef, 0x757371, 0x201b19, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x413a3c, 0x70706e, 0x626565, 0x585a5a, 0x333230, 0x3a3c38, 0x585755, 0xd6d6d6, 0xeeefef, 0xf0efef, 0x8a8886, 0xd8d9d8, 0xbbb9b6, 0x959491, 0xf0f1f1, 0xeaeaea, 0xe8e8e8, 0x686663, 0x383939, 0x433f44, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x3c3a37, 0x6f7170, 0x7f7f7f, 0x3b3a3a, 0x545655, 0x32302e, 0xcdcdcc, 0xededed, 0xefeeed, 0x74736f, 0xd4d4d3, 0xb0ada9, 0x807d7c, 0xeff0f0, 0xeeeeee, 0xa9a8a8, 0x302e2d, 0x515454, 0x464146, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x2f2b27, 0x777575, 0x8b8c8b, 0x3d3c3a, 0x8a8f8b, 0x484848, 0xcac9c9, 0xeeeeef, 0xefeeed, 0x787774, 0xd5d5d5, 0xb2b0ad, 0x84827f, 0xeff0f0, 0xf1f1f1, 0xa5a5a5, 0x575858, 0x7f837f, 0x473f44, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x5d5b5b, 0x7d7f7b, 0x413f3c, 0x8c908e, 0x7f807d, 0x656362, 0xdedfdd, 0xf8f8f7, 0x7f7d7b, 0xdddddd, 0xbab8b5, 0x8b8a87, 0xfafafa, 0xc6c6c5, 0x5b5b5a, 0x878a87, 0x7f827c, 0x544952, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x423b41, 0x2a2824, 0x292724, 0x5b5b55, 0x2c2b28, 0x4a4743, 0xcfcecb, 0x60605c, 0xb1b0ad, 0x908c8a, 0x686766, 0xcccccc, 0x3b3937, 0x3b3934, 0x4d4948, 0x433841, 0x000000, 0x000000};
        	
        	
        	System.out.println("Frame Size " + frame.length);
        	long[] newFrame = new long[900];
        	int z = 0;
        	for (int i = 0; i < frame.length; i++) {
        		
        		System.out.print(String.format("0x%08X", frame[i])+ ",");
        		z ++;
        		if (z == 36) {
        			System.out.println("");
        			z =0;
        		}
        		}
        	
        	System.out.println("Frame Size " + frame.length);

        	for (int i = 0; i < frame.length; i++) {
        		
        		System.out.print(String.format("0x%08X", frame[i])+ ",");
        		z ++;
        		if (z == 36) {
        		//	System.out.println("");
        			z =0;
        		}
        		}
        	
        	
        	boolean reverse = false;  
        	System.out.println(" ");
        	int q = 900+35;
        	for (int i = 0; i < 25; i++) {
        		reverse = !reverse;
        		if(reverse) {
        			System.out.println("-35");
        			q -= 35;
        		}else {
        			System.out.println("-37");
        			q -= 37;
        		}

        		for (int j = 0; j < 36; j++) {
        			if(reverse) {
        				q--;
        			}else {
        				q++;
        			}
        			System.out.println("i: " + i + ",j: "+ j + ",i+j: " + (i+j*25) + " " + String.format("0x%08X", frame[i+j*25]) + " Goes to: " + q);
        			newFrame[q] = frame[i+j*25];

        		}

        	}
        	
        	System.out.println("New Frame Size " + newFrame.length);
        	z = 0;
        	for (int i = 0; i < newFrame.length; i++) {
        		System.out.print(String.format("0x%08X", newFrame[i])+ ",");
        		z ++;
        		if (z == 36) {
        			System.out.println("");
        			z = 0;
        		}
        	}
        	
        	System.out.println("New Frame Size " + newFrame.length);
        	z = 0;
        	for (int i = 0; i < newFrame.length; i++) {
        		System.out.print( newFrame[i]+ ",");
        		z ++;
        		if (z == 36) {
        		//	System.out.println("");
        			z = 0;
        		}
        	}
        	
        	System.out.println("New Frame Size " + newFrame.length);

        	for (int i = 0; i < newFrame.length; i++) {
        		System.out.print(String.format("0x%08X", newFrame[i])+ ",");
        		z ++;
        		if (z == 25) {
        		//	System.out.println("");
        			z =0;
        		}
        	}
        	
        	long[] newFrameShadow = new long[900];
        	int k =0;
        	for (int i = 0; i < newFrame.length; i++) {
        		//System.out.print(String.format("0x%08X", newFrame[i])+ ",");
        		z ++;
        		if (newFrame[i] != 0) {
        			//System.out.println("");
        			newFrameShadow[k] = i;
        			System.out.println(i);
        			k++;
        		}
        	}
        	
        	for (int i = 0; i < newFrameShadow.length; i++) {
        		//System.out.print(String.format("0x%08X", newFrame[i])+ ",");
        		
        			newFrameShadow[k] = i;
        			System.out.print(newFrameShadow[i] + ",");
        			//k++;

        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}