package com.jenaConcurrencyInsertion.singleton;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.LockMRSW;
import com.hp.hpl.jena.shared.LockSRMW;

public class GlobalModel {

	public static boolean isLockSRMW = true;
	private static Model model_ = null;
	public static int readFail = 0;
	public static int writeFail = 0;
	public static boolean poolEnd = false;


	private GlobalModel() {

	}

	public static Model getInstance() {
		if (model_ == null) {
			if(isLockSRMW)
				model_ = ModelFactory.createDefaultModel(new LockSRMW());
			else
				model_ = ModelFactory.createDefaultModel(new LockMRSW());
		}

		return model_;
	}

}
