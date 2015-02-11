package com.jenaConcurrencyInsertion.singleton;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.LockMRSW;
import com.hp.hpl.jena.shared.LockSRMW;

public class GlobalModel {

	private static Model model_ = null;

	private GlobalModel() {

	}

	public static Model getInstance() {
		if (model_ == null)
			model_ = ModelFactory.createDefaultModel(new LockSRMW());
		// model_ = ModelFactory.createDefaultModel(new LockMRSW());

		return model_;
	}

}
