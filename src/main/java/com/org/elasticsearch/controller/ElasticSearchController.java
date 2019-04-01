package com.org.elasticsearch.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.org.elasticsearch.dao.Record;
import com.org.elasticsearch.service.ElasticSearchService;

// TODO: Auto-generated Javadoc
/**
 * The Class ElasticSearchController.
 */
@Controller
public class ElasticSearchController {
	
	/** The elastic search service. */
	@Autowired
	ElasticSearchService elasticSearchService;

	/**
	 * First page.
	 *
	 * @return the model and view
	 */
	@RequestMapping("/elasticSearch")
	public ModelAndView firstPage() {
		return new ModelAndView("Welcome");
	}
	

	/**
	 * Gets the names.
	 *
	 * @param query the query
	 * @return the names
	 */
	@RequestMapping(value = "/users/names")
	@ResponseBody
	public List<String> getNames(@RequestParam(value="term", required=false, defaultValue="") String query) {
		
		List<String> names = elasticSearchService.getMatchingNames(query);
	     
	    return names;
	}
	
	/**
	 * Gets the email adresses.
	 *
	 * @param query the query
	 * @return the email adresses
	 */
	@RequestMapping(value = "/users/email")
	@ResponseBody
	public List<String> getEmailAdresses(@RequestParam(value="term", required=false, defaultValue="") String query) {
		
		List<String> emailAddresses = elasticSearchService.getMatchingEmailAdresses(query);
	     
	    return emailAddresses;
	}
	
	/**
	 * Gets the users.
	 *
	 * @param query the query
	 * @return the users
	 */
	@RequestMapping(value = "/matches", produces = "application/json")
	@ResponseBody
	public List<Record> getUsers(@RequestParam(value="q", required=false, defaultValue="") String query) {
		
		List<Record> users = elasticSearchService.getMatchingUsersAndVehicles(query);
		
		//System.out.println("Users: "+users.toString());
	     
	    return users;
	}

}