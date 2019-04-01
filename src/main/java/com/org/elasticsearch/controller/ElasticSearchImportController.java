package com.org.elasticsearch.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ElasticSearchImportController {
	
	public static final String SAMPLE_XLSX_FILE_PATH = "/Users/lkalapa/Documents/Learning/TorisSearchUnique_4000.xlsx";
	
	TransportClient client;
	
	public ElasticSearchImportController() throws UnknownHostException {
    	
    	client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

    }
	
	@GetMapping("/users/insert")
    public void insertUsers() throws IOException, InvalidFormatException {
    	// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }
        
        /*
        ==================================================================
        Iterating over all the rows and columns in a Sheet (Multiple ways)
        ==================================================================
	     */
	
	     // Getting the Sheet at index zero
	     Sheet sheet = workbook.getSheetAt(0);
	
	     // Create a DataFormatter to format and get each cell's value as String
	     DataFormatter dataFormatter = new DataFormatter();

	     System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        
	     for (Row row: sheet) {
	    	 int i=0;
	    	 String[] values = new String[5];
	    	 for(Cell cell: row) {
	    		 String cellValue = dataFormatter.formatCellValue(cell);
	    		 values[i]=cellValue;
	    		 i++;
	             System.out.print(cellValue + "\t");
	         }
	    	
	    	 XContentBuilder contentBuilder = jsonBuilder().startObject();
	         contentBuilder.startArray("users");
	             contentBuilder.startObject();
	             contentBuilder.field("firstname", values[0]);
	             contentBuilder.field("lastname", values[1]);
	             contentBuilder.field("email", values[2]);
	             contentBuilder.field("accountId", values[3]);
	             contentBuilder.field("systemId", values[4]);
	             contentBuilder.endObject();

	         contentBuilder.endArray().endObject();

	         IndexResponse response = client.prepareIndex("search-index1", "search-mapping")
	                 .setSource(contentBuilder)
	                 .get();
	         System.out.println(response.getResult().toString());
	     }
        
        
    }
	
	@GetMapping("/vehicles/insert")
    public void insertVehicles() throws IOException, InvalidFormatException {
    	// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }
        
        /*
        ==================================================================
        Iterating over all the rows and columns in a Sheet (Multiple ways)
        ==================================================================
	     */
	
	     // Getting the Sheet at index zero
	     Sheet sheet = workbook.getSheetAt(0);
	
	     // Create a DataFormatter to format and get each cell's value as String
	     DataFormatter dataFormatter = new DataFormatter();

	     System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        
	     for (Row row: sheet) {
	    	 int i=0;
	    	 String[] values = new String[3];
	    	 for(Cell cell: row) {
	    		 String cellValue = dataFormatter.formatCellValue(cell);
	    		 values[i]=cellValue;
	    		 i++;
	             System.out.print(cellValue + "\t");
	         }
	    	
	    	 XContentBuilder contentBuilder = jsonBuilder().startObject();
	         contentBuilder.startArray("vehicles");
	             contentBuilder.startObject();
	             contentBuilder.field("vehiclenumber", values[0]);
	             contentBuilder.field("accountId", values[1]);
	             contentBuilder.field("systemId", values[2]);
	             contentBuilder.endObject();

	         contentBuilder.endArray().endObject();

	         IndexResponse response = client.prepareIndex("search-index1", "search-mapping")
	                 .setSource(contentBuilder)
	                 .get();
	         System.out.println(response.getResult().toString());
	     }
        
        
    }
	
	@GetMapping("/view")
    public Map<String, Object> view() {
        GetResponse getResponse = client.prepareGet("search-index1", "id", "1").get();
        
        System.out.println(getResponse.getSource());


        return getResponse.getSource();
    }

	@GetMapping("/searchRecords")
    public void viewRecords() {
		
		long startTime = System.currentTimeMillis();
        
        QueryBuilder qb = QueryBuilders.nestedQuery("users", QueryBuilders.boolQuery()           
                .must(QueryBuilders.matchQuery("users.firstname", "z"))
                .must(QueryBuilders.matchQuery("users.accountId", "TORID125"))
                .must(QueryBuilders.matchQuery("users.systemId", "CFC")),ScoreMode.Total);
        
        SearchResponse response = client.prepareSearch("search-index1")
                .setQuery(qb).setSize(10) // Query
                .execute().actionGet();
        
        long diffTime = System.currentTimeMillis() - startTime;
        
        SearchHits searchHits=response.getHits();
        
        int i=0;
        for(SearchHit hit : searchHits){
            
            System.out.println(hit.getSourceAsString());
            i++;
            
            JSONObject root = new JSONObject(hit.getSourceAsString());
            

            JSONArray jsonArray = root.getJSONArray("users");
            // now get the first element:
            JSONObject firstSport = jsonArray.getJSONObject(0);
            // and so on
            String name = firstSport.getString("firstname");
        }
        
        System.out.println("data: "+i);
        
        System.out.println("Number of records which matched: "+response.getHits().totalHits + " with time taking "+diffTime);

    }
	
	

}
