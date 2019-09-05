package com.k8s.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrClient {

	public static void main(String[] args) {
		String urlString = "http://c0018681.test.cloud.fedex.com:8983/solr/k8scollection";
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());
	
		 SolrInputDocument document = new SolrInputDocument();
		 
		 document.addField("id", "123456"); document.addField("name","Kenmore Dishwasher"); document.addField("price", "599.99");
		 

		try {
			solr.add(document);
			solr.commit();

			
			 solr.addBean( new ProductBean("888", "Apple iPhone 6s", "299.99") );
			 solr.commit();
			 
			 SolrQuery query = new SolrQuery();
			 query.set("q", "price:599.99");
			 QueryResponse response = solr.query(query);
			  
			 SolrDocumentList docList = response.getResults();
			 System.out.println("Result Size:::::: " + docList.size()); 
			 
			 for (SolrDocument doc : docList) {
				 System.out.println(doc.toString());
			 }
			 
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
