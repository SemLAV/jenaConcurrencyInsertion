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


		// Nb threads
		GlobalModel.nbWorkers = 50; // 10 et 50 //


		Thread t = new Thread(new QueryThread());
		t.start();
		
		// Display model size each 10 seconds
		Thread tModel = new Thread(new LockLogThread());
		tModel.start();

		ThreadPool threadPool = new ThreadPool();
		threadPool.execute();

	}

}
