package com.bobwares.core.util.stream;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;
import org.supercsv.quote.NormalQuoteMode;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;


public abstract class AbstractDelimitedDataStreamingOutput implements StreamingOutput {

    protected String[] headers;
    protected CellProcessor[] cellProcessors = null;
    protected char delimiter = ',';
    protected char quoteChar = '"';
    protected String lineTerminator = "\n";
    protected boolean alwaysQuote = false;
    

    public AbstractDelimitedDataStreamingOutput() {
    }

    public AbstractDelimitedDataStreamingOutput setDelimiter(char delimiter) {
        this.delimiter = delimiter;
        return this;
    }
    
    public AbstractDelimitedDataStreamingOutput setLineTerminator(String lineTerminator) {
        this.lineTerminator = lineTerminator;
        return this;
    }

    public AbstractDelimitedDataStreamingOutput setQuoteChar(char quoteChar) {
        this.quoteChar = quoteChar;
        return this;
    }

    public AbstractDelimitedDataStreamingOutput setAlwaysQuote(boolean alwaysQuote) {
        this.alwaysQuote = alwaysQuote;
        return this;
    }

    public AbstractDelimitedDataStreamingOutput setHeaders(List<String> headers) {
        this.headers = (headers != null && !headers.isEmpty()) ? headers.toArray(new String[headers.size()]) : null;
        return this;
    }
    
    public AbstractDelimitedDataStreamingOutput setCellProcessors(List<CellProcessor> cellProcessors) {
        this.cellProcessors = (cellProcessors != null && !cellProcessors.isEmpty()) ? cellProcessors.toArray(new CellProcessor[cellProcessors.size()]) : null;
        return this;
    }
    
    @Override
    public void write(OutputStream os) throws IOException {
       	OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);        	
       	writeRows(writer, new CsvPreference.Builder(quoteChar, delimiter, lineTerminator)
   			.useQuoteMode(alwaysQuote ? new AlwaysQuoteMode() : new NormalQuoteMode())
   			.build()
   		);
    }
        	

    protected abstract void writeRows(Writer writer, CsvPreference csvPreference) throws IOException;
    
}