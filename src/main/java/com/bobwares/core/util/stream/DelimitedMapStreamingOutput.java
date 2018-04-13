package com.bobwares.core.util.stream;

import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class DelimitedMapStreamingOutput<T> extends AbstractDelimitedDataStreamingOutput {

    private List<Map<String, T>> rows;
    private String[] properties;

    public DelimitedMapStreamingOutput(List<Map<String, T>> rows) {
    	this.rows = rows;
    	
    	//determine the properties to write from the first row
    	if (rows != null && !rows.isEmpty()) {
    		this.properties = buildPropertiesArray(rows.get(0).keySet());
    	}
    }
    
    public DelimitedMapStreamingOutput(List<Map<String, T>> rows, List<String> properties) {
    	this.rows = rows;
        this.properties = buildPropertiesArray(properties);
    }
    
    @Override
    protected void writeRows(Writer writer, CsvPreference csvPreference) throws IOException {
        if (rows != null) {
        	@SuppressWarnings("resource")
			CsvMapWriter csvWriter = new CsvMapWriter(writer, csvPreference);
        	
        	if (headers != null) csvWriter.writeHeader(headers);
        	
        	if (headers != null && cellProcessors != null) {
        		for (Map<String, T> row : rows) {
                	csvWriter.write(row, properties, cellProcessors);
                }
        	}
        	else if (headers != null) {
        		for (Map<String, ?> row : rows) {
                	csvWriter.write(row, properties);
                }
        	}
        	
            csvWriter.flush();
        }
    }
    
    private String[] buildPropertiesArray(Collection<String> properties) {
    	return properties != null && !properties.isEmpty() ? properties.toArray(new String[properties.size()]) : null;
    }
    
}