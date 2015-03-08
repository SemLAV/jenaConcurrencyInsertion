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
	private DateFormat dateFormat_;

	public WorkerThread(int index, int nbInsertionsInJobs) {
		model_ = GlobalModel.getInstance();
		startIndex_ = index;
		nbInsertionsInJobs_ = nbInsertionsInJobs;
		dateFormat_ = new SimpleDateFormat("HH:mm:ss");
	}

	@Override
	public void run() {
		processInsertion();
	}

	private void processInsertion() {

		

			for (int i = startIndex_; i < (startIndex_ + nbInsertionsInJobs_); i++) {
				try {
					long start = System.currentTimeMillis();
					if(GlobalModel.isLockSRMW)
						model_.enterCriticalSection(LockSRMW.WRITE);
					else
						model_.enterCriticalSection(LockMRSW.WRITE);
					//GlobalModel.avgLockWrite = (GlobalModel.avgLockWrite*GlobalModel.nbLockWrite+System.currentTimeMillis() - start)/++GlobalModel.nbLockWrite;
					//System.out.println(System.currentTimeMillis() - start);
//
					Resource ressource = model_.createResource("uri_" + i);
					ressource.addProperty(FOAF.name, "literal_" + i);
				} finally {
					model_.leaveCriticalSection();
				}
			}
		
	}

}
