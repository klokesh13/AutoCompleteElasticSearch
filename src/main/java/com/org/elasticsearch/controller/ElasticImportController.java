package com.org.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.elasticsearch.service.ElasticImportService;

// TODO: Auto-generated Javadoc
/**
 * The Class ElasticImportController.
 */
@RestController
public class ElasticImportController {
	
	/** The elastic import service. */
	@Autowired
	ElasticImportService elasticImportService;
	
	/**
	 * Import users.
	 *
	 * @throws Exception the exception
	 */
	@RequestMapping("/elasticSearch/users")
	public void importUsers() throws Exception {
		
		elasticImportService.insertUsers();
		
	}
	
	/**
	 * Import vehicles.
	 *
	 * @throws Exception the exception
	 */
	@RequestMapping("/elasticSearch/vehicles")
	public void importVehicles() throws Exception {
		
		elasticImportService.insertVehicles();
		
	}

}
