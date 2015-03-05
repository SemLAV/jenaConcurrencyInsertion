package com.jenaConcurrencyInsertion.threadPool;

import com.jenaConcurrencyInsertion.singleton.GlobalModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private ArrayList<Integer> startIndexes_;
	private int nbJobs_;
	private int nbWorkers_;
	private int maxNbInsertionsInJobs_;
	private int nbInsertionsInJobs_;
	
	 public ThreadPool() {
		  nbJobs_ = GlobalModel.nbJobs;
	      nbWorkers_ = GlobalModel.nbWorkers;
	      maxNbInsertionsInJobs_ = GlobalModel.maxNbInsertionsInJobs;
	      nbInsertionsInJobs_ = GlobalModel.nbInsertionsInJobs;
		 
	      startIndexes_ = new ArrayList<Integer>();
	      for (int i = 0; i <= nbJobs_; i++){
	    	  if (i == 0)
	    		  startIndexes_.add(0);
	    	  else
	    		  startIndexes_.add(i * maxNbInsertionsInJobs_);
	      }
	 }
	 
	 public void execute () {
		 
		 ExecutorService executor = Executors.newFixedThreadPool(nbWorkers_);
	     for (int i = 0; i <= startIndexes_.size() - 1; i++) {
	         Runnable worker = new WorkerThread(startIndexes_.get(i), nbInsertionsInJobs_);
	         executor.execute(worker);
	     }
	     executor.shutdown();
	     
	     while (!executor.isTerminated()) {}
	     
	     System.out.println("Finished all threads");
	     System.exit(0);
	        
//	     Model model = GlobalModel.getInstance();
//	     
//	     String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
//	     		+ "SELECT * "
//	     		+ "WHERE { ?s ?p ?o}" ;
//	     Query query = QueryFactory.create(queryString) ;
//	     QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
//	     try {
//	       ResultSet results = qexec.execSelect() ;
//	       ResultSetFormatter.out(System.out, results, query) ;
//	     } finally { qexec.close() ; }
	 }

}
