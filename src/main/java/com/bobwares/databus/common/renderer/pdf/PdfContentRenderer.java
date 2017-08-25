package com.bobwares.databus.common.renderer.pdf;

import com.bobwares.databus.common.annotation.ContentRendererComponent;
import com.bobwares.databus.common.model.*;
import com.bobwares.databus.common.renderer.ContentRenderer;
import com.bobwares.databus.common.util.PDDocumentStreamingOutput;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ContentRendererComponent
public class PdfContentRenderer implements ContentRenderer {

    @Inject
    protected PDFRendererService pdfReportService;

    @Override
	public Object render(ResourceDefinition resourceDefinition, ResponseObject page,  MultiValueMap<String, String> parameters) throws IOException {
        ResourceConfiguration configuration = resourceDefinition.getResourceConfiguration();
        List<PdfHeader> pdfHeaders = getReportHeaders(configuration, parameters);

        PDDocument pdf = pdfReportService.renderAsPDF(configuration, pdfHeaders, page);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=\"" + configuration.getTitle() + ".pdf\"");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<PDDocumentStreamingOutput>(new PDDocumentStreamingOutput(pdf), headers, HttpStatus.OK);
    }

    protected List<PdfHeader> getReportHeaders(ResourceConfiguration configuration, MultiValueMap<String, String> allFilters) {
        List<PdfHeader> pdfHeaders = new ArrayList<>();

        List<Header> headers = configuration.getHeaders();
        if (headers != null) {
            for (Header header : headers) {

                PdfHeader pdfHeader = new PdfHeader();
                pdfHeader.setTitle(header.getTitle());

                Map<String, String[]> pdfFieldHeaders = new LinkedHashMap<>();
                for (HeaderField headerField : header.getFields()) {
                    ArrayList<String> values = new ArrayList<>();
                    for (Field field : headerField.getProperties()) {
                        List<String> strings = allFilters.get("header-" + field.getProperty());
                        // defense against UI choosing not to send req param with value for configured header
                        // but show the header label with no attached value anyway
                        String value = (strings == null || strings.isEmpty()) ? "" : strings.get(0);
                        values.add(value);
                    }
                    pdfFieldHeaders.put(headerField.getLabel(), values.toArray(new String[values.size()]));
                }
                pdfHeader.setHeaders(pdfFieldHeaders);

                pdfHeaders.add(pdfHeader);
            }
        }

        return pdfHeaders;
    }
}
