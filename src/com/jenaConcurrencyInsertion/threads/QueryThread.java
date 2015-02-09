package com.jenaConcurrencyInsertion.threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
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

		while (!GlobalModel.poolEnd)
			processQuery();
		processQuery();
		System.out.println("-->endTimeQuery : " + System.currentTimeMillis());
		System.out.println("-->endTimeQuery : Read Fail : " + GlobalModel.readFail);
	}

	private void processQuery() {
		WriteFile.write("\t [" + dateFormat_.format(new Date())
				+ "] Process Query \n");
		query();
		WriteFile
				.write("\t [" + dateFormat_.format(new Date()) + "] End Query \n");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void query() {
		try {
			if(GlobalModel.isLockSRMW)
				model_ = ModelFactory.createDefaultModel(new LockSRMW());
			else
				model_ = ModelFactory.createDefaultModel(new LockMRSW());

			WriteFile.write("\t \t [" + dateFormat_.format(new Date())
					+ "] Lock Read \n");

			String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
					+ "SELECT * " + "WHERE { 'uri_100' ?p ?o}";
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, model_);
			try {
				ResultSet results = qexec.execSelect();
				//ResultSetFormatter.out(System.out, results, query);
				
				WriteFile.write("\t \t \t [" + dateFormat_.format(new Date())
						+ "] nbTriples : " + results.getRowNumber() + " \n");
				
			} finally {
				qexec.close();
			}
		} catch(Exception e) {
			System.out.println("-->readFail : " +(++GlobalModel.readFail) +" : "+ System.currentTimeMillis());
		} finally {
			model_.leaveCriticalSection();
			WriteFile.write("\t \t [" + dateFormat_.format(new Date())
					+ "] Lock Release \n");
		}
	}

}
