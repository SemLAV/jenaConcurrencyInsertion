package com.jenaConcurrencyInsertion.threadPool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.LockMRSW;
import com.hp.hpl.jena.shared.LockSRMW;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.jenaConcurrencyInsertion.singleton.GlobalModel;
import com.jenaConcurrencyInsertion.utils.WriteFile;

public class WorkerThread implements Runnable {

	private Model model_;
	private int startIndex_;
	private int nbInsertionsInJobs_;
	private boolean verbose_;
	private DateFormat dateFormat_;

	public WorkerThread(int index, int nbInsertionsInJobs, boolean verbose) {
		model_ = GlobalModel.getInstance();
		startIndex_ = index;
		nbInsertionsInJobs_ = nbInsertionsInJobs;
		verbose_ = verbose;
		dateFormat_ = new SimpleDateFormat("HH:mm:ss");
	}

	@Override
	public void run() {

		if (verbose_)
			WriteFile.write("\t [" + dateFormat_.format(new Date())
					+ "] Start - " + Thread.currentThread().getName() + " \n");

		processInsertion();

		if (verbose_)
			WriteFile.write("\t [" + dateFormat_.format(new Date())
					+ "] End - " + Thread.currentThread().getName() + " \n");
	}

	private void processInsertion() {

		

			for (int i = startIndex_; i < (startIndex_ + nbInsertionsInJobs_); i++) {
				try {
					model_.enterCriticalSection(LockSRMW.WRITE);
//					 model_.enterCriticalSection(LockMRSW.WRITE);
					Resource ressource = model_.createResource("uri_" + i);
					ressource.addProperty(FOAF.name, "literal_" + i);
				} finally {
					model_.leaveCriticalSection();
				}
			}
		
	}

}
