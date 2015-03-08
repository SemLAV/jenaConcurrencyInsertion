package com.jenaConcurrencyInsertion.singleton;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.LockMRSW;
import com.hp.hpl.jena.shared.LockSRMW;

public class GlobalModel {

	private static Model model_ = null;
	public static long startTime = 0;
	public static long nbLockRead = 0;
	public static double avgLockRead = 0.0;
	public static boolean isLockSRMW = true;
    public static int nbWorkers = 50;
    public static int nbJobs = 100;
    public static int maxNbInsertionsInJobs = 100000000;
    public static int nbInsertionsInJobs = 100000000;

	private GlobalModel() {

	}

	public static Model getInstance() {
		if (model_ == null)
			if(isLockSRMW)
				model_ = ModelFactory.createDefaultModel(new LockSRMW());
			else
		 		model_ = ModelFactory.createDefaultModel(new LockMRSW());

		return model_;
	}

}
