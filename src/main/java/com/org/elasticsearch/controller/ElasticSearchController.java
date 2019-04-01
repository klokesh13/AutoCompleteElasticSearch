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

@Controller
public class ElasticSearchController {
	
	@Autowired
	ElasticSearchService elasticSearchService;

	@RequestMapping("/elasticSearch")
	public ModelAndView firstPage() {
		return new ModelAndView("Welcome");
	}
	

	@RequestMapping(value = "/users/names")
	@ResponseBody
	public List<String> getNames(@RequestParam(value="term", required=false, defaultValue="") String query) {
		
		List<String> names = elasticSearchService.getMatchingNames(query);
	     
	    return names;
	}
	
	@RequestMapping(value = "/users/email")
	@ResponseBody
	public List<String> getEmailAdresses(@RequestParam(value="term", required=false, defaultValue="") String query) {
		
		List<String> emailAddresses = elasticSearchService.getMatchingEmailAdresses(query);
	     
	    return emailAddresses;
	}
	
	@RequestMapping(value = "/matches", produces = "application/json")
	@ResponseBody
	public List<Record> getUsers(@RequestParam(value="q", required=false, defaultValue="") String query) {
		
		List<Record> users = elasticSearchService.getMatchingUsersAndVehicles(query);
		
		//System.out.println("Users: "+users.toString());
	     
	    return users;
	}

}