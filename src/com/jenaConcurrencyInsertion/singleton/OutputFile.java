package com.jenaConcurrencyInsertion.singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class OutputFile {

	private static FileOutputStream outputStream_ = null;

	private OutputFile() {

	}

	public static synchronized FileOutputStream getInstance() {
		if (outputStream_ == null) {
            String lockName;
            if(GlobalModel.isLockSRMW)
                lockName = "SRMW";
            else
                lockName = "MRSW";
			File file = new File("src/out-"+lockName+"-pool"+GlobalModel.nbWorkers+".txt");
			try {
				outputStream_ = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return outputStream_;
	}

}
