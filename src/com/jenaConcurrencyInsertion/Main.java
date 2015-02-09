package com.jenaConcurrencyInsertion;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jenaConcurrencyInsertion.singleton.GlobalModel;
import com.jenaConcurrencyInsertion.threadPool.ThreadPool;
import com.jenaConcurrencyInsertion.threads.ModelSizeThread;
import com.jenaConcurrencyInsertion.threads.QueryThread;
import com.jenaConcurrencyInsertion.utils.WriteFile;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		DateFormat dateFormat_ = new SimpleDateFormat("HH:mm:ss");

		WriteFile.write("[" + dateFormat_.format(new Date())+ "] Start Process \n");

		// Nb views
		int nbJobs = 100;
		// Nb threads
		int nbWorkers = 5;
		// Size of the views
		int maxNbInsertionsInJobs = 100000000;
		int nbInsertionsInJobs = 100000000;

		// Use LockSRMW
		GlobalModel.isLockSRMW = true;

		System.out.println("-->startTime : " + System.currentTimeMillis());

		Thread t = new Thread(new QueryThread());
		t.start();
		
		// Display model size each 10 seconds
		Thread tModel = new Thread(new ModelSizeThread());
		tModel.start();

		// Display start and stop for each thread
		boolean verbose = false;

		ThreadPool threadPool = new ThreadPool(nbJobs, nbWorkers,
				maxNbInsertionsInJobs, nbInsertionsInJobs, verbose);
		threadPool.execute();

	}

}
