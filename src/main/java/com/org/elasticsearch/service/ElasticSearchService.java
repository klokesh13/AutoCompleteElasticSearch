package com.org.elasticsearch.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.org.elasticsearch.dao.Record;

@Service
public class ElasticSearchService {
	
	TransportClient client;
	
	public ElasticSearchService() throws UnknownHostException {
    	
    	client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

    }
	
	/**
	 * Returns the names which matches the search text
	 * 
	 * @param searchText
	 * @return
	 */
	public List<String> getMatchingNames(String searchText) {
		
		List<String> names = new ArrayList<String>();
		
		Map<String, List<String>> zipMap = new HashMap<String, List<String>>();
		
		QueryBuilder qb = QueryBuilders.nestedQuery("users", QueryBuilders.boolQuery()           
	             //.must(QueryBuilders.matchQuery("users.firstname", searchText))
	             .must(QueryBuilders.multiMatchQuery(searchText, "users.firstname", "users.lastname"))
	             .must(QueryBuilders.matchQuery("users.accountId", "TORID125"))
	             .must(QueryBuilders.matchQuery("users.systemId", "CFC")),ScoreMode.Total);
	     
	     SearchResponse response1 = client.prepareSearch("search-index6")
	             .setQuery(qb).setSize(10) // Query
	             .execute().actionGet();
	     
	     
	     SearchHits searchHits=response1.getHits();
	     
	     for(SearchHit hit : searchHits){
	         
	         JSONObject root = new JSONObject(hit.getSourceAsString());
	         JSONArray jsonArray = root.getJSONArray("users");
	         // now get the first element:
	         JSONObject firstSport = jsonArray.getJSONObject(0);
	         // and so on
	         String firstName = firstSport.getString("firstname");
	         String lastName = firstSport.getString("lastname");
	         
	         String fullName = lastName+", "+firstName;
	         
	         names.add(fullName);
		
	     }
	     
	     zipMap.put("users", names);

	     
	     return names;
		
	}
	
	/**
	 * Returns the email addresses which matches the search text
	 * 
	 * @param searchText
	 * @return
	 */
	public List<String> getMatchingEmailAdresses(String searchText) {
		
		List<String> emailAddresses = new ArrayList<String>();
		
		Map<String, List<String>> zipMap = new HashMap<String, List<String>>();
		
		QueryBuilder qb = QueryBuilders.nestedQuery("users", QueryBuilders.boolQuery()           
	             .must(QueryBuilders.matchQuery("users.email", searchText))
	             .must(QueryBuilders.matchQuery("users.accountId", "TORID125"))
	             .must(QueryBuilders.matchQuery("users.systemId", "CFC")),ScoreMode.Total);
	     
	     SearchResponse response1 = client.prepareSearch("search-index6")
	             .setQuery(qb).setSize(10) // Query
	             .execute().actionGet();
	     
	     
	     SearchHits searchHits=response1.getHits();
	     
	     for(SearchHit hit : searchHits){
	         
	         JSONObject root = new JSONObject(hit.getSourceAsString());
	         JSONArray jsonArray = root.getJSONArray("users");
	         // now get the first element:
	         JSONObject firstSport = jsonArray.getJSONObject(0);
	         // and so on
	         String email = firstSport.getString("email");
	         
	         emailAddresses.add(email);
		
	     }
	     
	     zipMap.put("users", emailAddresses);

	     
	     return emailAddresses;
		
	}
	
	/**
	 * Returns the email addresses which matches the search text
	 * 
	 * @param searchText
	 * @return
	 */
	public List<Record> getMatchingUsersAndVehicles(String searchText) {
		
		List<Record> users = new ArrayList<Record>();
		
		Map<String, List<Record>> zipMap = new HashMap<String, List<Record>>();
		
		QueryBuilder qbVehicles = QueryBuilders.nestedQuery("vehicles", QueryBuilders.boolQuery()           
				 .must(QueryBuilders.matchQuery("vehicles.vehiclenumber", searchText))
	             //.must(QueryBuilders.matchQuery("users.accountId", "TORID125"))
	             //.must(QueryBuilders.matchQuery("users.systemId", "CFC"))
	             ,ScoreMode.Total);
	     
	    SearchResponse responseVehicles = client.prepareSearch("search-index1")
	             .setQuery(qbVehicles).setSize(5) 
	             .execute().actionGet();
	    
	    SearchHits vehiclesHits = responseVehicles.getHits();
		
		QueryBuilder qbUsers = QueryBuilders.nestedQuery("users", QueryBuilders.boolQuery()           
				 .must(QueryBuilders.multiMatchQuery(searchText, "users.firstname", "users.lastname"))
	             //.must(QueryBuilders.matchQuery("users.accountId", "TORID125"))
	             //.must(QueryBuilders.matchQuery("users.systemId", "CFC"))
	             ,ScoreMode.Total);
	     
		SearchResponse responseUsers = null;
		
		if(vehiclesHits.totalHits >= 5) {
			responseUsers = client.prepareSearch("search-index1")
		             .setQuery(qbUsers).setSize(5) 
		             .execute().actionGet();
		} else {
			int size = 10 - (int)vehiclesHits.totalHits;
			
			responseUsers = client.prepareSearch("search-index1")
		             .setQuery(qbUsers).setSize(size) 
		             .execute().actionGet();
		}
	     
	     
	     
	     SearchHits usersHits = responseUsers.getHits();
	     
	     
	     for(SearchHit userhit : usersHits){
	    	 
	    	 Record record = new Record();
	         
	         JSONObject root = new JSONObject(userhit.getSourceAsString());
	         
	         JSONArray jsonArray = root.getJSONArray("users");
	         JSONObject firstSport = jsonArray.getJSONObject(0);
	         
	         String fullName = firstSport.getString("lastname")+", "+firstSport.getString("firstname");
	         
	         record.setName(fullName);
	         record.setEmail(firstSport.getString("email"));
	         record.setSystemId(firstSport.getString("systemId"));
	         record.setAccountId(firstSport.getString("accountId"));
	         
	         users.add(record);
		
	     }
	     
	     for(SearchHit vehiclehit : vehiclesHits){
	    	 
	    	 Record record = new Record();
	         
	         JSONObject root = new JSONObject(vehiclehit.getSourceAsString());
	         
	         JSONArray jsonArray = root.getJSONArray("vehicles");
	         JSONObject firstSport = jsonArray.getJSONObject(0);
	         
	         record.setVehicleNumber(firstSport.getString("vehiclenumber"));
	         record.setVehicleSystemId(firstSport.getString("systemId"));
	         record.setSystemId(firstSport.getString("accountId"));
	         
	         users.add(record);
		
	     }
	     
	     zipMap.put("users", users);

	     
	     return users;
		
	}

}
