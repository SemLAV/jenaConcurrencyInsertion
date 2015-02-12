package com.jenaConcurrencyInsertion.threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.LockMRSW;
import com.hp.hpl.jena.shared.LockSRMW;
import com.jenaConcurrencyInsertion.singleton.GlobalModel;
import com.jenaConcurrencyInsertion.utils.WriteFile;

public class QueryThread implements Runnable {

	private Model model_;
	private DateFormat dateFormat_;

	public QueryThread() {
		model_ = GlobalModel.getInstance();
		dateFormat_ = new SimpleDateFormat("HH:mm:ss");
	}

	@Override
	public void run() {
		// First time we wait 5 seconds
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true)
			processQuery();
	}

	private void processQuery() {
		query();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void query() {
		try {
			long start = System.currentTimeMillis();
			if(GlobalModel.isLockSRMW)
				model_.enterCriticalSection(LockSRMW.READ);
			else
				model_.enterCriticalSection(LockMRSW.READ);

			GlobalModel.avgLockRead = (GlobalModel.avgLockRead*GlobalModel.nbLockRead+System.currentTimeMillis() - start)/++GlobalModel.nbLockRead;


			String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
					+ "SELECT * " + "WHERE { 'uri_100' ?p ?o}";
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, model_);
			try {
				ResultSet results = qexec.execSelect();
				//ResultSetFormatter.out(System.out, results, query);
				
			} finally {
				qexec.close();
			}
		} finally {
			model_.leaveCriticalSection();
		}
	}

}
