package com.bobwares.databus.common.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bobwares.core.util.ResourceUtils;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.renderer.pdf.PDFRendererServiceImpl;
import com.bobwares.databus.common.renderer.pdf.PdfHeader;
import com.bobwares.databus.common.util.ConfigurationLoader;
import com.bobwares.test.AbstractDatabaseTest;

public class PDFReportServiceIntegrationTest extends AbstractDatabaseTest {

	@Inject ConfigurationLoader configurationLoader;

    @Before
    public void startup() {
        System.setProperty("test.pdfreport.timestamp", "1427984940000");
    }

    @After
    public void shutdown() {
        System.clearProperty("test.pdfreport.timestamp");
    }

    @Ignore
    @Test
    public void renderCurrentBalanceAsPdf() throws IOException {
    	renderAsPDF("gmf-current-balance");
    }

    @Ignore
    @Test
    public void renderEarnedDividendAsPdf() throws IOException {
    	renderAsPDF("gmf-earned-dividend");
    }

    @Ignore
    @Test
    public void renderEnrollmentAsPdf() throws IOException {
    	renderAsPDF("enrollment");
    }

    @Ignore
    @Test
    public void renderEnrollmentTxnAsPdf() throws IOException {
    	renderAsPDF("enrollment-txn");
    }

    protected void renderAsPDF(String resource) throws IOException {
        PDFRendererServiceImpl pdfSvc = new PDFRendererServiceImpl();
        Configuration mmd = createFields(resource);
        List<PdfHeader> headers = createHeaders();
        ResponseObject mmp = createContent(resource);
        PDDocument doc = pdfSvc.renderAsPDF(mmd, headers, mmp);

        ByteArrayOutputStream pdOut = new ByteArrayOutputStream();
        doc.save(pdOut);
        doc.close();

        byte[] expected = ResourceUtils.loadResourceAsBytes("databus/service/" + resource + "-expected.pdf");
        byte[] actual = pdOut.toByteArray();

        // for debugging if necessary
        //ResourceUtils.saveResource(actual, resource + "-actual.pdf");

        BufferedReader expectedBR = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(expected)));
        BufferedReader actualBR = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(actual)));
        int lineNo = 0;
        while (true) {
            String expectedLine = expectedBR.readLine();
            if (expectedLine.matches("^trailer.*")) {
                break;
            }
            String actualLine = actualBR.readLine();
            ++lineNo;
            assertEquals("Match failed at line: " + lineNo, expectedLine, actualLine);
        }
        expectedBR.close();
        actualBR.close();
    }

    protected Configuration createFields(String resource) {
    	return configurationLoader.loadConfiguration("databus/" + resource + "-test.json", Configuration.class);
    }

    protected ResponseObject createContent(String resource) throws IOException {
        final String splitOnTabRegex = "[\\s&&[^\\t]]*\\t[\\s&&[^\\t]]*";
        InputStream dataIS = ResourceUtils.getResourceStream("databus/service/" + resource + "-testdata.tsv");
        List<Map<String, Object>> rows = new ArrayList<>();
        String[] props = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(dataIS))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (props == null) {
                    props = line.split(splitOnTabRegex);
                    continue;
                }
                String[] rowData = line.split(splitOnTabRegex,-1);
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 0; i < props.length; ++i) {
                    row.put(props[i], rowData[i]);
                }
                rows.add(row);
            }
        }
        PagingObject pagingObject = new PagingObject(1, rows.size(), rows);
        //pagingObject.setItems(rows);
        //pagingObject.pageNum = 1;
        //pagingObject.startIndex = 0;
        //pagingObject.endIndex = rows.size();
        //pagingObject.itemCount = rows.size();
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultsModel(pagingObject);
        return responseObject;
    }

    protected List<PdfHeader> createHeaders() {
        List<PdfHeader> headers = new ArrayList<>();

        PdfHeader pdfheader = new PdfHeader();
        pdfheader.setTitle("Report Description");
        Map<String, String[]> header = new LinkedHashMap<>();
        header.put("BAC", new String[]{"111324"});
        header.put("Dealership Name", new String[]{"JIM BUTLER CHEVROLET, INC."});
        header.put("Dealership Address", new String[] {
            "759 GRAVOIS BLUFFS BLVD",
            "FENTON, MO 630267719"
        });
        pdfheader.setHeaders(header);
        headers.add(pdfheader);

        pdfheader = new PdfHeader();
        header = new LinkedHashMap<>();
        pdfheader.setTitle("Report Filters");
        header.put("Transaction Date From", new String[]{"April 2014"});
        header.put("Transaction Date To", new String[]{"April 2015"});
        pdfheader.setHeaders(header);
        headers.add(pdfheader);

        return headers;
    }
}