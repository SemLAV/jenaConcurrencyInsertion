package com.jenaConcurrencyInsertion.threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jenaConcurrencyInsertion.singleton.GlobalModel;
import com.jenaConcurrencyInsertion.utils.WriteFile;

public class LockLogThread implements Runnable {

	@Override
	public void run() {
		
		while (true)
			writeModelSize();
	}

	private void writeModelSize() {
		//DateFormat dateFormat_ = new SimpleDateFormat("HH:mm:ss");
		
		long modelSize = GlobalModel.getInstance().size();
		
		//WriteFile.write(GlobalModel.nbLockWrite+ "\t" + GlobalModel.avgLockWrite + "\t" + GlobalModel.nbLockRead + "\t" + GlobalModel.avgLockRead + "\n");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
