package com.jenaConcurrencyInsertion;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jenaConcurrencyInsertion.singleton.GlobalModel;
import com.jenaConcurrencyInsertion.threadPool.ThreadPool;
import com.jenaConcurrencyInsertion.threads.LockLogThread;
import com.jenaConcurrencyInsertion.threads.QueryThread;
import com.jenaConcurrencyInsertion.utils.WriteFile;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		DateFormat dateFormat_ = new SimpleDateFormat("HH:mm:ss");

		System.out.println(args[0]+ " "+ args[1]);
		// Nb threads
		GlobalModel.nbWorkers = Integer.valueOf(args[0]); // 10 et 20 //

		GlobalModel.isLockSRMW = Boolean.valueOf(args[1]);
		GlobalModel.finishTime = System.currentTimeMillis()+(Integer.valueOf(args[2])*60000);

		WriteFile.write("Number of queries \tDuration since launch\tLock time\n");

		GlobalModel.startTime = System.currentTimeMillis();
		Thread t = new Thread(new QueryThread());
		t.start();
		
		// Display model size each 10 seconds
		//Thread tModel = new Thread(new LockLogThread());
		//tModel.start();

		ThreadPool threadPool = new ThreadPool();
		threadPool.execute();

	}

}
