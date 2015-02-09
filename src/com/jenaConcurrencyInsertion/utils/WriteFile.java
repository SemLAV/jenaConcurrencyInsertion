package com.jenaConcurrencyInsertion.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import com.jenaConcurrencyInsertion.singleton.OutputFile;

public class WriteFile {
	
	public static void write(final String message) {
		FileOutputStream fos = OutputFile.getInstance();
    	byte[] contentInBytes = message.getBytes();
    	try {
			fos.write(contentInBytes);
			fos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
