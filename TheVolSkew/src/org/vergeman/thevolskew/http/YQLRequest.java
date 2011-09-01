package org.vergeman.thevolskew.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.apphosting.api.ApiProxy.ApiDeadlineExceededException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.vergeman.thevolskew.servlet.VolSkewRequestServlet;


public class YQLRequest {
	private static final Logger log = Logger.getLogger(VolSkewRequestServlet.class.getName());
	ArrayList<URL> urls;
	LinkedList<Future<HTTPResponse>> responses;
	ArrayList<String> results;
	
	
	// takes ArrayList of String URLS to be queried
	public YQLRequest(HashMap<String, Boolean> requests) {

		this.urls = new ArrayList<URL>();
		int i = 0;
		for (String request : requests.keySet()) {
			addRequest(request);
			i++;
		}
	}


	private void addRequest(String request) {
		try {
			this.urls.add(new URL(request));
			log.info("Added url: " + request);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getResults() {
		return this.results;
	}
	
	
	public boolean request() {
		//initialize data structures
        responses = new LinkedList<Future<HTTPResponse>>();
        results = new ArrayList<String>();
        URLFetchService YQL = URLFetchServiceFactory.getURLFetchService();
        boolean success = true;
        int count = 0;
        
        //query and store url async responses
        for (URL url : urls) {
        	log.info("Calling URL");
        	try {
        	Future<HTTPResponse> HTTP_response = YQL.fetchAsync(url);
        	responses.add(HTTP_response);
        	}
        	catch(Exception e) {
        		log.info(e.toString());
        	}
        }

        //check for completion
        /*
         * TODO
         * probably make this better with a listener implementation vs. polling
         * maybe internal task queue propagation (i.e. call another servlet with data)
         */

        Future<HTTPResponse> response;
        while (responses.size() > 0) {
        	
        	for (Iterator<Future<HTTPResponse>> it= responses.iterator(); it.hasNext(); ) {
        		response = it.next();
        		//System.out.println("Checking response");
        		if ( response.isDone()) {
        			try {
 
						results.add( new String( response.get().getContent() ) );
						log.info("response found");
						it.remove();	//pop from iterator so won't be checked
					

        			}catch (ApiDeadlineExceededException e) {
        				success = false;
        				count++;
        			}
        			catch (InterruptedException e) {
						success = false;
						//e.printStackTrace();
						count++;
					} catch (ExecutionException e) {
						success = false;
						//e.printStackTrace();
						count++;
					}
        		}
        	}
        	//break on repeated failures (malformed input, etc)
        	if (count > 3) {
        		return false;
        	}
        }
        return success;
		
	}
	

	
}
