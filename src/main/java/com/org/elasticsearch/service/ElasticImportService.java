package com.org.elasticsearch.service;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class ElasticSearchImportController.
 */

@Service
public class ElasticImportService {
	
	/** The client. */
	TransportClient client;
	
	/** The xlsx file path. */
	@Value("${xlsx.file.path}")
    private String XLSX_FILE_PATH;
	
	/** The host name. */
	@Value("${elasticsearch.host}")
    private String hostName;
	
	/**
	 * Instantiates a new elastic search import controller.
	 *
	 * @throws UnknownHostException the unknown host exception
	 */
	public ElasticImportService() throws UnknownHostException {
    	
    	client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), 9300));

    }
	

    /**
     * Insert users.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidFormatException the invalid format exception
     */
    public void insertUsers() throws IOException, InvalidFormatException {
    	// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));

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
	

    /**
     * Insert vehicles.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidFormatException the invalid format exception
     */
    public void insertVehicles() throws IOException, InvalidFormatException {
    	// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));

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
	
	

}
